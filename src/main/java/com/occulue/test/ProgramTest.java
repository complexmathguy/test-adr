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
 * Test Program class.
 *
 * @author your_name_here
 */
public class ProgramTest {

  // ------------------------------------
  // default constructor
  // ------------------------------------
  public ProgramTest() {
    subscriber = new ProgramSubscriber();
  }

  // test methods
  @Test
  /*
   * Initiate ProgramTest.
   */
  public void startTest() throws Throwable {
    try {
      LOGGER.info("**********************************************************");
      LOGGER.info("Beginning test on Program...");
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

  /** jumpstart the process by instantiating2 Program */
  protected void jumpStart() throws Throwable {
    LOGGER.info("\n======== create instances to get the ball rolling  ======== ");

    programId =
        ProgramBusinessDelegate.getProgramInstance().createProgram(generateNewCommand()).get();

    // ---------------------------------------------
    // set up query subscriptions after the 1st create
    // ---------------------------------------------
    testingStep = "create";
    setUpQuerySubscriptions();

    ProgramBusinessDelegate.getProgramInstance().createProgram(generateNewCommand()).get();
  }

  /** Set up query subscriptions */
  protected void setUpQuerySubscriptions() throws Throwable {
    LOGGER.info("\n======== Setting Up Query Subscriptions ======== ");

    try {
      subscriber
          .programSubscribe()
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetAll update received for Program : " + successValue.getProgramId());
                  if (successValue.getProgramId().equals(programId)) {
                    if (testingStep.equals("create")) {
                      testingStep = "update";
                      update();
                    } else if (testingStep.equals("delete")) {
                      testingStep = "complete";
                      state(
                          getAll().size() == sizeOfProgramList - 1, "value not deleted from list");
                      LOGGER.info("**********************************************************");
                      LOGGER.info("Program test completed successfully...");
                      LOGGER.info("**********************************************************\n");
                    }
                  }
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on program consumed"));
      subscriber
          .programSubscribe(programId)
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetOne update received for Program : "
                          + successValue.getProgramId()
                          + " in step "
                          + testingStep);
                  testingStep = "delete";
                  sizeOfProgramList = getAll().size();
                  delete();
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on program for programId consumed"));

    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
      throw e;
    }
  }

  /** read a Program. */
  protected Program read() throws Throwable {
    LOGGER.info("\n======== READ ======== ");
    LOGGER.info("-- Reading a previously created Program");

    Program entity = null;
    StringBuilder msg = new StringBuilder("-- Failed to read Program with primary key");
    msg.append(programId);

    ProgramFetchOneSummary fetchOneSummary = new ProgramFetchOneSummary(programId);

    try {
      entity = ProgramBusinessDelegate.getProgramInstance().getProgram(fetchOneSummary);

      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Successfully found Program " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(msg.toString() + " : " + e);

      throw e;
    }

    return entity;
  }

  /** updating a Program. */
  protected void update() throws Throwable {
    LOGGER.info("\n======== UPDATE ======== ");
    LOGGER.info("-- Attempting to update a Program.");

    StringBuilder msg = new StringBuilder("Failed to update a Program : ");
    Program entity = read();
    UpdateProgramCommand command = generateUpdateCommand();
    command.setProgramId(entity.getProgramId());

    try {
      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Now updating the created Program.");

      // for use later on...
      programId = entity.getProgramId();

      ProgramBusinessDelegate proxy = ProgramBusinessDelegate.getProgramInstance();

      proxy.updateProgram(command).get();

      LOGGER.info("-- Successfully saved Program - " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          msg.toString() + " : primarykey = " + programId + " : command -" + command + " : " + e);

      throw e;
    }
  }

  /** delete a Program. */
  protected void delete() throws Throwable {
    LOGGER.info("\n======== DELETE ======== ");
    LOGGER.info("-- Deleting a previously created Program.");

    Program entity = null;

    try {
      entity = read();
      LOGGER.info("-- Successfully read Program with id " + programId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to read Program with id " + programId);

      throw e;
    }

    try {
      ProgramBusinessDelegate.getProgramInstance()
          .delete(new DeleteProgramCommand(entity.getProgramId()))
          .get();
      LOGGER.info("-- Successfully deleted Program with id " + programId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to delete Program with id " + programId);

      throw e;
    }
  }

  /** get all Programs. */
  protected List<Program> getAll() throws Throwable {
    LOGGER.info("======== GETALL ======== ");
    LOGGER.info("-- Retrieving Collection of Programs:");

    StringBuilder msg = new StringBuilder("-- Failed to get all Program : ");
    List<Program> collection = new ArrayList<>();

    try {
      // call the static get method on the ProgramBusinessDelegate
      collection = ProgramBusinessDelegate.getProgramInstance().getAllProgram();
      assertNotNull(collection, "An Empty collection of Program was incorrectly returned.");

      // Now print out the values
      Program entity = null;
      Iterator<Program> iter = collection.iterator();
      int index = 1;

      while (iter.hasNext()) {
        // Retrieve the entity
        entity = iter.next();

        assertNotNull(entity, "-- null entity in Collection.");
        assertNotNull(entity.getProgramId(), "-- entity in Collection has a null primary key");

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
   * @return ProgramTest
   */
  protected ProgramTest setHandler(Handler handler) {
    if (handler != null) LOGGER.addHandler(handler);
    return this;
  }

  /**
   * Returns a new populated Program
   *
   * @return CreateProgramCommand alias
   */
  protected CreateProgramCommand generateNewCommand() {
    CreateProgramCommand command =
        new CreateProgramCommand(
            null,
            new Date(),
            new Date(),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            new Duration(),
            null,
            new Boolean(true),
            new Boolean(true),
            ObjectType.values()[0]);

    return (command);
  }

  /**
   * Returns a new populated Program
   *
   * @return UpdateProgramCommand alias
   */
  protected UpdateProgramCommand generateUpdateCommand() {
    UpdateProgramCommand command =
        new UpdateProgramCommand(
            null,
            new Date(),
            new Date(),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            new Duration(),
            null,
            new Boolean(true),
            new Boolean(true),
            new HashSet<>(),
            new HashSet<>(),
            ObjectType.values()[0],
            null);

    return (command);
  }
  // -----------------------------------------------------
  // attributes
  // -----------------------------------------------------
  protected UUID programId = null;
  protected ProgramSubscriber subscriber = null;
  private final String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
  private final Logger LOGGER = Logger.getLogger(ProgramTest.class.getName());
  private String testingStep = "";
  private Integer sizeOfProgramList = 0;
}
