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
 * Subscriber for Notification related events. .
 *
 * @author your_name_here
 */
@Component("notification-subscriber")
public class NotificationSubscriber extends BaseSubscriber {

  public NotificationSubscriber() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
  }

  public SubscriptionQueryResult<List<Notification>, Notification> notificationSubscribe() {
    return queryGateway.subscriptionQuery(
        new FindAllNotificationQuery(),
        ResponseTypes.multipleInstancesOf(Notification.class),
        ResponseTypes.instanceOf(Notification.class));
  }

  public SubscriptionQueryResult<Notification, Notification> notificationSubscribe(
      @DestinationVariable UUID notificationId) {
    return queryGateway.subscriptionQuery(
        new FindNotificationQuery(new LoadNotificationFilter(notificationId)),
        ResponseTypes.instanceOf(Notification.class),
        ResponseTypes.instanceOf(Notification.class));
  }

  // -------------------------------------------------
  // attributes
  // -------------------------------------------------
  @Autowired private final QueryGateway queryGateway;
}
