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
 * Projector for Interval as outlined for the CQRS pattern. All event handling and query handling
 * related to Interval are invoked here and dispersed as an event to be handled elsewhere.
 *
 * <p>Commands are handled by IntervalAggregate
 *
 * @author your_name_here
 */
// @ProcessingGroup("interval")
@Component("interval-projector")
public class IntervalProjector extends IntervalEntityProjector {

  // core constructor
  public IntervalProjector(IntervalRepository repository, QueryUpdateEmitter queryUpdateEmitter) {
    super(repository);
    this.queryUpdateEmitter = queryUpdateEmitter;
  }

  /*
   * @param	event CreateIntervalEvent
   */
  @EventHandler(payloadType = CreateIntervalEvent.class)
  public void handle(CreateIntervalEvent event) {
    LOGGER.info("handling CreateIntervalEvent - " + event);
    Interval entity = new Interval();
    entity.setIntervalId(event.getIntervalId());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    create(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllInterval(entity);
  }

  /*
   * @param	event UpdateIntervalEvent
   */
  @EventHandler(payloadType = UpdateIntervalEvent.class)
  public void handle(UpdateIntervalEvent event) {
    LOGGER.info("handling UpdateIntervalEvent - " + event);

    Interval entity = new Interval();
    entity.setIntervalId(event.getIntervalId());
    entity.setPayloads(event.getPayloads());
    entity.setIntervalPeriod(event.getIntervalPeriod());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    update(entity);

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindInterval(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllInterval(entity);
  }

  /*
   * @param	event DeleteIntervalEvent
   */
  @EventHandler(payloadType = DeleteIntervalEvent.class)
  public void handle(DeleteIntervalEvent event) {
    LOGGER.info("handling DeleteIntervalEvent - " + event);

    // ------------------------------------------
    // delete delegation
    // ------------------------------------------
    Interval entity = delete(event.getIntervalId());

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllInterval(entity);
  }

  /*
   * @param	event AssignIntervalPeriodToIntervalEvent
   */
  @EventHandler(payloadType = AssignIntervalPeriodToIntervalEvent.class)
  public void handle(AssignIntervalPeriodToIntervalEvent event) {
    LOGGER.info("handling AssignIntervalPeriodToIntervalEvent - " + event);

    // ------------------------------------------
    // delegate to assignTo
    // ------------------------------------------
    Interval entity = assignIntervalPeriod(event.getIntervalId(), event.getAssignment());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindInterval(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllInterval(entity);
  }

  /*
   * @param	event UnAssignIntervalPeriodFromIntervalEvent
   */
  @EventHandler(payloadType = UnAssignIntervalPeriodFromIntervalEvent.class)
  public void handle(UnAssignIntervalPeriodFromIntervalEvent event) {
    LOGGER.info("handling UnAssignIntervalPeriodFromIntervalEvent - " + event);

    // ------------------------------------------
    // delegate to unAssignFrom
    // ------------------------------------------
    Interval entity = unAssignIntervalPeriod(event.getIntervalId());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindInterval(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllInterval(entity);
  }

  /*
   * @param	event AssignPayloadsToIntervalEvent
   */
  @EventHandler(payloadType = AssignPayloadsToIntervalEvent.class)
  public void handle(AssignPayloadsToIntervalEvent event) {
    LOGGER.info("handling AssignPayloadsToIntervalEvent - " + event);

    // ------------------------------------------
    // delegate to addTo
    // ------------------------------------------
    Interval entity = addToPayloads(event.getIntervalId(), event.getAddTo());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindInterval(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllInterval(entity);
  }

  /*
   * @param	event RemovePayloadsFromIntervalEvent
   */
  @EventHandler(payloadType = RemovePayloadsFromIntervalEvent.class)
  public void handle(RemovePayloadsFromIntervalEvent event) {
    LOGGER.info("handling RemovePayloadsFromIntervalEvent - " + event);

    Interval entity = removeFromPayloads(event.getIntervalId(), event.getRemoveFrom());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindInterval(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllInterval(entity);
  }

  /**
   * Method to retrieve the Interval via an IntervalPrimaryKey.
   *
   * @param id Long
   * @return Interval
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public Interval handle(FindIntervalQuery query)
      throws ProcessingException, IllegalArgumentException {
    return find(query.getFilter().getIntervalId());
  }

  /**
   * Method to retrieve a collection of all Intervals
   *
   * @param query FindAllIntervalQuery
   * @return List<Interval>
   * @exception ProcessingException Thrown if any problems
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public List<Interval> handle(FindAllIntervalQuery query) throws ProcessingException {
    return findAll(query);
  }

  /**
   * emit to subscription queries of type FindInterval, but only if the id matches
   *
   * @param entity Interval
   */
  protected void emitFindInterval(Interval entity) {
    LOGGER.info("handling emitFindInterval");

    queryUpdateEmitter.emit(
        FindIntervalQuery.class,
        query -> query.getFilter().getIntervalId().equals(entity.getIntervalId()),
        entity);
  }

  /**
   * unconditionally emit to subscription queries of type FindAllInterval
   *
   * @param entity Interval
   */
  protected void emitFindAllInterval(Interval entity) {
    LOGGER.info("handling emitFindAllInterval");

    queryUpdateEmitter.emit(FindAllIntervalQuery.class, query -> true, entity);
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired private final QueryUpdateEmitter queryUpdateEmitter;
  private static final Logger LOGGER = Logger.getLogger(IntervalProjector.class.getName());
}
