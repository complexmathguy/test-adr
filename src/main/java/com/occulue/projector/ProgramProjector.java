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
 * Projector for Program as outlined for the CQRS pattern. All event handling and query handling
 * related to Program are invoked here and dispersed as an event to be handled elsewhere.
 *
 * <p>Commands are handled by ProgramAggregate
 *
 * @author your_name_here
 */
// @ProcessingGroup("program")
@Component("program-projector")
public class ProgramProjector extends ProgramEntityProjector {

  // core constructor
  public ProgramProjector(ProgramRepository repository, QueryUpdateEmitter queryUpdateEmitter) {
    super(repository);
    this.queryUpdateEmitter = queryUpdateEmitter;
  }

  /*
   * @param	event CreateProgramEvent
   */
  @EventHandler(payloadType = CreateProgramEvent.class)
  public void handle(CreateProgramEvent event) {
    LOGGER.info("handling CreateProgramEvent - " + event);
    Program entity = new Program();
    entity.setProgramId(event.getProgramId());
    entity.setCreatedDateTime(event.getCreatedDateTime());
    entity.setModificationDateTime(event.getModificationDateTime());
    entity.setProgramName(event.getProgramName());
    entity.setProgramLongName(event.getProgramLongName());
    entity.setRetailerName(event.getRetailerName());
    entity.setRetailerLongName(event.getRetailerLongName());
    entity.setProgramType(event.getProgramType());
    entity.setCountry(event.getCountry());
    entity.setPrincipalSubdivision(event.getPrincipalSubdivision());
    entity.setTimeZoneOffset(event.getTimeZoneOffset());
    entity.setProgramDescriptions(event.getProgramDescriptions());
    entity.setBindingEvents(event.getBindingEvents());
    entity.setLocalPrice(event.getLocalPrice());
    entity.setObjectType(event.getObjectType());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    create(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllProgram(entity);
  }

  /*
   * @param	event UpdateProgramEvent
   */
  @EventHandler(payloadType = UpdateProgramEvent.class)
  public void handle(UpdateProgramEvent event) {
    LOGGER.info("handling UpdateProgramEvent - " + event);

    Program entity = new Program();
    entity.setProgramId(event.getProgramId());
    entity.setCreatedDateTime(event.getCreatedDateTime());
    entity.setModificationDateTime(event.getModificationDateTime());
    entity.setProgramName(event.getProgramName());
    entity.setProgramLongName(event.getProgramLongName());
    entity.setRetailerName(event.getRetailerName());
    entity.setRetailerLongName(event.getRetailerLongName());
    entity.setProgramType(event.getProgramType());
    entity.setCountry(event.getCountry());
    entity.setPrincipalSubdivision(event.getPrincipalSubdivision());
    entity.setTimeZoneOffset(event.getTimeZoneOffset());
    entity.setProgramDescriptions(event.getProgramDescriptions());
    entity.setBindingEvents(event.getBindingEvents());
    entity.setLocalPrice(event.getLocalPrice());
    entity.setPayloadDescriptors(event.getPayloadDescriptors());
    entity.setTargets(event.getTargets());
    entity.setObjectType(event.getObjectType());
    entity.setIntervalPeriod(event.getIntervalPeriod());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    update(entity);

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindProgram(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllProgram(entity);
  }

  /*
   * @param	event DeleteProgramEvent
   */
  @EventHandler(payloadType = DeleteProgramEvent.class)
  public void handle(DeleteProgramEvent event) {
    LOGGER.info("handling DeleteProgramEvent - " + event);

    // ------------------------------------------
    // delete delegation
    // ------------------------------------------
    Program entity = delete(event.getProgramId());

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllProgram(entity);
  }

  /*
   * @param	event AssignIntervalPeriodToProgramEvent
   */
  @EventHandler(payloadType = AssignIntervalPeriodToProgramEvent.class)
  public void handle(AssignIntervalPeriodToProgramEvent event) {
    LOGGER.info("handling AssignIntervalPeriodToProgramEvent - " + event);

    // ------------------------------------------
    // delegate to assignTo
    // ------------------------------------------
    Program entity = assignIntervalPeriod(event.getProgramId(), event.getAssignment());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindProgram(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllProgram(entity);
  }

  /*
   * @param	event UnAssignIntervalPeriodFromProgramEvent
   */
  @EventHandler(payloadType = UnAssignIntervalPeriodFromProgramEvent.class)
  public void handle(UnAssignIntervalPeriodFromProgramEvent event) {
    LOGGER.info("handling UnAssignIntervalPeriodFromProgramEvent - " + event);

    // ------------------------------------------
    // delegate to unAssignFrom
    // ------------------------------------------
    Program entity = unAssignIntervalPeriod(event.getProgramId());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindProgram(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllProgram(entity);
  }

  /*
   * @param	event AssignPayloadDescriptorsToProgramEvent
   */
  @EventHandler(payloadType = AssignPayloadDescriptorsToProgramEvent.class)
  public void handle(AssignPayloadDescriptorsToProgramEvent event) {
    LOGGER.info("handling AssignPayloadDescriptorsToProgramEvent - " + event);

    // ------------------------------------------
    // delegate to addTo
    // ------------------------------------------
    Program entity = addToPayloadDescriptors(event.getProgramId(), event.getAddTo());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindProgram(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllProgram(entity);
  }

  /*
   * @param	event RemovePayloadDescriptorsFromProgramEvent
   */
  @EventHandler(payloadType = RemovePayloadDescriptorsFromProgramEvent.class)
  public void handle(RemovePayloadDescriptorsFromProgramEvent event) {
    LOGGER.info("handling RemovePayloadDescriptorsFromProgramEvent - " + event);

    Program entity = removeFromPayloadDescriptors(event.getProgramId(), event.getRemoveFrom());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindProgram(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllProgram(entity);
  }

  /*
   * @param	event AssignTargetsToProgramEvent
   */
  @EventHandler(payloadType = AssignTargetsToProgramEvent.class)
  public void handle(AssignTargetsToProgramEvent event) {
    LOGGER.info("handling AssignTargetsToProgramEvent - " + event);

    // ------------------------------------------
    // delegate to addTo
    // ------------------------------------------
    Program entity = addToTargets(event.getProgramId(), event.getAddTo());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindProgram(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllProgram(entity);
  }

  /*
   * @param	event RemoveTargetsFromProgramEvent
   */
  @EventHandler(payloadType = RemoveTargetsFromProgramEvent.class)
  public void handle(RemoveTargetsFromProgramEvent event) {
    LOGGER.info("handling RemoveTargetsFromProgramEvent - " + event);

    Program entity = removeFromTargets(event.getProgramId(), event.getRemoveFrom());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindProgram(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllProgram(entity);
  }

  /**
   * Method to retrieve the Program via an ProgramPrimaryKey.
   *
   * @param id Long
   * @return Program
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public Program handle(FindProgramQuery query)
      throws ProcessingException, IllegalArgumentException {
    return find(query.getFilter().getProgramId());
  }

  /**
   * Method to retrieve a collection of all Programs
   *
   * @param query FindAllProgramQuery
   * @return List<Program>
   * @exception ProcessingException Thrown if any problems
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public List<Program> handle(FindAllProgramQuery query) throws ProcessingException {
    return findAll(query);
  }

  /**
   * emit to subscription queries of type FindProgram, but only if the id matches
   *
   * @param entity Program
   */
  protected void emitFindProgram(Program entity) {
    LOGGER.info("handling emitFindProgram");

    queryUpdateEmitter.emit(
        FindProgramQuery.class,
        query -> query.getFilter().getProgramId().equals(entity.getProgramId()),
        entity);
  }

  /**
   * unconditionally emit to subscription queries of type FindAllProgram
   *
   * @param entity Program
   */
  protected void emitFindAllProgram(Program entity) {
    LOGGER.info("handling emitFindAllProgram");

    queryUpdateEmitter.emit(FindAllProgramQuery.class, query -> true, entity);
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired private final QueryUpdateEmitter queryUpdateEmitter;
  private static final Logger LOGGER = Logger.getLogger(ProgramProjector.class.getName());
}
