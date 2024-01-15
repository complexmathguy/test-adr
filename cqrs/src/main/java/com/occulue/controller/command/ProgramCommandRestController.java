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
 * Implements Spring Controller command CQRS processing for entity Program.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/Program")
public class ProgramCommandRestController extends BaseSpringRestController {

  /**
   * Handles create a Program. if not key provided, calls create, otherwise calls save
   *
   * @param Program program
   * @return CompletableFuture<UUID>
   */
  @PostMapping("/create")
  public CompletableFuture<UUID> create(
      @RequestBody(required = true) CreateProgramCommand command) {
    CompletableFuture<UUID> completableFuture = null;
    try {

      completableFuture = ProgramBusinessDelegate.getProgramInstance().createProgram(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage(), exc);
    }

    return completableFuture;
  }

  /**
   * Handles updating a Program. if no key provided, calls create, otherwise calls save
   *
   * @param Program program
   * @return CompletableFuture<Void>
   */
  @PutMapping("/update")
  public CompletableFuture<Void> update(
      @RequestBody(required = true) UpdateProgramCommand command) {
    CompletableFuture<Void> completableFuture = null;
    try {
      // -----------------------------------------------
      // delegate the UpdateProgramCommand
      // -----------------------------------------------
      completableFuture = ProgramBusinessDelegate.getProgramInstance().updateProgram(command);
      ;
    } catch (Throwable exc) {
      LOGGER.log(
          Level.WARNING,
          "ProgramController:update() - successfully update Program - " + exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * Handles deleting a Program entity
   *
   * @param command ${class.getDeleteCommandAlias()}
   * @return CompletableFuture<Void>
   */
  @DeleteMapping("/delete")
  public CompletableFuture<Void> delete(@RequestParam(required = true) UUID programId) {
    CompletableFuture<Void> completableFuture = null;
    DeleteProgramCommand command = new DeleteProgramCommand(programId);

    try {
      ProgramBusinessDelegate delegate = ProgramBusinessDelegate.getProgramInstance();

      completableFuture = delegate.delete(command);
      LOGGER.log(Level.WARNING, "Successfully deleted Program with key " + command.getProgramId());
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * save IntervalPeriod on Program
   *
   * @param command AssignIntervalPeriodToProgramCommand
   */
  @PutMapping("/assignIntervalPeriod")
  public void assignIntervalPeriod(@RequestBody AssignIntervalPeriodToProgramCommand command) {
    try {
      ProgramBusinessDelegate.getProgramInstance().assignIntervalPeriod(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "Failed to assign IntervalPeriod", exc);
    }
  }

  /**
   * unassign IntervalPeriod on Program
   *
   * @param command UnAssignIntervalPeriodFromProgramCommand
   */
  @PutMapping("/unAssignIntervalPeriod")
  public void unAssignIntervalPeriod(
      @RequestBody(required = true) UnAssignIntervalPeriodFromProgramCommand command) {
    try {
      ProgramBusinessDelegate.getProgramInstance().unAssignIntervalPeriod(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to unassign IntervalPeriod", exc);
    }
  }

  /**
   * save PayloadDescriptors on Program
   *
   * @param command AssignPayloadDescriptorsToProgramCommand
   */
  @PutMapping("/addToPayloadDescriptors")
  public void addToPayloadDescriptors(
      @RequestBody(required = true) AssignPayloadDescriptorsToProgramCommand command) {
    try {
      ProgramBusinessDelegate.getProgramInstance().addToPayloadDescriptors(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to add to Set PayloadDescriptors", exc);
    }
  }

  /**
   * remove PayloadDescriptors on Program
   *
   * @param command RemovePayloadDescriptorsFromProgramCommand
   */
  @PutMapping("/removeFromPayloadDescriptors")
  public void removeFromPayloadDescriptors(
      @RequestBody(required = true) RemovePayloadDescriptorsFromProgramCommand command) {
    try {
      ProgramBusinessDelegate.getProgramInstance().removeFromPayloadDescriptors(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to remove from Set PayloadDescriptors", exc);
    }
  }

  /**
   * save Targets on Program
   *
   * @param command AssignTargetsToProgramCommand
   */
  @PutMapping("/addToTargets")
  public void addToTargets(@RequestBody(required = true) AssignTargetsToProgramCommand command) {
    try {
      ProgramBusinessDelegate.getProgramInstance().addToTargets(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to add to Set Targets", exc);
    }
  }

  /**
   * remove Targets on Program
   *
   * @param command RemoveTargetsFromProgramCommand
   */
  @PutMapping("/removeFromTargets")
  public void removeFromTargets(
      @RequestBody(required = true) RemoveTargetsFromProgramCommand command) {
    try {
      ProgramBusinessDelegate.getProgramInstance().removeFromTargets(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to remove from Set Targets", exc);
    }
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected Program program = null;
  private static final Logger LOGGER =
      Logger.getLogger(ProgramCommandRestController.class.getName());
}
