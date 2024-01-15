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
package com.occulue.projector;

import com.occulue.api.*;
import com.occulue.entity.*;
import com.occulue.exception.*;
import com.occulue.repository.*;
import java.util.*;
import java.util.logging.Logger;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Projector for Subscription as outlined for the CQRS pattern. All event handling and query
 * handling related to Subscription are invoked here and dispersed as an event to be handled
 * elsewhere.
 *
 * <p>Commands are handled by SubscriptionAggregate
 *
 * @author your_name_here
 */
// @ProcessingGroup("subscription")
@Component("subscription-projector")
public class SubscriptionProjector extends SubscriptionEntityProjector {

  // core constructor
  public SubscriptionProjector(
      SubscriptionRepository repository, QueryUpdateEmitter queryUpdateEmitter) {
    super(repository);
    this.queryUpdateEmitter = queryUpdateEmitter;
  }

  /*
   * @param	event CreateSubscriptionEvent
   */
  @EventHandler(payloadType = CreateSubscriptionEvent.class)
  public void handle(CreateSubscriptionEvent event) {
    LOGGER.info("handling CreateSubscriptionEvent - " + event);
    Subscription entity = new Subscription();
    entity.setSubscriptionId(event.getSubscriptionId());
    entity.setCreatedDateTime(event.getCreatedDateTime());
    entity.setModificationDateTime(event.getModificationDateTime());
    entity.setClientName(event.getClientName());
    entity.setObjectType(event.getObjectType());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    create(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllSubscription(entity);
  }

  /*
   * @param	event UpdateSubscriptionEvent
   */
  @EventHandler(payloadType = UpdateSubscriptionEvent.class)
  public void handle(UpdateSubscriptionEvent event) {
    LOGGER.info("handling UpdateSubscriptionEvent - " + event);

    Subscription entity = new Subscription();
    entity.setSubscriptionId(event.getSubscriptionId());
    entity.setCreatedDateTime(event.getCreatedDateTime());
    entity.setModificationDateTime(event.getModificationDateTime());
    entity.setClientName(event.getClientName());
    entity.setProgram(event.getProgram());
    entity.setObjectOperations(event.getObjectOperations());
    entity.setTargets(event.getTargets());
    entity.setObjectType(event.getObjectType());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    update(entity);

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindSubscription(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllSubscription(entity);
  }

  /*
   * @param	event DeleteSubscriptionEvent
   */
  @EventHandler(payloadType = DeleteSubscriptionEvent.class)
  public void handle(DeleteSubscriptionEvent event) {
    LOGGER.info("handling DeleteSubscriptionEvent - " + event);

    // ------------------------------------------
    // delete delegation
    // ------------------------------------------
    Subscription entity = delete(event.getSubscriptionId());

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllSubscription(entity);
  }

  /*
   * @param	event AssignProgramToSubscriptionEvent
   */
  @EventHandler(payloadType = AssignProgramToSubscriptionEvent.class)
  public void handle(AssignProgramToSubscriptionEvent event) {
    LOGGER.info("handling AssignProgramToSubscriptionEvent - " + event);

    // ------------------------------------------
    // delegate to assignTo
    // ------------------------------------------
    Subscription entity = assignProgram(event.getSubscriptionId(), event.getAssignment());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindSubscription(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllSubscription(entity);
  }

  /*
   * @param	event UnAssignProgramFromSubscriptionEvent
   */
  @EventHandler(payloadType = UnAssignProgramFromSubscriptionEvent.class)
  public void handle(UnAssignProgramFromSubscriptionEvent event) {
    LOGGER.info("handling UnAssignProgramFromSubscriptionEvent - " + event);

    // ------------------------------------------
    // delegate to unAssignFrom
    // ------------------------------------------
    Subscription entity = unAssignProgram(event.getSubscriptionId());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindSubscription(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllSubscription(entity);
  }

  /*
   * @param	event AssignTargetsToSubscriptionEvent
   */
  @EventHandler(payloadType = AssignTargetsToSubscriptionEvent.class)
  public void handle(AssignTargetsToSubscriptionEvent event) {
    LOGGER.info("handling AssignTargetsToSubscriptionEvent - " + event);

    // ------------------------------------------
    // delegate to assignTo
    // ------------------------------------------
    Subscription entity = assignTargets(event.getSubscriptionId(), event.getAssignment());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindSubscription(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllSubscription(entity);
  }

  /*
   * @param	event UnAssignTargetsFromSubscriptionEvent
   */
  @EventHandler(payloadType = UnAssignTargetsFromSubscriptionEvent.class)
  public void handle(UnAssignTargetsFromSubscriptionEvent event) {
    LOGGER.info("handling UnAssignTargetsFromSubscriptionEvent - " + event);

    // ------------------------------------------
    // delegate to unAssignFrom
    // ------------------------------------------
    Subscription entity = unAssignTargets(event.getSubscriptionId());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindSubscription(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllSubscription(entity);
  }

  /*
   * @param	event AssignObjectOperationsToSubscriptionEvent
   */
  @EventHandler(payloadType = AssignObjectOperationsToSubscriptionEvent.class)
  public void handle(AssignObjectOperationsToSubscriptionEvent event) {
    LOGGER.info("handling AssignObjectOperationsToSubscriptionEvent - " + event);

    // ------------------------------------------
    // delegate to addTo
    // ------------------------------------------
    Subscription entity = addToObjectOperations(event.getSubscriptionId(), event.getAddTo());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindSubscription(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllSubscription(entity);
  }

  /*
   * @param	event RemoveObjectOperationsFromSubscriptionEvent
   */
  @EventHandler(payloadType = RemoveObjectOperationsFromSubscriptionEvent.class)
  public void handle(RemoveObjectOperationsFromSubscriptionEvent event) {
    LOGGER.info("handling RemoveObjectOperationsFromSubscriptionEvent - " + event);

    Subscription entity =
        removeFromObjectOperations(event.getSubscriptionId(), event.getRemoveFrom());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindSubscription(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllSubscription(entity);
  }

  /**
   * Method to retrieve the Subscription via an SubscriptionPrimaryKey.
   *
   * @param id Long
   * @return Subscription
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public Subscription handle(FindSubscriptionQuery query)
      throws ProcessingException, IllegalArgumentException {
    return find(query.getFilter().getSubscriptionId());
  }

  /**
   * Method to retrieve a collection of all Subscriptions
   *
   * @param query FindAllSubscriptionQuery
   * @return List<Subscription>
   * @exception ProcessingException Thrown if any problems
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public List<Subscription> handle(FindAllSubscriptionQuery query) throws ProcessingException {
    return findAll(query);
  }

  /**
   * emit to subscription queries of type FindSubscription, but only if the id matches
   *
   * @param entity Subscription
   */
  protected void emitFindSubscription(Subscription entity) {
    LOGGER.info("handling emitFindSubscription");

    queryUpdateEmitter.emit(
        FindSubscriptionQuery.class,
        query -> query.getFilter().getSubscriptionId().equals(entity.getSubscriptionId()),
        entity);
  }

  /**
   * unconditionally emit to subscription queries of type FindAllSubscription
   *
   * @param entity Subscription
   */
  protected void emitFindAllSubscription(Subscription entity) {
    LOGGER.info("handling emitFindAllSubscription");

    queryUpdateEmitter.emit(FindAllSubscriptionQuery.class, query -> true, entity);
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired private final QueryUpdateEmitter queryUpdateEmitter;
  private static final Logger LOGGER = Logger.getLogger(SubscriptionProjector.class.getName());
}
