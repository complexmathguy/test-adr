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
 * Subscriber for ObjectOperation related events. .
 *
 * @author your_name_here
 */
@Component("objectOperation-subscriber")
public class ObjectOperationSubscriber extends BaseSubscriber {

  public ObjectOperationSubscriber() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
  }

  public SubscriptionQueryResult<List<ObjectOperation>, ObjectOperation>
      objectOperationSubscribe() {
    return queryGateway.subscriptionQuery(
        new FindAllObjectOperationQuery(),
        ResponseTypes.multipleInstancesOf(ObjectOperation.class),
        ResponseTypes.instanceOf(ObjectOperation.class));
  }

  public SubscriptionQueryResult<ObjectOperation, ObjectOperation> objectOperationSubscribe(
      @DestinationVariable UUID objectOperationId) {
    return queryGateway.subscriptionQuery(
        new FindObjectOperationQuery(new LoadObjectOperationFilter(objectOperationId)),
        ResponseTypes.instanceOf(ObjectOperation.class),
        ResponseTypes.instanceOf(ObjectOperation.class));
  }

  // -------------------------------------------------
  // attributes
  // -------------------------------------------------
  @Autowired private final QueryGateway queryGateway;
}
