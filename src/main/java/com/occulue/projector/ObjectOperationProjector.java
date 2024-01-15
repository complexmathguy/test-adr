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
 * Projector for ObjectOperation as outlined for the CQRS pattern. All event handling and query
 * handling related to ObjectOperation are invoked here and dispersed as an event to be handled
 * elsewhere.
 *
 * <p>Commands are handled by ObjectOperationAggregate
 *
 * @author your_name_here
 */
// @ProcessingGroup("objectOperation")
@Component("objectOperation-projector")
public class ObjectOperationProjector extends ObjectOperationEntityProjector {

  // core constructor
  public ObjectOperationProjector(
      ObjectOperationRepository repository, QueryUpdateEmitter queryUpdateEmitter) {
    super(repository);
    this.queryUpdateEmitter = queryUpdateEmitter;
  }

  /*
   * @param	event CreateObjectOperationEvent
   */
  @EventHandler(payloadType = CreateObjectOperationEvent.class)
  public void handle(CreateObjectOperationEvent event) {
    LOGGER.info("handling CreateObjectOperationEvent - " + event);
    ObjectOperation entity = new ObjectOperation();
    entity.setObjectOperationId(event.getObjectOperationId());
    entity.setCallbackUrl(event.getCallbackUrl());
    entity.setBearerToken(event.getBearerToken());
    entity.setObjects(event.getObjects());
    entity.setOperations(event.getOperations());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    create(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllObjectOperation(entity);
  }

  /*
   * @param	event UpdateObjectOperationEvent
   */
  @EventHandler(payloadType = UpdateObjectOperationEvent.class)
  public void handle(UpdateObjectOperationEvent event) {
    LOGGER.info("handling UpdateObjectOperationEvent - " + event);

    ObjectOperation entity = new ObjectOperation();
    entity.setObjectOperationId(event.getObjectOperationId());
    entity.setCallbackUrl(event.getCallbackUrl());
    entity.setBearerToken(event.getBearerToken());
    entity.setObjects(event.getObjects());
    entity.setOperations(event.getOperations());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    update(entity);

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindObjectOperation(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllObjectOperation(entity);
  }

  /*
   * @param	event DeleteObjectOperationEvent
   */
  @EventHandler(payloadType = DeleteObjectOperationEvent.class)
  public void handle(DeleteObjectOperationEvent event) {
    LOGGER.info("handling DeleteObjectOperationEvent - " + event);

    // ------------------------------------------
    // delete delegation
    // ------------------------------------------
    ObjectOperation entity = delete(event.getObjectOperationId());

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllObjectOperation(entity);
  }

  /**
   * Method to retrieve the ObjectOperation via an ObjectOperationPrimaryKey.
   *
   * @param id Long
   * @return ObjectOperation
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public ObjectOperation handle(FindObjectOperationQuery query)
      throws ProcessingException, IllegalArgumentException {
    return find(query.getFilter().getObjectOperationId());
  }

  /**
   * Method to retrieve a collection of all ObjectOperations
   *
   * @param query FindAllObjectOperationQuery
   * @return List<ObjectOperation>
   * @exception ProcessingException Thrown if any problems
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public List<ObjectOperation> handle(FindAllObjectOperationQuery query)
      throws ProcessingException {
    return findAll(query);
  }

  /**
   * emit to subscription queries of type FindObjectOperation, but only if the id matches
   *
   * @param entity ObjectOperation
   */
  protected void emitFindObjectOperation(ObjectOperation entity) {
    LOGGER.info("handling emitFindObjectOperation");

    queryUpdateEmitter.emit(
        FindObjectOperationQuery.class,
        query -> query.getFilter().getObjectOperationId().equals(entity.getObjectOperationId()),
        entity);
  }

  /**
   * unconditionally emit to subscription queries of type FindAllObjectOperation
   *
   * @param entity ObjectOperation
   */
  protected void emitFindAllObjectOperation(ObjectOperation entity) {
    LOGGER.info("handling emitFindAllObjectOperation");

    queryUpdateEmitter.emit(FindAllObjectOperationQuery.class, query -> true, entity);
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired private final QueryUpdateEmitter queryUpdateEmitter;
  private static final Logger LOGGER = Logger.getLogger(ObjectOperationProjector.class.getName());
}
