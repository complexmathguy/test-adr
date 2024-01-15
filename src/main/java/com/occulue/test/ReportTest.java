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
 * Test Report class.
 *
 * @author your_name_here
 */
public class ReportTest {

  // ------------------------------------
  // default constructor
  // ------------------------------------
  public ReportTest() {
    subscriber = new ReportSubscriber();
  }

  // test methods
  @Test
  /*
   * Initiate ReportTest.
   */
  public void startTest() throws Throwable {
    try {
      LOGGER.info("**********************************************************");
      LOGGER.info("Beginning test on Report...");
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

  /** jumpstart the process by instantiating2 Report */
  protected void jumpStart() throws Throwable {
    LOGGER.info("\n======== create instances to get the ball rolling  ======== ");

    reportId = ReportBusinessDelegate.getReportInstance().createReport(generateNewCommand()).get();

    // ---------------------------------------------
    // set up query subscriptions after the 1st create
    // ---------------------------------------------
    testingStep = "create";
    setUpQuerySubscriptions();

    ReportBusinessDelegate.getReportInstance().createReport(generateNewCommand()).get();
  }

  /** Set up query subscriptions */
  protected void setUpQuerySubscriptions() throws Throwable {
    LOGGER.info("\n======== Setting Up Query Subscriptions ======== ");

    try {
      subscriber
          .reportSubscribe()
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info("GetAll update received for Report : " + successValue.getReportId());
                  if (successValue.getReportId().equals(reportId)) {
                    if (testingStep.equals("create")) {
                      testingStep = "update";
                      update();
                    } else if (testingStep.equals("delete")) {
                      testingStep = "complete";
                      state(getAll().size() == sizeOfReportList - 1, "value not deleted from list");
                      LOGGER.info("**********************************************************");
                      LOGGER.info("Report test completed successfully...");
                      LOGGER.info("**********************************************************\n");
                    }
                  }
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on report consumed"));
      subscriber
          .reportSubscribe(reportId)
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetOne update received for Report : "
                          + successValue.getReportId()
                          + " in step "
                          + testingStep);
                  testingStep = "delete";
                  sizeOfReportList = getAll().size();
                  delete();
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on report for reportId consumed"));

    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
      throw e;
    }
  }

  /** read a Report. */
  protected Report read() throws Throwable {
    LOGGER.info("\n======== READ ======== ");
    LOGGER.info("-- Reading a previously created Report");

    Report entity = null;
    StringBuilder msg = new StringBuilder("-- Failed to read Report with primary key");
    msg.append(reportId);

    ReportFetchOneSummary fetchOneSummary = new ReportFetchOneSummary(reportId);

    try {
      entity = ReportBusinessDelegate.getReportInstance().getReport(fetchOneSummary);

      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Successfully found Report " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(msg.toString() + " : " + e);

      throw e;
    }

    return entity;
  }

  /** updating a Report. */
  protected void update() throws Throwable {
    LOGGER.info("\n======== UPDATE ======== ");
    LOGGER.info("-- Attempting to update a Report.");

    StringBuilder msg = new StringBuilder("Failed to update a Report : ");
    Report entity = read();
    UpdateReportCommand command = generateUpdateCommand();
    command.setReportId(entity.getReportId());

    try {
      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Now updating the created Report.");

      // for use later on...
      reportId = entity.getReportId();

      ReportBusinessDelegate proxy = ReportBusinessDelegate.getReportInstance();

      proxy.updateReport(command).get();

      LOGGER.info("-- Successfully saved Report - " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          msg.toString() + " : primarykey = " + reportId + " : command -" + command + " : " + e);

      throw e;
    }
  }

  /** delete a Report. */
  protected void delete() throws Throwable {
    LOGGER.info("\n======== DELETE ======== ");
    LOGGER.info("-- Deleting a previously created Report.");

    Report entity = null;

    try {
      entity = read();
      LOGGER.info("-- Successfully read Report with id " + reportId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to read Report with id " + reportId);

      throw e;
    }

    try {
      ReportBusinessDelegate.getReportInstance()
          .delete(new DeleteReportCommand(entity.getReportId()))
          .get();
      LOGGER.info("-- Successfully deleted Report with id " + reportId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to delete Report with id " + reportId);

      throw e;
    }
  }

  /** get all Reports. */
  protected List<Report> getAll() throws Throwable {
    LOGGER.info("======== GETALL ======== ");
    LOGGER.info("-- Retrieving Collection of Reports:");

    StringBuilder msg = new StringBuilder("-- Failed to get all Report : ");
    List<Report> collection = new ArrayList<>();

    try {
      // call the static get method on the ReportBusinessDelegate
      collection = ReportBusinessDelegate.getReportInstance().getAllReport();
      assertNotNull(collection, "An Empty collection of Report was incorrectly returned.");

      // Now print out the values
      Report entity = null;
      Iterator<Report> iter = collection.iterator();
      int index = 1;

      while (iter.hasNext()) {
        // Retrieve the entity
        entity = iter.next();

        assertNotNull(entity, "-- null entity in Collection.");
        assertNotNull(entity.getReportId(), "-- entity in Collection has a null primary key");

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
   * @return ReportTest
   */
  protected ReportTest setHandler(Handler handler) {
    if (handler != null) LOGGER.addHandler(handler);
    return this;
  }

  /**
   * Returns a new populated Report
   *
   * @return CreateReportCommand alias
   */
  protected CreateReportCommand generateNewCommand() {
    CreateReportCommand command =
        new CreateReportCommand(
            null,
            new Date(),
            new Date(),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            ObjectType.values()[0]);

    return (command);
  }

  /**
   * Returns a new populated Report
   *
   * @return UpdateReportCommand alias
   */
  protected UpdateReportCommand generateUpdateCommand() {
    UpdateReportCommand command =
        new UpdateReportCommand(
            null,
            new Date(),
            new Date(),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            null,
            null,
            new HashSet<>(),
            new HashSet<>(),
            null,
            ObjectType.values()[0],
            null);

    return (command);
  }
  // -----------------------------------------------------
  // attributes
  // -----------------------------------------------------
  protected UUID reportId = null;
  protected ReportSubscriber subscriber = null;
  private final String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
  private final Logger LOGGER = Logger.getLogger(ReportTest.class.getName());
  private String testingStep = "";
  private Integer sizeOfReportList = 0;
}
