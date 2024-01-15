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
 * Test IntervalPeriod class.
 *
 * @author your_name_here
 */
public class IntervalPeriodTest {

  // ------------------------------------
  // default constructor
  // ------------------------------------
  public IntervalPeriodTest() {
    subscriber = new IntervalPeriodSubscriber();
  }

  // test methods
  @Test
  /*
   * Initiate IntervalPeriodTest.
   */
  public void startTest() throws Throwable {
    try {
      LOGGER.info("**********************************************************");
      LOGGER.info("Beginning test on IntervalPeriod...");
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

  /** jumpstart the process by instantiating2 IntervalPeriod */
  protected void jumpStart() throws Throwable {
    LOGGER.info("\n======== create instances to get the ball rolling  ======== ");

    intervalPeriodId =
        IntervalPeriodBusinessDelegate.getIntervalPeriodInstance()
            .createIntervalPeriod(generateNewCommand())
            .get();

    // ---------------------------------------------
    // set up query subscriptions after the 1st create
    // ---------------------------------------------
    testingStep = "create";
    setUpQuerySubscriptions();

    IntervalPeriodBusinessDelegate.getIntervalPeriodInstance()
        .createIntervalPeriod(generateNewCommand())
        .get();
  }

  /** Set up query subscriptions */
  protected void setUpQuerySubscriptions() throws Throwable {
    LOGGER.info("\n======== Setting Up Query Subscriptions ======== ");

    try {
      subscriber
          .intervalPeriodSubscribe()
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetAll update received for IntervalPeriod : "
                          + successValue.getIntervalPeriodId());
                  if (successValue.getIntervalPeriodId().equals(intervalPeriodId)) {
                    if (testingStep.equals("create")) {
                      testingStep = "update";
                      update();
                    } else if (testingStep.equals("delete")) {
                      testingStep = "complete";
                      state(
                          getAll().size() == sizeOfIntervalPeriodList - 1,
                          "value not deleted from list");
                      LOGGER.info("**********************************************************");
                      LOGGER.info("IntervalPeriod test completed successfully...");
                      LOGGER.info("**********************************************************\n");
                    }
                  }
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on intervalPeriod consumed"));
      subscriber
          .intervalPeriodSubscribe(intervalPeriodId)
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetOne update received for IntervalPeriod : "
                          + successValue.getIntervalPeriodId()
                          + " in step "
                          + testingStep);
                  testingStep = "delete";
                  sizeOfIntervalPeriodList = getAll().size();
                  delete();
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on intervalPeriod for intervalPeriodId consumed"));

    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
      throw e;
    }
  }

  /** read a IntervalPeriod. */
  protected IntervalPeriod read() throws Throwable {
    LOGGER.info("\n======== READ ======== ");
    LOGGER.info("-- Reading a previously created IntervalPeriod");

    IntervalPeriod entity = null;
    StringBuilder msg = new StringBuilder("-- Failed to read IntervalPeriod with primary key");
    msg.append(intervalPeriodId);

    IntervalPeriodFetchOneSummary fetchOneSummary =
        new IntervalPeriodFetchOneSummary(intervalPeriodId);

    try {
      entity =
          IntervalPeriodBusinessDelegate.getIntervalPeriodInstance()
              .getIntervalPeriod(fetchOneSummary);

      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Successfully found IntervalPeriod " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(msg.toString() + " : " + e);

      throw e;
    }

    return entity;
  }

  /** updating a IntervalPeriod. */
  protected void update() throws Throwable {
    LOGGER.info("\n======== UPDATE ======== ");
    LOGGER.info("-- Attempting to update a IntervalPeriod.");

    StringBuilder msg = new StringBuilder("Failed to update a IntervalPeriod : ");
    IntervalPeriod entity = read();
    UpdateIntervalPeriodCommand command = generateUpdateCommand();
    command.setIntervalPeriodId(entity.getIntervalPeriodId());

    try {
      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Now updating the created IntervalPeriod.");

      // for use later on...
      intervalPeriodId = entity.getIntervalPeriodId();

      IntervalPeriodBusinessDelegate proxy =
          IntervalPeriodBusinessDelegate.getIntervalPeriodInstance();

      proxy.updateIntervalPeriod(command).get();

      LOGGER.info("-- Successfully saved IntervalPeriod - " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          msg.toString()
              + " : primarykey = "
              + intervalPeriodId
              + " : command -"
              + command
              + " : "
              + e);

      throw e;
    }
  }

  /** delete a IntervalPeriod. */
  protected void delete() throws Throwable {
    LOGGER.info("\n======== DELETE ======== ");
    LOGGER.info("-- Deleting a previously created IntervalPeriod.");

    IntervalPeriod entity = null;

    try {
      entity = read();
      LOGGER.info("-- Successfully read IntervalPeriod with id " + intervalPeriodId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to read IntervalPeriod with id " + intervalPeriodId);

      throw e;
    }

    try {
      IntervalPeriodBusinessDelegate.getIntervalPeriodInstance()
          .delete(new DeleteIntervalPeriodCommand(entity.getIntervalPeriodId()))
          .get();
      LOGGER.info("-- Successfully deleted IntervalPeriod with id " + intervalPeriodId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to delete IntervalPeriod with id " + intervalPeriodId);

      throw e;
    }
  }

  /** get all IntervalPeriods. */
  protected List<IntervalPeriod> getAll() throws Throwable {
    LOGGER.info("======== GETALL ======== ");
    LOGGER.info("-- Retrieving Collection of IntervalPeriods:");

    StringBuilder msg = new StringBuilder("-- Failed to get all IntervalPeriod : ");
    List<IntervalPeriod> collection = new ArrayList<>();

    try {
      // call the static get method on the IntervalPeriodBusinessDelegate
      collection =
          IntervalPeriodBusinessDelegate.getIntervalPeriodInstance().getAllIntervalPeriod();
      assertNotNull(collection, "An Empty collection of IntervalPeriod was incorrectly returned.");

      // Now print out the values
      IntervalPeriod entity = null;
      Iterator<IntervalPeriod> iter = collection.iterator();
      int index = 1;

      while (iter.hasNext()) {
        // Retrieve the entity
        entity = iter.next();

        assertNotNull(entity, "-- null entity in Collection.");
        assertNotNull(
            entity.getIntervalPeriodId(), "-- entity in Collection has a null primary key");

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
   * @return IntervalPeriodTest
   */
  protected IntervalPeriodTest setHandler(Handler handler) {
    if (handler != null) LOGGER.addHandler(handler);
    return this;
  }

  /**
   * Returns a new populated IntervalPeriod
   *
   * @return CreateIntervalPeriodCommand alias
   */
  protected CreateIntervalPeriodCommand generateNewCommand() {
    CreateIntervalPeriodCommand command =
        new CreateIntervalPeriodCommand(null, new Date(), new Duration(), new Duration());

    return (command);
  }

  /**
   * Returns a new populated IntervalPeriod
   *
   * @return UpdateIntervalPeriodCommand alias
   */
  protected UpdateIntervalPeriodCommand generateUpdateCommand() {
    UpdateIntervalPeriodCommand command =
        new UpdateIntervalPeriodCommand(null, new Date(), new Duration(), new Duration());

    return (command);
  }
  // -----------------------------------------------------
  // attributes
  // -----------------------------------------------------
  protected UUID intervalPeriodId = null;
  protected IntervalPeriodSubscriber subscriber = null;
  private final String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
  private final Logger LOGGER = Logger.getLogger(IntervalPeriodTest.class.getName());
  private String testingStep = "";
  private Integer sizeOfIntervalPeriodList = 0;
}
