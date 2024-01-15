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

public class ReportValidator {

  /** default constructor */
  protected ReportValidator() {}

  /** factory method */
  public static ReportValidator getInstance() {
    return new ReportValidator();
  }

  /** handles creation validation for a Report */
  public void validate(CreateReportCommand report) throws Exception {
    Assert.notNull(report, "CreateReportCommand should not be null");
    //		Assert.isNull( report.getReportId(), "CreateReportCommand identifier should be null" );
    Assert.notNull(
        report.getCreatedDateTime(),
        "Field CreateReportCommand.createdDateTime should not be null");
    Assert.notNull(
        report.getModificationDateTime(),
        "Field CreateReportCommand.modificationDateTime should not be null");
    Assert.notNull(
        report.getClientName(), "Field CreateReportCommand.clientName should not be null");
    Assert.notNull(
        report.getReportName(), "Field CreateReportCommand.reportName should not be null");
  }

  /** handles update validation for a Report */
  public void validate(UpdateReportCommand report) throws Exception {
    Assert.notNull(report, "UpdateReportCommand should not be null");
    Assert.notNull(report.getReportId(), "UpdateReportCommand identifier should not be null");
    Assert.notNull(
        report.getCreatedDateTime(),
        "Field UpdateReportCommand.createdDateTime should not be null");
    Assert.notNull(
        report.getModificationDateTime(),
        "Field UpdateReportCommand.modificationDateTime should not be null");
    Assert.notNull(
        report.getClientName(), "Field UpdateReportCommand.clientName should not be null");
    Assert.notNull(
        report.getReportName(), "Field UpdateReportCommand.reportName should not be null");
  }

  /** handles delete validation for a Report */
  public void validate(DeleteReportCommand report) throws Exception {
    Assert.notNull(report, "{commandAlias} should not be null");
    Assert.notNull(report.getReportId(), "DeleteReportCommand identifier should not be null");
  }

  /** handles fetchOne validation for a Report */
  public void validate(ReportFetchOneSummary summary) throws Exception {
    Assert.notNull(summary, "ReportFetchOneSummary should not be null");
    Assert.notNull(summary.getReportId(), "ReportFetchOneSummary identifier should not be null");
  }

  /**
   * handles assign Program validation for a Report
   *
   * @param command AssignProgramToReportCommand
   */
  public void validate(AssignProgramToReportCommand command) throws Exception {
    Assert.notNull(command, "AssignProgramToReportCommand should not be null");
    Assert.notNull(
        command.getReportId(), "AssignProgramToReportCommand identifier should not be null");
    Assert.notNull(
        command.getAssignment(), "AssignProgramToReportCommand assignment should not be null");
  }

  /**
   * handles unassign Program validation for a Report
   *
   * @param command UnAssignProgramFromReportCommand
   */
  public void validate(UnAssignProgramFromReportCommand command) throws Exception {
    Assert.notNull(command, "UnAssignProgramFromReportCommand should not be null");
    Assert.notNull(
        command.getReportId(), "UnAssignProgramFromReportCommand identifier should not be null");
  }
  /**
   * handles assign Event validation for a Report
   *
   * @param command AssignEventToReportCommand
   */
  public void validate(AssignEventToReportCommand command) throws Exception {
    Assert.notNull(command, "AssignEventToReportCommand should not be null");
    Assert.notNull(
        command.getReportId(), "AssignEventToReportCommand identifier should not be null");
    Assert.notNull(
        command.getAssignment(), "AssignEventToReportCommand assignment should not be null");
  }

  /**
   * handles unassign Event validation for a Report
   *
   * @param command UnAssignEventFromReportCommand
   */
  public void validate(UnAssignEventFromReportCommand command) throws Exception {
    Assert.notNull(command, "UnAssignEventFromReportCommand should not be null");
    Assert.notNull(
        command.getReportId(), "UnAssignEventFromReportCommand identifier should not be null");
  }
  /**
   * handles assign Intervals validation for a Report
   *
   * @param command AssignIntervalsToReportCommand
   */
  public void validate(AssignIntervalsToReportCommand command) throws Exception {
    Assert.notNull(command, "AssignIntervalsToReportCommand should not be null");
    Assert.notNull(
        command.getReportId(), "AssignIntervalsToReportCommand identifier should not be null");
    Assert.notNull(
        command.getAssignment(), "AssignIntervalsToReportCommand assignment should not be null");
  }

