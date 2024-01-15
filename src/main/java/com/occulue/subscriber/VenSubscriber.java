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
 * Subscriber for Ven related events. .
 *
 * @author your_name_here
 */
@Component("ven-subscriber")
public class VenSubscriber extends BaseSubscriber {

  public VenSubscriber() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
  }

  public SubscriptionQueryResult<List<Ven>, Ven> venSubscribe() {
    return queryGateway.subscriptionQuery(
        new FindAllVenQuery(),
        ResponseTypes.multipleInstancesOf(Ven.class),
        ResponseTypes.instanceOf(Ven.class));
  }

  public SubscriptionQueryResult<Ven, Ven> venSubscribe(@DestinationVariable UUID venId) {
    return queryGateway.subscriptionQuery(
        new FindVenQuery(new LoadVenFilter(venId)),
        ResponseTypes.instanceOf(Ven.class),
        ResponseTypes.instanceOf(Ven.class));
  }

  // -------------------------------------------------
  // attributes
  // -------------------------------------------------
  @Autowired private final QueryGateway queryGateway;
}
