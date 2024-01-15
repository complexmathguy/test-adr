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
 * Subscriber for Problem related events. .
 *
 * @author your_name_here
 */
@Component("problem-subscriber")
public class ProblemSubscriber extends BaseSubscriber {

  public ProblemSubscriber() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
  }

  public SubscriptionQueryResult<List<Problem>, Problem> problemSubscribe() {
    return queryGateway.subscriptionQuery(
        new FindAllProblemQuery(),
        ResponseTypes.multipleInstancesOf(Problem.class),
        ResponseTypes.instanceOf(Problem.class));
  }

  public SubscriptionQueryResult<Problem, Problem> problemSubscribe(
      @DestinationVariable UUID problemId) {
    return queryGateway.subscriptionQuery(
        new FindProblemQuery(new LoadProblemFilter(problemId)),
        ResponseTypes.instanceOf(Problem.class),
        ResponseTypes.instanceOf(Problem.class));
  }

  // -------------------------------------------------
  // attributes
  // -------------------------------------------------
  @Autowired private final QueryGateway queryGateway;
}
