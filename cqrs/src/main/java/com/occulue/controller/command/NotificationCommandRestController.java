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
 * Implements Spring Controller command CQRS processing for entity Notification.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/Notification")
public class NotificationCommandRestController extends BaseSpringRestController {

  /**
   * Handles create a Notification. if not key provided, calls create, otherwise calls save
   *
   * @param Notification notification
   * @return CompletableFuture<UUID>
   */
  @PostMapping("/create")
  public CompletableFuture<UUID> create(
      @RequestBody(required = true) CreateNotificationCommand command) {
    CompletableFuture<UUID> completableFuture = null;
    try {

      completableFuture =
          NotificationBusinessDelegate.getNotificationInstance().createNotification(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage(), exc);
    }

    return completableFuture;
  }

  /**
   * Handles updating a Notification. if no key provided, calls create, otherwise calls save
   *
   * @param Notification notification
   * @return CompletableFuture<Void>
   */
  @PutMapping("/update")
  public CompletableFuture<Void> update(
      @RequestBody(required = true) UpdateNotificationCommand command) {
    CompletableFuture<Void> completableFuture = null;
    try {
      // -----------------------------------------------
      // delegate the UpdateNotificationCommand
      // -----------------------------------------------
      completableFuture =
          NotificationBusinessDelegate.getNotificationInstance().updateNotification(command);
      ;
    } catch (Throwable exc) {
      LOGGER.log(
          Level.WARNING,
          "NotificationController:update() - successfully update Notification - "
              + exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * Handles deleting a Notification entity
   *
   * @param command ${class.getDeleteCommandAlias()}
   * @return CompletableFuture<Void>
   */
  @DeleteMapping("/delete")
  public CompletableFuture<Void> delete(@RequestParam(required = true) UUID notificationId) {
    CompletableFuture<Void> completableFuture = null;
    DeleteNotificationCommand command = new DeleteNotificationCommand(notificationId);

    try {
      NotificationBusinessDelegate delegate =
          NotificationBusinessDelegate.getNotificationInstance();

      completableFuture = delegate.delete(command);
      LOGGER.log(
          Level.WARNING,
          "Successfully deleted Notification with key " + command.getNotificationId());
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * save Targets on Notification
   *
   * @param command AssignTargetsToNotificationCommand
   */
  @PutMapping("/assignTargets")
  public void assignTargets(@RequestBody AssignTargetsToNotificationCommand command) {
    try {
      NotificationBusinessDelegate.getNotificationInstance().assignTargets(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "Failed to assign Targets", exc);
    }
  }

  /**
   * unassign Targets on Notification
   *
   * @param command UnAssignTargetsFromNotificationCommand
   */
  @PutMapping("/unAssignTargets")
  public void unAssignTargets(
      @RequestBody(required = true) UnAssignTargetsFromNotificationCommand command) {
    try {
      NotificationBusinessDelegate.getNotificationInstance().unAssignTargets(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to unassign Targets", exc);
    }
  }

  /**
   * save Notifier on Notification
   *
   * @param command AssignNotifierToNotificationCommand
   */
  @PutMapping("/assignNotifier")
  public void assignNotifier(@RequestBody AssignNotifierToNotificationCommand command) {
    try {
      NotificationBusinessDelegate.getNotificationInstance().assignNotifier(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "Failed to assign Notifier", exc);
    }
  }

  /**
   * unassign Notifier on Notification
   *
   * @param command UnAssignNotifierFromNotificationCommand
   */
  @PutMapping("/unAssignNotifier")
  public void unAssignNotifier(
      @RequestBody(required = true) UnAssignNotifierFromNotificationCommand command) {
    try {
      NotificationBusinessDelegate.getNotificationInstance().unAssignNotifier(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to unassign Notifier", exc);
    }
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected Notification notification = null;
  private static final Logger LOGGER =
      Logger.getLogger(NotificationCommandRestController.class.getName());
}
