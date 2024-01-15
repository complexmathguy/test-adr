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
 * Projector for Notification as outlined for the CQRS pattern. All event handling and query
 * handling related to Notification are invoked here and dispersed as an event to be handled
 * elsewhere.
 *
 * <p>Commands are handled by NotificationAggregate
 *
 * @author your_name_here
 */
// @ProcessingGroup("notification")
@Component("notification-projector")
public class NotificationProjector extends NotificationEntityProjector {

  // core constructor
  public NotificationProjector(
      NotificationRepository repository, QueryUpdateEmitter queryUpdateEmitter) {
    super(repository);
    this.queryUpdateEmitter = queryUpdateEmitter;
  }

  /*
   * @param	event CreateNotificationEvent
   */
  @EventHandler(payloadType = CreateNotificationEvent.class)
  public void handle(CreateNotificationEvent event) {
    LOGGER.info("handling CreateNotificationEvent - " + event);
    Notification entity = new Notification();
    entity.setNotificationId(event.getNotificationId());
    entity.setObjectType(event.getObjectType());
    entity.setOperation(event.getOperation());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    create(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllNotification(entity);
  }

  /*
   * @param	event UpdateNotificationEvent
   */
  @EventHandler(payloadType = UpdateNotificationEvent.class)
  public void handle(UpdateNotificationEvent event) {
    LOGGER.info("handling UpdateNotificationEvent - " + event);

    Notification entity = new Notification();
    entity.setNotificationId(event.getNotificationId());
    entity.setTargets(event.getTargets());
    entity.setObjectType(event.getObjectType());
    entity.setOperation(event.getOperation());
    entity.setNotifier(event.getNotifier());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    update(entity);

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindNotification(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllNotification(entity);
  }

  /*
   * @param	event DeleteNotificationEvent
   */
  @EventHandler(payloadType = DeleteNotificationEvent.class)
  public void handle(DeleteNotificationEvent event) {
    LOGGER.info("handling DeleteNotificationEvent - " + event);

    // ------------------------------------------
    // delete delegation
    // ------------------------------------------
    Notification entity = delete(event.getNotificationId());

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllNotification(entity);
  }

  /*
   * @param	event AssignTargetsToNotificationEvent
   */
  @EventHandler(payloadType = AssignTargetsToNotificationEvent.class)
  public void handle(AssignTargetsToNotificationEvent event) {
    LOGGER.info("handling AssignTargetsToNotificationEvent - " + event);

    // ------------------------------------------
    // delegate to assignTo
    // ------------------------------------------
    Notification entity = assignTargets(event.getNotificationId(), event.getAssignment());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindNotification(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllNotification(entity);
  }

  /*
   * @param	event UnAssignTargetsFromNotificationEvent
   */
  @EventHandler(payloadType = UnAssignTargetsFromNotificationEvent.class)
  public void handle(UnAssignTargetsFromNotificationEvent event) {
    LOGGER.info("handling UnAssignTargetsFromNotificationEvent - " + event);

    // ------------------------------------------
    // delegate to unAssignFrom
    // ------------------------------------------
    Notification entity = unAssignTargets(event.getNotificationId());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindNotification(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllNotification(entity);
  }

  /*
   * @param	event AssignNotifierToNotificationEvent
   */
  @EventHandler(payloadType = AssignNotifierToNotificationEvent.class)
  public void handle(AssignNotifierToNotificationEvent event) {
    LOGGER.info("handling AssignNotifierToNotificationEvent - " + event);

    // ------------------------------------------
    // delegate to assignTo
    // ------------------------------------------
    Notification entity = assignNotifier(event.getNotificationId(), event.getAssignment());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindNotification(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllNotification(entity);
  }

  /*
   * @param	event UnAssignNotifierFromNotificationEvent
   */
  @EventHandler(payloadType = UnAssignNotifierFromNotificationEvent.class)
  public void handle(UnAssignNotifierFromNotificationEvent event) {
    LOGGER.info("handling UnAssignNotifierFromNotificationEvent - " + event);

    // ------------------------------------------
    // delegate to unAssignFrom
    // ------------------------------------------
    Notification entity = unAssignNotifier(event.getNotificationId());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindNotification(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllNotification(entity);
  }

  /**
   * Method to retrieve the Notification via an NotificationPrimaryKey.
   *
   * @param id Long
   * @return Notification
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public Notification handle(FindNotificationQuery query)
      throws ProcessingException, IllegalArgumentException {
    return find(query.getFilter().getNotificationId());
  }

  /**
   * Method to retrieve a collection of all Notifications
   *
   * @param query FindAllNotificationQuery
   * @return List<Notification>
   * @exception ProcessingException Thrown if any problems
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public List<Notification> handle(FindAllNotificationQuery query) throws ProcessingException {
    return findAll(query);
  }

  /**
   * emit to subscription queries of type FindNotification, but only if the id matches
   *
   * @param entity Notification
   */
  protected void emitFindNotification(Notification entity) {
    LOGGER.info("handling emitFindNotification");

    queryUpdateEmitter.emit(
        FindNotificationQuery.class,
        query -> query.getFilter().getNotificationId().equals(entity.getNotificationId()),
        entity);
  }

  /**
   * unconditionally emit to subscription queries of type FindAllNotification
   *
   * @param entity Notification
   */
  protected void emitFindAllNotification(Notification entity) {
    LOGGER.info("handling emitFindAllNotification");

    queryUpdateEmitter.emit(FindAllNotificationQuery.class, query -> true, entity);
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired private final QueryUpdateEmitter queryUpdateEmitter;
  private static final Logger LOGGER = Logger.getLogger(NotificationProjector.class.getName());
}
