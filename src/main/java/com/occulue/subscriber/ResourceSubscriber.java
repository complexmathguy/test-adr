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
 * Subscriber for Resource related events. .
 *
 * @author your_name_here
 */
@Component("resource-subscriber")
public class ResourceSubscriber extends BaseSubscriber {

  public ResourceSubscriber() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
  }

  public SubscriptionQueryResult<List<Resource>, Resource> resourceSubscribe() {
    return queryGateway.subscriptionQuery(
        new FindAllResourceQuery(),
        ResponseTypes.multipleInstancesOf(Resource.class),
        ResponseTypes.instanceOf(Resource.class));
  }

  public SubscriptionQueryResult<Resource, Resource> resourceSubscribe(
      @DestinationVariable UUID resourceId) {
    return queryGateway.subscriptionQuery(
        new FindResourceQuery(new LoadResourceFilter(resourceId)),
        ResponseTypes.instanceOf(Resource.class),
        ResponseTypes.instanceOf(Resource.class));
  }

  // -------------------------------------------------
  // attributes
  // -------------------------------------------------
  @Autowired private final QueryGateway queryGateway;
}
