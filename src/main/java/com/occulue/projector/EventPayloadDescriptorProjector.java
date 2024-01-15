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
 * Projector for EventPayloadDescriptor as outlined for the CQRS pattern. All event handling and
 * query handling related to EventPayloadDescriptor are invoked here and dispersed as an event to be
 * handled elsewhere.
 *
 * <p>Commands are handled by EventPayloadDescriptorAggregate
 *
 * @author your_name_here
 */
// @ProcessingGroup("eventPayloadDescriptor")
@Component("eventPayloadDescriptor-projector")
public class EventPayloadDescriptorProjector extends EventPayloadDescriptorEntityProjector {

  // core constructor
  public EventPayloadDescriptorProjector(
      EventPayloadDescriptorRepository repository, QueryUpdateEmitter queryUpdateEmitter) {
    super(repository);
    this.queryUpdateEmitter = queryUpdateEmitter;
  }

  /*
   * @param	event CreateEventPayloadDescriptorEvent
   */
  @EventHandler(payloadType = CreateEventPayloadDescriptorEvent.class)
  public void handle(CreateEventPayloadDescriptorEvent event) {
    LOGGER.info("handling CreateEventPayloadDescriptorEvent - " + event);
    EventPayloadDescriptor entity = new EventPayloadDescriptor();
    entity.setEventPayloadDescriptorId(event.getEventPayloadDescriptorId());
    entity.setPayloadType(event.getPayloadType());
    entity.setUnits(event.getUnits());
    entity.setCurrency(event.getCurrency());
    entity.setObjectType(event.getObjectType());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    create(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllEventPayloadDescriptor(entity);
  }

  /*
   * @param	event UpdateEventPayloadDescriptorEvent
   */
  @EventHandler(payloadType = UpdateEventPayloadDescriptorEvent.class)
  public void handle(UpdateEventPayloadDescriptorEvent event) {
    LOGGER.info("handling UpdateEventPayloadDescriptorEvent - " + event);

    EventPayloadDescriptor entity = new EventPayloadDescriptor();
    entity.setEventPayloadDescriptorId(event.getEventPayloadDescriptorId());
    entity.setPayloadType(event.getPayloadType());
    entity.setUnits(event.getUnits());
    entity.setCurrency(event.getCurrency());
    entity.setObjectType(event.getObjectType());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    update(entity);

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindEventPayloadDescriptor(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllEventPayloadDescriptor(entity);
  }

  /*
   * @param	event DeleteEventPayloadDescriptorEvent
   */
  @EventHandler(payloadType = DeleteEventPayloadDescriptorEvent.class)
  public void handle(DeleteEventPayloadDescriptorEvent event) {
    LOGGER.info("handling DeleteEventPayloadDescriptorEvent - " + event);

    // ------------------------------------------
    // delete delegation
    // ------------------------------------------
    EventPayloadDescriptor entity = delete(event.getEventPayloadDescriptorId());

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllEventPayloadDescriptor(entity);
  }

  /**
   * Method to retrieve the EventPayloadDescriptor via an EventPayloadDescriptorPrimaryKey.
   *
   * @param id Long
   * @return EventPayloadDescriptor
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public EventPayloadDescriptor handle(FindEventPayloadDescriptorQuery query)
      throws ProcessingException, IllegalArgumentException {
    return find(query.getFilter().getEventPayloadDescriptorId());
  }

  /**
   * Method to retrieve a collection of all EventPayloadDescriptors
   *
   * @param query FindAllEventPayloadDescriptorQuery
   * @return List<EventPayloadDescriptor>
   * @exception ProcessingException Thrown if any problems
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public List<EventPayloadDescriptor> handle(FindAllEventPayloadDescriptorQuery query)
      throws ProcessingException {
    return findAll(query);
  }

  /**
   * emit to subscription queries of type FindEventPayloadDescriptor, but only if the id matches
   *
   * @param entity EventPayloadDescriptor
   */
  protected void emitFindEventPayloadDescriptor(EventPayloadDescriptor entity) {
    LOGGER.info("handling emitFindEventPayloadDescriptor");

    queryUpdateEmitter.emit(
        FindEventPayloadDescriptorQuery.class,
        query ->
            query
                .getFilter()
                .getEventPayloadDescriptorId()
                .equals(entity.getEventPayloadDescriptorId()),
        entity);
  }

  /**
   * unconditionally emit to subscription queries of type FindAllEventPayloadDescriptor
   *
   * @param entity EventPayloadDescriptor
   */
  protected void emitFindAllEventPayloadDescriptor(EventPayloadDescriptor entity) {
    LOGGER.info("handling emitFindAllEventPayloadDescriptor");

    queryUpdateEmitter.emit(FindAllEventPayloadDescriptorQuery.class, query -> true, entity);
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired private final QueryUpdateEmitter queryUpdateEmitter;
  private static final Logger LOGGER =
      Logger.getLogger(EventPayloadDescriptorProjector.class.getName());
}