  /**
   * handles unassign Intervals validation for a Report
   *
   * @param command UnAssignIntervalsFromReportCommand
   */
  public void validate(UnAssignIntervalsFromReportCommand command) throws Exception {
    Assert.notNull(command, "UnAssignIntervalsFromReportCommand should not be null");
    Assert.notNull(
        command.getReportId(), "UnAssignIntervalsFromReportCommand identifier should not be null");
  }
  /**
   * handles assign IntervalPeriod validation for a Report
   *
   * @param command AssignIntervalPeriodToReportCommand
   */
  public void validate(AssignIntervalPeriodToReportCommand command) throws Exception {
    Assert.notNull(command, "AssignIntervalPeriodToReportCommand should not be null");
    Assert.notNull(
        command.getReportId(), "AssignIntervalPeriodToReportCommand identifier should not be null");
    Assert.notNull(
        command.getAssignment(),
        "AssignIntervalPeriodToReportCommand assignment should not be null");
  }

  /**
   * handles unassign IntervalPeriod validation for a Report
   *
   * @param command UnAssignIntervalPeriodFromReportCommand
   */
  public void validate(UnAssignIntervalPeriodFromReportCommand command) throws Exception {
    Assert.notNull(command, "UnAssignIntervalPeriodFromReportCommand should not be null");
    Assert.notNull(
        command.getReportId(),
        "UnAssignIntervalPeriodFromReportCommand identifier should not be null");
  }

  /**
   * handles add to PayloadDescriptors validation for a Report
   *
   * @param command AssignPayloadDescriptorsToReportCommand
   */
  public void validate(AssignPayloadDescriptorsToReportCommand command) throws Exception {
    Assert.notNull(command, "AssignPayloadDescriptorsToReportCommand should not be null");
    Assert.notNull(
        command.getReportId(),
        "AssignPayloadDescriptorsToReportCommand identifier should not be null");
    Assert.notNull(
        command.getAddTo(),
        "AssignPayloadDescriptorsToReportCommand addTo attribute should not be null");
  }

  /**
   * handles remove from PayloadDescriptors validation for a Report
   *
   * @param command RemovePayloadDescriptorsFromReportCommand
   */
  public void validate(RemovePayloadDescriptorsFromReportCommand command) throws Exception {
    Assert.notNull(command, "RemovePayloadDescriptorsFromReportCommand should not be null");
    Assert.notNull(
        command.getReportId(),
        "RemovePayloadDescriptorsFromReportCommand identifier should not be null");
    Assert.notNull(
        command.getRemoveFrom(),
        "RemovePayloadDescriptorsFromReportCommand removeFrom attribute should not be null");
    Assert.notNull(
        command.getRemoveFrom().getPayloadDescriptorId(),
        "RemovePayloadDescriptorsFromReportCommand removeFrom attribubte identifier should not be null");
  }

  /**
   * handles add to Resources validation for a Report
   *
   * @param command AssignResourcesToReportCommand
   */
  public void validate(AssignResourcesToReportCommand command) throws Exception {
    Assert.notNull(command, "AssignResourcesToReportCommand should not be null");
    Assert.notNull(
        command.getReportId(), "AssignResourcesToReportCommand identifier should not be null");
    Assert.notNull(
        command.getAddTo(), "AssignResourcesToReportCommand addTo attribute should not be null");
  }

  /**
   * handles remove from Resources validation for a Report
   *
   * @param command RemoveResourcesFromReportCommand
   */
  public void validate(RemoveResourcesFromReportCommand command) throws Exception {
    Assert.notNull(command, "RemoveResourcesFromReportCommand should not be null");
    Assert.notNull(
        command.getReportId(), "RemoveResourcesFromReportCommand identifier should not be null");
    Assert.notNull(
        command.getRemoveFrom(),
        "RemoveResourcesFromReportCommand removeFrom attribute should not be null");
    Assert.notNull(
        command.getRemoveFrom().getResourceId(),
        "RemoveResourcesFromReportCommand removeFrom attribubte identifier should not be null");
  }
}
