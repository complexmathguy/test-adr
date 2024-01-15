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
 * Test Event class.
 *
 * @author your_name_here
 */
public class EventTest {

  // ------------------------------------
  // default constructor
  // ------------------------------------
  public EventTest() {
    subscriber = new EventSubscriber();
  }

  // test methods
  @Test
  /*
   * Initiate EventTest.
   */
  public void startTest() throws Throwable {
    try {
      LOGGER.info("**********************************************************");
      LOGGER.info("Beginning test on Event...");
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

  /** jumpstart the process by instantiating2 Event */
  protected void jumpStart() throws Throwable {
    LOGGER.info("\n======== create instances to get the ball rolling  ======== ");

    eventId = EventBusinessDelegate.getEventInstance().createEvent(generateNewCommand()).get();

    // ---------------------------------------------
    // set up query subscriptions after the 1st create
    // ---------------------------------------------
    testingStep = "create";
    setUpQuerySubscriptions();

    EventBusinessDelegate.getEventInstance().createEvent(generateNewCommand()).get();
  }

  /** Set up query subscriptions */
  protected void setUpQuerySubscriptions() throws Throwable {
    LOGGER.info("\n======== Setting Up Query Subscriptions ======== ");

    try {
      subscriber
          .eventSubscribe()
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info("GetAll update received for Event : " + successValue.getEventId());
                  if (successValue.getEventId().equals(eventId)) {
                    if (testingStep.equals("create")) {
                      testingStep = "update";
                      update();
                    } else if (testingStep.equals("delete")) {
                      testingStep = "complete";
                      state(getAll().size() == sizeOfEventList - 1, "value not deleted from list");
                      LOGGER.info("**********************************************************");
                      LOGGER.info("Event test completed successfully...");
                      LOGGER.info("**********************************************************\n");
                    }
                  }
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on event consumed"));
      subscriber
          .eventSubscribe(eventId)
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetOne update received for Event : "
                          + successValue.getEventId()
                          + " in step "
                          + testingStep);
                  testingStep = "delete";
                  sizeOfEventList = getAll().size();
                  delete();
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on event for eventId consumed"));

    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
      throw e;
    }
  }

  /** read a Event. */
  protected Event read() throws Throwable {
    LOGGER.info("\n======== READ ======== ");
    LOGGER.info("-- Reading a previously created Event");

    Event entity = null;
    StringBuilder msg = new StringBuilder("-- Failed to read Event with primary key");
    msg.append(eventId);

    EventFetchOneSummary fetchOneSummary = new EventFetchOneSummary(eventId);

    try {
      entity = EventBusinessDelegate.getEventInstance().getEvent(fetchOneSummary);

      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Successfully found Event " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(msg.toString() + " : " + e);

      throw e;
    }

    return entity;
  }

  /** updating a Event. */
  protected void update() throws Throwable {
    LOGGER.info("\n======== UPDATE ======== ");
    LOGGER.info("-- Attempting to update a Event.");

    StringBuilder msg = new StringBuilder("Failed to update a Event : ");
    Event entity = read();
    UpdateEventCommand command = generateUpdateCommand();
    command.setEventId(entity.getEventId());

    try {
      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Now updating the created Event.");

      // for use later on...
      eventId = entity.getEventId();

      EventBusinessDelegate proxy = EventBusinessDelegate.getEventInstance();

      proxy.updateEvent(command).get();

      LOGGER.info("-- Successfully saved Event - " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          msg.toString() + " : primarykey = " + eventId + " : command -" + command + " : " + e);

      throw e;
    }
  }

  /** delete a Event. */
  protected void delete() throws Throwable {
    LOGGER.info("\n======== DELETE ======== ");
    LOGGER.info("-- Deleting a previously created Event.");

    Event entity = null;

    try {
      entity = read();
      LOGGER.info("-- Successfully read Event with id " + eventId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to read Event with id " + eventId);

      throw e;
    }

    try {
      EventBusinessDelegate.getEventInstance()
          .delete(new DeleteEventCommand(entity.getEventId()))
          .get();
      LOGGER.info("-- Successfully deleted Event with id " + eventId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to delete Event with id " + eventId);

      throw e;
    }
  }

  /** get all Events. */
  protected List<Event> getAll() throws Throwable {
    LOGGER.info("======== GETALL ======== ");
    LOGGER.info("-- Retrieving Collection of Events:");

    StringBuilder msg = new StringBuilder("-- Failed to get all Event : ");
    List<Event> collection = new ArrayList<>();

    try {
      // call the static get method on the EventBusinessDelegate
      collection = EventBusinessDelegate.getEventInstance().getAllEvent();
      assertNotNull(collection, "An Empty collection of Event was incorrectly returned.");

      // Now print out the values
      Event entity = null;
      Iterator<Event> iter = collection.iterator();
      int index = 1;

      while (iter.hasNext()) {
        // Retrieve the entity
        entity = iter.next();

        assertNotNull(entity, "-- null entity in Collection.");
        assertNotNull(entity.getEventId(), "-- entity in Collection has a null primary key");

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
   * @return EventTest
   */
  protected EventTest setHandler(Handler handler) {
    if (handler != null) LOGGER.addHandler(handler);
    return this;
  }

  /**
   * Returns a new populated Event
   *
   * @return CreateEventCommand alias
   */
  protected CreateEventCommand generateNewCommand() {
    CreateEventCommand command =
        new CreateEventCommand(
            null,
            new Date(),
            new Date(),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            0,
            ObjectType.values()[0]);

    return (command);
  }

  /**
   * Returns a new populated Event
   *
   * @return UpdateEventCommand alias
   */
  protected UpdateEventCommand generateUpdateCommand() {
    UpdateEventCommand command =
        new UpdateEventCommand(
            null,
            new Date(),
            new Date(),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            0,
            null,
            null,
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>(),
            ObjectType.values()[0],
            null);

    return (command);
  }
  // -----------------------------------------------------
  // attributes
  // -----------------------------------------------------
  protected UUID eventId = null;
  protected EventSubscriber subscriber = null;
  private final String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
  private final Logger LOGGER = Logger.getLogger(EventTest.class.getName());
  private String testingStep = "";
  private Integer sizeOfEventList = 0;
}
