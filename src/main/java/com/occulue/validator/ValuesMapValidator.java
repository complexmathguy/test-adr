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

public class ValuesMapValidator {

  /** default constructor */
  protected ValuesMapValidator() {}

  /** factory method */
  public static ValuesMapValidator getInstance() {
    return new ValuesMapValidator();
  }

  /** handles creation validation for a ValuesMap */
  public void validate(CreateValuesMapCommand valuesMap) throws Exception {
    Assert.notNull(valuesMap, "CreateValuesMapCommand should not be null");
    //		Assert.isNull( valuesMap.getValuesMapId(), "CreateValuesMapCommand identifier should be
    // null" );
    Assert.notNull(valuesMap.getType(), "Field CreateValuesMapCommand.type should not be null");
    Assert.notNull(valuesMap.getValues(), "Field CreateValuesMapCommand.values should not be null");
  }

  /** handles update validation for a ValuesMap */
  public void validate(UpdateValuesMapCommand valuesMap) throws Exception {
    Assert.notNull(valuesMap, "UpdateValuesMapCommand should not be null");
    Assert.notNull(
        valuesMap.getValuesMapId(), "UpdateValuesMapCommand identifier should not be null");
    Assert.notNull(valuesMap.getType(), "Field UpdateValuesMapCommand.type should not be null");
    Assert.notNull(valuesMap.getValues(), "Field UpdateValuesMapCommand.values should not be null");
  }

  /** handles delete validation for a ValuesMap */
  public void validate(DeleteValuesMapCommand valuesMap) throws Exception {
    Assert.notNull(valuesMap, "{commandAlias} should not be null");
    Assert.notNull(
        valuesMap.getValuesMapId(), "DeleteValuesMapCommand identifier should not be null");
  }

  /** handles fetchOne validation for a ValuesMap */
  public void validate(ValuesMapFetchOneSummary summary) throws Exception {
    Assert.notNull(summary, "ValuesMapFetchOneSummary should not be null");
    Assert.notNull(
        summary.getValuesMapId(), "ValuesMapFetchOneSummary identifier should not be null");
  }
}
