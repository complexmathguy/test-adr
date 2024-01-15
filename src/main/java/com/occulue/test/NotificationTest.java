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
 * Test Notification class.
 *
 * @author your_name_here
 */
public class NotificationTest {

  // ------------------------------------
  // default constructor
  // ------------------------------------
  public NotificationTest() {
    subscriber = new NotificationSubscriber();
  }

  // test methods
  @Test
  /*
   * Initiate NotificationTest.
   */
  public void startTest() throws Throwable {
    try {
      LOGGER.info("**********************************************************");
      LOGGER.info("Beginning test on Notification...");
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

  /** jumpstart the process by instantiating2 Notification */
  protected void jumpStart() throws Throwable {
    LOGGER.info("\n======== create instances to get the ball rolling  ======== ");

    notificationId =
        NotificationBusinessDelegate.getNotificationInstance()
            .createNotification(generateNewCommand())
            .get();

    // ---------------------------------------------
    // set up query subscriptions after the 1st create
    // ---------------------------------------------
    testingStep = "create";
    setUpQuerySubscriptions();

    NotificationBusinessDelegate.getNotificationInstance()
        .createNotification(generateNewCommand())
        .get();
  }

  /** Set up query subscriptions */
  protected void setUpQuerySubscriptions() throws Throwable {
    LOGGER.info("\n======== Setting Up Query Subscriptions ======== ");

    try {
      subscriber
          .notificationSubscribe()
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetAll update received for Notification : "
                          + successValue.getNotificationId());
                  if (successValue.getNotificationId().equals(notificationId)) {
                    if (testingStep.equals("create")) {
                      testingStep = "update";
                      update();
                    } else if (testingStep.equals("delete")) {
                      testingStep = "complete";
                      state(
                          getAll().size() == sizeOfNotificationList - 1,
                          "value not deleted from list");
                      LOGGER.info("**********************************************************");
                      LOGGER.info("Notification test completed successfully...");
                      LOGGER.info("**********************************************************\n");
                    }
                  }
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on notification consumed"));
      subscriber
          .notificationSubscribe(notificationId)
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetOne update received for Notification : "
                          + successValue.getNotificationId()
                          + " in step "
                          + testingStep);
                  testingStep = "delete";
                  sizeOfNotificationList = getAll().size();
                  delete();
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on notification for notificationId consumed"));

    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
      throw e;
    }
  }

  /** read a Notification. */
  protected Notification read() throws Throwable {
    LOGGER.info("\n======== READ ======== ");
    LOGGER.info("-- Reading a previously created Notification");

    Notification entity = null;
    StringBuilder msg = new StringBuilder("-- Failed to read Notification with primary key");
    msg.append(notificationId);

    NotificationFetchOneSummary fetchOneSummary = new NotificationFetchOneSummary(notificationId);

    try {
      entity =
          NotificationBusinessDelegate.getNotificationInstance().getNotification(fetchOneSummary);

      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Successfully found Notification " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(msg.toString() + " : " + e);

      throw e;
    }

    return entity;
  }

  /** updating a Notification. */
  protected void update() throws Throwable {
    LOGGER.info("\n======== UPDATE ======== ");
    LOGGER.info("-- Attempting to update a Notification.");

    StringBuilder msg = new StringBuilder("Failed to update a Notification : ");
    Notification entity = read();
    UpdateNotificationCommand command = generateUpdateCommand();
    command.setNotificationId(entity.getNotificationId());

    try {
      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Now updating the created Notification.");

      // for use later on...
      notificationId = entity.getNotificationId();

      NotificationBusinessDelegate proxy = NotificationBusinessDelegate.getNotificationInstance();

      proxy.updateNotification(command).get();

      LOGGER.info("-- Successfully saved Notification - " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          msg.toString()
              + " : primarykey = "
              + notificationId
              + " : command -"
              + command
              + " : "
              + e);

      throw e;
    }
  }

  /** delete a Notification. */
  protected void delete() throws Throwable {
    LOGGER.info("\n======== DELETE ======== ");
    LOGGER.info("-- Deleting a previously created Notification.");

    Notification entity = null;

    try {
      entity = read();
      LOGGER.info("-- Successfully read Notification with id " + notificationId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to read Notification with id " + notificationId);

      throw e;
    }

    try {
      NotificationBusinessDelegate.getNotificationInstance()
          .delete(new DeleteNotificationCommand(entity.getNotificationId()))
          .get();
      LOGGER.info("-- Successfully deleted Notification with id " + notificationId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to delete Notification with id " + notificationId);

      throw e;
    }
  }

  /** get all Notifications. */
  protected List<Notification> getAll() throws Throwable {
    LOGGER.info("======== GETALL ======== ");
    LOGGER.info("-- Retrieving Collection of Notifications:");

    StringBuilder msg = new StringBuilder("-- Failed to get all Notification : ");
    List<Notification> collection = new ArrayList<>();

    try {
      // call the static get method on the NotificationBusinessDelegate
      collection = NotificationBusinessDelegate.getNotificationInstance().getAllNotification();
      assertNotNull(collection, "An Empty collection of Notification was incorrectly returned.");

      // Now print out the values
      Notification entity = null;
      Iterator<Notification> iter = collection.iterator();
      int index = 1;

      while (iter.hasNext()) {
        // Retrieve the entity
        entity = iter.next();

        assertNotNull(entity, "-- null entity in Collection.");
        assertNotNull(entity.getNotificationId(), "-- entity in Collection has a null primary key");

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
   * @return NotificationTest
   */
  protected NotificationTest setHandler(Handler handler) {
    if (handler != null) LOGGER.addHandler(handler);
    return this;
  }

  /**
   * Returns a new populated Notification
   *
   * @return CreateNotificationCommand alias
   */
  protected CreateNotificationCommand generateNewCommand() {
    CreateNotificationCommand command =
        new CreateNotificationCommand(null, ObjectType.values()[0], Operation.values()[0]);

    return (command);
  }

  /**
   * Returns a new populated Notification
   *
   * @return UpdateNotificationCommand alias
   */
  protected UpdateNotificationCommand generateUpdateCommand() {
    UpdateNotificationCommand command =
        new UpdateNotificationCommand(
            null, null, ObjectType.values()[0], Operation.values()[0], null);

    return (command);
  }
  // -----------------------------------------------------
  // attributes
  // -----------------------------------------------------
  protected UUID notificationId = null;
  protected NotificationSubscriber subscriber = null;
  private final String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
  private final Logger LOGGER = Logger.getLogger(NotificationTest.class.getName());
  private String testingStep = "";
  private Integer sizeOfNotificationList = 0;
}
