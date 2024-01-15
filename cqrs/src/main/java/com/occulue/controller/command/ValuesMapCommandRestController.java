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
 * Implements Spring Controller command CQRS processing for entity ValuesMap.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/ValuesMap")
public class ValuesMapCommandRestController extends BaseSpringRestController {

  /**
   * Handles create a ValuesMap. if not key provided, calls create, otherwise calls save
   *
   * @param ValuesMap valuesMap
   * @return CompletableFuture<UUID>
   */
  @PostMapping("/create")
  public CompletableFuture<UUID> create(
      @RequestBody(required = true) CreateValuesMapCommand command) {
    CompletableFuture<UUID> completableFuture = null;
    try {

      completableFuture = ValuesMapBusinessDelegate.getValuesMapInstance().createValuesMap(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage(), exc);
    }

    return completableFuture;
  }

  /**
   * Handles updating a ValuesMap. if no key provided, calls create, otherwise calls save
   *
   * @param ValuesMap valuesMap
   * @return CompletableFuture<Void>
   */
  @PutMapping("/update")
  public CompletableFuture<Void> update(
      @RequestBody(required = true) UpdateValuesMapCommand command) {
    CompletableFuture<Void> completableFuture = null;
    try {
      // -----------------------------------------------
      // delegate the UpdateValuesMapCommand
      // -----------------------------------------------
      completableFuture = ValuesMapBusinessDelegate.getValuesMapInstance().updateValuesMap(command);
      ;
    } catch (Throwable exc) {
      LOGGER.log(
          Level.WARNING,
          "ValuesMapController:update() - successfully update ValuesMap - " + exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * Handles deleting a ValuesMap entity
   *
   * @param command ${class.getDeleteCommandAlias()}
   * @return CompletableFuture<Void>
   */
  @DeleteMapping("/delete")
  public CompletableFuture<Void> delete(@RequestParam(required = true) UUID valuesMapId) {
    CompletableFuture<Void> completableFuture = null;
    DeleteValuesMapCommand command = new DeleteValuesMapCommand(valuesMapId);

    try {
      ValuesMapBusinessDelegate delegate = ValuesMapBusinessDelegate.getValuesMapInstance();

      completableFuture = delegate.delete(command);
      LOGGER.log(
          Level.WARNING, "Successfully deleted ValuesMap with key " + command.getValuesMapId());
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage());
    }

    return completableFuture;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected ValuesMap valuesMap = null;
  private static final Logger LOGGER =
      Logger.getLogger(ValuesMapCommandRestController.class.getName());
}
