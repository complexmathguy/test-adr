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
 * Subscriber for PayloadDescriptor related events. .
 *
 * @author your_name_here
 */
@Component("payloadDescriptor-subscriber")
public class PayloadDescriptorSubscriber extends BaseSubscriber {

  public PayloadDescriptorSubscriber() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
  }

  public SubscriptionQueryResult<List<PayloadDescriptor>, PayloadDescriptor>
      payloadDescriptorSubscribe() {
    return queryGateway.subscriptionQuery(
        new FindAllPayloadDescriptorQuery(),
        ResponseTypes.multipleInstancesOf(PayloadDescriptor.class),
        ResponseTypes.instanceOf(PayloadDescriptor.class));
  }

  public SubscriptionQueryResult<PayloadDescriptor, PayloadDescriptor> payloadDescriptorSubscribe(
      @DestinationVariable UUID payloadDescriptorId) {
    return queryGateway.subscriptionQuery(
        new FindPayloadDescriptorQuery(new LoadPayloadDescriptorFilter(payloadDescriptorId)),
        ResponseTypes.instanceOf(PayloadDescriptor.class),
        ResponseTypes.instanceOf(PayloadDescriptor.class));
  }

  // -------------------------------------------------
  // attributes
  // -------------------------------------------------
  @Autowired private final QueryGateway queryGateway;
}
