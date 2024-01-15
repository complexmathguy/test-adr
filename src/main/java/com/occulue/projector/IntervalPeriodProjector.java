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
 * Projector for IntervalPeriod as outlined for the CQRS pattern. All event handling and query
 * handling related to IntervalPeriod are invoked here and dispersed as an event to be handled
 * elsewhere.
 *
 * <p>Commands are handled by IntervalPeriodAggregate
 *
 * @author your_name_here
 */
// @ProcessingGroup("intervalPeriod")
@Component("intervalPeriod-projector")
public class IntervalPeriodProjector extends IntervalPeriodEntityProjector {

  // core constructor
  public IntervalPeriodProjector(
      IntervalPeriodRepository repository, QueryUpdateEmitter queryUpdateEmitter) {
    super(repository);
    this.queryUpdateEmitter = queryUpdateEmitter;
  }

  /*
   * @param	event CreateIntervalPeriodEvent
   */
  @EventHandler(payloadType = CreateIntervalPeriodEvent.class)
  public void handle(CreateIntervalPeriodEvent event) {
    LOGGER.info("handling CreateIntervalPeriodEvent - " + event);
    IntervalPeriod entity = new IntervalPeriod();
    entity.setIntervalPeriodId(event.getIntervalPeriodId());
    entity.setStart(event.getStart());
    entity.setDuration(event.getDuration());
    entity.setRandomizeStart(event.getRandomizeStart());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    create(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllIntervalPeriod(entity);
  }

  /*
   * @param	event UpdateIntervalPeriodEvent
   */
  @EventHandler(payloadType = UpdateIntervalPeriodEvent.class)
  public void handle(UpdateIntervalPeriodEvent event) {
    LOGGER.info("handling UpdateIntervalPeriodEvent - " + event);

    IntervalPeriod entity = new IntervalPeriod();
    entity.setIntervalPeriodId(event.getIntervalPeriodId());
    entity.setStart(event.getStart());
    entity.setDuration(event.getDuration());
    entity.setRandomizeStart(event.getRandomizeStart());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    update(entity);

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindIntervalPeriod(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllIntervalPeriod(entity);
  }

  /*
   * @param	event DeleteIntervalPeriodEvent
   */
  @EventHandler(payloadType = DeleteIntervalPeriodEvent.class)
  public void handle(DeleteIntervalPeriodEvent event) {
    LOGGER.info("handling DeleteIntervalPeriodEvent - " + event);

    // ------------------------------------------
    // delete delegation
    // ------------------------------------------
    IntervalPeriod entity = delete(event.getIntervalPeriodId());

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllIntervalPeriod(entity);
  }

  /**
   * Method to retrieve the IntervalPeriod via an IntervalPeriodPrimaryKey.
   *
   * @param id Long
   * @return IntervalPeriod
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public IntervalPeriod handle(FindIntervalPeriodQuery query)
      throws ProcessingException, IllegalArgumentException {
    return find(query.getFilter().getIntervalPeriodId());
  }

  /**
   * Method to retrieve a collection of all IntervalPeriods
   *
   * @param query FindAllIntervalPeriodQuery
   * @return List<IntervalPeriod>
   * @exception ProcessingException Thrown if any problems
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public List<IntervalPeriod> handle(FindAllIntervalPeriodQuery query) throws ProcessingException {
    return findAll(query);
  }

  /**
   * emit to subscription queries of type FindIntervalPeriod, but only if the id matches
   *
   * @param entity IntervalPeriod
   */
  protected void emitFindIntervalPeriod(IntervalPeriod entity) {
    LOGGER.info("handling emitFindIntervalPeriod");

    queryUpdateEmitter.emit(
        FindIntervalPeriodQuery.class,
        query -> query.getFilter().getIntervalPeriodId().equals(entity.getIntervalPeriodId()),
        entity);
  }

  /**
   * unconditionally emit to subscription queries of type FindAllIntervalPeriod
   *
   * @param entity IntervalPeriod
   */
  protected void emitFindAllIntervalPeriod(IntervalPeriod entity) {
    LOGGER.info("handling emitFindAllIntervalPeriod");

    queryUpdateEmitter.emit(FindAllIntervalPeriodQuery.class, query -> true, entity);
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired private final QueryUpdateEmitter queryUpdateEmitter;
  private static final Logger LOGGER = Logger.getLogger(IntervalPeriodProjector.class.getName());
}
