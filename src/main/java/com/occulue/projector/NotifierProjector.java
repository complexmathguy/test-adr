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
 * Projector for Notifier as outlined for the CQRS pattern. All event handling and query handling
 * related to Notifier are invoked here and dispersed as an event to be handled elsewhere.
 *
 * <p>Commands are handled by NotifierAggregate
 *
 * @author your_name_here
 */
// @ProcessingGroup("notifier")
@Component("notifier-projector")
public class NotifierProjector extends NotifierEntityProjector {

  // core constructor
  public NotifierProjector(NotifierRepository repository, QueryUpdateEmitter queryUpdateEmitter) {
    super(repository);
    this.queryUpdateEmitter = queryUpdateEmitter;
  }

  /*
   * @param	event CreateNotifierEvent
   */
  @EventHandler(payloadType = CreateNotifierEvent.class)
  public void handle(CreateNotifierEvent event) {
    LOGGER.info("handling CreateNotifierEvent - " + event);
    Notifier entity = new Notifier();
    entity.setNotifierId(event.getNotifierId());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    create(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllNotifier(entity);
  }

  /*
   * @param	event UpdateNotifierEvent
   */
  @EventHandler(payloadType = UpdateNotifierEvent.class)
  public void handle(UpdateNotifierEvent event) {
    LOGGER.info("handling UpdateNotifierEvent - " + event);

    Notifier entity = new Notifier();
    entity.setNotifierId(event.getNotifierId());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    update(entity);

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindNotifier(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllNotifier(entity);
  }

  /*
   * @param	event DeleteNotifierEvent
   */
  @EventHandler(payloadType = DeleteNotifierEvent.class)
  public void handle(DeleteNotifierEvent event) {
    LOGGER.info("handling DeleteNotifierEvent - " + event);

    // ------------------------------------------
    // delete delegation
    // ------------------------------------------
    Notifier entity = delete(event.getNotifierId());

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllNotifier(entity);
  }

  /**
   * Method to retrieve the Notifier via an NotifierPrimaryKey.
   *
   * @param id Long
   * @return Notifier
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public Notifier handle(FindNotifierQuery query)
      throws ProcessingException, IllegalArgumentException {
    return find(query.getFilter().getNotifierId());
  }

  /**
   * Method to retrieve a collection of all Notifiers
   *
   * @param query FindAllNotifierQuery
   * @return List<Notifier>
   * @exception ProcessingException Thrown if any problems
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public List<Notifier> handle(FindAllNotifierQuery query) throws ProcessingException {
    return findAll(query);
  }

  /**
   * emit to subscription queries of type FindNotifier, but only if the id matches
   *
   * @param entity Notifier
   */
  protected void emitFindNotifier(Notifier entity) {
    LOGGER.info("handling emitFindNotifier");

    queryUpdateEmitter.emit(
        FindNotifierQuery.class,
        query -> query.getFilter().getNotifierId().equals(entity.getNotifierId()),
        entity);
  }

  /**
   * unconditionally emit to subscription queries of type FindAllNotifier
   *
   * @param entity Notifier
   */
  protected void emitFindAllNotifier(Notifier entity) {
    LOGGER.info("handling emitFindAllNotifier");

    queryUpdateEmitter.emit(FindAllNotifierQuery.class, query -> true, entity);
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired private final QueryUpdateEmitter queryUpdateEmitter;
  private static final Logger LOGGER = Logger.getLogger(NotifierProjector.class.getName());
}
