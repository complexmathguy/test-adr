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
 * Test ReportPayloadDescriptor class.
 *
 * @author your_name_here
 */
public class ReportPayloadDescriptorTest {

  // ------------------------------------
  // default constructor
  // ------------------------------------
  public ReportPayloadDescriptorTest() {
    subscriber = new ReportPayloadDescriptorSubscriber();
  }

  // test methods
  @Test
  /*
   * Initiate ReportPayloadDescriptorTest.
   */
  public void startTest() throws Throwable {
    try {
      LOGGER.info("**********************************************************");
      LOGGER.info("Beginning test on ReportPayloadDescriptor...");
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

  /** jumpstart the process by instantiating2 ReportPayloadDescriptor */
  protected void jumpStart() throws Throwable {
    LOGGER.info("\n======== create instances to get the ball rolling  ======== ");

    reportPayloadDescriptorId =
        ReportPayloadDescriptorBusinessDelegate.getReportPayloadDescriptorInstance()
            .createReportPayloadDescriptor(generateNewCommand())
            .get();

    // ---------------------------------------------
    // set up query subscriptions after the 1st create
    // ---------------------------------------------
    testingStep = "create";
    setUpQuerySubscriptions();

    ReportPayloadDescriptorBusinessDelegate.getReportPayloadDescriptorInstance()
        .createReportPayloadDescriptor(generateNewCommand())
        .get();
  }

  /** Set up query subscriptions */
  protected void setUpQuerySubscriptions() throws Throwable {
    LOGGER.info("\n======== Setting Up Query Subscriptions ======== ");

    try {
      subscriber
          .reportPayloadDescriptorSubscribe()
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetAll update received for ReportPayloadDescriptor : "
                          + successValue.getReportPayloadDescriptorId());
                  if (successValue
                      .getReportPayloadDescriptorId()
                      .equals(reportPayloadDescriptorId)) {
                    if (testingStep.equals("create")) {
                      testingStep = "update";
                      update();
                    } else if (testingStep.equals("delete")) {
                      testingStep = "complete";
                      state(
                          getAll().size() == sizeOfReportPayloadDescriptorList - 1,
                          "value not deleted from list");
                      LOGGER.info("**********************************************************");
                      LOGGER.info("ReportPayloadDescriptor test completed successfully...");
                      LOGGER.info("**********************************************************\n");
                    }
                  }
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on reportPayloadDescriptor consumed"));
      subscriber
          .reportPayloadDescriptorSubscribe(reportPayloadDescriptorId)
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetOne update received for ReportPayloadDescriptor : "
                          + successValue.getReportPayloadDescriptorId()
                          + " in step "
                          + testingStep);
                  testingStep = "delete";
                  sizeOfReportPayloadDescriptorList = getAll().size();
                  delete();
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () ->
                  LOGGER.info(
                      "Subscription on reportPayloadDescriptor for reportPayloadDescriptorId consumed"));

    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
      throw e;
    }
  }

  /** read a ReportPayloadDescriptor. */
  protected ReportPayloadDescriptor read() throws Throwable {
    LOGGER.info("\n======== READ ======== ");
    LOGGER.info("-- Reading a previously created ReportPayloadDescriptor");

    ReportPayloadDescriptor entity = null;
    StringBuilder msg =
        new StringBuilder("-- Failed to read ReportPayloadDescriptor with primary key");
    msg.append(reportPayloadDescriptorId);

    ReportPayloadDescriptorFetchOneSummary fetchOneSummary =
        new ReportPayloadDescriptorFetchOneSummary(reportPayloadDescriptorId);

    try {
      entity =
          ReportPayloadDescriptorBusinessDelegate.getReportPayloadDescriptorInstance()
              .getReportPayloadDescriptor(fetchOneSummary);

      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Successfully found ReportPayloadDescriptor " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(msg.toString() + " : " + e);

      throw e;
    }

    return entity;
  }

  /** updating a ReportPayloadDescriptor. */
  protected void update() throws Throwable {
    LOGGER.info("\n======== UPDATE ======== ");
    LOGGER.info("-- Attempting to update a ReportPayloadDescriptor.");

    StringBuilder msg = new StringBuilder("Failed to update a ReportPayloadDescriptor : ");
    ReportPayloadDescriptor entity = read();
    UpdateReportPayloadDescriptorCommand command = generateUpdateCommand();
    command.setReportPayloadDescriptorId(entity.getReportPayloadDescriptorId());

    try {
      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Now updating the created ReportPayloadDescriptor.");

      // for use later on...
      reportPayloadDescriptorId = entity.getReportPayloadDescriptorId();

      ReportPayloadDescriptorBusinessDelegate proxy =
          ReportPayloadDescriptorBusinessDelegate.getReportPayloadDescriptorInstance();

      proxy.updateReportPayloadDescriptor(command).get();

      LOGGER.info("-- Successfully saved ReportPayloadDescriptor - " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          msg.toString()
              + " : primarykey = "
              + reportPayloadDescriptorId
              + " : command -"
              + command
              + " : "
              + e);

      throw e;
    }
  }

  /** delete a ReportPayloadDescriptor. */
  protected void delete() throws Throwable {
    LOGGER.info("\n======== DELETE ======== ");
    LOGGER.info("-- Deleting a previously created ReportPayloadDescriptor.");

    ReportPayloadDescriptor entity = null;

    try {
      entity = read();
      LOGGER.info(
          "-- Successfully read ReportPayloadDescriptor with id " + reportPayloadDescriptorId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          "-- Failed to read ReportPayloadDescriptor with id " + reportPayloadDescriptorId);

      throw e;
    }

    try {
      ReportPayloadDescriptorBusinessDelegate.getReportPayloadDescriptorInstance()
          .delete(new DeleteReportPayloadDescriptorCommand(entity.getReportPayloadDescriptorId()))
          .get();
      LOGGER.info(
          "-- Successfully deleted ReportPayloadDescriptor with id " + reportPayloadDescriptorId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          "-- Failed to delete ReportPayloadDescriptor with id " + reportPayloadDescriptorId);

      throw e;
    }
  }

  /** get all ReportPayloadDescriptors. */
  protected List<ReportPayloadDescriptor> getAll() throws Throwable {
    LOGGER.info("======== GETALL ======== ");
    LOGGER.info("-- Retrieving Collection of ReportPayloadDescriptors:");

    StringBuilder msg = new StringBuilder("-- Failed to get all ReportPayloadDescriptor : ");
    List<ReportPayloadDescriptor> collection = new ArrayList<>();

    try {
      // call the static get method on the ReportPayloadDescriptorBusinessDelegate
      collection =
          ReportPayloadDescriptorBusinessDelegate.getReportPayloadDescriptorInstance()
              .getAllReportPayloadDescriptor();
      assertNotNull(
          collection, "An Empty collection of ReportPayloadDescriptor was incorrectly returned.");

      // Now print out the values
      ReportPayloadDescriptor entity = null;
      Iterator<ReportPayloadDescriptor> iter = collection.iterator();
      int index = 1;

      while (iter.hasNext()) {
        // Retrieve the entity
        entity = iter.next();

        assertNotNull(entity, "-- null entity in Collection.");
        assertNotNull(
            entity.getReportPayloadDescriptorId(),
            "-- entity in Collection has a null primary key");

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
   * @return ReportPayloadDescriptorTest
   */
  protected ReportPayloadDescriptorTest setHandler(Handler handler) {
    if (handler != null) LOGGER.addHandler(handler);
    return this;
  }

  /**
   * Returns a new populated ReportPayloadDescriptor
   *
   * @return CreateReportPayloadDescriptorCommand alias
   */
  protected CreateReportPayloadDescriptorCommand generateNewCommand() {
    CreateReportPayloadDescriptorCommand command =
        new CreateReportPayloadDescriptorCommand(
            null,
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            0.0F,
            0,
            ObjectType.values()[0]);

    return (command);
  }

  /**
   * Returns a new populated ReportPayloadDescriptor
   *
   * @return UpdateReportPayloadDescriptorCommand alias
   */
  protected UpdateReportPayloadDescriptorCommand generateUpdateCommand() {
    UpdateReportPayloadDescriptorCommand command =
        new UpdateReportPayloadDescriptorCommand(
            null,
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            0.0F,
            0,
            ObjectType.values()[0]);

    return (command);
  }
  // -----------------------------------------------------
  // attributes
  // -----------------------------------------------------
  protected UUID reportPayloadDescriptorId = null;
  protected ReportPayloadDescriptorSubscriber subscriber = null;
  private final String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
  private final Logger LOGGER = Logger.getLogger(ReportPayloadDescriptorTest.class.getName());
  private String testingStep = "";
  private Integer sizeOfReportPayloadDescriptorList = 0;
}
