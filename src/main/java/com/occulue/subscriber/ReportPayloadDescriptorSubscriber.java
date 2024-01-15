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
 * Subscriber for ReportPayloadDescriptor related events. .
 *
 * @author your_name_here
 */
@Component("reportPayloadDescriptor-subscriber")
public class ReportPayloadDescriptorSubscriber extends BaseSubscriber {

  public ReportPayloadDescriptorSubscriber() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
  }

  public SubscriptionQueryResult<List<ReportPayloadDescriptor>, ReportPayloadDescriptor>
      reportPayloadDescriptorSubscribe() {
    return queryGateway.subscriptionQuery(
        new FindAllReportPayloadDescriptorQuery(),
        ResponseTypes.multipleInstancesOf(ReportPayloadDescriptor.class),
        ResponseTypes.instanceOf(ReportPayloadDescriptor.class));
  }

  public SubscriptionQueryResult<ReportPayloadDescriptor, ReportPayloadDescriptor>
      reportPayloadDescriptorSubscribe(@DestinationVariable UUID reportPayloadDescriptorId) {
    return queryGateway.subscriptionQuery(
        new FindReportPayloadDescriptorQuery(
            new LoadReportPayloadDescriptorFilter(reportPayloadDescriptorId)),
        ResponseTypes.instanceOf(ReportPayloadDescriptor.class),
        ResponseTypes.instanceOf(ReportPayloadDescriptor.class));
  }

  // -------------------------------------------------
  // attributes
  // -------------------------------------------------
  @Autowired private final QueryGateway queryGateway;
}
