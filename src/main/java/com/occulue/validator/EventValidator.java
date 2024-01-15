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
package com.occulue.validator;

import com.occulue.api.*;
import org.springframework.util.Assert;

public class EventValidator {

  /** default constructor */
  protected EventValidator() {}

  /** factory method */
  public static EventValidator getInstance() {
    return new EventValidator();
  }

  /** handles creation validation for a Event */
  public void validate(CreateEventCommand event) throws Exception {
    Assert.notNull(event, "CreateEventCommand should not be null");
    //		Assert.isNull( event.getEventId(), "CreateEventCommand identifier should be null" );
    Assert.notNull(
        event.getCreatedDateTime(), "Field CreateEventCommand.createdDateTime should not be null");
    Assert.notNull(
        event.getModificationDateTime(),
        "Field CreateEventCommand.modificationDateTime should not be null");
    Assert.notNull(event.getEventName(), "Field CreateEventCommand.eventName should not be null");
    Assert.notNull(event.getPriority(), "Field CreateEventCommand.priority should not be null");
  }

  /** handles update validation for a Event */
  public void validate(UpdateEventCommand event) throws Exception {
    Assert.notNull(event, "UpdateEventCommand should not be null");
    Assert.notNull(event.getEventId(), "UpdateEventCommand identifier should not be null");
    Assert.notNull(
        event.getCreatedDateTime(), "Field UpdateEventCommand.createdDateTime should not be null");
    Assert.notNull(
        event.getModificationDateTime(),
        "Field UpdateEventCommand.modificationDateTime should not be null");
    Assert.notNull(event.getEventName(), "Field UpdateEventCommand.eventName should not be null");
    Assert.notNull(event.getPriority(), "Field UpdateEventCommand.priority should not be null");
  }

  /** handles delete validation for a Event */
  public void validate(DeleteEventCommand event) throws Exception {
    Assert.notNull(event, "{commandAlias} should not be null");
    Assert.notNull(event.getEventId(), "DeleteEventCommand identifier should not be null");
  }

  /** handles fetchOne validation for a Event */
  public void validate(EventFetchOneSummary summary) throws Exception {
    Assert.notNull(summary, "EventFetchOneSummary should not be null");
    Assert.notNull(summary.getEventId(), "EventFetchOneSummary identifier should not be null");
  }

  /**
   * handles assign Program validation for a Event
   *
   * @param command AssignProgramToEventCommand
   */
  public void validate(AssignProgramToEventCommand command) throws Exception {
    Assert.notNull(command, "AssignProgramToEventCommand should not be null");
    Assert.notNull(
        command.getEventId(), "AssignProgramToEventCommand identifier should not be null");
    Assert.notNull(
        command.getAssignment(), "AssignProgramToEventCommand assignment should not be null");
  }

  /**
   * handles unassign Program validation for a Event
   *
   * @param command UnAssignProgramFromEventCommand
   */
  public void validate(UnAssignProgramFromEventCommand command) throws Exception {
    Assert.notNull(command, "UnAssignProgramFromEventCommand should not be null");
    Assert.notNull(
        command.getEventId(), "UnAssignProgramFromEventCommand identifier should not be null");
  }
  /**
   * handles assign Targets validation for a Event
   *
   * @param command AssignTargetsToEventCommand
   */
  public void validate(AssignTargetsToEventCommand command) throws Exception {
    Assert.notNull(command, "AssignTargetsToEventCommand should not be null");
    Assert.notNull(
        command.getEventId(), "AssignTargetsToEventCommand identifier should not be null");
    Assert.notNull(
        command.getAssignment(), "AssignTargetsToEventCommand assignment should not be null");
  }

  /**
   * handles unassign Targets validation for a Event
   *
   * @param command UnAssignTargetsFromEventCommand
   */
  public void validate(UnAssignTargetsFromEventCommand command) throws Exception {
    Assert.notNull(command, "UnAssignTargetsFromEventCommand should not be null");
    Assert.notNull(
        command.getEventId(), "UnAssignTargetsFromEventCommand identifier should not be null");
  }
  /**
   * handles assign IntervalPeriod validation for a Event
   *
   * @param command AssignIntervalPeriodToEventCommand
   */
  public void validate(AssignIntervalPeriodToEventCommand command) throws Exception {
    Assert.notNull(command, "AssignIntervalPeriodToEventCommand should not be null");
    Assert.notNull(
        command.getEventId(), "AssignIntervalPeriodToEventCommand identifier should not be null");
    Assert.notNull(
        command.getAssignment(),
        "AssignIntervalPeriodToEventCommand assignment should not be null");
  }

