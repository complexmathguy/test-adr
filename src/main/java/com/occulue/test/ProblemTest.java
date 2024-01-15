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
 * Test Problem class.
 *
 * @author your_name_here
 */
public class ProblemTest {

  // ------------------------------------
  // default constructor
  // ------------------------------------
  public ProblemTest() {
    subscriber = new ProblemSubscriber();
  }

  // test methods
  @Test
  /*
   * Initiate ProblemTest.
   */
  public void startTest() throws Throwable {
    try {
      LOGGER.info("**********************************************************");
      LOGGER.info("Beginning test on Problem...");
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

  /** jumpstart the process by instantiating2 Problem */
  protected void jumpStart() throws Throwable {
    LOGGER.info("\n======== create instances to get the ball rolling  ======== ");

    problemId =
        ProblemBusinessDelegate.getProblemInstance().createProblem(generateNewCommand()).get();

    // ---------------------------------------------
    // set up query subscriptions after the 1st create
    // ---------------------------------------------
    testingStep = "create";
    setUpQuerySubscriptions();

    ProblemBusinessDelegate.getProblemInstance().createProblem(generateNewCommand()).get();
  }

  /** Set up query subscriptions */
  protected void setUpQuerySubscriptions() throws Throwable {
    LOGGER.info("\n======== Setting Up Query Subscriptions ======== ");

    try {
      subscriber
          .problemSubscribe()
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetAll update received for Problem : " + successValue.getProblemId());
                  if (successValue.getProblemId().equals(problemId)) {
                    if (testingStep.equals("create")) {
                      testingStep = "update";
                      update();
                    } else if (testingStep.equals("delete")) {
                      testingStep = "complete";
                      state(
                          getAll().size() == sizeOfProblemList - 1, "value not deleted from list");
                      LOGGER.info("**********************************************************");
                      LOGGER.info("Problem test completed successfully...");
                      LOGGER.info("**********************************************************\n");
                    }
                  }
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on problem consumed"));
      subscriber
          .problemSubscribe(problemId)
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetOne update received for Problem : "
                          + successValue.getProblemId()
                          + " in step "
                          + testingStep);
                  testingStep = "delete";
                  sizeOfProblemList = getAll().size();
                  delete();
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on problem for problemId consumed"));

    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
      throw e;
    }
  }

  /** read a Problem. */
  protected Problem read() throws Throwable {
    LOGGER.info("\n======== READ ======== ");
    LOGGER.info("-- Reading a previously created Problem");

    Problem entity = null;
    StringBuilder msg = new StringBuilder("-- Failed to read Problem with primary key");
    msg.append(problemId);

    ProblemFetchOneSummary fetchOneSummary = new ProblemFetchOneSummary(problemId);

    try {
      entity = ProblemBusinessDelegate.getProblemInstance().getProblem(fetchOneSummary);

      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Successfully found Problem " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(msg.toString() + " : " + e);

      throw e;
    }

    return entity;
  }

  /** updating a Problem. */
  protected void update() throws Throwable {
    LOGGER.info("\n======== UPDATE ======== ");
    LOGGER.info("-- Attempting to update a Problem.");

    StringBuilder msg = new StringBuilder("Failed to update a Problem : ");
    Problem entity = read();
    UpdateProblemCommand command = generateUpdateCommand();
    command.setProblemId(entity.getProblemId());

    try {
      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Now updating the created Problem.");

      // for use later on...
      problemId = entity.getProblemId();

      ProblemBusinessDelegate proxy = ProblemBusinessDelegate.getProblemInstance();

      proxy.updateProblem(command).get();

      LOGGER.info("-- Successfully saved Problem - " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          msg.toString() + " : primarykey = " + problemId + " : command -" + command + " : " + e);

      throw e;
    }
  }

  /** delete a Problem. */
  protected void delete() throws Throwable {
    LOGGER.info("\n======== DELETE ======== ");
    LOGGER.info("-- Deleting a previously created Problem.");

    Problem entity = null;

    try {
      entity = read();
      LOGGER.info("-- Successfully read Problem with id " + problemId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to read Problem with id " + problemId);

      throw e;
    }

    try {
      ProblemBusinessDelegate.getProblemInstance()
          .delete(new DeleteProblemCommand(entity.getProblemId()))
          .get();
      LOGGER.info("-- Successfully deleted Problem with id " + problemId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to delete Problem with id " + problemId);

      throw e;
    }
  }

  /** get all Problems. */
  protected List<Problem> getAll() throws Throwable {
    LOGGER.info("======== GETALL ======== ");
    LOGGER.info("-- Retrieving Collection of Problems:");

    StringBuilder msg = new StringBuilder("-- Failed to get all Problem : ");
    List<Problem> collection = new ArrayList<>();

    try {
      // call the static get method on the ProblemBusinessDelegate
      collection = ProblemBusinessDelegate.getProblemInstance().getAllProblem();
      assertNotNull(collection, "An Empty collection of Problem was incorrectly returned.");

      // Now print out the values
      Problem entity = null;
      Iterator<Problem> iter = collection.iterator();
      int index = 1;

      while (iter.hasNext()) {
        // Retrieve the entity
        entity = iter.next();

        assertNotNull(entity, "-- null entity in Collection.");
        assertNotNull(entity.getProblemId(), "-- entity in Collection has a null primary key");

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
   * @return ProblemTest
   */
  protected ProblemTest setHandler(Handler handler) {
    if (handler != null) LOGGER.addHandler(handler);
    return this;
  }

  /**
   * Returns a new populated Problem
   *
   * @return CreateProblemCommand alias
   */
  protected CreateProblemCommand generateNewCommand() {
    CreateProblemCommand command =
        new CreateProblemCommand(
            null,
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            0,
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16));

    return (command);
  }

  /**
   * Returns a new populated Problem
   *
   * @return UpdateProblemCommand alias
   */
  protected UpdateProblemCommand generateUpdateCommand() {
    UpdateProblemCommand command =
        new UpdateProblemCommand(
            null,
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            0,
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16));

    return (command);
  }
  // -----------------------------------------------------
  // attributes
  // -----------------------------------------------------
  protected UUID problemId = null;
  protected ProblemSubscriber subscriber = null;
  private final String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
  private final Logger LOGGER = Logger.getLogger(ProblemTest.class.getName());
  private String testingStep = "";
  private Integer sizeOfProblemList = 0;
}
