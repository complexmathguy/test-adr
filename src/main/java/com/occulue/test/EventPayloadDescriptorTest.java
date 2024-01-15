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
 * Test EventPayloadDescriptor class.
 *
 * @author your_name_here
 */
public class EventPayloadDescriptorTest {

  // ------------------------------------
  // default constructor
  // ------------------------------------
  public EventPayloadDescriptorTest() {
    subscriber = new EventPayloadDescriptorSubscriber();
  }

  // test methods
  @Test
  /*
   * Initiate EventPayloadDescriptorTest.
   */
  public void startTest() throws Throwable {
    try {
      LOGGER.info("**********************************************************");
      LOGGER.info("Beginning test on EventPayloadDescriptor...");
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

  /** jumpstart the process by instantiating2 EventPayloadDescriptor */
  protected void jumpStart() throws Throwable {
    LOGGER.info("\n======== create instances to get the ball rolling  ======== ");

    eventPayloadDescriptorId =
        EventPayloadDescriptorBusinessDelegate.getEventPayloadDescriptorInstance()
            .createEventPayloadDescriptor(generateNewCommand())
            .get();

    // ---------------------------------------------
    // set up query subscriptions after the 1st create
    // ---------------------------------------------
    testingStep = "create";
    setUpQuerySubscriptions();

    EventPayloadDescriptorBusinessDelegate.getEventPayloadDescriptorInstance()
        .createEventPayloadDescriptor(generateNewCommand())
        .get();
  }

  /** Set up query subscriptions */
  protected void setUpQuerySubscriptions() throws Throwable {
    LOGGER.info("\n======== Setting Up Query Subscriptions ======== ");

    try {
      subscriber
          .eventPayloadDescriptorSubscribe()
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetAll update received for EventPayloadDescriptor : "
                          + successValue.getEventPayloadDescriptorId());
                  if (successValue.getEventPayloadDescriptorId().equals(eventPayloadDescriptorId)) {
                    if (testingStep.equals("create")) {
                      testingStep = "update";
                      update();
                    } else if (testingStep.equals("delete")) {
                      testingStep = "complete";
                      state(
                          getAll().size() == sizeOfEventPayloadDescriptorList - 1,
                          "value not deleted from list");
                      LOGGER.info("**********************************************************");
                      LOGGER.info("EventPayloadDescriptor test completed successfully...");
                      LOGGER.info("**********************************************************\n");
                    }
                  }
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on eventPayloadDescriptor consumed"));
      subscriber
          .eventPayloadDescriptorSubscribe(eventPayloadDescriptorId)
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetOne update received for EventPayloadDescriptor : "
                          + successValue.getEventPayloadDescriptorId()
                          + " in step "
                          + testingStep);
                  testingStep = "delete";
                  sizeOfEventPayloadDescriptorList = getAll().size();
                  delete();
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () ->
                  LOGGER.info(
                      "Subscription on eventPayloadDescriptor for eventPayloadDescriptorId consumed"));

    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
      throw e;
    }
  }

  /** read a EventPayloadDescriptor. */
  protected EventPayloadDescriptor read() throws Throwable {
    LOGGER.info("\n======== READ ======== ");
    LOGGER.info("-- Reading a previously created EventPayloadDescriptor");

    EventPayloadDescriptor entity = null;
    StringBuilder msg =
        new StringBuilder("-- Failed to read EventPayloadDescriptor with primary key");
    msg.append(eventPayloadDescriptorId);

    EventPayloadDescriptorFetchOneSummary fetchOneSummary =
        new EventPayloadDescriptorFetchOneSummary(eventPayloadDescriptorId);

    try {
      entity =
          EventPayloadDescriptorBusinessDelegate.getEventPayloadDescriptorInstance()
              .getEventPayloadDescriptor(fetchOneSummary);

      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Successfully found EventPayloadDescriptor " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(msg.toString() + " : " + e);

      throw e;
    }

    return entity;
  }

  /** updating a EventPayloadDescriptor. */
  protected void update() throws Throwable {
    LOGGER.info("\n======== UPDATE ======== ");
    LOGGER.info("-- Attempting to update a EventPayloadDescriptor.");

    StringBuilder msg = new StringBuilder("Failed to update a EventPayloadDescriptor : ");
    EventPayloadDescriptor entity = read();
    UpdateEventPayloadDescriptorCommand command = generateUpdateCommand();
    command.setEventPayloadDescriptorId(entity.getEventPayloadDescriptorId());

    try {
      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Now updating the created EventPayloadDescriptor.");

      // for use later on...
      eventPayloadDescriptorId = entity.getEventPayloadDescriptorId();

      EventPayloadDescriptorBusinessDelegate proxy =
          EventPayloadDescriptorBusinessDelegate.getEventPayloadDescriptorInstance();

      proxy.updateEventPayloadDescriptor(command).get();

      LOGGER.info("-- Successfully saved EventPayloadDescriptor - " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          msg.toString()
              + " : primarykey = "
              + eventPayloadDescriptorId
              + " : command -"
              + command
              + " : "
              + e);

      throw e;
    }
  }

  /** delete a EventPayloadDescriptor. */
  protected void delete() throws Throwable {
    LOGGER.info("\n======== DELETE ======== ");
    LOGGER.info("-- Deleting a previously created EventPayloadDescriptor.");

    EventPayloadDescriptor entity = null;

    try {
      entity = read();
      LOGGER.info(
          "-- Successfully read EventPayloadDescriptor with id " + eventPayloadDescriptorId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          "-- Failed to read EventPayloadDescriptor with id " + eventPayloadDescriptorId);

      throw e;
    }

    try {
      EventPayloadDescriptorBusinessDelegate.getEventPayloadDescriptorInstance()
          .delete(new DeleteEventPayloadDescriptorCommand(entity.getEventPayloadDescriptorId()))
          .get();
      LOGGER.info(
          "-- Successfully deleted EventPayloadDescriptor with id " + eventPayloadDescriptorId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          "-- Failed to delete EventPayloadDescriptor with id " + eventPayloadDescriptorId);

      throw e;
    }
  }

  /** get all EventPayloadDescriptors. */
  protected List<EventPayloadDescriptor> getAll() throws Throwable {
    LOGGER.info("======== GETALL ======== ");
    LOGGER.info("-- Retrieving Collection of EventPayloadDescriptors:");

    StringBuilder msg = new StringBuilder("-- Failed to get all EventPayloadDescriptor : ");
    List<EventPayloadDescriptor> collection = new ArrayList<>();

    try {
      // call the static get method on the EventPayloadDescriptorBusinessDelegate
      collection =
          EventPayloadDescriptorBusinessDelegate.getEventPayloadDescriptorInstance()
              .getAllEventPayloadDescriptor();
      assertNotNull(
          collection, "An Empty collection of EventPayloadDescriptor was incorrectly returned.");

      // Now print out the values
      EventPayloadDescriptor entity = null;
      Iterator<EventPayloadDescriptor> iter = collection.iterator();
      int index = 1;

      while (iter.hasNext()) {
        // Retrieve the entity
        entity = iter.next();

        assertNotNull(entity, "-- null entity in Collection.");
        assertNotNull(
            entity.getEventPayloadDescriptorId(), "-- entity in Collection has a null primary key");

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
   * @return EventPayloadDescriptorTest
   */
  protected EventPayloadDescriptorTest setHandler(Handler handler) {
    if (handler != null) LOGGER.addHandler(handler);
    return this;
  }

  /**
   * Returns a new populated EventPayloadDescriptor
   *
   * @return CreateEventPayloadDescriptorCommand alias
   */
  protected CreateEventPayloadDescriptorCommand generateNewCommand() {
    CreateEventPayloadDescriptorCommand command =
        new CreateEventPayloadDescriptorCommand(
            null,
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            ObjectType.values()[0]);

    return (command);
  }

  /**
   * Returns a new populated EventPayloadDescriptor
   *
   * @return UpdateEventPayloadDescriptorCommand alias
   */
  protected UpdateEventPayloadDescriptorCommand generateUpdateCommand() {
    UpdateEventPayloadDescriptorCommand command =
        new UpdateEventPayloadDescriptorCommand(
            null,
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            ObjectType.values()[0]);

    return (command);
  }
  // -----------------------------------------------------
  // attributes
  // -----------------------------------------------------
  protected UUID eventPayloadDescriptorId = null;
  protected EventPayloadDescriptorSubscriber subscriber = null;
  private final String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
  private final Logger LOGGER = Logger.getLogger(EventPayloadDescriptorTest.class.getName());
  private String testingStep = "";
  private Integer sizeOfEventPayloadDescriptorList = 0;
}
