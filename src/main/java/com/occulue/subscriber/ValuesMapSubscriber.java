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
package com.occulue.subscriber;

import com.occulue.api.*;
import com.occulue.entity.*;
import com.occulue.exception.*;
import java.util.*;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.stereotype.Component;

/**
 * Subscriber for ValuesMap related events. .
 *
 * @author your_name_here
 */
@Component("valuesMap-subscriber")
public class ValuesMapSubscriber extends BaseSubscriber {

  public ValuesMapSubscriber() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
  }

  public SubscriptionQueryResult<List<ValuesMap>, ValuesMap> valuesMapSubscribe() {
    return queryGateway.subscriptionQuery(
        new FindAllValuesMapQuery(),
        ResponseTypes.multipleInstancesOf(ValuesMap.class),
        ResponseTypes.instanceOf(ValuesMap.class));
  }

  public SubscriptionQueryResult<ValuesMap, ValuesMap> valuesMapSubscribe(
      @DestinationVariable UUID valuesMapId) {
    return queryGateway.subscriptionQuery(
        new FindValuesMapQuery(new LoadValuesMapFilter(valuesMapId)),
        ResponseTypes.instanceOf(ValuesMap.class),
        ResponseTypes.instanceOf(ValuesMap.class));
  }

  // -------------------------------------------------
  // attributes
  // -------------------------------------------------
  @Autowired private final QueryGateway queryGateway;
}
