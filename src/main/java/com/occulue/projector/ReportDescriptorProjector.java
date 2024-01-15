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
 * Projector for ReportDescriptor as outlined for the CQRS pattern. All event handling and query
 * handling related to ReportDescriptor are invoked here and dispersed as an event to be handled
 * elsewhere.
 *
 * <p>Commands are handled by ReportDescriptorAggregate
 *
 * @author your_name_here
 */
// @ProcessingGroup("reportDescriptor")
@Component("reportDescriptor-projector")
public class ReportDescriptorProjector extends ReportDescriptorEntityProjector {

  // core constructor
  public ReportDescriptorProjector(
      ReportDescriptorRepository repository, QueryUpdateEmitter queryUpdateEmitter) {
    super(repository);
    this.queryUpdateEmitter = queryUpdateEmitter;
  }

  /*
   * @param	event CreateReportDescriptorEvent
   */
  @EventHandler(payloadType = CreateReportDescriptorEvent.class)
  public void handle(CreateReportDescriptorEvent event) {
    LOGGER.info("handling CreateReportDescriptorEvent - " + event);
    ReportDescriptor entity = new ReportDescriptor();
    entity.setReportDescriptorId(event.getReportDescriptorId());
    entity.setPayloadType(event.getPayloadType());
    entity.setReadingType(event.getReadingType());
    entity.setUnits(event.getUnits());
    entity.setAggregate(event.getAggregate());
    entity.setStartInterval(event.getStartInterval());
    entity.setNumIntervals(event.getNumIntervals());
    entity.setHistorical(event.getHistorical());
    entity.setFrequency(event.getFrequency());
    entity.setRepeat(event.getRepeat());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    create(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReportDescriptor(entity);
  }

  /*
   * @param	event UpdateReportDescriptorEvent
   */
  @EventHandler(payloadType = UpdateReportDescriptorEvent.class)
  public void handle(UpdateReportDescriptorEvent event) {
    LOGGER.info("handling UpdateReportDescriptorEvent - " + event);

    ReportDescriptor entity = new ReportDescriptor();
    entity.setReportDescriptorId(event.getReportDescriptorId());
    entity.setPayloadType(event.getPayloadType());
    entity.setReadingType(event.getReadingType());
    entity.setUnits(event.getUnits());
    entity.setAggregate(event.getAggregate());
    entity.setStartInterval(event.getStartInterval());
    entity.setNumIntervals(event.getNumIntervals());
    entity.setHistorical(event.getHistorical());
    entity.setFrequency(event.getFrequency());
    entity.setRepeat(event.getRepeat());
    entity.setTargets(event.getTargets());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    update(entity);

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindReportDescriptor(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReportDescriptor(entity);
  }

  /*
   * @param	event DeleteReportDescriptorEvent
   */
  @EventHandler(payloadType = DeleteReportDescriptorEvent.class)
  public void handle(DeleteReportDescriptorEvent event) {
    LOGGER.info("handling DeleteReportDescriptorEvent - " + event);

    // ------------------------------------------
    // delete delegation
    // ------------------------------------------
    ReportDescriptor entity = delete(event.getReportDescriptorId());

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReportDescriptor(entity);
  }

  /*
   * @param	event AssignTargetsToReportDescriptorEvent
   */
  @EventHandler(payloadType = AssignTargetsToReportDescriptorEvent.class)
  public void handle(AssignTargetsToReportDescriptorEvent event) {
    LOGGER.info("handling AssignTargetsToReportDescriptorEvent - " + event);

    // ------------------------------------------
    // delegate to assignTo
    // ------------------------------------------
    ReportDescriptor entity = assignTargets(event.getReportDescriptorId(), event.getAssignment());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindReportDescriptor(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReportDescriptor(entity);
  }

  /*
   * @param	event UnAssignTargetsFromReportDescriptorEvent
   */
  @EventHandler(payloadType = UnAssignTargetsFromReportDescriptorEvent.class)
  public void handle(UnAssignTargetsFromReportDescriptorEvent event) {
    LOGGER.info("handling UnAssignTargetsFromReportDescriptorEvent - " + event);

    // ------------------------------------------
    // delegate to unAssignFrom
    // ------------------------------------------
    ReportDescriptor entity = unAssignTargets(event.getReportDescriptorId());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindReportDescriptor(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReportDescriptor(entity);
  }

  /**
   * Method to retrieve the ReportDescriptor via an ReportDescriptorPrimaryKey.
   *
   * @param id Long
   * @return ReportDescriptor
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public ReportDescriptor handle(FindReportDescriptorQuery query)
      throws ProcessingException, IllegalArgumentException {
    return find(query.getFilter().getReportDescriptorId());
  }

  /**
   * Method to retrieve a collection of all ReportDescriptors
   *
   * @param query FindAllReportDescriptorQuery
   * @return List<ReportDescriptor>
   * @exception ProcessingException Thrown if any problems
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public List<ReportDescriptor> handle(FindAllReportDescriptorQuery query)
      throws ProcessingException {
    return findAll(query);
  }

  /**
   * emit to subscription queries of type FindReportDescriptor, but only if the id matches
   *
   * @param entity ReportDescriptor
   */
  protected void emitFindReportDescriptor(ReportDescriptor entity) {
    LOGGER.info("handling emitFindReportDescriptor");

    queryUpdateEmitter.emit(
        FindReportDescriptorQuery.class,
        query -> query.getFilter().getReportDescriptorId().equals(entity.getReportDescriptorId()),
        entity);
  }

  /**
   * unconditionally emit to subscription queries of type FindAllReportDescriptor
   *
   * @param entity ReportDescriptor
   */
  protected void emitFindAllReportDescriptor(ReportDescriptor entity) {
    LOGGER.info("handling emitFindAllReportDescriptor");

    queryUpdateEmitter.emit(FindAllReportDescriptorQuery.class, query -> true, entity);
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired private final QueryUpdateEmitter queryUpdateEmitter;
  private static final Logger LOGGER = Logger.getLogger(ReportDescriptorProjector.class.getName());
}
