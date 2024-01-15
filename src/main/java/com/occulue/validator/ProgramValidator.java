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

public class ProgramValidator {

  /** default constructor */
  protected ProgramValidator() {}

  /** factory method */
  public static ProgramValidator getInstance() {
    return new ProgramValidator();
  }

  /** handles creation validation for a Program */
  public void validate(CreateProgramCommand program) throws Exception {
    Assert.notNull(program, "CreateProgramCommand should not be null");
    //		Assert.isNull( program.getProgramId(), "CreateProgramCommand identifier should be null" );
    Assert.notNull(
        program.getCreatedDateTime(),
        "Field CreateProgramCommand.createdDateTime should not be null");
    Assert.notNull(
        program.getModificationDateTime(),
        "Field CreateProgramCommand.modificationDateTime should not be null");
    Assert.notNull(
        program.getProgramName(), "Field CreateProgramCommand.programName should not be null");
    Assert.notNull(
        program.getProgramLongName(),
        "Field CreateProgramCommand.programLongName should not be null");
    Assert.notNull(
        program.getRetailerName(), "Field CreateProgramCommand.retailerName should not be null");
    Assert.notNull(
        program.getRetailerLongName(),
        "Field CreateProgramCommand.retailerLongName should not be null");
    Assert.notNull(
        program.getProgramType(), "Field CreateProgramCommand.programType should not be null");
    Assert.notNull(program.getCountry(), "Field CreateProgramCommand.country should not be null");
    Assert.notNull(
        program.getPrincipalSubdivision(),
        "Field CreateProgramCommand.principalSubdivision should not be null");
    Assert.notNull(
        program.getTimeZoneOffset(),
        "Field CreateProgramCommand.timeZoneOffset should not be null");
    Assert.notNull(
        program.getProgramDescriptions(),
        "Field CreateProgramCommand.programDescriptions should not be null");
  }

  /** handles update validation for a Program */
  public void validate(UpdateProgramCommand program) throws Exception {
    Assert.notNull(program, "UpdateProgramCommand should not be null");
    Assert.notNull(program.getProgramId(), "UpdateProgramCommand identifier should not be null");
    Assert.notNull(
        program.getCreatedDateTime(),
        "Field UpdateProgramCommand.createdDateTime should not be null");
    Assert.notNull(
        program.getModificationDateTime(),
        "Field UpdateProgramCommand.modificationDateTime should not be null");
    Assert.notNull(
        program.getProgramName(), "Field UpdateProgramCommand.programName should not be null");
    Assert.notNull(
        program.getProgramLongName(),
        "Field UpdateProgramCommand.programLongName should not be null");
    Assert.notNull(
        program.getRetailerName(), "Field UpdateProgramCommand.retailerName should not be null");
    Assert.notNull(
        program.getRetailerLongName(),
        "Field UpdateProgramCommand.retailerLongName should not be null");
    Assert.notNull(
        program.getProgramType(), "Field UpdateProgramCommand.programType should not be null");
    Assert.notNull(program.getCountry(), "Field UpdateProgramCommand.country should not be null");
    Assert.notNull(
        program.getPrincipalSubdivision(),
        "Field UpdateProgramCommand.principalSubdivision should not be null");
    Assert.notNull(
        program.getTimeZoneOffset(),
        "Field UpdateProgramCommand.timeZoneOffset should not be null");
    Assert.notNull(
        program.getProgramDescriptions(),
        "Field UpdateProgramCommand.programDescriptions should not be null");
  }

  /** handles delete validation for a Program */
  public void validate(DeleteProgramCommand program) throws Exception {
    Assert.notNull(program, "{commandAlias} should not be null");
    Assert.notNull(program.getProgramId(), "DeleteProgramCommand identifier should not be null");
  }

