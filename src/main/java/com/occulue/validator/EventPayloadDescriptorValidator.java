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

public class EventPayloadDescriptorValidator {

  /** default constructor */
  protected EventPayloadDescriptorValidator() {}

  /** factory method */
  public static EventPayloadDescriptorValidator getInstance() {
    return new EventPayloadDescriptorValidator();
  }

  /** handles creation validation for a EventPayloadDescriptor */
  public void validate(CreateEventPayloadDescriptorCommand eventPayloadDescriptor)
      throws Exception {
    Assert.notNull(
        eventPayloadDescriptor, "CreateEventPayloadDescriptorCommand should not be null");
    //		Assert.isNull( eventPayloadDescriptor.getEventPayloadDescriptorId(),
    // "CreateEventPayloadDescriptorCommand identifier should be null" );
    Assert.notNull(
        eventPayloadDescriptor.getPayloadType(),
        "Field CreateEventPayloadDescriptorCommand.payloadType should not be null");
    Assert.notNull(
        eventPayloadDescriptor.getUnits(),
        "Field CreateEventPayloadDescriptorCommand.units should not be null");
    Assert.notNull(
        eventPayloadDescriptor.getCurrency(),
        "Field CreateEventPayloadDescriptorCommand.currency should not be null");
  }

  /** handles update validation for a EventPayloadDescriptor */
  public void validate(UpdateEventPayloadDescriptorCommand eventPayloadDescriptor)
      throws Exception {
    Assert.notNull(
        eventPayloadDescriptor, "UpdateEventPayloadDescriptorCommand should not be null");
    Assert.notNull(
        eventPayloadDescriptor.getEventPayloadDescriptorId(),
        "UpdateEventPayloadDescriptorCommand identifier should not be null");
    Assert.notNull(
        eventPayloadDescriptor.getPayloadType(),
        "Field UpdateEventPayloadDescriptorCommand.payloadType should not be null");
    Assert.notNull(
        eventPayloadDescriptor.getUnits(),
        "Field UpdateEventPayloadDescriptorCommand.units should not be null");
    Assert.notNull(
        eventPayloadDescriptor.getCurrency(),
        "Field UpdateEventPayloadDescriptorCommand.currency should not be null");
  }

  /** handles delete validation for a EventPayloadDescriptor */
  public void validate(DeleteEventPayloadDescriptorCommand eventPayloadDescriptor)
      throws Exception {
    Assert.notNull(eventPayloadDescriptor, "{commandAlias} should not be null");
    Assert.notNull(
        eventPayloadDescriptor.getEventPayloadDescriptorId(),
        "DeleteEventPayloadDescriptorCommand identifier should not be null");
  }

  /** handles fetchOne validation for a EventPayloadDescriptor */
  public void validate(EventPayloadDescriptorFetchOneSummary summary) throws Exception {
    Assert.notNull(summary, "EventPayloadDescriptorFetchOneSummary should not be null");
    Assert.notNull(
        summary.getEventPayloadDescriptorId(),
        "EventPayloadDescriptorFetchOneSummary identifier should not be null");
  }
}
