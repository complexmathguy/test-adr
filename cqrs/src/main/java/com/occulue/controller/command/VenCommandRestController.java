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
package com.occulue.controller.command;

import com.occulue.api.*;
import com.occulue.controller.*;
import com.occulue.delegate.*;
import com.occulue.entity.*;
import com.occulue.exception.*;
import com.occulue.projector.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.*;

/**
 * Implements Spring Controller command CQRS processing for entity Ven.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/Ven")
public class VenCommandRestController extends BaseSpringRestController {

  /**
   * Handles create a Ven. if not key provided, calls create, otherwise calls save
   *
   * @param Ven ven
   * @return CompletableFuture<UUID>
   */
  @PostMapping("/create")
  public CompletableFuture<UUID> create(@RequestBody(required = true) CreateVenCommand command) {
    CompletableFuture<UUID> completableFuture = null;
    try {

      completableFuture = VenBusinessDelegate.getVenInstance().createVen(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage(), exc);
    }

    return completableFuture;
  }

  /**
   * Handles updating a Ven. if no key provided, calls create, otherwise calls save
   *
   * @param Ven ven
   * @return CompletableFuture<Void>
   */
  @PutMapping("/update")
  public CompletableFuture<Void> update(@RequestBody(required = true) UpdateVenCommand command) {
    CompletableFuture<Void> completableFuture = null;
    try {
      // -----------------------------------------------
      // delegate the UpdateVenCommand
      // -----------------------------------------------
      completableFuture = VenBusinessDelegate.getVenInstance().updateVen(command);
      ;
    } catch (Throwable exc) {
      LOGGER.log(
          Level.WARNING, "VenController:update() - successfully update Ven - " + exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * Handles deleting a Ven entity
   *
   * @param command ${class.getDeleteCommandAlias()}
   * @return CompletableFuture<Void>
   */
  @DeleteMapping("/delete")
  public CompletableFuture<Void> delete(@RequestParam(required = true) UUID venId) {
    CompletableFuture<Void> completableFuture = null;
    DeleteVenCommand command = new DeleteVenCommand(venId);

    try {
      VenBusinessDelegate delegate = VenBusinessDelegate.getVenInstance();

      completableFuture = delegate.delete(command);
      LOGGER.log(Level.WARNING, "Successfully deleted Ven with key " + command.getVenId());
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * save Attributes on Ven
   *
   * @param command AssignAttributesToVenCommand
   */
  @PutMapping("/addToAttributes")
  public void addToAttributes(@RequestBody(required = true) AssignAttributesToVenCommand command) {
    try {
      VenBusinessDelegate.getVenInstance().addToAttributes(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to add to Set Attributes", exc);
    }
  }

  /**
   * remove Attributes on Ven
   *
   * @param command RemoveAttributesFromVenCommand
   */
  @PutMapping("/removeFromAttributes")
  public void removeFromAttributes(
      @RequestBody(required = true) RemoveAttributesFromVenCommand command) {
    try {
      VenBusinessDelegate.getVenInstance().removeFromAttributes(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to remove from Set Attributes", exc);
    }
  }

  /**
   * save Targets on Ven
   *
   * @param command AssignTargetsToVenCommand
   */
  @PutMapping("/addToTargets")
  public void addToTargets(@RequestBody(required = true) AssignTargetsToVenCommand command) {
    try {
      VenBusinessDelegate.getVenInstance().addToTargets(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to add to Set Targets", exc);
    }
  }

  /**
   * remove Targets on Ven
   *
   * @param command RemoveTargetsFromVenCommand
   */
  @PutMapping("/removeFromTargets")
  public void removeFromTargets(@RequestBody(required = true) RemoveTargetsFromVenCommand command) {
    try {
      VenBusinessDelegate.getVenInstance().removeFromTargets(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to remove from Set Targets", exc);
    }
  }

  /**
   * save Resources on Ven
   *
   * @param command AssignResourcesToVenCommand
   */
  @PutMapping("/addToResources")
  public void addToResources(@RequestBody(required = true) AssignResourcesToVenCommand command) {
    try {
      VenBusinessDelegate.getVenInstance().addToResources(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to add to Set Resources", exc);
    }
  }

  /**
   * remove Resources on Ven
   *
   * @param command RemoveResourcesFromVenCommand
   */
  @PutMapping("/removeFromResources")
  public void removeFromResources(
      @RequestBody(required = true) RemoveResourcesFromVenCommand command) {
    try {
      VenBusinessDelegate.getVenInstance().removeFromResources(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to remove from Set Resources", exc);
    }
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected Ven ven = null;
  private static final Logger LOGGER = Logger.getLogger(VenCommandRestController.class.getName());
}
