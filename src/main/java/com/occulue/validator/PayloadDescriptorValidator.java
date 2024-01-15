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

public class PayloadDescriptorValidator {

  /** default constructor */
  protected PayloadDescriptorValidator() {}

  /** factory method */
  public static PayloadDescriptorValidator getInstance() {
    return new PayloadDescriptorValidator();
  }

  /** handles creation validation for a PayloadDescriptor */
  public void validate(CreatePayloadDescriptorCommand payloadDescriptor) throws Exception {
    Assert.notNull(payloadDescriptor, "CreatePayloadDescriptorCommand should not be null");
    //		Assert.isNull( payloadDescriptor.getPayloadDescriptorId(), "CreatePayloadDescriptorCommand
    // identifier should be null" );
  }

  /** handles update validation for a PayloadDescriptor */
  public void validate(UpdatePayloadDescriptorCommand payloadDescriptor) throws Exception {
    Assert.notNull(payloadDescriptor, "UpdatePayloadDescriptorCommand should not be null");
    Assert.notNull(
        payloadDescriptor.getPayloadDescriptorId(),
        "UpdatePayloadDescriptorCommand identifier should not be null");
  }

  /** handles delete validation for a PayloadDescriptor */
  public void validate(DeletePayloadDescriptorCommand payloadDescriptor) throws Exception {
    Assert.notNull(payloadDescriptor, "{commandAlias} should not be null");
    Assert.notNull(
        payloadDescriptor.getPayloadDescriptorId(),
        "DeletePayloadDescriptorCommand identifier should not be null");
  }

  /** handles fetchOne validation for a PayloadDescriptor */
  public void validate(PayloadDescriptorFetchOneSummary summary) throws Exception {
    Assert.notNull(summary, "PayloadDescriptorFetchOneSummary should not be null");
    Assert.notNull(
        summary.getPayloadDescriptorId(),
        "PayloadDescriptorFetchOneSummary identifier should not be null");
  }
}
