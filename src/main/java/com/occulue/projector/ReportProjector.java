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
 * Projector for Report as outlined for the CQRS pattern. All event handling and query handling
 * related to Report are invoked here and dispersed as an event to be handled elsewhere.
 *
 * <p>Commands are handled by ReportAggregate
 *
 * @author your_name_here
 */
// @ProcessingGroup("report")
@Component("report-projector")
public class ReportProjector extends ReportEntityProjector {

  // core constructor
  public ReportProjector(ReportRepository repository, QueryUpdateEmitter queryUpdateEmitter) {
    super(repository);
    this.queryUpdateEmitter = queryUpdateEmitter;
  }

  /*
   * @param	event CreateReportEvent
   */
  @EventHandler(payloadType = CreateReportEvent.class)
  public void handle(CreateReportEvent event) {
    LOGGER.info("handling CreateReportEvent - " + event);
    Report entity = new Report();
    entity.setReportId(event.getReportId());
    entity.setCreatedDateTime(event.getCreatedDateTime());
    entity.setModificationDateTime(event.getModificationDateTime());
    entity.setClientName(event.getClientName());
    entity.setReportName(event.getReportName());
    entity.setObjectType(event.getObjectType());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    create(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReport(entity);
  }

  /*
   * @param	event UpdateReportEvent
   */
  @EventHandler(payloadType = UpdateReportEvent.class)
  public void handle(UpdateReportEvent event) {
    LOGGER.info("handling UpdateReportEvent - " + event);

    Report entity = new Report();
    entity.setReportId(event.getReportId());
    entity.setCreatedDateTime(event.getCreatedDateTime());
    entity.setModificationDateTime(event.getModificationDateTime());
    entity.setClientName(event.getClientName());
    entity.setReportName(event.getReportName());
    entity.setProgram(event.getProgram());
    entity.setEvent(event.getEvent());
    entity.setPayloadDescriptors(event.getPayloadDescriptors());
    entity.setResources(event.getResources());
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
    emitFindReport(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReport(entity);
  }

  /*
   * @param	event DeleteReportEvent
   */
  @EventHandler(payloadType = DeleteReportEvent.class)
  public void handle(DeleteReportEvent event) {
    LOGGER.info("handling DeleteReportEvent - " + event);

    // ------------------------------------------
    // delete delegation
    // ------------------------------------------
    Report entity = delete(event.getReportId());

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReport(entity);
  }

  /*
   * @param	event AssignProgramToReportEvent
   */
  @EventHandler(payloadType = AssignProgramToReportEvent.class)
  public void handle(AssignProgramToReportEvent event) {
    LOGGER.info("handling AssignProgramToReportEvent - " + event);

    // ------------------------------------------
    // delegate to assignTo
    // ------------------------------------------
    Report entity = assignProgram(event.getReportId(), event.getAssignment());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindReport(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReport(entity);
  }

  /*
   * @param	event UnAssignProgramFromReportEvent
   */
  @EventHandler(payloadType = UnAssignProgramFromReportEvent.class)
  public void handle(UnAssignProgramFromReportEvent event) {
    LOGGER.info("handling UnAssignProgramFromReportEvent - " + event);

    // ------------------------------------------
    // delegate to unAssignFrom
    // ------------------------------------------
    Report entity = unAssignProgram(event.getReportId());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindReport(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReport(entity);
  }

  /*
   * @param	event AssignEventToReportEvent
   */
  @EventHandler(payloadType = AssignEventToReportEvent.class)
  public void handle(AssignEventToReportEvent event) {
    LOGGER.info("handling AssignEventToReportEvent - " + event);

    // ------------------------------------------
    // delegate to assignTo
    // ------------------------------------------
    Report entity = assignEvent(event.getReportId(), event.getAssignment());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindReport(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReport(entity);
  }

  /*
   * @param	event UnAssignEventFromReportEvent
   */
  @EventHandler(payloadType = UnAssignEventFromReportEvent.class)
  public void handle(UnAssignEventFromReportEvent event) {
    LOGGER.info("handling UnAssignEventFromReportEvent - " + event);

    // ------------------------------------------
    // delegate to unAssignFrom
    // ------------------------------------------
    Report entity = unAssignEvent(event.getReportId());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindReport(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReport(entity);
  }

  /*
   * @param	event AssignIntervalsToReportEvent
   */
  @EventHandler(payloadType = AssignIntervalsToReportEvent.class)
  public void handle(AssignIntervalsToReportEvent event) {
    LOGGER.info("handling AssignIntervalsToReportEvent - " + event);

    // ------------------------------------------
    // delegate to assignTo
    // ------------------------------------------
    Report entity = assignIntervals(event.getReportId(), event.getAssignment());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindReport(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReport(entity);
  }

  /*
   * @param	event UnAssignIntervalsFromReportEvent
   */
  @EventHandler(payloadType = UnAssignIntervalsFromReportEvent.class)
  public void handle(UnAssignIntervalsFromReportEvent event) {
    LOGGER.info("handling UnAssignIntervalsFromReportEvent - " + event);

    // ------------------------------------------
    // delegate to unAssignFrom
    // ------------------------------------------
    Report entity = unAssignIntervals(event.getReportId());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindReport(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReport(entity);
  }

  /*
   * @param	event AssignIntervalPeriodToReportEvent
   */
  @EventHandler(payloadType = AssignIntervalPeriodToReportEvent.class)
  public void handle(AssignIntervalPeriodToReportEvent event) {
    LOGGER.info("handling AssignIntervalPeriodToReportEvent - " + event);

    // ------------------------------------------
    // delegate to assignTo
    // ------------------------------------------
    Report entity = assignIntervalPeriod(event.getReportId(), event.getAssignment());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindReport(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReport(entity);
  }

  /*
   * @param	event UnAssignIntervalPeriodFromReportEvent
   */
  @EventHandler(payloadType = UnAssignIntervalPeriodFromReportEvent.class)
  public void handle(UnAssignIntervalPeriodFromReportEvent event) {
    LOGGER.info("handling UnAssignIntervalPeriodFromReportEvent - " + event);

    // ------------------------------------------
    // delegate to unAssignFrom
    // ------------------------------------------
    Report entity = unAssignIntervalPeriod(event.getReportId());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindReport(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReport(entity);
  }

  /*
   * @param	event AssignPayloadDescriptorsToReportEvent
   */
  @EventHandler(payloadType = AssignPayloadDescriptorsToReportEvent.class)
  public void handle(AssignPayloadDescriptorsToReportEvent event) {
    LOGGER.info("handling AssignPayloadDescriptorsToReportEvent - " + event);

    // ------------------------------------------
    // delegate to addTo
    // ------------------------------------------
    Report entity = addToPayloadDescriptors(event.getReportId(), event.getAddTo());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindReport(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReport(entity);
  }

  /*
   * @param	event RemovePayloadDescriptorsFromReportEvent
   */
  @EventHandler(payloadType = RemovePayloadDescriptorsFromReportEvent.class)
  public void handle(RemovePayloadDescriptorsFromReportEvent event) {
    LOGGER.info("handling RemovePayloadDescriptorsFromReportEvent - " + event);

    Report entity = removeFromPayloadDescriptors(event.getReportId(), event.getRemoveFrom());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindReport(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReport(entity);
  }

  /*
   * @param	event AssignResourcesToReportEvent
   */
  @EventHandler(payloadType = AssignResourcesToReportEvent.class)
  public void handle(AssignResourcesToReportEvent event) {
    LOGGER.info("handling AssignResourcesToReportEvent - " + event);

    // ------------------------------------------
    // delegate to addTo
    // ------------------------------------------
    Report entity = addToResources(event.getReportId(), event.getAddTo());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindReport(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReport(entity);
  }

  /*
   * @param	event RemoveResourcesFromReportEvent
   */
  @EventHandler(payloadType = RemoveResourcesFromReportEvent.class)
  public void handle(RemoveResourcesFromReportEvent event) {
    LOGGER.info("handling RemoveResourcesFromReportEvent - " + event);

    Report entity = removeFromResources(event.getReportId(), event.getRemoveFrom());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindReport(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllReport(entity);
  }

  /**
   * Method to retrieve the Report via an ReportPrimaryKey.
   *
   * @param id Long
   * @return Report
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public Report handle(FindReportQuery query) throws ProcessingException, IllegalArgumentException {
    return find(query.getFilter().getReportId());
  }

  /**
   * Method to retrieve a collection of all Reports
   *
   * @param query FindAllReportQuery
   * @return List<Report>
   * @exception ProcessingException Thrown if any problems
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public List<Report> handle(FindAllReportQuery query) throws ProcessingException {
    return findAll(query);
  }

  /**
   * emit to subscription queries of type FindReport, but only if the id matches
   *
   * @param entity Report
   */
  protected void emitFindReport(Report entity) {
    LOGGER.info("handling emitFindReport");

    queryUpdateEmitter.emit(
        FindReportQuery.class,
        query -> query.getFilter().getReportId().equals(entity.getReportId()),
        entity);
  }

  /**
   * unconditionally emit to subscription queries of type FindAllReport
   *
   * @param entity Report
   */
  protected void emitFindAllReport(Report entity) {
    LOGGER.info("handling emitFindAllReport");

    queryUpdateEmitter.emit(FindAllReportQuery.class, query -> true, entity);
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired private final QueryUpdateEmitter queryUpdateEmitter;
  private static final Logger LOGGER = Logger.getLogger(ReportProjector.class.getName());
}
