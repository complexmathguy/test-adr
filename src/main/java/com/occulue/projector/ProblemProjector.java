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
 * Projector for Problem as outlined for the CQRS pattern. All event handling and query handling
 * related to Problem are invoked here and dispersed as an event to be handled elsewhere.
 *
 * <p>Commands are handled by ProblemAggregate
 *
 * @author your_name_here
 */
// @ProcessingGroup("problem")
@Component("problem-projector")
public class ProblemProjector extends ProblemEntityProjector {

  // core constructor
  public ProblemProjector(ProblemRepository repository, QueryUpdateEmitter queryUpdateEmitter) {
    super(repository);
    this.queryUpdateEmitter = queryUpdateEmitter;
  }

  /*
   * @param	event CreateProblemEvent
   */
  @EventHandler(payloadType = CreateProblemEvent.class)
  public void handle(CreateProblemEvent event) {
    LOGGER.info("handling CreateProblemEvent - " + event);
    Problem entity = new Problem();
    entity.setProblemId(event.getProblemId());
    entity.setType(event.getType());
    entity.setTitle(event.getTitle());
    entity.setStatus(event.getStatus());
    entity.setDetail(event.getDetail());
    entity.setInstance(event.getInstance());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    create(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllProblem(entity);
  }

  /*
   * @param	event UpdateProblemEvent
   */
  @EventHandler(payloadType = UpdateProblemEvent.class)
  public void handle(UpdateProblemEvent event) {
    LOGGER.info("handling UpdateProblemEvent - " + event);

    Problem entity = new Problem();
    entity.setProblemId(event.getProblemId());
    entity.setType(event.getType());
    entity.setTitle(event.getTitle());
    entity.setStatus(event.getStatus());
    entity.setDetail(event.getDetail());
    entity.setInstance(event.getInstance());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    update(entity);

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindProblem(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllProblem(entity);
  }

  /*
   * @param	event DeleteProblemEvent
   */
  @EventHandler(payloadType = DeleteProblemEvent.class)
  public void handle(DeleteProblemEvent event) {
    LOGGER.info("handling DeleteProblemEvent - " + event);

    // ------------------------------------------
    // delete delegation
    // ------------------------------------------
    Problem entity = delete(event.getProblemId());

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllProblem(entity);
  }

  /**
   * Method to retrieve the Problem via an ProblemPrimaryKey.
   *
   * @param id Long
   * @return Problem
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public Problem handle(FindProblemQuery query)
      throws ProcessingException, IllegalArgumentException {
    return find(query.getFilter().getProblemId());
  }

  /**
   * Method to retrieve a collection of all Problems
   *
   * @param query FindAllProblemQuery
   * @return List<Problem>
   * @exception ProcessingException Thrown if any problems
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public List<Problem> handle(FindAllProblemQuery query) throws ProcessingException {
    return findAll(query);
  }

  /**
   * emit to subscription queries of type FindProblem, but only if the id matches
   *
   * @param entity Problem
   */
  protected void emitFindProblem(Problem entity) {
    LOGGER.info("handling emitFindProblem");

    queryUpdateEmitter.emit(
        FindProblemQuery.class,
        query -> query.getFilter().getProblemId().equals(entity.getProblemId()),
        entity);
  }

  /**
   * unconditionally emit to subscription queries of type FindAllProblem
   *
   * @param entity Problem
   */
  protected void emitFindAllProblem(Problem entity) {
    LOGGER.info("handling emitFindAllProblem");

    queryUpdateEmitter.emit(FindAllProblemQuery.class, query -> true, entity);
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired private final QueryUpdateEmitter queryUpdateEmitter;
  private static final Logger LOGGER = Logger.getLogger(ProblemProjector.class.getName());
}
