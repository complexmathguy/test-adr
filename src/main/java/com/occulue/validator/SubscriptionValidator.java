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

public class SubscriptionValidator {

  /** default constructor */
  protected SubscriptionValidator() {}

  /** factory method */
  public static SubscriptionValidator getInstance() {
    return new SubscriptionValidator();
  }

  /** handles creation validation for a Subscription */
  public void validate(CreateSubscriptionCommand subscription) throws Exception {
    Assert.notNull(subscription, "CreateSubscriptionCommand should not be null");
    //		Assert.isNull( subscription.getSubscriptionId(), "CreateSubscriptionCommand identifier
    // should be null" );
    Assert.notNull(
        subscription.getCreatedDateTime(),
        "Field CreateSubscriptionCommand.createdDateTime should not be null");
    Assert.notNull(
        subscription.getModificationDateTime(),
        "Field CreateSubscriptionCommand.modificationDateTime should not be null");
    Assert.notNull(
        subscription.getClientName(),
        "Field CreateSubscriptionCommand.clientName should not be null");
  }

  /** handles update validation for a Subscription */
  public void validate(UpdateSubscriptionCommand subscription) throws Exception {
    Assert.notNull(subscription, "UpdateSubscriptionCommand should not be null");
    Assert.notNull(
        subscription.getSubscriptionId(),
        "UpdateSubscriptionCommand identifier should not be null");
    Assert.notNull(
        subscription.getCreatedDateTime(),
        "Field UpdateSubscriptionCommand.createdDateTime should not be null");
    Assert.notNull(
        subscription.getModificationDateTime(),
        "Field UpdateSubscriptionCommand.modificationDateTime should not be null");
    Assert.notNull(
        subscription.getClientName(),
        "Field UpdateSubscriptionCommand.clientName should not be null");
  }

  /** handles delete validation for a Subscription */
  public void validate(DeleteSubscriptionCommand subscription) throws Exception {
    Assert.notNull(subscription, "{commandAlias} should not be null");
    Assert.notNull(
        subscription.getSubscriptionId(),
        "DeleteSubscriptionCommand identifier should not be null");
  }

  /** handles fetchOne validation for a Subscription */
  public void validate(SubscriptionFetchOneSummary summary) throws Exception {
    Assert.notNull(summary, "SubscriptionFetchOneSummary should not be null");
    Assert.notNull(
        summary.getSubscriptionId(), "SubscriptionFetchOneSummary identifier should not be null");
  }

  /**
   * handles assign Program validation for a Subscription
   *
   * @param command AssignProgramToSubscriptionCommand
   */
  public void validate(AssignProgramToSubscriptionCommand command) throws Exception {
    Assert.notNull(command, "AssignProgramToSubscriptionCommand should not be null");
    Assert.notNull(
        command.getSubscriptionId(),
        "AssignProgramToSubscriptionCommand identifier should not be null");
    Assert.notNull(
        command.getAssignment(),
        "AssignProgramToSubscriptionCommand assignment should not be null");
  }

  /**
   * handles unassign Program validation for a Subscription
   *
   * @param command UnAssignProgramFromSubscriptionCommand
   */
  public void validate(UnAssignProgramFromSubscriptionCommand command) throws Exception {
    Assert.notNull(command, "UnAssignProgramFromSubscriptionCommand should not be null");
    Assert.notNull(
        command.getSubscriptionId(),
        "UnAssignProgramFromSubscriptionCommand identifier should not be null");
  }
  /**
   * handles assign Targets validation for a Subscription
   *
   * @param command AssignTargetsToSubscriptionCommand
   */
  public void validate(AssignTargetsToSubscriptionCommand command) throws Exception {
    Assert.notNull(command, "AssignTargetsToSubscriptionCommand should not be null");
    Assert.notNull(
        command.getSubscriptionId(),
        "AssignTargetsToSubscriptionCommand identifier should not be null");
    Assert.notNull(
        command.getAssignment(),
        "AssignTargetsToSubscriptionCommand assignment should not be null");
  }

  /**
   * handles unassign Targets validation for a Subscription
   *
   * @param command UnAssignTargetsFromSubscriptionCommand
   */
  public void validate(UnAssignTargetsFromSubscriptionCommand command) throws Exception {
    Assert.notNull(command, "UnAssignTargetsFromSubscriptionCommand should not be null");
    Assert.notNull(
        command.getSubscriptionId(),
        "UnAssignTargetsFromSubscriptionCommand identifier should not be null");
  }

  /**
   * handles add to ObjectOperations validation for a Subscription
   *
   * @param command AssignObjectOperationsToSubscriptionCommand
   */
  public void validate(AssignObjectOperationsToSubscriptionCommand command) throws Exception {
    Assert.notNull(command, "AssignObjectOperationsToSubscriptionCommand should not be null");
    Assert.notNull(
        command.getSubscriptionId(),
        "AssignObjectOperationsToSubscriptionCommand identifier should not be null");
    Assert.notNull(
        command.getAddTo(),
        "AssignObjectOperationsToSubscriptionCommand addTo attribute should not be null");
  }

  /**
   * handles remove from ObjectOperations validation for a Subscription
   *
   * @param command RemoveObjectOperationsFromSubscriptionCommand
   */
  public void validate(RemoveObjectOperationsFromSubscriptionCommand command) throws Exception {
    Assert.notNull(command, "RemoveObjectOperationsFromSubscriptionCommand should not be null");
    Assert.notNull(
        command.getSubscriptionId(),
        "RemoveObjectOperationsFromSubscriptionCommand identifier should not be null");
    Assert.notNull(
        command.getRemoveFrom(),
        "RemoveObjectOperationsFromSubscriptionCommand removeFrom attribute should not be null");
    Assert.notNull(
        command.getRemoveFrom().getObjectOperationId(),
        "RemoveObjectOperationsFromSubscriptionCommand removeFrom attribubte identifier should not be null");
  }
}
