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
 * Test PayloadDescriptor class.
 *
 * @author your_name_here
 */
public class PayloadDescriptorTest {

  // ------------------------------------
  // default constructor
  // ------------------------------------
  public PayloadDescriptorTest() {
    subscriber = new PayloadDescriptorSubscriber();
  }

  // test methods
  @Test
  /*
   * Initiate PayloadDescriptorTest.
   */
  public void startTest() throws Throwable {
    try {
      LOGGER.info("**********************************************************");
      LOGGER.info("Beginning test on PayloadDescriptor...");
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

  /** jumpstart the process by instantiating2 PayloadDescriptor */
  protected void jumpStart() throws Throwable {
    LOGGER.info("\n======== create instances to get the ball rolling  ======== ");

    payloadDescriptorId =
        PayloadDescriptorBusinessDelegate.getPayloadDescriptorInstance()
            .createPayloadDescriptor(generateNewCommand())
            .get();

    // ---------------------------------------------
    // set up query subscriptions after the 1st create
    // ---------------------------------------------
    testingStep = "create";
    setUpQuerySubscriptions();

    PayloadDescriptorBusinessDelegate.getPayloadDescriptorInstance()
        .createPayloadDescriptor(generateNewCommand())
        .get();
  }

  /** Set up query subscriptions */
  protected void setUpQuerySubscriptions() throws Throwable {
    LOGGER.info("\n======== Setting Up Query Subscriptions ======== ");

    try {
      subscriber
          .payloadDescriptorSubscribe()
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetAll update received for PayloadDescriptor : "
                          + successValue.getPayloadDescriptorId());
                  if (successValue.getPayloadDescriptorId().equals(payloadDescriptorId)) {
                    if (testingStep.equals("create")) {
                      testingStep = "update";
                      update();
                    } else if (testingStep.equals("delete")) {
                      testingStep = "complete";
                      state(
                          getAll().size() == sizeOfPayloadDescriptorList - 1,
                          "value not deleted from list");
                      LOGGER.info("**********************************************************");
                      LOGGER.info("PayloadDescriptor test completed successfully...");
                      LOGGER.info("**********************************************************\n");
                    }
                  }
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on payloadDescriptor consumed"));
      subscriber
          .payloadDescriptorSubscribe(payloadDescriptorId)
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetOne update received for PayloadDescriptor : "
                          + successValue.getPayloadDescriptorId()
                          + " in step "
                          + testingStep);
                  testingStep = "delete";
                  sizeOfPayloadDescriptorList = getAll().size();
                  delete();
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () ->
                  LOGGER.info(
                      "Subscription on payloadDescriptor for payloadDescriptorId consumed"));

    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
      throw e;
    }
  }

  /** read a PayloadDescriptor. */
  protected PayloadDescriptor read() throws Throwable {
    LOGGER.info("\n======== READ ======== ");
    LOGGER.info("-- Reading a previously created PayloadDescriptor");

    PayloadDescriptor entity = null;
    StringBuilder msg = new StringBuilder("-- Failed to read PayloadDescriptor with primary key");
    msg.append(payloadDescriptorId);

    PayloadDescriptorFetchOneSummary fetchOneSummary =
        new PayloadDescriptorFetchOneSummary(payloadDescriptorId);

    try {
      entity =
          PayloadDescriptorBusinessDelegate.getPayloadDescriptorInstance()
              .getPayloadDescriptor(fetchOneSummary);

      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Successfully found PayloadDescriptor " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(msg.toString() + " : " + e);

      throw e;
    }

    return entity;
  }

  /** updating a PayloadDescriptor. */
  protected void update() throws Throwable {
    LOGGER.info("\n======== UPDATE ======== ");
    LOGGER.info("-- Attempting to update a PayloadDescriptor.");

    StringBuilder msg = new StringBuilder("Failed to update a PayloadDescriptor : ");
    PayloadDescriptor entity = read();
    UpdatePayloadDescriptorCommand command = generateUpdateCommand();
    command.setPayloadDescriptorId(entity.getPayloadDescriptorId());

    try {
      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Now updating the created PayloadDescriptor.");

      // for use later on...
      payloadDescriptorId = entity.getPayloadDescriptorId();

      PayloadDescriptorBusinessDelegate proxy =
          PayloadDescriptorBusinessDelegate.getPayloadDescriptorInstance();

      proxy.updatePayloadDescriptor(command).get();

      LOGGER.info("-- Successfully saved PayloadDescriptor - " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          msg.toString()
              + " : primarykey = "
              + payloadDescriptorId
              + " : command -"
              + command
              + " : "
              + e);

      throw e;
    }
  }

  /** delete a PayloadDescriptor. */
  protected void delete() throws Throwable {
    LOGGER.info("\n======== DELETE ======== ");
    LOGGER.info("-- Deleting a previously created PayloadDescriptor.");

    PayloadDescriptor entity = null;

    try {
      entity = read();
      LOGGER.info("-- Successfully read PayloadDescriptor with id " + payloadDescriptorId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to read PayloadDescriptor with id " + payloadDescriptorId);

      throw e;
    }

    try {
      PayloadDescriptorBusinessDelegate.getPayloadDescriptorInstance()
          .delete(new DeletePayloadDescriptorCommand(entity.getPayloadDescriptorId()))
          .get();
      LOGGER.info("-- Successfully deleted PayloadDescriptor with id " + payloadDescriptorId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to delete PayloadDescriptor with id " + payloadDescriptorId);

      throw e;
    }
  }

  /** get all PayloadDescriptors. */
  protected List<PayloadDescriptor> getAll() throws Throwable {
    LOGGER.info("======== GETALL ======== ");
    LOGGER.info("-- Retrieving Collection of PayloadDescriptors:");

    StringBuilder msg = new StringBuilder("-- Failed to get all PayloadDescriptor : ");
    List<PayloadDescriptor> collection = new ArrayList<>();

    try {
      // call the static get method on the PayloadDescriptorBusinessDelegate
      collection =
          PayloadDescriptorBusinessDelegate.getPayloadDescriptorInstance()
              .getAllPayloadDescriptor();
      assertNotNull(
          collection, "An Empty collection of PayloadDescriptor was incorrectly returned.");

      // Now print out the values
      PayloadDescriptor entity = null;
      Iterator<PayloadDescriptor> iter = collection.iterator();
      int index = 1;

      while (iter.hasNext()) {
        // Retrieve the entity
        entity = iter.next();

        assertNotNull(entity, "-- null entity in Collection.");
        assertNotNull(
            entity.getPayloadDescriptorId(), "-- entity in Collection has a null primary key");

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
   * @return PayloadDescriptorTest
   */
  protected PayloadDescriptorTest setHandler(Handler handler) {
    if (handler != null) LOGGER.addHandler(handler);
    return this;
  }

  /**
   * Returns a new populated PayloadDescriptor
   *
   * @return CreatePayloadDescriptorCommand alias
   */
  protected CreatePayloadDescriptorCommand generateNewCommand() {
    CreatePayloadDescriptorCommand command = new CreatePayloadDescriptorCommand(null);

    return (command);
  }

  /**
   * Returns a new populated PayloadDescriptor
   *
   * @return UpdatePayloadDescriptorCommand alias
   */
  protected UpdatePayloadDescriptorCommand generateUpdateCommand() {
    UpdatePayloadDescriptorCommand command = new UpdatePayloadDescriptorCommand(null);

    return (command);
  }
  // -----------------------------------------------------
  // attributes
  // -----------------------------------------------------
  protected UUID payloadDescriptorId = null;
  protected PayloadDescriptorSubscriber subscriber = null;
  private final String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
  private final Logger LOGGER = Logger.getLogger(PayloadDescriptorTest.class.getName());
  private String testingStep = "";
  private Integer sizeOfPayloadDescriptorList = 0;
}
