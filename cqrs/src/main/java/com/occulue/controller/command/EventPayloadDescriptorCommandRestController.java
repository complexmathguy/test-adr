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
 * Implements Spring Controller command CQRS processing for entity EventPayloadDescriptor.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/EventPayloadDescriptor")
public class EventPayloadDescriptorCommandRestController extends BaseSpringRestController {

  /**
   * Handles create a EventPayloadDescriptor. if not key provided, calls create, otherwise calls
   * save
   *
   * @param EventPayloadDescriptor eventPayloadDescriptor
   * @return CompletableFuture<UUID>
   */
  @PostMapping("/create")
  public CompletableFuture<UUID> create(
      @RequestBody(required = true) CreateEventPayloadDescriptorCommand command) {
    CompletableFuture<UUID> completableFuture = null;
    try {

      completableFuture =
          EventPayloadDescriptorBusinessDelegate.getEventPayloadDescriptorInstance()
              .createEventPayloadDescriptor(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage(), exc);
    }

    return completableFuture;
  }

  /**
   * Handles updating a EventPayloadDescriptor. if no key provided, calls create, otherwise calls
   * save
   *
   * @param EventPayloadDescriptor eventPayloadDescriptor
   * @return CompletableFuture<Void>
   */
  @PutMapping("/update")
  public CompletableFuture<Void> update(
      @RequestBody(required = true) UpdateEventPayloadDescriptorCommand command) {
    CompletableFuture<Void> completableFuture = null;
    try {
      // -----------------------------------------------
      // delegate the UpdateEventPayloadDescriptorCommand
      // -----------------------------------------------
      completableFuture =
          EventPayloadDescriptorBusinessDelegate.getEventPayloadDescriptorInstance()
              .updateEventPayloadDescriptor(command);
      ;
    } catch (Throwable exc) {
      LOGGER.log(
          Level.WARNING,
          "EventPayloadDescriptorController:update() - successfully update EventPayloadDescriptor - "
              + exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * Handles deleting a EventPayloadDescriptor entity
   *
   * @param command ${class.getDeleteCommandAlias()}
   * @return CompletableFuture<Void>
   */
  @DeleteMapping("/delete")
  public CompletableFuture<Void> delete(
      @RequestParam(required = true) UUID eventPayloadDescriptorId) {
    CompletableFuture<Void> completableFuture = null;
    DeleteEventPayloadDescriptorCommand command =
        new DeleteEventPayloadDescriptorCommand(eventPayloadDescriptorId);

    try {
      EventPayloadDescriptorBusinessDelegate delegate =
          EventPayloadDescriptorBusinessDelegate.getEventPayloadDescriptorInstance();

      completableFuture = delegate.delete(command);
      LOGGER.log(
          Level.WARNING,
          "Successfully deleted EventPayloadDescriptor with key "
              + command.getEventPayloadDescriptorId());
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage());
    }

    return completableFuture;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected EventPayloadDescriptor eventPayloadDescriptor = null;
  private static final Logger LOGGER =
      Logger.getLogger(EventPayloadDescriptorCommandRestController.class.getName());
}
