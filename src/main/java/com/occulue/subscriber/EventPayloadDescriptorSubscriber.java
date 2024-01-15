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
 * Subscriber for EventPayloadDescriptor related events. .
 *
 * @author your_name_here
 */
@Component("eventPayloadDescriptor-subscriber")
public class EventPayloadDescriptorSubscriber extends BaseSubscriber {

  public EventPayloadDescriptorSubscriber() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
  }

  public SubscriptionQueryResult<List<EventPayloadDescriptor>, EventPayloadDescriptor>
      eventPayloadDescriptorSubscribe() {
    return queryGateway.subscriptionQuery(
        new FindAllEventPayloadDescriptorQuery(),
        ResponseTypes.multipleInstancesOf(EventPayloadDescriptor.class),
        ResponseTypes.instanceOf(EventPayloadDescriptor.class));
  }

  public SubscriptionQueryResult<EventPayloadDescriptor, EventPayloadDescriptor>
      eventPayloadDescriptorSubscribe(@DestinationVariable UUID eventPayloadDescriptorId) {
    return queryGateway.subscriptionQuery(
        new FindEventPayloadDescriptorQuery(
            new LoadEventPayloadDescriptorFilter(eventPayloadDescriptorId)),
        ResponseTypes.instanceOf(EventPayloadDescriptor.class),
        ResponseTypes.instanceOf(EventPayloadDescriptor.class));
  }

  // -------------------------------------------------
  // attributes
  // -------------------------------------------------
  @Autowired private final QueryGateway queryGateway;
}
