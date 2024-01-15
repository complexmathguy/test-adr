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
 * Implements Spring Controller command CQRS processing for entity Notifier.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/Notifier")
public class NotifierCommandRestController extends BaseSpringRestController {

  /**
   * Handles create a Notifier. if not key provided, calls create, otherwise calls save
   *
   * @param Notifier notifier
   * @return CompletableFuture<UUID>
   */
  @PostMapping("/create")
  public CompletableFuture<UUID> create(
      @RequestBody(required = true) CreateNotifierCommand command) {
    CompletableFuture<UUID> completableFuture = null;
    try {

      completableFuture = NotifierBusinessDelegate.getNotifierInstance().createNotifier(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage(), exc);
    }

    return completableFuture;
  }

  /**
   * Handles updating a Notifier. if no key provided, calls create, otherwise calls save
   *
   * @param Notifier notifier
   * @return CompletableFuture<Void>
   */
  @PutMapping("/update")
  public CompletableFuture<Void> update(
      @RequestBody(required = true) UpdateNotifierCommand command) {
    CompletableFuture<Void> completableFuture = null;
    try {
      // -----------------------------------------------
      // delegate the UpdateNotifierCommand
      // -----------------------------------------------
      completableFuture = NotifierBusinessDelegate.getNotifierInstance().updateNotifier(command);
      ;
    } catch (Throwable exc) {
      LOGGER.log(
          Level.WARNING,
          "NotifierController:update() - successfully update Notifier - " + exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * Handles deleting a Notifier entity
   *
   * @param command ${class.getDeleteCommandAlias()}
   * @return CompletableFuture<Void>
   */
  @DeleteMapping("/delete")
  public CompletableFuture<Void> delete(@RequestParam(required = true) UUID notifierId) {
    CompletableFuture<Void> completableFuture = null;
    DeleteNotifierCommand command = new DeleteNotifierCommand(notifierId);

    try {
      NotifierBusinessDelegate delegate = NotifierBusinessDelegate.getNotifierInstance();

      completableFuture = delegate.delete(command);
      LOGGER.log(
          Level.WARNING, "Successfully deleted Notifier with key " + command.getNotifierId());
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage());
    }

    return completableFuture;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected Notifier notifier = null;
  private static final Logger LOGGER =
      Logger.getLogger(NotifierCommandRestController.class.getName());
}
