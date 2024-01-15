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
 * Implements Spring Controller command CQRS processing for entity ObjectOperation.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/ObjectOperation")
public class ObjectOperationCommandRestController extends BaseSpringRestController {

  /**
   * Handles create a ObjectOperation. if not key provided, calls create, otherwise calls save
   *
   * @param ObjectOperation objectOperation
   * @return CompletableFuture<UUID>
   */
  @PostMapping("/create")
  public CompletableFuture<UUID> create(
      @RequestBody(required = true) CreateObjectOperationCommand command) {
    CompletableFuture<UUID> completableFuture = null;
    try {

      completableFuture =
          ObjectOperationBusinessDelegate.getObjectOperationInstance()
              .createObjectOperation(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage(), exc);
    }

    return completableFuture;
  }

  /**
   * Handles updating a ObjectOperation. if no key provided, calls create, otherwise calls save
   *
   * @param ObjectOperation objectOperation
   * @return CompletableFuture<Void>
   */
  @PutMapping("/update")
  public CompletableFuture<Void> update(
      @RequestBody(required = true) UpdateObjectOperationCommand command) {
    CompletableFuture<Void> completableFuture = null;
    try {
      // -----------------------------------------------
      // delegate the UpdateObjectOperationCommand
      // -----------------------------------------------
      completableFuture =
          ObjectOperationBusinessDelegate.getObjectOperationInstance()
              .updateObjectOperation(command);
      ;
    } catch (Throwable exc) {
      LOGGER.log(
          Level.WARNING,
          "ObjectOperationController:update() - successfully update ObjectOperation - "
              + exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * Handles deleting a ObjectOperation entity
   *
   * @param command ${class.getDeleteCommandAlias()}
   * @return CompletableFuture<Void>
   */
  @DeleteMapping("/delete")
  public CompletableFuture<Void> delete(@RequestParam(required = true) UUID objectOperationId) {
    CompletableFuture<Void> completableFuture = null;
    DeleteObjectOperationCommand command = new DeleteObjectOperationCommand(objectOperationId);

    try {
      ObjectOperationBusinessDelegate delegate =
          ObjectOperationBusinessDelegate.getObjectOperationInstance();

      completableFuture = delegate.delete(command);
      LOGGER.log(
          Level.WARNING,
          "Successfully deleted ObjectOperation with key " + command.getObjectOperationId());
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage());
    }

    return completableFuture;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected ObjectOperation objectOperation = null;
  private static final Logger LOGGER =
      Logger.getLogger(ObjectOperationCommandRestController.class.getName());
}
