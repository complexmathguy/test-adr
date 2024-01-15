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
 * Test Ven class.
 *
 * @author your_name_here
 */
public class VenTest {

  // ------------------------------------
  // default constructor
  // ------------------------------------
  public VenTest() {
    subscriber = new VenSubscriber();
  }

  // test methods
  @Test
  /*
   * Initiate VenTest.
   */
  public void startTest() throws Throwable {
    try {
      LOGGER.info("**********************************************************");
      LOGGER.info("Beginning test on Ven...");
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

  /** jumpstart the process by instantiating2 Ven */
  protected void jumpStart() throws Throwable {
    LOGGER.info("\n======== create instances to get the ball rolling  ======== ");

    venId = VenBusinessDelegate.getVenInstance().createVen(generateNewCommand()).get();

    // ---------------------------------------------
    // set up query subscriptions after the 1st create
    // ---------------------------------------------
    testingStep = "create";
    setUpQuerySubscriptions();

    VenBusinessDelegate.getVenInstance().createVen(generateNewCommand()).get();
  }

  /** Set up query subscriptions */
  protected void setUpQuerySubscriptions() throws Throwable {
    LOGGER.info("\n======== Setting Up Query Subscriptions ======== ");

    try {
      subscriber
          .venSubscribe()
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info("GetAll update received for Ven : " + successValue.getVenId());
                  if (successValue.getVenId().equals(venId)) {
                    if (testingStep.equals("create")) {
                      testingStep = "update";
                      update();
                    } else if (testingStep.equals("delete")) {
                      testingStep = "complete";
                      state(getAll().size() == sizeOfVenList - 1, "value not deleted from list");
                      LOGGER.info("**********************************************************");
                      LOGGER.info("Ven test completed successfully...");
                      LOGGER.info("**********************************************************\n");
                    }
                  }
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on ven consumed"));
      subscriber
          .venSubscribe(venId)
          .updates()
          .subscribe(
              successValue -> {
                LOGGER.info(successValue.toString());
                try {
                  LOGGER.info(
                      "GetOne update received for Ven : "
                          + successValue.getVenId()
                          + " in step "
                          + testingStep);
                  testingStep = "delete";
                  sizeOfVenList = getAll().size();
                  delete();
                } catch (Throwable exc) {
                  LOGGER.warning(exc.getMessage());
                }
              },
              error -> LOGGER.warning(error.getMessage()),
              () -> LOGGER.info("Subscription on ven for venId consumed"));

    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
      throw e;
    }
  }

  /** read a Ven. */
  protected Ven read() throws Throwable {
    LOGGER.info("\n======== READ ======== ");
    LOGGER.info("-- Reading a previously created Ven");

    Ven entity = null;
    StringBuilder msg = new StringBuilder("-- Failed to read Ven with primary key");
    msg.append(venId);

    VenFetchOneSummary fetchOneSummary = new VenFetchOneSummary(venId);

    try {
      entity = VenBusinessDelegate.getVenInstance().getVen(fetchOneSummary);

      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Successfully found Ven " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(msg.toString() + " : " + e);

      throw e;
    }

    return entity;
  }

  /** updating a Ven. */
  protected void update() throws Throwable {
    LOGGER.info("\n======== UPDATE ======== ");
    LOGGER.info("-- Attempting to update a Ven.");

    StringBuilder msg = new StringBuilder("Failed to update a Ven : ");
    Ven entity = read();
    UpdateVenCommand command = generateUpdateCommand();
    command.setVenId(entity.getVenId());

    try {
      assertNotNull(entity, msg.toString());

      LOGGER.info("-- Now updating the created Ven.");

      // for use later on...
      venId = entity.getVenId();

      VenBusinessDelegate proxy = VenBusinessDelegate.getVenInstance();

      proxy.updateVen(command).get();

      LOGGER.info("-- Successfully saved Ven - " + entity.toString());
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning(
          msg.toString() + " : primarykey = " + venId + " : command -" + command + " : " + e);

      throw e;
    }
  }

  /** delete a Ven. */
  protected void delete() throws Throwable {
    LOGGER.info("\n======== DELETE ======== ");
    LOGGER.info("-- Deleting a previously created Ven.");

    Ven entity = null;

    try {
      entity = read();
      LOGGER.info("-- Successfully read Ven with id " + venId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to read Ven with id " + venId);

      throw e;
    }

    try {
      VenBusinessDelegate.getVenInstance().delete(new DeleteVenCommand(entity.getVenId())).get();
      LOGGER.info("-- Successfully deleted Ven with id " + venId);
    } catch (Throwable e) {
      LOGGER.warning(unexpectedErrorMsg);
      LOGGER.warning("-- Failed to delete Ven with id " + venId);

      throw e;
    }
  }

  /** get all Vens. */
  protected List<Ven> getAll() throws Throwable {
    LOGGER.info("======== GETALL ======== ");
    LOGGER.info("-- Retrieving Collection of Vens:");

    StringBuilder msg = new StringBuilder("-- Failed to get all Ven : ");
    List<Ven> collection = new ArrayList<>();

    try {
      // call the static get method on the VenBusinessDelegate
      collection = VenBusinessDelegate.getVenInstance().getAllVen();
      assertNotNull(collection, "An Empty collection of Ven was incorrectly returned.");

      // Now print out the values
      Ven entity = null;
      Iterator<Ven> iter = collection.iterator();
      int index = 1;

      while (iter.hasNext()) {
        // Retrieve the entity
        entity = iter.next();

        assertNotNull(entity, "-- null entity in Collection.");
        assertNotNull(entity.getVenId(), "-- entity in Collection has a null primary key");

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
   * @return VenTest
   */
  protected VenTest setHandler(Handler handler) {
    if (handler != null) LOGGER.addHandler(handler);
    return this;
  }

  /**
   * Returns a new populated Ven
   *
   * @return CreateVenCommand alias
   */
  protected CreateVenCommand generateNewCommand() {
    CreateVenCommand command =
        new CreateVenCommand(
            null,
            new Date(),
            new Date(),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            ObjectType.values()[0]);

    return (command);
  }

  /**
   * Returns a new populated Ven
   *
   * @return UpdateVenCommand alias
   */
  protected UpdateVenCommand generateUpdateCommand() {
    UpdateVenCommand command =
        new UpdateVenCommand(
            null,
            new Date(),
            new Date(),
            org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(16),
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>(),
            ObjectType.values()[0]);

    return (command);
  }
  // -----------------------------------------------------
  // attributes
  // -----------------------------------------------------
  protected UUID venId = null;
  protected VenSubscriber subscriber = null;
  private final String unexpectedErrorMsg = ":::::::::::::: Unexpected Error :::::::::::::::::";
  private final Logger LOGGER = Logger.getLogger(VenTest.class.getName());
  private String testingStep = "";
  private Integer sizeOfVenList = 0;
}
