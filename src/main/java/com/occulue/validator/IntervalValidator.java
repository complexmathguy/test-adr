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

public class IntervalValidator {

  /** default constructor */
  protected IntervalValidator() {}

  /** factory method */
  public static IntervalValidator getInstance() {
    return new IntervalValidator();
  }

  /** handles creation validation for a Interval */
  public void validate(CreateIntervalCommand interval) throws Exception {
    Assert.notNull(interval, "CreateIntervalCommand should not be null");
    //		Assert.isNull( interval.getIntervalId(), "CreateIntervalCommand identifier should be null"
    // );
  }

  /** handles update validation for a Interval */
  public void validate(UpdateIntervalCommand interval) throws Exception {
    Assert.notNull(interval, "UpdateIntervalCommand should not be null");
    Assert.notNull(interval.getIntervalId(), "UpdateIntervalCommand identifier should not be null");
  }

  /** handles delete validation for a Interval */
  public void validate(DeleteIntervalCommand interval) throws Exception {
    Assert.notNull(interval, "{commandAlias} should not be null");
    Assert.notNull(interval.getIntervalId(), "DeleteIntervalCommand identifier should not be null");
  }

  /** handles fetchOne validation for a Interval */
  public void validate(IntervalFetchOneSummary summary) throws Exception {
    Assert.notNull(summary, "IntervalFetchOneSummary should not be null");
    Assert.notNull(
        summary.getIntervalId(), "IntervalFetchOneSummary identifier should not be null");
  }

  /**
   * handles assign IntervalPeriod validation for a Interval
   *
   * @param command AssignIntervalPeriodToIntervalCommand
   */
  public void validate(AssignIntervalPeriodToIntervalCommand command) throws Exception {
    Assert.notNull(command, "AssignIntervalPeriodToIntervalCommand should not be null");
    Assert.notNull(
        command.getIntervalId(),
        "AssignIntervalPeriodToIntervalCommand identifier should not be null");
    Assert.notNull(
        command.getAssignment(),
        "AssignIntervalPeriodToIntervalCommand assignment should not be null");
  }

  /**
   * handles unassign IntervalPeriod validation for a Interval
   *
   * @param command UnAssignIntervalPeriodFromIntervalCommand
   */
  public void validate(UnAssignIntervalPeriodFromIntervalCommand command) throws Exception {
    Assert.notNull(command, "UnAssignIntervalPeriodFromIntervalCommand should not be null");
    Assert.notNull(
        command.getIntervalId(),
        "UnAssignIntervalPeriodFromIntervalCommand identifier should not be null");
  }

  /**
   * handles add to Payloads validation for a Interval
   *
   * @param command AssignPayloadsToIntervalCommand
   */
  public void validate(AssignPayloadsToIntervalCommand command) throws Exception {
    Assert.notNull(command, "AssignPayloadsToIntervalCommand should not be null");
    Assert.notNull(
        command.getIntervalId(), "AssignPayloadsToIntervalCommand identifier should not be null");
    Assert.notNull(
        command.getAddTo(), "AssignPayloadsToIntervalCommand addTo attribute should not be null");
  }

  /**
   * handles remove from Payloads validation for a Interval
   *
   * @param command RemovePayloadsFromIntervalCommand
   */
  public void validate(RemovePayloadsFromIntervalCommand command) throws Exception {
    Assert.notNull(command, "RemovePayloadsFromIntervalCommand should not be null");
    Assert.notNull(
        command.getIntervalId(), "RemovePayloadsFromIntervalCommand identifier should not be null");
    Assert.notNull(
        command.getRemoveFrom(),
        "RemovePayloadsFromIntervalCommand removeFrom attribute should not be null");
    Assert.notNull(
        command.getRemoveFrom().getValuesMapId(),
        "RemovePayloadsFromIntervalCommand removeFrom attribubte identifier should not be null");
  }
}