  /** handles fetchOne validation for a Program */
  public void validate(ProgramFetchOneSummary summary) throws Exception {
    Assert.notNull(summary, "ProgramFetchOneSummary should not be null");
    Assert.notNull(summary.getProgramId(), "ProgramFetchOneSummary identifier should not be null");
  }

  /**
   * handles assign IntervalPeriod validation for a Program
   *
   * @param command AssignIntervalPeriodToProgramCommand
   */
  public void validate(AssignIntervalPeriodToProgramCommand command) throws Exception {
    Assert.notNull(command, "AssignIntervalPeriodToProgramCommand should not be null");
    Assert.notNull(
        command.getProgramId(),
        "AssignIntervalPeriodToProgramCommand identifier should not be null");
    Assert.notNull(
        command.getAssignment(),
        "AssignIntervalPeriodToProgramCommand assignment should not be null");
  }

  /**
   * handles unassign IntervalPeriod validation for a Program
   *
   * @param command UnAssignIntervalPeriodFromProgramCommand
   */
  public void validate(UnAssignIntervalPeriodFromProgramCommand command) throws Exception {
    Assert.notNull(command, "UnAssignIntervalPeriodFromProgramCommand should not be null");
    Assert.notNull(
        command.getProgramId(),
        "UnAssignIntervalPeriodFromProgramCommand identifier should not be null");
  }

  /**
   * handles add to PayloadDescriptors validation for a Program
   *
   * @param command AssignPayloadDescriptorsToProgramCommand
   */
  public void validate(AssignPayloadDescriptorsToProgramCommand command) throws Exception {
    Assert.notNull(command, "AssignPayloadDescriptorsToProgramCommand should not be null");
    Assert.notNull(
        command.getProgramId(),
        "AssignPayloadDescriptorsToProgramCommand identifier should not be null");
    Assert.notNull(
        command.getAddTo(),
        "AssignPayloadDescriptorsToProgramCommand addTo attribute should not be null");
  }

  /**
   * handles remove from PayloadDescriptors validation for a Program
   *
   * @param command RemovePayloadDescriptorsFromProgramCommand
   */
  public void validate(RemovePayloadDescriptorsFromProgramCommand command) throws Exception {
    Assert.notNull(command, "RemovePayloadDescriptorsFromProgramCommand should not be null");
    Assert.notNull(
        command.getProgramId(),
        "RemovePayloadDescriptorsFromProgramCommand identifier should not be null");
    Assert.notNull(
        command.getRemoveFrom(),
        "RemovePayloadDescriptorsFromProgramCommand removeFrom attribute should not be null");
    Assert.notNull(
        command.getRemoveFrom().getPayloadDescriptorId(),
        "RemovePayloadDescriptorsFromProgramCommand removeFrom attribubte identifier should not be null");
  }

  /**
   * handles add to Targets validation for a Program
   *
   * @param command AssignTargetsToProgramCommand
   */
  public void validate(AssignTargetsToProgramCommand command) throws Exception {
    Assert.notNull(command, "AssignTargetsToProgramCommand should not be null");
    Assert.notNull(
        command.getProgramId(), "AssignTargetsToProgramCommand identifier should not be null");
    Assert.notNull(
        command.getAddTo(), "AssignTargetsToProgramCommand addTo attribute should not be null");
  }

  /**
   * handles remove from Targets validation for a Program
   *
   * @param command RemoveTargetsFromProgramCommand
   */
  public void validate(RemoveTargetsFromProgramCommand command) throws Exception {
    Assert.notNull(command, "RemoveTargetsFromProgramCommand should not be null");
    Assert.notNull(
        command.getProgramId(), "RemoveTargetsFromProgramCommand identifier should not be null");
    Assert.notNull(
        command.getRemoveFrom(),
        "RemoveTargetsFromProgramCommand removeFrom attribute should not be null");
    Assert.notNull(
        command.getRemoveFrom().getValuesMapId(),
        "RemoveTargetsFromProgramCommand removeFrom attribubte identifier should not be null");
  }
}
