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
 * Implements Spring Controller command CQRS processing for entity IntervalPeriod.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/IntervalPeriod")
public class IntervalPeriodCommandRestController extends BaseSpringRestController {

  /**
   * Handles create a IntervalPeriod. if not key provided, calls create, otherwise calls save
   *
   * @param IntervalPeriod intervalPeriod
   * @return CompletableFuture<UUID>
   */
  @PostMapping("/create")
  public CompletableFuture<UUID> create(
      @RequestBody(required = true) CreateIntervalPeriodCommand command) {
    CompletableFuture<UUID> completableFuture = null;
    try {

      completableFuture =
          IntervalPeriodBusinessDelegate.getIntervalPeriodInstance().createIntervalPeriod(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage(), exc);
    }

    return completableFuture;
  }

  /**
   * Handles updating a IntervalPeriod. if no key provided, calls create, otherwise calls save
   *
   * @param IntervalPeriod intervalPeriod
   * @return CompletableFuture<Void>
   */
  @PutMapping("/update")
  public CompletableFuture<Void> update(
      @RequestBody(required = true) UpdateIntervalPeriodCommand command) {
    CompletableFuture<Void> completableFuture = null;
    try {
      // -----------------------------------------------
      // delegate the UpdateIntervalPeriodCommand
      // -----------------------------------------------
      completableFuture =
          IntervalPeriodBusinessDelegate.getIntervalPeriodInstance().updateIntervalPeriod(command);
      ;
    } catch (Throwable exc) {
      LOGGER.log(
          Level.WARNING,
          "IntervalPeriodController:update() - successfully update IntervalPeriod - "
              + exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * Handles deleting a IntervalPeriod entity
   *
   * @param command ${class.getDeleteCommandAlias()}
   * @return CompletableFuture<Void>
   */
  @DeleteMapping("/delete")
  public CompletableFuture<Void> delete(@RequestParam(required = true) UUID intervalPeriodId) {
    CompletableFuture<Void> completableFuture = null;
    DeleteIntervalPeriodCommand command = new DeleteIntervalPeriodCommand(intervalPeriodId);

    try {
      IntervalPeriodBusinessDelegate delegate =
          IntervalPeriodBusinessDelegate.getIntervalPeriodInstance();

      completableFuture = delegate.delete(command);
      LOGGER.log(
          Level.WARNING,
          "Successfully deleted IntervalPeriod with key " + command.getIntervalPeriodId());
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage());
    }

    return completableFuture;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected IntervalPeriod intervalPeriod = null;
  private static final Logger LOGGER =
      Logger.getLogger(IntervalPeriodCommandRestController.class.getName());
}
