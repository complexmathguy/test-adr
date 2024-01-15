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
 * Test ReportDescriptor class.
 *
 * @author your_name_here
 */
public class ReportDescriptorTest {

  // ------------------------------------
  // default constructor
  // ------------------------------------
  public ReportDescriptorTest() {
    subscriber = new ReportDescriptorSubscriber();
  }

  // test methods
  @Test
  /*
   * Initiate ReportDescriptorTest.
   */
  public void startTest() throws Throwable {
    try {
      LOGGER.info("**********************************************************");
      LOGGER.info("Beginning test on ReportDescriptor...");
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

  /** jumpstart the process by instantiating2 ReportDescriptor */
  protected void jumpStart() throws Throwable {
    LOGGER.info("\n======== create instances to get the ball rolling  ======== ");

    reportDescriptorId =
        ReportDescriptorBusinessDelegate.getReportDescriptorInstance()
            .createReportDescriptor(generateNewCommand())
            .get();

    // ---------------------------------------------
    // set up query subscriptions after the 1st create
    // ---------------------------------------------
    testingStep = "create";
    setUpQuerySubscriptions();

    ReportDescriptorBusinessDelegate.getReportDescriptorInstance()
        .createReportDescriptor(generateNewCommand())
        .get();
  }

  /** Set up query subscriptions */
  protected void setUpQuerySubscriptions() throws Throwable {
    LOGGER.info("\n======== Setting Up Query Subscriptions ======== ");

    try {
      subscriber
          .reportDescriptorSubscribe()
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetAll update received for ReportDescriptor : "
                          + successValue.getReportDescriptorId());
                  if (successValue.getReportDescriptorId().equals(reportDescriptorId)) {
                    if (testingStep.equals("create")) {
                      testingStep = "update";
                      update();
                    } else if (testingStep.equals("delete")) {
                      testingStep = "complete";
                      state(
                          getAll().size() == sizeOfReportDescriptorList - 1,
                          "value not deleted from list");
                      LOGGER.info("**********************************************************");
                      LOGGER.info("ReportDescriptor test completed successfully...");
                      LOGGER.info("**********************************************************\n");
                    }
                  }
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on reportDescriptor consumed"));
      subscriber
          .reportDescriptorSubscribe(reportDescriptorId)
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetOne update received for ReportDescriptor : "
                          + successValue.getReportDescriptorId()
                          + " in step "
                          + testingStep);
                  testingStep = "delete";
                  sizeOfReportDescriptorList = getAll().size();
                  delete();
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () ->
                  LOGGER.info("Subscription on reportDescriptor for reportDescriptorId consumed"));

    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
      throw e;
    }
  }

  /** read a ReportDescriptor. */
  protected ReportDescriptor read() throws Throwable {
    LOGGER.info("\n======== READ ======== ");
    LOGGER.info("-- Reading a previously created ReportDescriptor");

    ReportDescriptor entity = null;
    StringBuilder msg = new StringBuilder("-- Failed to read ReportDescriptor with primary key");
    msg.append(reportDescriptorId);

    ReportDescriptorFetchOneSummary fetchOneSummary =
        new ReportDescriptorFetchOneSummary(reportDescriptorId);

    try {
      entity =
          ReportDescriptorBusinessDelegate.getReportDescriptorInstance()
              .getReportDescriptor(fetchOneSummary);

      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Successfully found ReportDescriptor " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(msg.toString() + " : " + e);

      throw e;
    }

    return entity;
  }

  /** updating a ReportDescriptor. */
  protected void update() throws Throwable {
    LOGGER.info("\n======== UPDATE ======== ");
    LOGGER.info("-- Attempting to update a ReportDescriptor.");

    StringBuilder msg = new StringBuilder("Failed to update a ReportDescriptor : ");
    ReportDescriptor entity = read();
    UpdateReportDescriptorCommand command = generateUpdateCommand();
    command.setReportDescriptorId(entity.getReportDescriptorId());

    try {
      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Now updating the created ReportDescriptor.");

      // for use later on...
      reportDescriptorId = entity.getReportDescriptorId();

      ReportDescriptorBusinessDelegate proxy =
          ReportDescriptorBusinessDelegate.getReportDescriptorInstance();

      proxy.updateReportDescriptor(command).get();

      LOGGER.info("-- Successfully saved ReportDescriptor - " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          msg.toString()
              + " : primarykey = "
              + reportDescriptorId
              + " : command -"
              + command
              + " : "
              + e);

      throw e;
    }
  }

  /** delete a ReportDescriptor. */
  protected void delete() throws Throwable {
    LOGGER.info("\n======== DELETE ======== ");
    LOGGER.info("-- Deleting a previously created ReportDescriptor.");

    ReportDescriptor entity = null;

    try {
      entity = read();
      LOGGER.info("-- Successfully read ReportDescriptor with id " + reportDescriptorId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to read ReportDescriptor with id " + reportDescriptorId);

      throw e;
    }

    try {
      ReportDescriptorBusinessDelegate.getReportDescriptorInstance()
          .delete(new DeleteReportDescriptorCommand(entity.getReportDescriptorId()))
          .get();
      LOGGER.info("-- Successfully deleted ReportDescriptor with id " + reportDescriptorId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to delete ReportDescriptor with id " + reportDescriptorId);

      throw e;
    }
  }

  /** get all ReportDescriptors. */
  protected List<ReportDescriptor> getAll() throws Throwable {
    LOGGER.info("======== GETALL ======== ");
    LOGGER.info("-- Retrieving Collection of ReportDescriptors:");

    StringBuilder msg = new StringBuilder("-- Failed to get all ReportDescriptor : ");
    List<ReportDescriptor> collection = new ArrayList<>();

    try {
      // call the static get method on the ReportDescriptorBusinessDelegate
      collection =
          ReportDescriptorBusinessDelegate.getReportDescriptorInstance().getAllReportDescriptor();
      assertNotNull(
          collection, "An Empty collection of ReportDescriptor was incorrectly returned.");

      // Now print out the values
      ReportDescriptor entity = null;
      Iterator<ReportDescriptor> iter = collection.iterator();
      int index = 1;

      while (iter.hasNext()) {
        // Retrieve the entity
        entity = iter.next();

        assertNotNull(entity, "-- null entity in Collection.");
        assertNotNull(
            entity.getReportDescriptorId(), "-- entity in Collection has a null primary key");

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
   * @return ReportDescriptorTest
   */
  protected ReportDescriptorTest setHandler(Handler handler) {
    if (handler != null) LOGGER.addHandler(handler);
    return this;
  }

  /**
   * Returns a new populated ReportDescriptor
   *
   * @return CreateReportDescriptorCommand alias
   */
  protected CreateReportDescriptorCommand generateNewCommand() {
    CreateReportDescriptorCommand command =
        new CreateReportDescriptorCommand(
            null,
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            new Boolean(true),
            0,
            0,
            new Boolean(true),
            0,
            0);

    return (command);
  }

  /**
   * Returns a new populated ReportDescriptor
   *
   * @return UpdateReportDescriptorCommand alias
   */
  protected UpdateReportDescriptorCommand generateUpdateCommand() {
    UpdateReportDescriptorCommand command =
        new UpdateReportDescriptorCommand(
            null,
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            new Boolean(true),
            0,
            0,
            new Boolean(true),
            0,
            0,
            null);

    return (command);
  }
  // -----------------------------------------------------
  // attributes
  // -----------------------------------------------------
  protected UUID reportDescriptorId = null;
  protected ReportDescriptorSubscriber subscriber = null;
  private final String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
  private final Logger LOGGER = Logger.getLogger(ReportDescriptorTest.class.getName());
  private String testingStep = "";
  private Integer sizeOfReportDescriptorList = 0;
}
