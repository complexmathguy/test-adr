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
 * Test Resource class.
 *
 * @author your_name_here
 */
public class ResourceTest {

  // ------------------------------------
  // default constructor
  // ------------------------------------
  public ResourceTest() {
    subscriber = new ResourceSubscriber();
  }

  // test methods
  @Test
  /*
   * Initiate ResourceTest.
   */
  public void startTest() throws Throwable {
    try {
      LOGGER.info("**********************************************************");
      LOGGER.info("Beginning test on Resource...");
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

  /** jumpstart the process by instantiating2 Resource */
  protected void jumpStart() throws Throwable {
    LOGGER.info("\n======== create instances to get the ball rolling  ======== ");

    resourceId =
        ResourceBusinessDelegate.getResourceInstance().createResource(generateNewCommand()).get();

    // ---------------------------------------------
    // set up query subscriptions after the 1st create
    // ---------------------------------------------
    testingStep = "create";
    setUpQuerySubscriptions();

    ResourceBusinessDelegate.getResourceInstance().createResource(generateNewCommand()).get();
  }

  /** Set up query subscriptions */
  protected void setUpQuerySubscriptions() throws Throwable {
    LOGGER.info("\n======== Setting Up Query Subscriptions ======== ");

    try {
      subscriber
          .resourceSubscribe()
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetAll update received for Resource : " + successValue.getResourceId());
                  if (successValue.getResourceId().equals(resourceId)) {
                    if (testingStep.equals("create")) {
                      testingStep = "update";
                      update();
                    } else if (testingStep.equals("delete")) {
                      testingStep = "complete";
                      state(
                          getAll().size() == sizeOfResourceList - 1, "value not deleted from list");
                      LOGGER.info("**********************************************************");
                      LOGGER.info("Resource test completed successfully...");
                      LOGGER.info("**********************************************************\n");
                    }
                  }
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on resource consumed"));
      subscriber
          .resourceSubscribe(resourceId)
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetOne update received for Resource : "
                          + successValue.getResourceId()
                          + " in step "
                          + testingStep);
                  testingStep = "delete";
                  sizeOfResourceList = getAll().size();
                  delete();
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on resource for resourceId consumed"));

    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
      throw e;
    }
  }

  /** read a Resource. */
  protected Resource read() throws Throwable {
    LOGGER.info("\n======== READ ======== ");
    LOGGER.info("-- Reading a previously created Resource");

    Resource entity = null;
    StringBuilder msg = new StringBuilder("-- Failed to read Resource with primary key");
    msg.append(resourceId);

    ResourceFetchOneSummary fetchOneSummary = new ResourceFetchOneSummary(resourceId);

    try {
      entity = ResourceBusinessDelegate.getResourceInstance().getResource(fetchOneSummary);

      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Successfully found Resource " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(msg.toString() + " : " + e);

      throw e;
    }

    return entity;
  }

  /** updating a Resource. */
  protected void update() throws Throwable {
    LOGGER.info("\n======== UPDATE ======== ");
    LOGGER.info("-- Attempting to update a Resource.");

    StringBuilder msg = new StringBuilder("Failed to update a Resource : ");
    Resource entity = read();
    UpdateResourceCommand command = generateUpdateCommand();
    command.setResourceId(entity.getResourceId());

    try {
      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Now updating the created Resource.");

      // for use later on...
      resourceId = entity.getResourceId();

      ResourceBusinessDelegate proxy = ResourceBusinessDelegate.getResourceInstance();

      proxy.updateResource(command).get();

      LOGGER.info("-- Successfully saved Resource - " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          msg.toString() + " : primarykey = " + resourceId + " : command -" + command + " : " + e);

      throw e;
    }
  }

  /** delete a Resource. */
  protected void delete() throws Throwable {
    LOGGER.info("\n======== DELETE ======== ");
    LOGGER.info("-- Deleting a previously created Resource.");

    Resource entity = null;

    try {
      entity = read();
      LOGGER.info("-- Successfully read Resource with id " + resourceId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to read Resource with id " + resourceId);

      throw e;
    }

    try {
      ResourceBusinessDelegate.getResourceInstance()
          .delete(new DeleteResourceCommand(entity.getResourceId()))
          .get();
      LOGGER.info("-- Successfully deleted Resource with id " + resourceId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to delete Resource with id " + resourceId);

      throw e;
    }
  }

  /** get all Resources. */
  protected List<Resource> getAll() throws Throwable {
    LOGGER.info("======== GETALL ======== ");
    LOGGER.info("-- Retrieving Collection of Resources:");

    StringBuilder msg = new StringBuilder("-- Failed to get all Resource : ");
    List<Resource> collection = new ArrayList<>();

    try {
      // call the static get method on the ResourceBusinessDelegate
      collection = ResourceBusinessDelegate.getResourceInstance().getAllResource();
      assertNotNull(collection, "An Empty collection of Resource was incorrectly returned.");

      // Now print out the values
      Resource entity = null;
      Iterator<Resource> iter = collection.iterator();
      int index = 1;

      while (iter.hasNext()) {
        // Retrieve the entity
        entity = iter.next();

        assertNotNull(entity, "-- null entity in Collection.");
        assertNotNull(entity.getResourceId(), "-- entity in Collection has a null primary key");

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
   * @return ResourceTest
   */
  protected ResourceTest setHandler(Handler handler) {
    if (handler != null) LOGGER.addHandler(handler);
    return this;
  }

  /**
   * Returns a new populated Resource
   *
   * @return CreateResourceCommand alias
   */
  protected CreateResourceCommand generateNewCommand() {
    CreateResourceCommand command =
        new CreateResourceCommand(
            null,
            new Date(),
            new Date(),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            ObjectType.values()[0]);

    return (command);
  }

  /**
   * Returns a new populated Resource
   *
   * @return UpdateResourceCommand alias
   */
  protected UpdateResourceCommand generateUpdateCommand() {
    UpdateResourceCommand command =
        new UpdateResourceCommand(
            null,
            new Date(),
            new Date(),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            null,
            new HashSet<>(),
            new HashSet<>(),
            ObjectType.values()[0]);

    return (command);
  }
  // -----------------------------------------------------
  // attributes
  // -----------------------------------------------------
  protected UUID resourceId = null;
  protected ResourceSubscriber subscriber = null;
  private final String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
  private final Logger LOGGER = Logger.getLogger(ResourceTest.class.getName());
  private String testingStep = "";
  private Integer sizeOfResourceList = 0;
}
