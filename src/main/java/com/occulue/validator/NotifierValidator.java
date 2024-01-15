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

public class NotifierValidator {

  /** default constructor */
  protected NotifierValidator() {}

  /** factory method */
  public static NotifierValidator getInstance() {
    return new NotifierValidator();
  }

  /** handles creation validation for a Notifier */
  public void validate(CreateNotifierCommand notifier) throws Exception {
    Assert.notNull(notifier, "CreateNotifierCommand should not be null");
    //		Assert.isNull( notifier.getNotifierId(), "CreateNotifierCommand identifier should be null"
    // );
  }

  /** handles update validation for a Notifier */
  public void validate(UpdateNotifierCommand notifier) throws Exception {
    Assert.notNull(notifier, "UpdateNotifierCommand should not be null");
    Assert.notNull(notifier.getNotifierId(), "UpdateNotifierCommand identifier should not be null");
  }

  /** handles delete validation for a Notifier */
  public void validate(DeleteNotifierCommand notifier) throws Exception {
    Assert.notNull(notifier, "{commandAlias} should not be null");
    Assert.notNull(notifier.getNotifierId(), "DeleteNotifierCommand identifier should not be null");
  }

  /** handles fetchOne validation for a Notifier */
  public void validate(NotifierFetchOneSummary summary) throws Exception {
    Assert.notNull(summary, "NotifierFetchOneSummary should not be null");
    Assert.notNull(
        summary.getNotifierId(), "NotifierFetchOneSummary identifier should not be null");
  }
}
