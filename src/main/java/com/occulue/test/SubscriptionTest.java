/**
 * ***************************************************************************** Turnstone Biologics
 * Confidential
 *
 * <p>2018 Turnstone Biologics All Rights Reserved.
 *
 * <p>This file is subject to the terms and conditions defined in file 'license.txt', which is part
 * of this source code package.
 *
 * <p>Contributors : Turnstone Biologics - General Release
 * ****************************************************************************
 */
package com.occulue.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.util.Assert.state;

import com.occulue.api.*;
import com.occulue.delegate.*;
import com.occulue.entity.*;
import com.occulue.subscriber.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import org.junit.jupiter.api.Test;

/**
 * Test Subscription class.
 *
 * @author your_name_here
 */
public class SubscriptionTest {

  // ------------------------------------
  // default constructor
  // ------------------------------------
  public SubscriptionTest() {
    subscriber = new SubscriptionSubscriber();
  }

  // test methods
  @Test
  /*
   * Initiate SubscriptionTest.
   */
  public void startTest() throws Throwable {
    try {
      LOGGER.info("**********************************************************");
      LOGGER.info("Beginning test on Subscription...");
      LOGGER.info("**********************************************************\n");

      // ---------------------------------------------
      // jumpstart process
      // ---------------------------------------------
      jumpStart();

    } catch (Throwable e) {
      throw e;
    } finally {
    }
  }

  /** jumpstart the process by instantiating2 Subscription */
  protected void jumpStart() throws Throwable {
    LOGGER.info("\n======== create instances to get the ball rolling  ======== ");

    subscriptionId =
        SubscriptionBusinessDelegate.getSubscriptionInstance()
            .createSubscription(generateNewCommand())
            .get();

    // ---------------------------------------------
    // set up query subscriptions after the 1st create
    // ---------------------------------------------
    testingStep = "create";
    setUpQuerySubscriptions();

    SubscriptionBusinessDelegate.getSubscriptionInstance()
        .createSubscription(generateNewCommand())
        .get();
  }

