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

public class ObjectOperationValidator {

  /** default constructor */
  protected ObjectOperationValidator() {}

  /** factory method */
  public static ObjectOperationValidator getInstance() {
    return new ObjectOperationValidator();
  }

  /** handles creation validation for a ObjectOperation */
  public void validate(CreateObjectOperationCommand objectOperation) throws Exception {
    Assert.notNull(objectOperation, "CreateObjectOperationCommand should not be null");
    //		Assert.isNull( objectOperation.getObjectOperationId(), "CreateObjectOperationCommand
    // identifier should be null" );
    Assert.notNull(
        objectOperation.getCallbackUrl(),
        "Field CreateObjectOperationCommand.callbackUrl should not be null");
    Assert.notNull(
        objectOperation.getBearerToken(),
        "Field CreateObjectOperationCommand.bearerToken should not be null");
  }

  /** handles update validation for a ObjectOperation */
  public void validate(UpdateObjectOperationCommand objectOperation) throws Exception {
    Assert.notNull(objectOperation, "UpdateObjectOperationCommand should not be null");
    Assert.notNull(
        objectOperation.getObjectOperationId(),
        "UpdateObjectOperationCommand identifier should not be null");
    Assert.notNull(
        objectOperation.getCallbackUrl(),
        "Field UpdateObjectOperationCommand.callbackUrl should not be null");
    Assert.notNull(
        objectOperation.getBearerToken(),
        "Field UpdateObjectOperationCommand.bearerToken should not be null");
  }

  /** handles delete validation for a ObjectOperation */
  public void validate(DeleteObjectOperationCommand objectOperation) throws Exception {
    Assert.notNull(objectOperation, "{commandAlias} should not be null");
    Assert.notNull(
        objectOperation.getObjectOperationId(),
        "DeleteObjectOperationCommand identifier should not be null");
  }

  /** handles fetchOne validation for a ObjectOperation */
  public void validate(ObjectOperationFetchOneSummary summary) throws Exception {
    Assert.notNull(summary, "ObjectOperationFetchOneSummary should not be null");
    Assert.notNull(
        summary.getObjectOperationId(),
        "ObjectOperationFetchOneSummary identifier should not be null");
  }
}
