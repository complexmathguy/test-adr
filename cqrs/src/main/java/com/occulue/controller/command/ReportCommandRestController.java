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
 * Implements Spring Controller command CQRS processing for entity Report.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/Report")
public class ReportCommandRestController extends BaseSpringRestController {

  /**
   * Handles create a Report. if not key provided, calls create, otherwise calls save
   *
   * @param Report report
   * @return CompletableFuture<UUID>
   */
  @PostMapping("/create")
  public CompletableFuture<UUID> create(@RequestBody(required = true) CreateReportCommand command) {
    CompletableFuture<UUID> completableFuture = null;
    try {

      completableFuture = ReportBusinessDelegate.getReportInstance().createReport(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage(), exc);
    }

    return completableFuture;
  }

  /**
   * Handles updating a Report. if no key provided, calls create, otherwise calls save
   *
   * @param Report report
   * @return CompletableFuture<Void>
   */
  @PutMapping("/update")
  public CompletableFuture<Void> update(@RequestBody(required = true) UpdateReportCommand command) {
    CompletableFuture<Void> completableFuture = null;
    try {
      // -----------------------------------------------
      // delegate the UpdateReportCommand
      // -----------------------------------------------
      completableFuture = ReportBusinessDelegate.getReportInstance().updateReport(command);
      ;
    } catch (Throwable exc) {
      LOGGER.log(
          Level.WARNING,
          "ReportController:update() - successfully update Report - " + exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * Handles deleting a Report entity
   *
   * @param command ${class.getDeleteCommandAlias()}
   * @return CompletableFuture<Void>
   */
  @DeleteMapping("/delete")
  public CompletableFuture<Void> delete(@RequestParam(required = true) UUID reportId) {
    CompletableFuture<Void> completableFuture = null;
    DeleteReportCommand command = new DeleteReportCommand(reportId);

    try {
      ReportBusinessDelegate delegate = ReportBusinessDelegate.getReportInstance();

      completableFuture = delegate.delete(command);
      LOGGER.log(Level.WARNING, "Successfully deleted Report with key " + command.getReportId());
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * save Program on Report
   *
   * @param command AssignProgramToReportCommand
   */
  @PutMapping("/assignProgram")
  public void assignProgram(@RequestBody AssignProgramToReportCommand command) {
    try {
      ReportBusinessDelegate.getReportInstance().assignProgram(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "Failed to assign Program", exc);
    }
  }

  /**
   * unassign Program on Report
   *
   * @param command UnAssignProgramFromReportCommand
   */
  @PutMapping("/unAssignProgram")
  public void unAssignProgram(
      @RequestBody(required = true) UnAssignProgramFromReportCommand command) {
    try {
      ReportBusinessDelegate.getReportInstance().unAssignProgram(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to unassign Program", exc);
    }
  }

  /**
   * save Event on Report
   *
   * @param command AssignEventToReportCommand
   */
  @PutMapping("/assignEvent")
  public void assignEvent(@RequestBody AssignEventToReportCommand command) {
    try {
      ReportBusinessDelegate.getReportInstance().assignEvent(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "Failed to assign Event", exc);
    }
  }

  /**
   * unassign Event on Report
   *
   * @param command UnAssignEventFromReportCommand
   */
  @PutMapping("/unAssignEvent")
  public void unAssignEvent(@RequestBody(required = true) UnAssignEventFromReportCommand command) {
    try {
      ReportBusinessDelegate.getReportInstance().unAssignEvent(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to unassign Event", exc);
    }
  }

  /**
   * save Intervals on Report
   *
   * @param command AssignIntervalsToReportCommand
   */
  @PutMapping("/assignIntervals")
  public void assignIntervals(@RequestBody AssignIntervalsToReportCommand command) {
    try {
      ReportBusinessDelegate.getReportInstance().assignIntervals(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "Failed to assign Intervals", exc);
    }
  }

  /**
   * unassign Intervals on Report
   *
   * @param command UnAssignIntervalsFromReportCommand
   */
  @PutMapping("/unAssignIntervals")
  public void unAssignIntervals(
      @RequestBody(required = true) UnAssignIntervalsFromReportCommand command) {
    try {
      ReportBusinessDelegate.getReportInstance().unAssignIntervals(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to unassign Intervals", exc);
    }
  }

  /**
   * save IntervalPeriod on Report
   *
   * @param command AssignIntervalPeriodToReportCommand
   */
  @PutMapping("/assignIntervalPeriod")
  public void assignIntervalPeriod(@RequestBody AssignIntervalPeriodToReportCommand command) {
    try {
      ReportBusinessDelegate.getReportInstance().assignIntervalPeriod(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "Failed to assign IntervalPeriod", exc);
    }
  }

  /**
   * unassign IntervalPeriod on Report
   *
   * @param command UnAssignIntervalPeriodFromReportCommand
   */
  @PutMapping("/unAssignIntervalPeriod")
  public void unAssignIntervalPeriod(
      @RequestBody(required = true) UnAssignIntervalPeriodFromReportCommand command) {
    try {
      ReportBusinessDelegate.getReportInstance().unAssignIntervalPeriod(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to unassign IntervalPeriod", exc);
    }
  }

  /**
   * save PayloadDescriptors on Report
   *
   * @param command AssignPayloadDescriptorsToReportCommand
   */
  @PutMapping("/addToPayloadDescriptors")
  public void addToPayloadDescriptors(
      @RequestBody(required = true) AssignPayloadDescriptorsToReportCommand command) {
    try {
      ReportBusinessDelegate.getReportInstance().addToPayloadDescriptors(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to add to Set PayloadDescriptors", exc);
    }
  }

  /**
   * remove PayloadDescriptors on Report
   *
   * @param command RemovePayloadDescriptorsFromReportCommand
   */
  @PutMapping("/removeFromPayloadDescriptors")
  public void removeFromPayloadDescriptors(
      @RequestBody(required = true) RemovePayloadDescriptorsFromReportCommand command) {
    try {
      ReportBusinessDelegate.getReportInstance().removeFromPayloadDescriptors(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to remove from Set PayloadDescriptors", exc);
    }
  }

  /**
   * save Resources on Report
   *
   * @param command AssignResourcesToReportCommand
   */
  @PutMapping("/addToResources")
  public void addToResources(@RequestBody(required = true) AssignResourcesToReportCommand command) {
    try {
      ReportBusinessDelegate.getReportInstance().addToResources(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to add to Set Resources", exc);
    }
  }

  /**
   * remove Resources on Report
   *
   * @param command RemoveResourcesFromReportCommand
   */
  @PutMapping("/removeFromResources")
  public void removeFromResources(
      @RequestBody(required = true) RemoveResourcesFromReportCommand command) {
    try {
      ReportBusinessDelegate.getReportInstance().removeFromResources(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to remove from Set Resources", exc);
    }
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected Report report = null;
  private static final Logger LOGGER =
      Logger.getLogger(ReportCommandRestController.class.getName());
}