  /** Set up query subscriptions */
  protected void setUpQuerySubscriptions() throws Throwable {
    LOGGER.info("\n======== Setting Up Query Subscriptions ======== ");

    try {
      subscriber
          .subscriptionSubscribe()
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetAll update received for Subscription : "
                          + successValue.getSubscriptionId());
                  if (successValue.getSubscriptionId().equals(subscriptionId)) {
                    if (testingStep.equals("create")) {
                      testingStep = "update";
                      update();
                    } else if (testingStep.equals("delete")) {
                      testingStep = "complete";
                      state(
                          getAll().size() == sizeOfSubscriptionList - 1,
                          "value not deleted from list");
                      LOGGER.info("**********************************************************");
                      LOGGER.info("Subscription test completed successfully...");
                      LOGGER.info("**********************************************************\n");
                    }
                  }
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on subscription consumed"));
      subscriber
          .subscriptionSubscribe(subscriptionId)
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetOne update received for Subscription : "
                          + successValue.getSubscriptionId()
                          + " in step "
                          + testingStep);
                  testingStep = "delete";
                  sizeOfSubscriptionList = getAll().size();
                  delete();
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on subscription for subscriptionId consumed"));

    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
      throw e;
    }
  }

  /** read a Subscription. */
  protected Subscription read() throws Throwable {
    LOGGER.info("\n======== READ ======== ");
    LOGGER.info("-- Reading a previously created Subscription");

    Subscription entity = null;
    StringBuilder msg = new StringBuilder("-- Failed to read Subscription with primary key");
    msg.append(subscriptionId);

    SubscriptionFetchOneSummary fetchOneSummary = new SubscriptionFetchOneSummary(subscriptionId);

    try {
      entity =
          SubscriptionBusinessDelegate.getSubscriptionInstance().getSubscription(fetchOneSummary);

      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Successfully found Subscription " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(msg.toString() + " : " + e);

      throw e;
    }

    return entity;
  }

  /** updating a Subscription. */
  protected void update() throws Throwable {
    LOGGER.info("\n======== UPDATE ======== ");
    LOGGER.info("-- Attempting to update a Subscription.");

    StringBuilder msg = new StringBuilder("Failed to update a Subscription : ");
    Subscription entity = read();
    UpdateSubscriptionCommand command = generateUpdateCommand();
    command.setSubscriptionId(entity.getSubscriptionId());

    try {
      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Now updating the created Subscription.");

      // for use later on...
      subscriptionId = entity.getSubscriptionId();

      SubscriptionBusinessDelegate proxy = SubscriptionBusinessDelegate.getSubscriptionInstance();

      proxy.updateSubscription(command).get();

      LOGGER.info("-- Successfully saved Subscription - " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          msg.toString()
              + " : primarykey = "
              + subscriptionId
              + " : command -"
              + command
              + " : "
              + e);

      throw e;
    }
  }

  /** delete a Subscription. */
  protected void delete() throws Throwable {
    LOGGER.info("\n======== DELETE ======== ");
    LOGGER.info("-- Deleting a previously created Subscription.");

    Subscription entity = null;

    try {
      entity = read();
      LOGGER.info("-- Successfully read Subscription with id " + subscriptionId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to read Subscription with id " + subscriptionId);

      throw e;
    }

    try {
      SubscriptionBusinessDelegate.getSubscriptionInstance()
          .delete(new DeleteSubscriptionCommand(entity.getSubscriptionId()))
          .get();
      LOGGER.info("-- Successfully deleted Subscription with id " + subscriptionId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to delete Subscription with id " + subscriptionId);

      throw e;
    }
  }

  /** get all Subscriptions. */
  protected List<Subscription> getAll() throws Throwable {
    LOGGER.info("======== GETALL ======== ");
    LOGGER.info("-- Retrieving Collection of Subscriptions:");

    StringBuilder msg = new StringBuilder("-- Failed to get all Subscription : ");
    List<Subscription> collection = new ArrayList<>();

    try {
      // call the static get method on the SubscriptionBusinessDelegate
      collection = SubscriptionBusinessDelegate.getSubscriptionInstance().getAllSubscription();
      assertNotNull(collection, "An Empty collection of Subscription was incorrectly returned.");

      // Now print out the values
      Subscription entity = null;
      Iterator<Subscription> iter = collection.iterator();
      int index = 1;

      while (iter.hasNext()) {
        // Retrieve the entity
        entity = iter.next();

        assertNotNull(entity, "-- null entity in Collection.");
        assertNotNull(entity.getSubscriptionId(), "-- entity in Collection has a null primary key");

        LOGGER.info(" - " + String.valueOf(index) + ". " + entity.toString());
        index++;
      }
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(msg.toString() + " : " + e);

      throw e;
    }

    return collection;
  }

  /**
   * Assigns a common log handler for each test class in the suite in the event log output needs to
   * go elsewhere
   *
   * @param handler Handler
   * @return SubscriptionTest
   */
  protected SubscriptionTest setHandler(Handler handler) {
    if (handler != null) LOGGER.addHandler(handler);
    return this;
  }

  /**
   * Returns a new populated Subscription
   *
   * @return CreateSubscriptionCommand alias
   */
  protected CreateSubscriptionCommand generateNewCommand() {
    CreateSubscriptionCommand command =
        new CreateSubscriptionCommand(
            null,
            new Date(),
            new Date(),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            ObjectType.values()[0]);

    return (command);
  }

  /**
   * Returns a new populated Subscription
   *
   * @return UpdateSubscriptionCommand alias
   */
  protected UpdateSubscriptionCommand generateUpdateCommand() {
    UpdateSubscriptionCommand command =
        new UpdateSubscriptionCommand(
            null,
            new Date(),
            new Date(),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            null,
            new HashSet<>(),
            null,
            ObjectType.values()[0]);

    return (command);
  }
  // -----------------------------------------------------
  // attributes
  // -----------------------------------------------------
  protected UUID subscriptionId = null;
  protected SubscriptionSubscriber subscriber = null;
  private final String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
  private final Logger LOGGER = Logger.getLogger(SubscriptionTest.class.getName());
  private String testingStep = "";
  private Integer sizeOfSubscriptionList = 0;
}
