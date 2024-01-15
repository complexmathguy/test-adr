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
 * Subscriber for Report related events. .
 *
 * @author your_name_here
 */
@Component("report-subscriber")
public class ReportSubscriber extends BaseSubscriber {

  public ReportSubscriber() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
  }

  public SubscriptionQueryResult<List<Report>, Report> reportSubscribe() {
    return queryGateway.subscriptionQuery(
        new FindAllReportQuery(),
        ResponseTypes.multipleInstancesOf(Report.class),
        ResponseTypes.instanceOf(Report.class));
  }

  public SubscriptionQueryResult<Report, Report> reportSubscribe(
      @DestinationVariable UUID reportId) {
    return queryGateway.subscriptionQuery(
        new FindReportQuery(new LoadReportFilter(reportId)),
        ResponseTypes.instanceOf(Report.class),
        ResponseTypes.instanceOf(Report.class));
  }

  // -------------------------------------------------
  // attributes
  // -------------------------------------------------
  @Autowired private final QueryGateway queryGateway;
}
