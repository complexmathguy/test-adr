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
 * Projector for Event as outlined for the CQRS pattern. All event handling and query handling
 * related to Event are invoked here and dispersed as an event to be handled elsewhere.
 *
 * <p>Commands are handled by EventAggregate
 *
 * @author your_name_here
 */
// @ProcessingGroup("event")
@Component("event-projector")
public class EventProjector extends EventEntityProjector {

  // core constructor
  public EventProjector(EventRepository repository, QueryUpdateEmitter queryUpdateEmitter) {
    super(repository);
    this.queryUpdateEmitter = queryUpdateEmitter;
  }

  /*
   * @param	event CreateEventEvent
   */
  @EventHandler(payloadType = CreateEventEvent.class)
  public void handle(CreateEventEvent event) {
    LOGGER.info("handling CreateEventEvent - " + event);
    Event entity = new Event();
    entity.setEventId(event.getEventId());
    entity.setCreatedDateTime(event.getCreatedDateTime());
    entity.setModificationDateTime(event.getModificationDateTime());
    entity.setEventName(event.getEventName());
    entity.setPriority(event.getPriority());
    entity.setObjectType(event.getObjectType());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    create(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllEvent(entity);
  }

  /*
   * @param	event UpdateEventEvent
   */
  @EventHandler(payloadType = UpdateEventEvent.class)
  public void handle(UpdateEventEvent event) {
    LOGGER.info("handling UpdateEventEvent - " + event);

    Event entity = new Event();
    entity.setEventId(event.getEventId());
    entity.setCreatedDateTime(event.getCreatedDateTime());
    entity.setModificationDateTime(event.getModificationDateTime());
    entity.setEventName(event.getEventName());
    entity.setPriority(event.getPriority());
    entity.setProgram(event.getProgram());
    entity.setTargets(event.getTargets());
    entity.setReportDescriptors(event.getReportDescriptors());
    entity.setPayloadDescriptors(event.getPayloadDescriptors());
    entity.setIntervals(event.getIntervals());
    entity.setObjectType(event.getObjectType());
    entity.setIntervalPeriod(event.getIntervalPeriod());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    update(entity);

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindEvent(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllEvent(entity);
  }

  /*
   * @param	event DeleteEventEvent
   */
  @EventHandler(payloadType = DeleteEventEvent.class)
  public void handle(DeleteEventEvent event) {
    LOGGER.info("handling DeleteEventEvent - " + event);

    // ------------------------------------------
    // delete delegation
    // ------------------------------------------
    Event entity = delete(event.getEventId());

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllEvent(entity);
  }

  /*
   * @param	event AssignProgramToEventEvent
   */
  @EventHandler(payloadType = AssignProgramToEventEvent.class)
  public void handle(AssignProgramToEventEvent event) {
    LOGGER.info("handling AssignProgramToEventEvent - " + event);

    // ------------------------------------------
    // delegate to assignTo
    // ------------------------------------------
    Event entity = assignProgram(event.getEventId(), event.getAssignment());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindEvent(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllEvent(entity);
  }

  /*
   * @param	event UnAssignProgramFromEventEvent
   */
  @EventHandler(payloadType = UnAssignProgramFromEventEvent.class)
  public void handle(UnAssignProgramFromEventEvent event) {
    LOGGER.info("handling UnAssignProgramFromEventEvent - " + event);

    // ------------------------------------------
    // delegate to unAssignFrom
    // ------------------------------------------
    Event entity = unAssignProgram(event.getEventId());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindEvent(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllEvent(entity);
  }

  /*
   * @param	event AssignTargetsToEventEvent
   */
  @EventHandler(payloadType = AssignTargetsToEventEvent.class)
  public void handle(AssignTargetsToEventEvent event) {
    LOGGER.info("handling AssignTargetsToEventEvent - " + event);

    // ------------------------------------------
    // delegate to assignTo
    // ------------------------------------------
    Event entity = assignTargets(event.getEventId(), event.getAssignment());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindEvent(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllEvent(entity);
  }

  /*
   * @param	event UnAssignTargetsFromEventEvent
   */
  @EventHandler(payloadType = UnAssignTargetsFromEventEvent.class)
  public void handle(UnAssignTargetsFromEventEvent event) {
    LOGGER.info("handling UnAssignTargetsFromEventEvent - " + event);

    // ------------------------------------------
    // delegate to unAssignFrom
    // ------------------------------------------
    Event entity = unAssignTargets(event.getEventId());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindEvent(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllEvent(entity);
  }

  /*
   * @param	event AssignIntervalPeriodToEventEvent
   */
  @EventHandler(payloadType = AssignIntervalPeriodToEventEvent.class)
  public void handle(AssignIntervalPeriodToEventEvent event) {
    LOGGER.info("handling AssignIntervalPeriodToEventEvent - " + event);

    // ------------------------------------------
    // delegate to assignTo
    // ------------------------------------------
    Event entity = assignIntervalPeriod(event.getEventId(), event.getAssignment());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindEvent(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllEvent(entity);
  }

  /*
   * @param	event UnAssignIntervalPeriodFromEventEvent
   */
  @EventHandler(payloadType = UnAssignIntervalPeriodFromEventEvent.class)
  public void handle(UnAssignIntervalPeriodFromEventEvent event) {
    LOGGER.info("handling UnAssignIntervalPeriodFromEventEvent - " + event);

    // ------------------------------------------
    // delegate to unAssignFrom
    // ------------------------------------------
    Event entity = unAssignIntervalPeriod(event.getEventId());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindEvent(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllEvent(entity);
  }

  /*
   * @param	event AssignReportDescriptorsToEventEvent
   */
  @EventHandler(payloadType = AssignReportDescriptorsToEventEvent.class)
  public void handle(AssignReportDescriptorsToEventEvent event) {
    LOGGER.info("handling AssignReportDescriptorsToEventEvent - " + event);

    // ------------------------------------------
    // delegate to addTo
    // ------------------------------------------
    Event entity = addToReportDescriptors(event.getEventId(), event.getAddTo());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindEvent(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllEvent(entity);
  }

  /*
   * @param	event RemoveReportDescriptorsFromEventEvent
   */
  @EventHandler(payloadType = RemoveReportDescriptorsFromEventEvent.class)
  public void handle(RemoveReportDescriptorsFromEventEvent event) {
    LOGGER.info("handling RemoveReportDescriptorsFromEventEvent - " + event);

    Event entity = removeFromReportDescriptors(event.getEventId(), event.getRemoveFrom());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindEvent(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllEvent(entity);
  }

  /*
   * @param	event AssignPayloadDescriptorsToEventEvent
   */
  @EventHandler(payloadType = AssignPayloadDescriptorsToEventEvent.class)
  public void handle(AssignPayloadDescriptorsToEventEvent event) {
    LOGGER.info("handling AssignPayloadDescriptorsToEventEvent - " + event);

    // ------------------------------------------
    // delegate to addTo
    // ------------------------------------------
    Event entity = addToPayloadDescriptors(event.getEventId(), event.getAddTo());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindEvent(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllEvent(entity);
  }

  /*
   * @param	event RemovePayloadDescriptorsFromEventEvent
   */
  @EventHandler(payloadType = RemovePayloadDescriptorsFromEventEvent.class)
  public void handle(RemovePayloadDescriptorsFromEventEvent event) {
    LOGGER.info("handling RemovePayloadDescriptorsFromEventEvent - " + event);

    Event entity = removeFromPayloadDescriptors(event.getEventId(), event.getRemoveFrom());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindEvent(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllEvent(entity);
  }

  /*
   * @param	event AssignIntervalsToEventEvent
   */
  @EventHandler(payloadType = AssignIntervalsToEventEvent.class)
  public void handle(AssignIntervalsToEventEvent event) {
    LOGGER.info("handling AssignIntervalsToEventEvent - " + event);

    // ------------------------------------------
    // delegate to addTo
    // ------------------------------------------
    Event entity = addToIntervals(event.getEventId(), event.getAddTo());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindEvent(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllEvent(entity);
  }

  /*
   * @param	event RemoveIntervalsFromEventEvent
   */
  @EventHandler(payloadType = RemoveIntervalsFromEventEvent.class)
  public void handle(RemoveIntervalsFromEventEvent event) {
    LOGGER.info("handling RemoveIntervalsFromEventEvent - " + event);

    Event entity = removeFromIntervals(event.getEventId(), event.getRemoveFrom());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindEvent(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllEvent(entity);
  }

  /**
   * Method to retrieve the Event via an EventPrimaryKey.
   *
   * @param id Long
   * @return Event
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public Event handle(FindEventQuery query) throws ProcessingException, IllegalArgumentException {
    return find(query.getFilter().getEventId());
  }

  /**
   * Method to retrieve a collection of all Events
   *
   * @param query FindAllEventQuery
   * @return List<Event>
   * @exception ProcessingException Thrown if any problems
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public List<Event> handle(FindAllEventQuery query) throws ProcessingException {
    return findAll(query);
  }

  /**
   * emit to subscription queries of type FindEvent, but only if the id matches
   *
   * @param entity Event
   */
  protected void emitFindEvent(Event entity) {
    LOGGER.info("handling emitFindEvent");

    queryUpdateEmitter.emit(
        FindEventQuery.class,
        query -> query.getFilter().getEventId().equals(entity.getEventId()),
        entity);
  }

  /**
   * unconditionally emit to subscription queries of type FindAllEvent
   *
   * @param entity Event
   */
  protected void emitFindAllEvent(Event entity) {
    LOGGER.info("handling emitFindAllEvent");

    queryUpdateEmitter.emit(FindAllEventQuery.class, query -> true, entity);
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired private final QueryUpdateEmitter queryUpdateEmitter;
  private static final Logger LOGGER = Logger.getLogger(EventProjector.class.getName());
}
