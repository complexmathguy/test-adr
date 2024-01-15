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
 * Test ObjectOperation class.
 *
 * @author your_name_here
 */
public class ObjectOperationTest {

  // ------------------------------------
  // default constructor
  // ------------------------------------
  public ObjectOperationTest() {
    subscriber = new ObjectOperationSubscriber();
  }

  // test methods
  @Test
  /*
   * Initiate ObjectOperationTest.
   */
  public void startTest() throws Throwable {
    try {
      LOGGER.info("**********************************************************");
      LOGGER.info("Beginning test on ObjectOperation...");
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

  /** jumpstart the process by instantiating2 ObjectOperation */
  protected void jumpStart() throws Throwable {
    LOGGER.info("\n======== create instances to get the ball rolling  ======== ");

    objectOperationId =
        ObjectOperationBusinessDelegate.getObjectOperationInstance()
            .createObjectOperation(generateNewCommand())
            .get();

    // ---------------------------------------------
    // set up query subscriptions after the 1st create
    // ---------------------------------------------
    testingStep = "create";
    setUpQuerySubscriptions();

    ObjectOperationBusinessDelegate.getObjectOperationInstance()
        .createObjectOperation(generateNewCommand())
        .get();
  }

  /** Set up query subscriptions */
  protected void setUpQuerySubscriptions() throws Throwable {
    LOGGER.info("\n======== Setting Up Query Subscriptions ======== ");

    try {
      subscriber
          .objectOperationSubscribe()
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetAll update received for ObjectOperation : "
                          + successValue.getObjectOperationId());
                  if (successValue.getObjectOperationId().equals(objectOperationId)) {
                    if (testingStep.equals("create")) {
                      testingStep = "update";
                      update();
                    } else if (testingStep.equals("delete")) {
                      testingStep = "complete";
                      state(
                          getAll().size() == sizeOfObjectOperationList - 1,
                          "value not deleted from list");
                      LOGGER.info("**********************************************************");
                      LOGGER.info("ObjectOperation test completed successfully...");
                      LOGGER.info("**********************************************************\n");
                    }
                  }
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on objectOperation consumed"));
      subscriber
          .objectOperationSubscribe(objectOperationId)
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetOne update received for ObjectOperation : "
                          + successValue.getObjectOperationId()
                          + " in step "
                          + testingStep);
                  testingStep = "delete";
                  sizeOfObjectOperationList = getAll().size();
                  delete();
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on objectOperation for objectOperationId consumed"));

    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
      throw e;
    }
  }

  /** read a ObjectOperation. */
  protected ObjectOperation read() throws Throwable {
    LOGGER.info("\n======== READ ======== ");
    LOGGER.info("-- Reading a previously created ObjectOperation");

    ObjectOperation entity = null;
    StringBuilder msg = new StringBuilder("-- Failed to read ObjectOperation with primary key");
    msg.append(objectOperationId);

    ObjectOperationFetchOneSummary fetchOneSummary =
        new ObjectOperationFetchOneSummary(objectOperationId);

    try {
      entity =
          ObjectOperationBusinessDelegate.getObjectOperationInstance()
              .getObjectOperation(fetchOneSummary);

      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Successfully found ObjectOperation " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(msg.toString() + " : " + e);

      throw e;
    }

    return entity;
  }

  /** updating a ObjectOperation. */
  protected void update() throws Throwable {
    LOGGER.info("\n======== UPDATE ======== ");
    LOGGER.info("-- Attempting to update a ObjectOperation.");

    StringBuilder msg = new StringBuilder("Failed to update a ObjectOperation : ");
    ObjectOperation entity = read();
    UpdateObjectOperationCommand command = generateUpdateCommand();
    command.setObjectOperationId(entity.getObjectOperationId());

    try {
      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Now updating the created ObjectOperation.");

      // for use later on...
      objectOperationId = entity.getObjectOperationId();

      ObjectOperationBusinessDelegate proxy =
          ObjectOperationBusinessDelegate.getObjectOperationInstance();

      proxy.updateObjectOperation(command).get();

      LOGGER.info("-- Successfully saved ObjectOperation - " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          msg.toString()
              + " : primarykey = "
              + objectOperationId
              + " : command -"
              + command
              + " : "
              + e);

      throw e;
    }
  }

  /** delete a ObjectOperation. */
  protected void delete() throws Throwable {
    LOGGER.info("\n======== DELETE ======== ");
    LOGGER.info("-- Deleting a previously created ObjectOperation.");

    ObjectOperation entity = null;

    try {
      entity = read();
      LOGGER.info("-- Successfully read ObjectOperation with id " + objectOperationId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to read ObjectOperation with id " + objectOperationId);

      throw e;
    }

    try {
      ObjectOperationBusinessDelegate.getObjectOperationInstance()
          .delete(new DeleteObjectOperationCommand(entity.getObjectOperationId()))
          .get();
      LOGGER.info("-- Successfully deleted ObjectOperation with id " + objectOperationId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to delete ObjectOperation with id " + objectOperationId);

      throw e;
    }
  }

  /** get all ObjectOperations. */
  protected List<ObjectOperation> getAll() throws Throwable {
    LOGGER.info("======== GETALL ======== ");
    LOGGER.info("-- Retrieving Collection of ObjectOperations:");

    StringBuilder msg = new StringBuilder("-- Failed to get all ObjectOperation : ");
    List<ObjectOperation> collection = new ArrayList<>();

    try {
      // call the static get method on the ObjectOperationBusinessDelegate
      collection =
          ObjectOperationBusinessDelegate.getObjectOperationInstance().getAllObjectOperation();
      assertNotNull(collection, "An Empty collection of ObjectOperation was incorrectly returned.");

      // Now print out the values
      ObjectOperation entity = null;
      Iterator<ObjectOperation> iter = collection.iterator();
      int index = 1;

      while (iter.hasNext()) {
        // Retrieve the entity
        entity = iter.next();

        assertNotNull(entity, "-- null entity in Collection.");
        assertNotNull(
            entity.getObjectOperationId(), "-- entity in Collection has a null primary key");

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
   * @return ObjectOperationTest
   */
  protected ObjectOperationTest setHandler(Handler handler) {
    if (handler != null) LOGGER.addHandler(handler);
    return this;
  }

  /**
   * Returns a new populated ObjectOperation
   *
   * @return CreateObjectOperationCommand alias
   */
  protected CreateObjectOperationCommand generateNewCommand() {
    CreateObjectOperationCommand command =
        new CreateObjectOperationCommand(
            null,
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            ObjectType.values()[0],
            Operation.values()[0]);

    return (command);
  }

  /**
   * Returns a new populated ObjectOperation
   *
   * @return UpdateObjectOperationCommand alias
   */
  protected UpdateObjectOperationCommand generateUpdateCommand() {
    UpdateObjectOperationCommand command =
        new UpdateObjectOperationCommand(
            null,
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            ObjectType.values()[0],
            Operation.values()[0]);

    return (command);
  }
  // -----------------------------------------------------
  // attributes
  // -----------------------------------------------------
  protected UUID objectOperationId = null;
  protected ObjectOperationSubscriber subscriber = null;
  private final String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
  private final Logger LOGGER = Logger.getLogger(ObjectOperationTest.class.getName());
  private String testingStep = "";
  private Integer sizeOfObjectOperationList = 0;
}
