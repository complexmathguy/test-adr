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
 * Test Notifier class.
 *
 * @author your_name_here
 */
public class NotifierTest {

  // ------------------------------------
  // default constructor
  // ------------------------------------
  public NotifierTest() {
    subscriber = new NotifierSubscriber();
  }

  // test methods
  @Test
  /*
   * Initiate NotifierTest.
   */
  public void startTest() throws Throwable {
    try {
      LOGGER.info("**********************************************************");
      LOGGER.info("Beginning test on Notifier...");
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

  /** jumpstart the process by instantiating2 Notifier */
  protected void jumpStart() throws Throwable {
    LOGGER.info("\n======== create instances to get the ball rolling  ======== ");

    notifierId =
        NotifierBusinessDelegate.getNotifierInstance().createNotifier(generateNewCommand()).get();

    // ---------------------------------------------
    // set up query subscriptions after the 1st create
    // ---------------------------------------------
    testingStep = "create";
    setUpQuerySubscriptions();

    NotifierBusinessDelegate.getNotifierInstance().createNotifier(generateNewCommand()).get();
  }

  /** Set up query subscriptions */
  protected void setUpQuerySubscriptions() throws Throwable {
    LOGGER.info("\n======== Setting Up Query Subscriptions ======== ");

    try {
      subscriber
          .notifierSubscribe()
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetAll update received for Notifier : " + successValue.getNotifierId());
                  if (successValue.getNotifierId().equals(notifierId)) {
                    if (testingStep.equals("create")) {
                      testingStep = "update";
                      update();
                    } else if (testingStep.equals("delete")) {
                      testingStep = "complete";
                      state(
                          getAll().size() == sizeOfNotifierList - 1, "value not deleted from list");
                      LOGGER.info("**********************************************************");
                      LOGGER.info("Notifier test completed successfully...");
                      LOGGER.info("**********************************************************\n");
                    }
                  }
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on notifier consumed"));
      subscriber
          .notifierSubscribe(notifierId)
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetOne update received for Notifier : "
                          + successValue.getNotifierId()
                          + " in step "
                          + testingStep);
                  testingStep = "delete";
                  sizeOfNotifierList = getAll().size();
                  delete();
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on notifier for notifierId consumed"));

    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
      throw e;
    }
  }

  /** read a Notifier. */
  protected Notifier read() throws Throwable {
    LOGGER.info("\n======== READ ======== ");
    LOGGER.info("-- Reading a previously created Notifier");

    Notifier entity = null;
    StringBuilder msg = new StringBuilder("-- Failed to read Notifier with primary key");
    msg.append(notifierId);

    NotifierFetchOneSummary fetchOneSummary = new NotifierFetchOneSummary(notifierId);

    try {
      entity = NotifierBusinessDelegate.getNotifierInstance().getNotifier(fetchOneSummary);

      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Successfully found Notifier " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(msg.toString() + " : " + e);

      throw e;
    }

    return entity;
  }

  /** updating a Notifier. */
  protected void update() throws Throwable {
    LOGGER.info("\n======== UPDATE ======== ");
    LOGGER.info("-- Attempting to update a Notifier.");

    StringBuilder msg = new StringBuilder("Failed to update a Notifier : ");
    Notifier entity = read();
    UpdateNotifierCommand command = generateUpdateCommand();
    command.setNotifierId(entity.getNotifierId());

    try {
      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Now updating the created Notifier.");

      // for use later on...
      notifierId = entity.getNotifierId();

      NotifierBusinessDelegate proxy = NotifierBusinessDelegate.getNotifierInstance();

      proxy.updateNotifier(command).get();

      LOGGER.info("-- Successfully saved Notifier - " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          msg.toString() + " : primarykey = " + notifierId + " : command -" + command + " : " + e);

      throw e;
    }
  }

  /** delete a Notifier. */
  protected void delete() throws Throwable {
    LOGGER.info("\n======== DELETE ======== ");
    LOGGER.info("-- Deleting a previously created Notifier.");

    Notifier entity = null;

    try {
      entity = read();
      LOGGER.info("-- Successfully read Notifier with id " + notifierId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to read Notifier with id " + notifierId);

      throw e;
    }

    try {
      NotifierBusinessDelegate.getNotifierInstance()
          .delete(new DeleteNotifierCommand(entity.getNotifierId()))
          .get();
      LOGGER.info("-- Successfully deleted Notifier with id " + notifierId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to delete Notifier with id " + notifierId);

      throw e;
    }
  }

  /** get all Notifiers. */
  protected List<Notifier> getAll() throws Throwable {
    LOGGER.info("======== GETALL ======== ");
    LOGGER.info("-- Retrieving Collection of Notifiers:");

    StringBuilder msg = new StringBuilder("-- Failed to get all Notifier : ");
    List<Notifier> collection = new ArrayList<>();

    try {
      // call the static get method on the NotifierBusinessDelegate
      collection = NotifierBusinessDelegate.getNotifierInstance().getAllNotifier();
      assertNotNull(collection, "An Empty collection of Notifier was incorrectly returned.");

      // Now print out the values
      Notifier entity = null;
      Iterator<Notifier> iter = collection.iterator();
      int index = 1;

      while (iter.hasNext()) {
        // Retrieve the entity
        entity = iter.next();

        assertNotNull(entity, "-- null entity in Collection.");
        assertNotNull(entity.getNotifierId(), "-- entity in Collection has a null primary key");

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
   * @return NotifierTest
   */
  protected NotifierTest setHandler(Handler handler) {
    if (handler != null) LOGGER.addHandler(handler);
    return this;
  }

  /**
   * Returns a new populated Notifier
   *
   * @return CreateNotifierCommand alias
   */
  protected CreateNotifierCommand generateNewCommand() {
    CreateNotifierCommand command = new CreateNotifierCommand(null);

    return (command);
  }

  /**
   * Returns a new populated Notifier
   *
   * @return UpdateNotifierCommand alias
   */
  protected UpdateNotifierCommand generateUpdateCommand() {
    UpdateNotifierCommand command = new UpdateNotifierCommand(null);

    return (command);
  }
  // -----------------------------------------------------
  // attributes
  // -----------------------------------------------------
  protected UUID notifierId = null;
  protected NotifierSubscriber subscriber = null;
  private final String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
  private final Logger LOGGER = Logger.getLogger(NotifierTest.class.getName());
  private String testingStep = "";
  private Integer sizeOfNotifierList = 0;
}
