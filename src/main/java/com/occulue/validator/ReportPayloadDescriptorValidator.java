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

public class ReportPayloadDescriptorValidator {

  /** default constructor */
  protected ReportPayloadDescriptorValidator() {}

  /** factory method */
  public static ReportPayloadDescriptorValidator getInstance() {
    return new ReportPayloadDescriptorValidator();
  }

  /** handles creation validation for a ReportPayloadDescriptor */
  public void validate(CreateReportPayloadDescriptorCommand reportPayloadDescriptor)
      throws Exception {
    Assert.notNull(
        reportPayloadDescriptor, "CreateReportPayloadDescriptorCommand should not be null");
    //		Assert.isNull( reportPayloadDescriptor.getReportPayloadDescriptorId(),
    // "CreateReportPayloadDescriptorCommand identifier should be null" );
    Assert.notNull(
        reportPayloadDescriptor.getPayloadType(),
        "Field CreateReportPayloadDescriptorCommand.payloadType should not be null");
    Assert.notNull(
        reportPayloadDescriptor.getReadingType(),
        "Field CreateReportPayloadDescriptorCommand.readingType should not be null");
    Assert.notNull(
        reportPayloadDescriptor.getUnits(),
        "Field CreateReportPayloadDescriptorCommand.units should not be null");
    Assert.notNull(
        reportPayloadDescriptor.getAccuracy(),
        "Field CreateReportPayloadDescriptorCommand.accuracy should not be null");
    Assert.notNull(
        reportPayloadDescriptor.getConfidence(),
        "Field CreateReportPayloadDescriptorCommand.confidence should not be null");
  }

  /** handles update validation for a ReportPayloadDescriptor */
  public void validate(UpdateReportPayloadDescriptorCommand reportPayloadDescriptor)
      throws Exception {
    Assert.notNull(
        reportPayloadDescriptor, "UpdateReportPayloadDescriptorCommand should not be null");
    Assert.notNull(
        reportPayloadDescriptor.getReportPayloadDescriptorId(),
        "UpdateReportPayloadDescriptorCommand identifier should not be null");
    Assert.notNull(
        reportPayloadDescriptor.getPayloadType(),
        "Field UpdateReportPayloadDescriptorCommand.payloadType should not be null");
    Assert.notNull(
        reportPayloadDescriptor.getReadingType(),
        "Field UpdateReportPayloadDescriptorCommand.readingType should not be null");
    Assert.notNull(
        reportPayloadDescriptor.getUnits(),
        "Field UpdateReportPayloadDescriptorCommand.units should not be null");
    Assert.notNull(
        reportPayloadDescriptor.getAccuracy(),
        "Field UpdateReportPayloadDescriptorCommand.accuracy should not be null");
    Assert.notNull(
        reportPayloadDescriptor.getConfidence(),
        "Field UpdateReportPayloadDescriptorCommand.confidence should not be null");
  }

  /** handles delete validation for a ReportPayloadDescriptor */
  public void validate(DeleteReportPayloadDescriptorCommand reportPayloadDescriptor)
      throws Exception {
    Assert.notNull(reportPayloadDescriptor, "{commandAlias} should not be null");
    Assert.notNull(
        reportPayloadDescriptor.getReportPayloadDescriptorId(),
        "DeleteReportPayloadDescriptorCommand identifier should not be null");
  }

  /** handles fetchOne validation for a ReportPayloadDescriptor */
  public void validate(ReportPayloadDescriptorFetchOneSummary summary) throws Exception {
    Assert.notNull(summary, "ReportPayloadDescriptorFetchOneSummary should not be null");
    Assert.notNull(
        summary.getReportPayloadDescriptorId(),
        "ReportPayloadDescriptorFetchOneSummary identifier should not be null");
  }
}
