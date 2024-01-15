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
 * Implements Spring Controller command CQRS processing for entity Resource.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/Resource")
public class ResourceCommandRestController extends BaseSpringRestController {

  /**
   * Handles create a Resource. if not key provided, calls create, otherwise calls save
   *
   * @param Resource resource
   * @return CompletableFuture<UUID>
   */
  @PostMapping("/create")
  public CompletableFuture<UUID> create(
      @RequestBody(required = true) CreateResourceCommand command) {
    CompletableFuture<UUID> completableFuture = null;
    try {

      completableFuture = ResourceBusinessDelegate.getResourceInstance().createResource(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage(), exc);
    }

    return completableFuture;
  }

  /**
   * Handles updating a Resource. if no key provided, calls create, otherwise calls save
   *
   * @param Resource resource
   * @return CompletableFuture<Void>
   */
  @PutMapping("/update")
  public CompletableFuture<Void> update(
      @RequestBody(required = true) UpdateResourceCommand command) {
    CompletableFuture<Void> completableFuture = null;
    try {
      // -----------------------------------------------
      // delegate the UpdateResourceCommand
      // -----------------------------------------------
      completableFuture = ResourceBusinessDelegate.getResourceInstance().updateResource(command);
      ;
    } catch (Throwable exc) {
      LOGGER.log(
          Level.WARNING,
          "ResourceController:update() - successfully update Resource - " + exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * Handles deleting a Resource entity
   *
   * @param command ${class.getDeleteCommandAlias()}
   * @return CompletableFuture<Void>
   */
  @DeleteMapping("/delete")
  public CompletableFuture<Void> delete(@RequestParam(required = true) UUID resourceId) {
    CompletableFuture<Void> completableFuture = null;
    DeleteResourceCommand command = new DeleteResourceCommand(resourceId);

    try {
      ResourceBusinessDelegate delegate = ResourceBusinessDelegate.getResourceInstance();

      completableFuture = delegate.delete(command);
      LOGGER.log(
          Level.WARNING, "Successfully deleted Resource with key " + command.getResourceId());
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * save Ven on Resource
   *
   * @param command AssignVenToResourceCommand
   */
  @PutMapping("/assignVen")
  public void assignVen(@RequestBody AssignVenToResourceCommand command) {
    try {
      ResourceBusinessDelegate.getResourceInstance().assignVen(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "Failed to assign Ven", exc);
    }
  }

  /**
   * unassign Ven on Resource
   *
   * @param command UnAssignVenFromResourceCommand
   */
  @PutMapping("/unAssignVen")
  public void unAssignVen(@RequestBody(required = true) UnAssignVenFromResourceCommand command) {
    try {
      ResourceBusinessDelegate.getResourceInstance().unAssignVen(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to unassign Ven", exc);
    }
  }

  /**
   * save Attributes on Resource
   *
   * @param command AssignAttributesToResourceCommand
   */
  @PutMapping("/addToAttributes")
  public void addToAttributes(
      @RequestBody(required = true) AssignAttributesToResourceCommand command) {
    try {
      ResourceBusinessDelegate.getResourceInstance().addToAttributes(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to add to Set Attributes", exc);
    }
  }

  /**
   * remove Attributes on Resource
   *
   * @param command RemoveAttributesFromResourceCommand
   */
  @PutMapping("/removeFromAttributes")
  public void removeFromAttributes(
      @RequestBody(required = true) RemoveAttributesFromResourceCommand command) {
    try {
      ResourceBusinessDelegate.getResourceInstance().removeFromAttributes(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to remove from Set Attributes", exc);
    }
  }

  /**
   * save Targets on Resource
   *
   * @param command AssignTargetsToResourceCommand
   */
  @PutMapping("/addToTargets")
  public void addToTargets(@RequestBody(required = true) AssignTargetsToResourceCommand command) {
    try {
      ResourceBusinessDelegate.getResourceInstance().addToTargets(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to add to Set Targets", exc);
    }
  }

  /**
   * remove Targets on Resource
   *
   * @param command RemoveTargetsFromResourceCommand
   */
  @PutMapping("/removeFromTargets")
  public void removeFromTargets(
      @RequestBody(required = true) RemoveTargetsFromResourceCommand command) {
    try {
      ResourceBusinessDelegate.getResourceInstance().removeFromTargets(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to remove from Set Targets", exc);
    }
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected Resource resource = null;
  private static final Logger LOGGER =
      Logger.getLogger(ResourceCommandRestController.class.getName());
}
