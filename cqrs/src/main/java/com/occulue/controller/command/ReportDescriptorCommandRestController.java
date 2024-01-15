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
 * Implements Spring Controller command CQRS processing for entity ReportDescriptor.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/ReportDescriptor")
public class ReportDescriptorCommandRestController extends BaseSpringRestController {

  /**
   * Handles create a ReportDescriptor. if not key provided, calls create, otherwise calls save
   *
   * @param ReportDescriptor reportDescriptor
   * @return CompletableFuture<UUID>
   */
  @PostMapping("/create")
  public CompletableFuture<UUID> create(
      @RequestBody(required = true) CreateReportDescriptorCommand command) {
    CompletableFuture<UUID> completableFuture = null;
    try {

      completableFuture =
          ReportDescriptorBusinessDelegate.getReportDescriptorInstance()
              .createReportDescriptor(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage(), exc);
    }

    return completableFuture;
  }

  /**
   * Handles updating a ReportDescriptor. if no key provided, calls create, otherwise calls save
   *
   * @param ReportDescriptor reportDescriptor
   * @return CompletableFuture<Void>
   */
  @PutMapping("/update")
  public CompletableFuture<Void> update(
      @RequestBody(required = true) UpdateReportDescriptorCommand command) {
    CompletableFuture<Void> completableFuture = null;
    try {
      // -----------------------------------------------
      // delegate the UpdateReportDescriptorCommand
      // -----------------------------------------------
      completableFuture =
          ReportDescriptorBusinessDelegate.getReportDescriptorInstance()
              .updateReportDescriptor(command);
      ;
    } catch (Throwable exc) {
      LOGGER.log(
          Level.WARNING,
          "ReportDescriptorController:update() - successfully update ReportDescriptor - "
              + exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * Handles deleting a ReportDescriptor entity
   *
   * @param command ${class.getDeleteCommandAlias()}
   * @return CompletableFuture<Void>
   */
  @DeleteMapping("/delete")
  public CompletableFuture<Void> delete(@RequestParam(required = true) UUID reportDescriptorId) {
    CompletableFuture<Void> completableFuture = null;
    DeleteReportDescriptorCommand command = new DeleteReportDescriptorCommand(reportDescriptorId);

    try {
      ReportDescriptorBusinessDelegate delegate =
          ReportDescriptorBusinessDelegate.getReportDescriptorInstance();

      completableFuture = delegate.delete(command);
      LOGGER.log(
          Level.WARNING,
          "Successfully deleted ReportDescriptor with key " + command.getReportDescriptorId());
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * save Targets on ReportDescriptor
   *
   * @param command AssignTargetsToReportDescriptorCommand
   */
  @PutMapping("/assignTargets")
  public void assignTargets(@RequestBody AssignTargetsToReportDescriptorCommand command) {
    try {
      ReportDescriptorBusinessDelegate.getReportDescriptorInstance().assignTargets(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "Failed to assign Targets", exc);
    }
  }

  /**
   * unassign Targets on ReportDescriptor
   *
   * @param command UnAssignTargetsFromReportDescriptorCommand
   */
  @PutMapping("/unAssignTargets")
  public void unAssignTargets(
      @RequestBody(required = true) UnAssignTargetsFromReportDescriptorCommand command) {
    try {
      ReportDescriptorBusinessDelegate.getReportDescriptorInstance().unAssignTargets(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to unassign Targets", exc);
    }
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected ReportDescriptor reportDescriptor = null;
  private static final Logger LOGGER =
      Logger.getLogger(ReportDescriptorCommandRestController.class.getName());
}
