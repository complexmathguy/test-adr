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

public class VenValidator {

  /** default constructor */
  protected VenValidator() {}

  /** factory method */
  public static VenValidator getInstance() {
    return new VenValidator();
  }

  /** handles creation validation for a Ven */
  public void validate(CreateVenCommand ven) throws Exception {
    Assert.notNull(ven, "CreateVenCommand should not be null");
    //		Assert.isNull( ven.getVenId(), "CreateVenCommand identifier should be null" );
    Assert.notNull(
        ven.getCreatedDateTime(), "Field CreateVenCommand.createdDateTime should not be null");
    Assert.notNull(
        ven.getModificationDateTime(),
        "Field CreateVenCommand.modificationDateTime should not be null");
    Assert.notNull(ven.getVenName(), "Field CreateVenCommand.venName should not be null");
  }

  /** handles update validation for a Ven */
  public void validate(UpdateVenCommand ven) throws Exception {
    Assert.notNull(ven, "UpdateVenCommand should not be null");
    Assert.notNull(ven.getVenId(), "UpdateVenCommand identifier should not be null");
    Assert.notNull(
        ven.getCreatedDateTime(), "Field UpdateVenCommand.createdDateTime should not be null");
    Assert.notNull(
        ven.getModificationDateTime(),
        "Field UpdateVenCommand.modificationDateTime should not be null");
    Assert.notNull(ven.getVenName(), "Field UpdateVenCommand.venName should not be null");
  }

  /** handles delete validation for a Ven */
  public void validate(DeleteVenCommand ven) throws Exception {
    Assert.notNull(ven, "{commandAlias} should not be null");
    Assert.notNull(ven.getVenId(), "DeleteVenCommand identifier should not be null");
  }

  /** handles fetchOne validation for a Ven */
  public void validate(VenFetchOneSummary summary) throws Exception {
    Assert.notNull(summary, "VenFetchOneSummary should not be null");
    Assert.notNull(summary.getVenId(), "VenFetchOneSummary identifier should not be null");
  }

  /**
   * handles add to Attributes validation for a Ven
   *
   * @param command AssignAttributesToVenCommand
   */
  public void validate(AssignAttributesToVenCommand command) throws Exception {
    Assert.notNull(command, "AssignAttributesToVenCommand should not be null");
    Assert.notNull(
        command.getVenId(), "AssignAttributesToVenCommand identifier should not be null");
    Assert.notNull(
        command.getAddTo(), "AssignAttributesToVenCommand addTo attribute should not be null");
  }

  /**
   * handles remove from Attributes validation for a Ven
   *
   * @param command RemoveAttributesFromVenCommand
   */
  public void validate(RemoveAttributesFromVenCommand command) throws Exception {
    Assert.notNull(command, "RemoveAttributesFromVenCommand should not be null");
    Assert.notNull(
        command.getVenId(), "RemoveAttributesFromVenCommand identifier should not be null");
    Assert.notNull(
        command.getRemoveFrom(),
        "RemoveAttributesFromVenCommand removeFrom attribute should not be null");
    Assert.notNull(
        command.getRemoveFrom().getValuesMapId(),
        "RemoveAttributesFromVenCommand removeFrom attribubte identifier should not be null");
  }

  /**
   * handles add to Targets validation for a Ven
   *
   * @param command AssignTargetsToVenCommand
   */
  public void validate(AssignTargetsToVenCommand command) throws Exception {
    Assert.notNull(command, "AssignTargetsToVenCommand should not be null");
    Assert.notNull(command.getVenId(), "AssignTargetsToVenCommand identifier should not be null");
    Assert.notNull(
        command.getAddTo(), "AssignTargetsToVenCommand addTo attribute should not be null");
  }

  /**
   * handles remove from Targets validation for a Ven
   *
   * @param command RemoveTargetsFromVenCommand
   */
  public void validate(RemoveTargetsFromVenCommand command) throws Exception {
    Assert.notNull(command, "RemoveTargetsFromVenCommand should not be null");
    Assert.notNull(command.getVenId(), "RemoveTargetsFromVenCommand identifier should not be null");
    Assert.notNull(
        command.getRemoveFrom(),
        "RemoveTargetsFromVenCommand removeFrom attribute should not be null");
    Assert.notNull(
        command.getRemoveFrom().getValuesMapId(),
        "RemoveTargetsFromVenCommand removeFrom attribubte identifier should not be null");
  }

  /**
   * handles add to Resources validation for a Ven
   *
   * @param command AssignResourcesToVenCommand
   */
  public void validate(AssignResourcesToVenCommand command) throws Exception {
    Assert.notNull(command, "AssignResourcesToVenCommand should not be null");
    Assert.notNull(command.getVenId(), "AssignResourcesToVenCommand identifier should not be null");
    Assert.notNull(
        command.getAddTo(), "AssignResourcesToVenCommand addTo attribute should not be null");
  }

  /**
   * handles remove from Resources validation for a Ven
   *
   * @param command RemoveResourcesFromVenCommand
   */
  public void validate(RemoveResourcesFromVenCommand command) throws Exception {
    Assert.notNull(command, "RemoveResourcesFromVenCommand should not be null");
    Assert.notNull(
        command.getVenId(), "RemoveResourcesFromVenCommand identifier should not be null");
    Assert.notNull(
        command.getRemoveFrom(),
        "RemoveResourcesFromVenCommand removeFrom attribute should not be null");
    Assert.notNull(
        command.getRemoveFrom().getResourceId(),
        "RemoveResourcesFromVenCommand removeFrom attribubte identifier should not be null");
  }
}
