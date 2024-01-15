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
 * Implements Spring Controller command CQRS processing for entity PayloadDescriptor.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/PayloadDescriptor")
public class PayloadDescriptorCommandRestController extends BaseSpringRestController {

  /**
   * Handles create a PayloadDescriptor. if not key provided, calls create, otherwise calls save
   *
   * @param PayloadDescriptor payloadDescriptor
   * @return CompletableFuture<UUID>
   */
  @PostMapping("/create")
  public CompletableFuture<UUID> create(
      @RequestBody(required = true) CreatePayloadDescriptorCommand command) {
    CompletableFuture<UUID> completableFuture = null;
    try {

      completableFuture =
          PayloadDescriptorBusinessDelegate.getPayloadDescriptorInstance()
              .createPayloadDescriptor(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage(), exc);
    }

    return completableFuture;
  }

  /**
   * Handles updating a PayloadDescriptor. if no key provided, calls create, otherwise calls save
   *
   * @param PayloadDescriptor payloadDescriptor
   * @return CompletableFuture<Void>
   */
  @PutMapping("/update")
  public CompletableFuture<Void> update(
      @RequestBody(required = true) UpdatePayloadDescriptorCommand command) {
    CompletableFuture<Void> completableFuture = null;
    try {
      // -----------------------------------------------
      // delegate the UpdatePayloadDescriptorCommand
      // -----------------------------------------------
      completableFuture =
          PayloadDescriptorBusinessDelegate.getPayloadDescriptorInstance()
              .updatePayloadDescriptor(command);
      ;
    } catch (Throwable exc) {
      LOGGER.log(
          Level.WARNING,
          "PayloadDescriptorController:update() - successfully update PayloadDescriptor - "
              + exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * Handles deleting a PayloadDescriptor entity
   *
   * @param command ${class.getDeleteCommandAlias()}
   * @return CompletableFuture<Void>
   */
  @DeleteMapping("/delete")
  public CompletableFuture<Void> delete(@RequestParam(required = true) UUID payloadDescriptorId) {
    CompletableFuture<Void> completableFuture = null;
    DeletePayloadDescriptorCommand command =
        new DeletePayloadDescriptorCommand(payloadDescriptorId);

    try {
      PayloadDescriptorBusinessDelegate delegate =
          PayloadDescriptorBusinessDelegate.getPayloadDescriptorInstance();

      completableFuture = delegate.delete(command);
      LOGGER.log(
          Level.WARNING,
          "Successfully deleted PayloadDescriptor with key " + command.getPayloadDescriptorId());
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage());
    }

    return completableFuture;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected PayloadDescriptor payloadDescriptor = null;
  private static final Logger LOGGER =
      Logger.getLogger(PayloadDescriptorCommandRestController.class.getName());
}
