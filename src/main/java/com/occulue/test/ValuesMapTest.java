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
 * Test ValuesMap class.
 *
 * @author your_name_here
 */
public class ValuesMapTest {

  // ------------------------------------
  // default constructor
  // ------------------------------------
  public ValuesMapTest() {
    subscriber = new ValuesMapSubscriber();
  }

  // test methods
  @Test
  /*
   * Initiate ValuesMapTest.
   */
  public void startTest() throws Throwable {
    try {
      LOGGER.info("**********************************************************");
      LOGGER.info("Beginning test on ValuesMap...");
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

  /** jumpstart the process by instantiating2 ValuesMap */
  protected void jumpStart() throws Throwable {
    LOGGER.info("\n======== create instances to get the ball rolling  ======== ");

    valuesMapId =
        ValuesMapBusinessDelegate.getValuesMapInstance()
            .createValuesMap(generateNewCommand())
            .get();

    // ---------------------------------------------
    // set up query subscriptions after the 1st create
    // ---------------------------------------------
    testingStep = "create";
    setUpQuerySubscriptions();

    ValuesMapBusinessDelegate.getValuesMapInstance().createValuesMap(generateNewCommand()).get();
  }

  /** Set up query subscriptions */
  protected void setUpQuerySubscriptions() throws Throwable {
    LOGGER.info("\n======== Setting Up Query Subscriptions ======== ");

    try {
      subscriber
          .valuesMapSubscribe()
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetAll update received for ValuesMap : " + successValue.getValuesMapId());
                  if (successValue.getValuesMapId().equals(valuesMapId)) {
                    if (testingStep.equals("create")) {
                      testingStep = "update";
                      update();
                    } else if (testingStep.equals("delete")) {
                      testingStep = "complete";
                      state(
                          getAll().size() == sizeOfValuesMapList - 1,
                          "value not deleted from list");
                      LOGGER.info("**********************************************************");
                      LOGGER.info("ValuesMap test completed successfully...");
                      LOGGER.info("**********************************************************\n");
                    }
                  }
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on valuesMap consumed"));
      subscriber
          .valuesMapSubscribe(valuesMapId)
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetOne update received for ValuesMap : "
                          + successValue.getValuesMapId()
                          + " in step "
                          + testingStep);
                  testingStep = "delete";
                  sizeOfValuesMapList = getAll().size();
                  delete();
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on valuesMap for valuesMapId consumed"));

    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
      throw e;
    }
  }

  /** read a ValuesMap. */
  protected ValuesMap read() throws Throwable {
    LOGGER.info("\n======== READ ======== ");
    LOGGER.info("-- Reading a previously created ValuesMap");

    ValuesMap entity = null;
    StringBuilder msg = new StringBuilder("-- Failed to read ValuesMap with primary key");
    msg.append(valuesMapId);

    ValuesMapFetchOneSummary fetchOneSummary = new ValuesMapFetchOneSummary(valuesMapId);

    try {
      entity = ValuesMapBusinessDelegate.getValuesMapInstance().getValuesMap(fetchOneSummary);

      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Successfully found ValuesMap " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(msg.toString() + " : " + e);

      throw e;
    }

    return entity;
  }

  /** updating a ValuesMap. */
  protected void update() throws Throwable {
    LOGGER.info("\n======== UPDATE ======== ");
    LOGGER.info("-- Attempting to update a ValuesMap.");

    StringBuilder msg = new StringBuilder("Failed to update a ValuesMap : ");
    ValuesMap entity = read();
    UpdateValuesMapCommand command = generateUpdateCommand();
    command.setValuesMapId(entity.getValuesMapId());

    try {
      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Now updating the created ValuesMap.");

      // for use later on...
      valuesMapId = entity.getValuesMapId();

      ValuesMapBusinessDelegate proxy = ValuesMapBusinessDelegate.getValuesMapInstance();

      proxy.updateValuesMap(command).get();

      LOGGER.info("-- Successfully saved ValuesMap - " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          msg.toString() + " : primarykey = " + valuesMapId + " : command -" + command + " : " + e);

      throw e;
    }
  }

  /** delete a ValuesMap. */
  protected void delete() throws Throwable {
    LOGGER.info("\n======== DELETE ======== ");
    LOGGER.info("-- Deleting a previously created ValuesMap.");

    ValuesMap entity = null;

    try {
      entity = read();
      LOGGER.info("-- Successfully read ValuesMap with id " + valuesMapId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to read ValuesMap with id " + valuesMapId);

      throw e;
    }

    try {
      ValuesMapBusinessDelegate.getValuesMapInstance()
          .delete(new DeleteValuesMapCommand(entity.getValuesMapId()))
          .get();
      LOGGER.info("-- Successfully deleted ValuesMap with id " + valuesMapId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to delete ValuesMap with id " + valuesMapId);

      throw e;
    }
  }

  /** get all ValuesMaps. */
  protected List<ValuesMap> getAll() throws Throwable {
    LOGGER.info("======== GETALL ======== ");
    LOGGER.info("-- Retrieving Collection of ValuesMaps:");

    StringBuilder msg = new StringBuilder("-- Failed to get all ValuesMap : ");
    List<ValuesMap> collection = new ArrayList<>();

    try {
      // call the static get method on the ValuesMapBusinessDelegate
      collection = ValuesMapBusinessDelegate.getValuesMapInstance().getAllValuesMap();
      assertNotNull(collection, "An Empty collection of ValuesMap was incorrectly returned.");

      // Now print out the values
      ValuesMap entity = null;
      Iterator<ValuesMap> iter = collection.iterator();
      int index = 1;

      while (iter.hasNext()) {
        // Retrieve the entity
        entity = iter.next();

        assertNotNull(entity, "-- null entity in Collection.");
        assertNotNull(entity.getValuesMapId(), "-- entity in Collection has a null primary key");

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
   * @return ValuesMapTest
   */
  protected ValuesMapTest setHandler(Handler handler) {
    if (handler != null) LOGGER.addHandler(handler);
    return this;
  }

  /**
   * Returns a new populated ValuesMap
   *
   * @return CreateValuesMapCommand alias
   */
  protected CreateValuesMapCommand generateNewCommand() {
    CreateValuesMapCommand command =
        new CreateValuesMapCommand(
            null, org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16), null);

    return (command);
  }

  /**
   * Returns a new populated ValuesMap
   *
   * @return UpdateValuesMapCommand alias
   */
  protected UpdateValuesMapCommand generateUpdateCommand() {
    UpdateValuesMapCommand command =
        new UpdateValuesMapCommand(
            null, org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16), null);

    return (command);
  }
  // -----------------------------------------------------
  // attributes
  // -----------------------------------------------------
  protected UUID valuesMapId = null;
  protected ValuesMapSubscriber subscriber = null;
  private final String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
  private final Logger LOGGER = Logger.getLogger(ValuesMapTest.class.getName());
  private String testingStep = "";
  private Integer sizeOfValuesMapList = 0;
}
