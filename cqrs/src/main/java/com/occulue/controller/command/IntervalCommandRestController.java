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
 * Implements Spring Controller command CQRS processing for entity Interval.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/Interval")
public class IntervalCommandRestController extends BaseSpringRestController {

  /**
   * Handles create a Interval. if not key provided, calls create, otherwise calls save
   *
   * @param Interval interval
   * @return CompletableFuture<UUID>
   */
  @PostMapping("/create")
  public CompletableFuture<UUID> create(
      @RequestBody(required = true) CreateIntervalCommand command) {
    CompletableFuture<UUID> completableFuture = null;
    try {

      completableFuture = IntervalBusinessDelegate.getIntervalInstance().createInterval(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage(), exc);
    }

    return completableFuture;
  }

  /**
   * Handles updating a Interval. if no key provided, calls create, otherwise calls save
   *
   * @param Interval interval
   * @return CompletableFuture<Void>
   */
  @PutMapping("/update")
  public CompletableFuture<Void> update(
      @RequestBody(required = true) UpdateIntervalCommand command) {
    CompletableFuture<Void> completableFuture = null;
    try {
      // -----------------------------------------------
      // delegate the UpdateIntervalCommand
      // -----------------------------------------------
      completableFuture = IntervalBusinessDelegate.getIntervalInstance().updateInterval(command);
      ;
    } catch (Throwable exc) {
      LOGGER.log(
          Level.WARNING,
          "IntervalController:update() - successfully update Interval - " + exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * Handles deleting a Interval entity
   *
   * @param command ${class.getDeleteCommandAlias()}
   * @return CompletableFuture<Void>
   */
  @DeleteMapping("/delete")
  public CompletableFuture<Void> delete(@RequestParam(required = true) UUID intervalId) {
    CompletableFuture<Void> completableFuture = null;
    DeleteIntervalCommand command = new DeleteIntervalCommand(intervalId);

    try {
      IntervalBusinessDelegate delegate = IntervalBusinessDelegate.getIntervalInstance();

      completableFuture = delegate.delete(command);
      LOGGER.log(
          Level.WARNING, "Successfully deleted Interval with key " + command.getIntervalId());
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * save IntervalPeriod on Interval
   *
   * @param command AssignIntervalPeriodToIntervalCommand
   */
  @PutMapping("/assignIntervalPeriod")
  public void assignIntervalPeriod(@RequestBody AssignIntervalPeriodToIntervalCommand command) {
    try {
      IntervalBusinessDelegate.getIntervalInstance().assignIntervalPeriod(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "Failed to assign IntervalPeriod", exc);
    }
  }

  /**
   * unassign IntervalPeriod on Interval
   *
   * @param command UnAssignIntervalPeriodFromIntervalCommand
   */
  @PutMapping("/unAssignIntervalPeriod")
  public void unAssignIntervalPeriod(
      @RequestBody(required = true) UnAssignIntervalPeriodFromIntervalCommand command) {
    try {
      IntervalBusinessDelegate.getIntervalInstance().unAssignIntervalPeriod(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to unassign IntervalPeriod", exc);
    }
  }

  /**
   * save Payloads on Interval
   *
   * @param command AssignPayloadsToIntervalCommand
   */
  @PutMapping("/addToPayloads")
  public void addToPayloads(@RequestBody(required = true) AssignPayloadsToIntervalCommand command) {
    try {
      IntervalBusinessDelegate.getIntervalInstance().addToPayloads(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to add to Set Payloads", exc);
    }
  }

  /**
   * remove Payloads on Interval
   *
   * @param command RemovePayloadsFromIntervalCommand
   */
  @PutMapping("/removeFromPayloads")
  public void removeFromPayloads(
      @RequestBody(required = true) RemovePayloadsFromIntervalCommand command) {
    try {
      IntervalBusinessDelegate.getIntervalInstance().removeFromPayloads(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to remove from Set Payloads", exc);
    }
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected Interval interval = null;
  private static final Logger LOGGER =
      Logger.getLogger(IntervalCommandRestController.class.getName());
}