  /**
   * handles unassign IntervalPeriod validation for a Event
   *
   * @param command UnAssignIntervalPeriodFromEventCommand
   */
  public void validate(UnAssignIntervalPeriodFromEventCommand command) throws Exception {
    Assert.notNull(command, "UnAssignIntervalPeriodFromEventCommand should not be null");
    Assert.notNull(
        command.getEventId(),
        "UnAssignIntervalPeriodFromEventCommand identifier should not be null");
  }

  /**
   * handles add to ReportDescriptors validation for a Event
   *
   * @param command AssignReportDescriptorsToEventCommand
   */
  public void validate(AssignReportDescriptorsToEventCommand command) throws Exception {
    Assert.notNull(command, "AssignReportDescriptorsToEventCommand should not be null");
    Assert.notNull(
        command.getEventId(),
        "AssignReportDescriptorsToEventCommand identifier should not be null");
    Assert.notNull(
        command.getAddTo(),
        "AssignReportDescriptorsToEventCommand addTo attribute should not be null");
  }

  /**
   * handles remove from ReportDescriptors validation for a Event
   *
   * @param command RemoveReportDescriptorsFromEventCommand
   */
  public void validate(RemoveReportDescriptorsFromEventCommand command) throws Exception {
    Assert.notNull(command, "RemoveReportDescriptorsFromEventCommand should not be null");
    Assert.notNull(
        command.getEventId(),
        "RemoveReportDescriptorsFromEventCommand identifier should not be null");
    Assert.notNull(
        command.getRemoveFrom(),
        "RemoveReportDescriptorsFromEventCommand removeFrom attribute should not be null");
    Assert.notNull(
        command.getRemoveFrom().getReportDescriptorId(),
        "RemoveReportDescriptorsFromEventCommand removeFrom attribubte identifier should not be null");
  }

  /**
   * handles add to PayloadDescriptors validation for a Event
   *
   * @param command AssignPayloadDescriptorsToEventCommand
   */
  public void validate(AssignPayloadDescriptorsToEventCommand command) throws Exception {
    Assert.notNull(command, "AssignPayloadDescriptorsToEventCommand should not be null");
    Assert.notNull(
        command.getEventId(),
        "AssignPayloadDescriptorsToEventCommand identifier should not be null");
    Assert.notNull(
        command.getAddTo(),
        "AssignPayloadDescriptorsToEventCommand addTo attribute should not be null");
  }

  /**
   * handles remove from PayloadDescriptors validation for a Event
   *
   * @param command RemovePayloadDescriptorsFromEventCommand
   */
  public void validate(RemovePayloadDescriptorsFromEventCommand command) throws Exception {
    Assert.notNull(command, "RemovePayloadDescriptorsFromEventCommand should not be null");
    Assert.notNull(
        command.getEventId(),
        "RemovePayloadDescriptorsFromEventCommand identifier should not be null");
    Assert.notNull(
        command.getRemoveFrom(),
        "RemovePayloadDescriptorsFromEventCommand removeFrom attribute should not be null");
    Assert.notNull(
        command.getRemoveFrom().getPayloadDescriptorId(),
        "RemovePayloadDescriptorsFromEventCommand removeFrom attribubte identifier should not be null");
  }

  /**
   * handles add to Intervals validation for a Event
   *
   * @param command AssignIntervalsToEventCommand
   */
  public void validate(AssignIntervalsToEventCommand command) throws Exception {
    Assert.notNull(command, "AssignIntervalsToEventCommand should not be null");
    Assert.notNull(
        command.getEventId(), "AssignIntervalsToEventCommand identifier should not be null");
    Assert.notNull(
        command.getAddTo(), "AssignIntervalsToEventCommand addTo attribute should not be null");
  }

  /**
   * handles remove from Intervals validation for a Event
   *
   * @param command RemoveIntervalsFromEventCommand
   */
  public void validate(RemoveIntervalsFromEventCommand command) throws Exception {
    Assert.notNull(command, "RemoveIntervalsFromEventCommand should not be null");
    Assert.notNull(
        command.getEventId(), "RemoveIntervalsFromEventCommand identifier should not be null");
    Assert.notNull(
        command.getRemoveFrom(),
        "RemoveIntervalsFromEventCommand removeFrom attribute should not be null");
    Assert.notNull(
        command.getRemoveFrom().getIntervalId(),
        "RemoveIntervalsFromEventCommand removeFrom attribubte identifier should not be null");
  }
}
