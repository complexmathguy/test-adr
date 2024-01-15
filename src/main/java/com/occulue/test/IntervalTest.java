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
 * Test Interval class.
 *
 * @author your_name_here
 */
public class IntervalTest {

  // ------------------------------------
  // default constructor
  // ------------------------------------
  public IntervalTest() {
    subscriber = new IntervalSubscriber();
  }

  // test methods
  @Test
  /*
   * Initiate IntervalTest.
   */
  public void startTest() throws Throwable {
    try {
      LOGGER.info("**********************************************************");
      LOGGER.info("Beginning test on Interval...");
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

  /** jumpstart the process by instantiating2 Interval */
  protected void jumpStart() throws Throwable {
    LOGGER.info("\n======== create instances to get the ball rolling  ======== ");

    intervalId =
        IntervalBusinessDelegate.getIntervalInstance().createInterval(generateNewCommand()).get();

    // ---------------------------------------------
    // set up query subscriptions after the 1st create
    // ---------------------------------------------
    testingStep = "create";
    setUpQuerySubscriptions();

    IntervalBusinessDelegate.getIntervalInstance().createInterval(generateNewCommand()).get();
  }

  /** Set up query subscriptions */
  protected void setUpQuerySubscriptions() throws Throwable {
    LOGGER.info("\n======== Setting Up Query Subscriptions ======== ");

    try {
      subscriber
          .intervalSubscribe()
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetAll update received for Interval : " + successValue.getIntervalId());
                  if (successValue.getIntervalId().equals(intervalId)) {
                    if (testingStep.equals("create")) {
                      testingStep = "update";
                      update();
                    } else if (testingStep.equals("delete")) {
                      testingStep = "complete";
                      state(
                          getAll().size() == sizeOfIntervalList - 1, "value not deleted from list");
                      LOGGER.info("**********************************************************");
                      LOGGER.info("Interval test completed successfully...");
                      LOGGER.info("**********************************************************\n");
                    }
                  }
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on interval consumed"));
      subscriber
          .intervalSubscribe(intervalId)
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetOne update received for Interval : "
                          + successValue.getIntervalId()
                          + " in step "
                          + testingStep);
                  testingStep = "delete";
                  sizeOfIntervalList = getAll().size();
                  delete();
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on interval for intervalId consumed"));

    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
      throw e;
    }
  }

  /** read a Interval. */
  protected Interval read() throws Throwable {
    LOGGER.info("\n======== READ ======== ");
    LOGGER.info("-- Reading a previously created Interval");

    Interval entity = null;
    StringBuilder msg = new StringBuilder("-- Failed to read Interval with primary key");
    msg.append(intervalId);

    IntervalFetchOneSummary fetchOneSummary = new IntervalFetchOneSummary(intervalId);

    try {
      entity = IntervalBusinessDelegate.getIntervalInstance().getInterval(fetchOneSummary);

      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Successfully found Interval " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(msg.toString() + " : " + e);

      throw e;
    }

    return entity;
  }

  /** updating a Interval. */
  protected void update() throws Throwable {
    LOGGER.info("\n======== UPDATE ======== ");
    LOGGER.info("-- Attempting to update a Interval.");

    StringBuilder msg = new StringBuilder("Failed to update a Interval : ");
    Interval entity = read();
    UpdateIntervalCommand command = generateUpdateCommand();
    command.setIntervalId(entity.getIntervalId());

    try {
      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Now updating the created Interval.");

      // for use later on...
      intervalId = entity.getIntervalId();

      IntervalBusinessDelegate proxy = IntervalBusinessDelegate.getIntervalInstance();

      proxy.updateInterval(command).get();

      LOGGER.info("-- Successfully saved Interval - " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          msg.toString() + " : primarykey = " + intervalId + " : command -" + command + " : " + e);

      throw e;
    }
  }

  /** delete a Interval. */
  protected void delete() throws Throwable {
    LOGGER.info("\n======== DELETE ======== ");
    LOGGER.info("-- Deleting a previously created Interval.");

    Interval entity = null;

    try {
      entity = read();
      LOGGER.info("-- Successfully read Interval with id " + intervalId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to read Interval with id " + intervalId);

      throw e;
    }

    try {
      IntervalBusinessDelegate.getIntervalInstance()
          .delete(new DeleteIntervalCommand(entity.getIntervalId()))
          .get();
      LOGGER.info("-- Successfully deleted Interval with id " + intervalId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to delete Interval with id " + intervalId);

      throw e;
    }
  }

  /** get all Intervals. */
  protected List<Interval> getAll() throws Throwable {
    LOGGER.info("======== GETALL ======== ");
    LOGGER.info("-- Retrieving Collection of Intervals:");

    StringBuilder msg = new StringBuilder("-- Failed to get all Interval : ");
    List<Interval> collection = new ArrayList<>();

    try {
      // call the static get method on the IntervalBusinessDelegate
      collection = IntervalBusinessDelegate.getIntervalInstance().getAllInterval();
      assertNotNull(collection, "An Empty collection of Interval was incorrectly returned.");

      // Now print out the values
      Interval entity = null;
      Iterator<Interval> iter = collection.iterator();
      int index = 1;

      while (iter.hasNext()) {
        // Retrieve the entity
        entity = iter.next();

        assertNotNull(entity, "-- null entity in Collection.");
        assertNotNull(entity.getIntervalId(), "-- entity in Collection has a null primary key");

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
   * @return IntervalTest
   */
  protected IntervalTest setHandler(Handler handler) {
    if (handler != null) LOGGER.addHandler(handler);
    return this;
  }

  /**
   * Returns a new populated Interval
   *
   * @return CreateIntervalCommand alias
   */
  protected CreateIntervalCommand generateNewCommand() {
    CreateIntervalCommand command = new CreateIntervalCommand(null);

    return (command);
  }

  /**
   * Returns a new populated Interval
   *
   * @return UpdateIntervalCommand alias
   */
  protected UpdateIntervalCommand generateUpdateCommand() {
    UpdateIntervalCommand command = new UpdateIntervalCommand(null, new HashSet<>(), null);

    return (command);
  }
  // -----------------------------------------------------
  // attributes
  // -----------------------------------------------------
  protected UUID intervalId = null;
  protected IntervalSubscriber subscriber = null;
  private final String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
  private final Logger LOGGER = Logger.getLogger(IntervalTest.class.getName());
  private String testingStep = "";
  private Integer sizeOfIntervalList = 0;
}
