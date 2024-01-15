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

public class ResourceValidator {

  /** default constructor */
  protected ResourceValidator() {}

  /** factory method */
  public static ResourceValidator getInstance() {
    return new ResourceValidator();
  }

  /** handles creation validation for a Resource */
  public void validate(CreateResourceCommand resource) throws Exception {
    Assert.notNull(resource, "CreateResourceCommand should not be null");
    //		Assert.isNull( resource.getResourceId(), "CreateResourceCommand identifier should be null"
    // );
    Assert.notNull(
        resource.getCreatedDateTime(),
        "Field CreateResourceCommand.createdDateTime should not be null");
    Assert.notNull(
        resource.getModificationDateTime(),
        "Field CreateResourceCommand.modificationDateTime should not be null");
    Assert.notNull(
        resource.getResourceName(), "Field CreateResourceCommand.resourceName should not be null");
  }

  /** handles update validation for a Resource */
  public void validate(UpdateResourceCommand resource) throws Exception {
    Assert.notNull(resource, "UpdateResourceCommand should not be null");
    Assert.notNull(resource.getResourceId(), "UpdateResourceCommand identifier should not be null");
    Assert.notNull(
        resource.getCreatedDateTime(),
        "Field UpdateResourceCommand.createdDateTime should not be null");
    Assert.notNull(
        resource.getModificationDateTime(),
        "Field UpdateResourceCommand.modificationDateTime should not be null");
    Assert.notNull(
        resource.getResourceName(), "Field UpdateResourceCommand.resourceName should not be null");
  }

  /** handles delete validation for a Resource */
  public void validate(DeleteResourceCommand resource) throws Exception {
    Assert.notNull(resource, "{commandAlias} should not be null");
    Assert.notNull(resource.getResourceId(), "DeleteResourceCommand identifier should not be null");
  }

  /** handles fetchOne validation for a Resource */
  public void validate(ResourceFetchOneSummary summary) throws Exception {
    Assert.notNull(summary, "ResourceFetchOneSummary should not be null");
    Assert.notNull(
        summary.getResourceId(), "ResourceFetchOneSummary identifier should not be null");
  }

  /**
   * handles assign Ven validation for a Resource
   *
   * @param command AssignVenToResourceCommand
   */
  public void validate(AssignVenToResourceCommand command) throws Exception {
    Assert.notNull(command, "AssignVenToResourceCommand should not be null");
    Assert.notNull(
        command.getResourceId(), "AssignVenToResourceCommand identifier should not be null");
    Assert.notNull(
        command.getAssignment(), "AssignVenToResourceCommand assignment should not be null");
  }

  /**
   * handles unassign Ven validation for a Resource
   *
   * @param command UnAssignVenFromResourceCommand
   */
  public void validate(UnAssignVenFromResourceCommand command) throws Exception {
    Assert.notNull(command, "UnAssignVenFromResourceCommand should not be null");
    Assert.notNull(
        command.getResourceId(), "UnAssignVenFromResourceCommand identifier should not be null");
  }

  /**
   * handles add to Attributes validation for a Resource
   *
   * @param command AssignAttributesToResourceCommand
   */
  public void validate(AssignAttributesToResourceCommand command) throws Exception {
    Assert.notNull(command, "AssignAttributesToResourceCommand should not be null");
    Assert.notNull(
        command.getResourceId(), "AssignAttributesToResourceCommand identifier should not be null");
    Assert.notNull(
        command.getAddTo(), "AssignAttributesToResourceCommand addTo attribute should not be null");
  }

  /**
   * handles remove from Attributes validation for a Resource
   *
   * @param command RemoveAttributesFromResourceCommand
   */
  public void validate(RemoveAttributesFromResourceCommand command) throws Exception {
    Assert.notNull(command, "RemoveAttributesFromResourceCommand should not be null");
    Assert.notNull(
        command.getResourceId(),
        "RemoveAttributesFromResourceCommand identifier should not be null");
    Assert.notNull(
        command.getRemoveFrom(),
        "RemoveAttributesFromResourceCommand removeFrom attribute should not be null");
    Assert.notNull(
        command.getRemoveFrom().getValuesMapId(),
        "RemoveAttributesFromResourceCommand removeFrom attribubte identifier should not be null");
  }

  /**
   * handles add to Targets validation for a Resource
   *
   * @param command AssignTargetsToResourceCommand
   */
  public void validate(AssignTargetsToResourceCommand command) throws Exception {
    Assert.notNull(command, "AssignTargetsToResourceCommand should not be null");
    Assert.notNull(
        command.getResourceId(), "AssignTargetsToResourceCommand identifier should not be null");
    Assert.notNull(
        command.getAddTo(), "AssignTargetsToResourceCommand addTo attribute should not be null");
  }

  /**
   * handles remove from Targets validation for a Resource
   *
   * @param command RemoveTargetsFromResourceCommand
   */
  public void validate(RemoveTargetsFromResourceCommand command) throws Exception {
    Assert.notNull(command, "RemoveTargetsFromResourceCommand should not be null");
    Assert.notNull(
        command.getResourceId(), "RemoveTargetsFromResourceCommand identifier should not be null");
    Assert.notNull(
        command.getRemoveFrom(),
        "RemoveTargetsFromResourceCommand removeFrom attribute should not be null");
    Assert.notNull(
        command.getRemoveFrom().getValuesMapId(),
        "RemoveTargetsFromResourceCommand removeFrom attribubte identifier should not be null");
  }
}
