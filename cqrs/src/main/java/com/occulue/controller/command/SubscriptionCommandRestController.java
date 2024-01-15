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
 * Implements Spring Controller command CQRS processing for entity Subscription.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/Subscription")
public class SubscriptionCommandRestController extends BaseSpringRestController {

  /**
   * Handles create a Subscription. if not key provided, calls create, otherwise calls save
   *
   * @param Subscription subscription
   * @return CompletableFuture<UUID>
   */
  @PostMapping("/create")
  public CompletableFuture<UUID> create(
      @RequestBody(required = true) CreateSubscriptionCommand command) {
    CompletableFuture<UUID> completableFuture = null;
    try {

      completableFuture =
          SubscriptionBusinessDelegate.getSubscriptionInstance().createSubscription(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage(), exc);
    }

    return completableFuture;
  }

  /**
   * Handles updating a Subscription. if no key provided, calls create, otherwise calls save
   *
   * @param Subscription subscription
   * @return CompletableFuture<Void>
   */
  @PutMapping("/update")
  public CompletableFuture<Void> update(
      @RequestBody(required = true) UpdateSubscriptionCommand command) {
    CompletableFuture<Void> completableFuture = null;
    try {
      // -----------------------------------------------
      // delegate the UpdateSubscriptionCommand
      // -----------------------------------------------
      completableFuture =
          SubscriptionBusinessDelegate.getSubscriptionInstance().updateSubscription(command);
      ;
    } catch (Throwable exc) {
      LOGGER.log(
          Level.WARNING,
          "SubscriptionController:update() - successfully update Subscription - "
              + exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * Handles deleting a Subscription entity
   *
   * @param command ${class.getDeleteCommandAlias()}
   * @return CompletableFuture<Void>
   */
  @DeleteMapping("/delete")
  public CompletableFuture<Void> delete(@RequestParam(required = true) UUID subscriptionId) {
    CompletableFuture<Void> completableFuture = null;
    DeleteSubscriptionCommand command = new DeleteSubscriptionCommand(subscriptionId);

    try {
      SubscriptionBusinessDelegate delegate =
          SubscriptionBusinessDelegate.getSubscriptionInstance();

      completableFuture = delegate.delete(command);
      LOGGER.log(
          Level.WARNING,
          "Successfully deleted Subscription with key " + command.getSubscriptionId());
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * save Program on Subscription
   *
   * @param command AssignProgramToSubscriptionCommand
   */
  @PutMapping("/assignProgram")
  public void assignProgram(@RequestBody AssignProgramToSubscriptionCommand command) {
    try {
      SubscriptionBusinessDelegate.getSubscriptionInstance().assignProgram(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "Failed to assign Program", exc);
    }
  }

  /**
   * unassign Program on Subscription
   *
   * @param command UnAssignProgramFromSubscriptionCommand
   */
  @PutMapping("/unAssignProgram")
  public void unAssignProgram(
      @RequestBody(required = true) UnAssignProgramFromSubscriptionCommand command) {
    try {
      SubscriptionBusinessDelegate.getSubscriptionInstance().unAssignProgram(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to unassign Program", exc);
    }
  }

  /**
   * save Targets on Subscription
   *
   * @param command AssignTargetsToSubscriptionCommand
   */
  @PutMapping("/assignTargets")
  public void assignTargets(@RequestBody AssignTargetsToSubscriptionCommand command) {
    try {
      SubscriptionBusinessDelegate.getSubscriptionInstance().assignTargets(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "Failed to assign Targets", exc);
    }
  }

  /**
   * unassign Targets on Subscription
   *
   * @param command UnAssignTargetsFromSubscriptionCommand
   */
  @PutMapping("/unAssignTargets")
  public void unAssignTargets(
      @RequestBody(required = true) UnAssignTargetsFromSubscriptionCommand command) {
    try {
      SubscriptionBusinessDelegate.getSubscriptionInstance().unAssignTargets(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to unassign Targets", exc);
    }
  }

  /**
   * save ObjectOperations on Subscription
   *
   * @param command AssignObjectOperationsToSubscriptionCommand
   */
  @PutMapping("/addToObjectOperations")
  public void addToObjectOperations(
      @RequestBody(required = true) AssignObjectOperationsToSubscriptionCommand command) {
    try {
      SubscriptionBusinessDelegate.getSubscriptionInstance().addToObjectOperations(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to add to Set ObjectOperations", exc);
    }
  }

  /**
   * remove ObjectOperations on Subscription
   *
   * @param command RemoveObjectOperationsFromSubscriptionCommand
   */
  @PutMapping("/removeFromObjectOperations")
  public void removeFromObjectOperations(
      @RequestBody(required = true) RemoveObjectOperationsFromSubscriptionCommand command) {
    try {
      SubscriptionBusinessDelegate.getSubscriptionInstance().removeFromObjectOperations(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to remove from Set ObjectOperations", exc);
    }
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected Subscription subscription = null;
  private static final Logger LOGGER =
      Logger.getLogger(SubscriptionCommandRestController.class.getName());
}
