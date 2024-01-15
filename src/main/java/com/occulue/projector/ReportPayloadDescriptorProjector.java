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
 * Projector for ReportPayloadDescriptor as outlined for the CQRS pattern. All event handling and
 * query handling related to ReportPayloadDescriptor are invoked here and dispersed as an event to
 * be handled elsewhere.
 *
 * <p>Commands are handled by ReportPayloadDescriptorAggregate
 *
 * @author your_name_here
 */
// @ProcessingGroup("reportPayloadDescriptor")
@Component("reportPayloadDescriptor-projector")
public class ReportPayloadDescriptorProjector extends ReportPayloadDescriptorEntityProjector {

  // core constructor
  public ReportPayloadDescriptorProjector(
      ReportPayloadDescriptorRepository repository, QueryUpdateEmitter queryUpdateEmitter) {
    super(repository);
    this.queryUpdateEmitter = queryUpdateEmitter;
  }

  /*
   * @param	event CreateReportPayloadDescriptorEvent
   */
  @EventHandler(payloadType = CreateReportPayloadDescriptorEvent.class)
  public void handle(CreateReportPayloadDescriptorEvent event) {
    LOGGER.info("handling CreateReportPayloadDescriptorEvent - " + event);
    ReportPayloadDescriptor entity = new ReportPayloadDescriptor();
    entity.setReportPayloadDescriptorId(event.getReportPayloadDescriptorId());
    entity.setPayloadType(event.getPayloadType());
    entity.setReadingType(event.getReadingType());
    entity.setUnits(event.getUnits());
    entity.setAccuracy(event.getAccuracy());
    entity.setConfidence(event.getConfidence());
    entity.setObjectType(event.getObjectType());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    create(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReportPayloadDescriptor(entity);
  }

  /*
   * @param	event UpdateReportPayloadDescriptorEvent
   */
  @EventHandler(payloadType = UpdateReportPayloadDescriptorEvent.class)
  public void handle(UpdateReportPayloadDescriptorEvent event) {
    LOGGER.info("handling UpdateReportPayloadDescriptorEvent - " + event);

    ReportPayloadDescriptor entity = new ReportPayloadDescriptor();
    entity.setReportPayloadDescriptorId(event.getReportPayloadDescriptorId());
    entity.setPayloadType(event.getPayloadType());
    entity.setReadingType(event.getReadingType());
    entity.setUnits(event.getUnits());
    entity.setAccuracy(event.getAccuracy());
    entity.setConfidence(event.getConfidence());
    entity.setObjectType(event.getObjectType());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    update(entity);

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindReportPayloadDescriptor(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReportPayloadDescriptor(entity);
  }

  /*
   * @param	event DeleteReportPayloadDescriptorEvent
   */
  @EventHandler(payloadType = DeleteReportPayloadDescriptorEvent.class)
  public void handle(DeleteReportPayloadDescriptorEvent event) {
    LOGGER.info("handling DeleteReportPayloadDescriptorEvent - " + event);

    // ------------------------------------------
    // delete delegation
    // ------------------------------------------
    ReportPayloadDescriptor entity = delete(event.getReportPayloadDescriptorId());

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReportPayloadDescriptor(entity);
  }

  /**
   * Method to retrieve the ReportPayloadDescriptor via an ReportPayloadDescriptorPrimaryKey.
   *
   * @param id Long
   * @return ReportPayloadDescriptor
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public ReportPayloadDescriptor handle(FindReportPayloadDescriptorQuery query)
      throws ProcessingException, IllegalArgumentException {
    return find(query.getFilter().getReportPayloadDescriptorId());
  }

  /**
   * Method to retrieve a collection of all ReportPayloadDescriptors
   *
   * @param query FindAllReportPayloadDescriptorQuery
   * @return List<ReportPayloadDescriptor>
   * @exception ProcessingException Thrown if any problems
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public List<ReportPayloadDescriptor> handle(FindAllReportPayloadDescriptorQuery query)
      throws ProcessingException {
    return findAll(query);
  }

  /**
   * emit to subscription queries of type FindReportPayloadDescriptor, but only if the id matches
   *
   * @param entity ReportPayloadDescriptor
   */
  protected void emitFindReportPayloadDescriptor(ReportPayloadDescriptor entity) {
    LOGGER.info("handling emitFindReportPayloadDescriptor");

    queryUpdateEmitter.emit(
        FindReportPayloadDescriptorQuery.class,
        query ->
            query
                .getFilter()
                .getReportPayloadDescriptorId()
                .equals(entity.getReportPayloadDescriptorId()),
        entity);
  }

  /**
   * unconditionally emit to subscription queries of type FindAllReportPayloadDescriptor
   *
   * @param entity ReportPayloadDescriptor
   */
  protected void emitFindAllReportPayloadDescriptor(ReportPayloadDescriptor entity) {
    LOGGER.info("handling emitFindAllReportPayloadDescriptor");

    queryUpdateEmitter.emit(FindAllReportPayloadDescriptorQuery.class, query -> true, entity);
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired private final QueryUpdateEmitter queryUpdateEmitter;
  private static final Logger LOGGER =
      Logger.getLogger(ReportPayloadDescriptorProjector.class.getName());
}
