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
 * Implements Spring Controller command CQRS processing for entity Event.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/Event")
public class EventCommandRestController extends BaseSpringRestController {

  /**
   * Handles create a Event. if not key provided, calls create, otherwise calls save
   *
   * @param Event event
   * @return CompletableFuture<UUID>
   */
  @PostMapping("/create")
  public CompletableFuture<UUID> create(@RequestBody(required = true) CreateEventCommand command) {
    CompletableFuture<UUID> completableFuture = null;
    try {

      completableFuture = EventBusinessDelegate.getEventInstance().createEvent(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage(), exc);
    }

    return completableFuture;
  }

  /**
   * Handles updating a Event. if no key provided, calls create, otherwise calls save
   *
   * @param Event event
   * @return CompletableFuture<Void>
   */
  @PutMapping("/update")
  public CompletableFuture<Void> update(@RequestBody(required = true) UpdateEventCommand command) {
    CompletableFuture<Void> completableFuture = null;
    try {
      // -----------------------------------------------
      // delegate the UpdateEventCommand
      // -----------------------------------------------
      completableFuture = EventBusinessDelegate.getEventInstance().updateEvent(command);
      ;
    } catch (Throwable exc) {
      LOGGER.log(
          Level.WARNING,
          "EventController:update() - successfully update Event - " + exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * Handles deleting a Event entity
   *
   * @param command ${class.getDeleteCommandAlias()}
   * @return CompletableFuture<Void>
   */
  @DeleteMapping("/delete")
  public CompletableFuture<Void> delete(@RequestParam(required = true) UUID eventId) {
    CompletableFuture<Void> completableFuture = null;
    DeleteEventCommand command = new DeleteEventCommand(eventId);

    try {
      EventBusinessDelegate delegate = EventBusinessDelegate.getEventInstance();

      completableFuture = delegate.delete(command);
      LOGGER.log(Level.WARNING, "Successfully deleted Event with key " + command.getEventId());
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * save Program on Event
   *
   * @param command AssignProgramToEventCommand
   */
  @PutMapping("/assignProgram")
  public void assignProgram(@RequestBody AssignProgramToEventCommand command) {
    try {
      EventBusinessDelegate.getEventInstance().assignProgram(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "Failed to assign Program", exc);
    }
  }

  /**
   * unassign Program on Event
   *
   * @param command UnAssignProgramFromEventCommand
   */
  @PutMapping("/unAssignProgram")
  public void unAssignProgram(
      @RequestBody(required = true) UnAssignProgramFromEventCommand command) {
    try {
      EventBusinessDelegate.getEventInstance().unAssignProgram(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to unassign Program", exc);
    }
  }

  /**
   * save Targets on Event
   *
   * @param command AssignTargetsToEventCommand
   */
  @PutMapping("/assignTargets")
  public void assignTargets(@RequestBody AssignTargetsToEventCommand command) {
    try {
      EventBusinessDelegate.getEventInstance().assignTargets(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "Failed to assign Targets", exc);
    }
  }

  /**
   * unassign Targets on Event
   *
   * @param command UnAssignTargetsFromEventCommand
   */
  @PutMapping("/unAssignTargets")
  public void unAssignTargets(
      @RequestBody(required = true) UnAssignTargetsFromEventCommand command) {
    try {
      EventBusinessDelegate.getEventInstance().unAssignTargets(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to unassign Targets", exc);
    }
  }

  /**
   * save IntervalPeriod on Event
   *
   * @param command AssignIntervalPeriodToEventCommand
   */
  @PutMapping("/assignIntervalPeriod")
  public void assignIntervalPeriod(@RequestBody AssignIntervalPeriodToEventCommand command) {
    try {
      EventBusinessDelegate.getEventInstance().assignIntervalPeriod(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "Failed to assign IntervalPeriod", exc);
    }
  }

  /**
   * unassign IntervalPeriod on Event
   *
   * @param command UnAssignIntervalPeriodFromEventCommand
   */
  @PutMapping("/unAssignIntervalPeriod")
  public void unAssignIntervalPeriod(
      @RequestBody(required = true) UnAssignIntervalPeriodFromEventCommand command) {
    try {
      EventBusinessDelegate.getEventInstance().unAssignIntervalPeriod(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to unassign IntervalPeriod", exc);
    }
  }

  /**
   * save ReportDescriptors on Event
   *
   * @param command AssignReportDescriptorsToEventCommand
   */
  @PutMapping("/addToReportDescriptors")
  public void addToReportDescriptors(
      @RequestBody(required = true) AssignReportDescriptorsToEventCommand command) {
    try {
      EventBusinessDelegate.getEventInstance().addToReportDescriptors(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to add to Set ReportDescriptors", exc);
    }
  }

  /**
   * remove ReportDescriptors on Event
   *
   * @param command RemoveReportDescriptorsFromEventCommand
   */
  @PutMapping("/removeFromReportDescriptors")
  public void removeFromReportDescriptors(
      @RequestBody(required = true) RemoveReportDescriptorsFromEventCommand command) {
    try {
      EventBusinessDelegate.getEventInstance().removeFromReportDescriptors(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to remove from Set ReportDescriptors", exc);
    }
  }

  /**
   * save PayloadDescriptors on Event
   *
   * @param command AssignPayloadDescriptorsToEventCommand
   */
  @PutMapping("/addToPayloadDescriptors")
  public void addToPayloadDescriptors(
      @RequestBody(required = true) AssignPayloadDescriptorsToEventCommand command) {
    try {
      EventBusinessDelegate.getEventInstance().addToPayloadDescriptors(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to add to Set PayloadDescriptors", exc);
    }
  }

  /**
   * remove PayloadDescriptors on Event
   *
   * @param command RemovePayloadDescriptorsFromEventCommand
   */
  @PutMapping("/removeFromPayloadDescriptors")
  public void removeFromPayloadDescriptors(
      @RequestBody(required = true) RemovePayloadDescriptorsFromEventCommand command) {
    try {
      EventBusinessDelegate.getEventInstance().removeFromPayloadDescriptors(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to remove from Set PayloadDescriptors", exc);
    }
  }

  /**
   * save Intervals on Event
   *
   * @param command AssignIntervalsToEventCommand
   */
  @PutMapping("/addToIntervals")
  public void addToIntervals(@RequestBody(required = true) AssignIntervalsToEventCommand command) {
    try {
      EventBusinessDelegate.getEventInstance().addToIntervals(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to add to Set Intervals", exc);
    }
  }

  /**
   * remove Intervals on Event
   *
   * @param command RemoveIntervalsFromEventCommand
   */
  @PutMapping("/removeFromIntervals")
  public void removeFromIntervals(
      @RequestBody(required = true) RemoveIntervalsFromEventCommand command) {
    try {
      EventBusinessDelegate.getEventInstance().removeFromIntervals(command);
    } catch (Exception exc) {
      LOGGER.log(Level.WARNING, "Failed to remove from Set Intervals", exc);
    }
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected Event event = null;
  private static final Logger LOGGER = Logger.getLogger(EventCommandRestController.class.getName());
}
