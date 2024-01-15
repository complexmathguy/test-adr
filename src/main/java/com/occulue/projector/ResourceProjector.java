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
 * Projector for Resource as outlined for the CQRS pattern. All event handling and query handling
 * related to Resource are invoked here and dispersed as an event to be handled elsewhere.
 *
 * <p>Commands are handled by ResourceAggregate
 *
 * @author your_name_here
 */
// @ProcessingGroup("resource")
@Component("resource-projector")
public class ResourceProjector extends ResourceEntityProjector {

  // core constructor
  public ResourceProjector(ResourceRepository repository, QueryUpdateEmitter queryUpdateEmitter) {
    super(repository);
    this.queryUpdateEmitter = queryUpdateEmitter;
  }

  /*
   * @param	event CreateResourceEvent
   */
  @EventHandler(payloadType = CreateResourceEvent.class)
  public void handle(CreateResourceEvent event) {
    LOGGER.info("handling CreateResourceEvent - " + event);
    Resource entity = new Resource();
    entity.setResourceId(event.getResourceId());
    entity.setCreatedDateTime(event.getCreatedDateTime());
    entity.setModificationDateTime(event.getModificationDateTime());
    entity.setResourceName(event.getResourceName());
    entity.setObjectType(event.getObjectType());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    create(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllResource(entity);
  }

  /*
   * @param	event UpdateResourceEvent
   */
  @EventHandler(payloadType = UpdateResourceEvent.class)
  public void handle(UpdateResourceEvent event) {
    LOGGER.info("handling UpdateResourceEvent - " + event);

    Resource entity = new Resource();
    entity.setResourceId(event.getResourceId());
    entity.setCreatedDateTime(event.getCreatedDateTime());
    entity.setModificationDateTime(event.getModificationDateTime());
    entity.setResourceName(event.getResourceName());
    entity.setVen(event.getVen());
    entity.setAttributes(event.getAttributes());
    entity.setTargets(event.getTargets());
    entity.setObjectType(event.getObjectType());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    update(entity);

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindResource(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllResource(entity);
  }

  /*
   * @param	event DeleteResourceEvent
   */
  @EventHandler(payloadType = DeleteResourceEvent.class)
  public void handle(DeleteResourceEvent event) {
    LOGGER.info("handling DeleteResourceEvent - " + event);

    // ------------------------------------------
    // delete delegation
    // ------------------------------------------
    Resource entity = delete(event.getResourceId());

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllResource(entity);
  }

  /*
   * @param	event AssignVenToResourceEvent
   */
  @EventHandler(payloadType = AssignVenToResourceEvent.class)
  public void handle(AssignVenToResourceEvent event) {
    LOGGER.info("handling AssignVenToResourceEvent - " + event);

    // ------------------------------------------
    // delegate to assignTo
    // ------------------------------------------
    Resource entity = assignVen(event.getResourceId(), event.getAssignment());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindResource(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllResource(entity);
  }

  /*
   * @param	event UnAssignVenFromResourceEvent
   */
  @EventHandler(payloadType = UnAssignVenFromResourceEvent.class)
  public void handle(UnAssignVenFromResourceEvent event) {
    LOGGER.info("handling UnAssignVenFromResourceEvent - " + event);

    // ------------------------------------------
    // delegate to unAssignFrom
    // ------------------------------------------
    Resource entity = unAssignVen(event.getResourceId());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindResource(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllResource(entity);
  }

  /*
   * @param	event AssignAttributesToResourceEvent
   */
  @EventHandler(payloadType = AssignAttributesToResourceEvent.class)
  public void handle(AssignAttributesToResourceEvent event) {
    LOGGER.info("handling AssignAttributesToResourceEvent - " + event);

    // ------------------------------------------
    // delegate to addTo
    // ------------------------------------------
    Resource entity = addToAttributes(event.getResourceId(), event.getAddTo());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindResource(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllResource(entity);
  }

  /*
   * @param	event RemoveAttributesFromResourceEvent
   */
  @EventHandler(payloadType = RemoveAttributesFromResourceEvent.class)
  public void handle(RemoveAttributesFromResourceEvent event) {
    LOGGER.info("handling RemoveAttributesFromResourceEvent - " + event);

    Resource entity = removeFromAttributes(event.getResourceId(), event.getRemoveFrom());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindResource(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllResource(entity);
  }

  /*
   * @param	event AssignTargetsToResourceEvent
   */
  @EventHandler(payloadType = AssignTargetsToResourceEvent.class)
  public void handle(AssignTargetsToResourceEvent event) {
    LOGGER.info("handling AssignTargetsToResourceEvent - " + event);

    // ------------------------------------------
    // delegate to addTo
    // ------------------------------------------
    Resource entity = addToTargets(event.getResourceId(), event.getAddTo());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindResource(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllResource(entity);
  }

  /*
   * @param	event RemoveTargetsFromResourceEvent
   */
  @EventHandler(payloadType = RemoveTargetsFromResourceEvent.class)
  public void handle(RemoveTargetsFromResourceEvent event) {
    LOGGER.info("handling RemoveTargetsFromResourceEvent - " + event);

    Resource entity = removeFromTargets(event.getResourceId(), event.getRemoveFrom());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindResource(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllResource(entity);
  }

  /**
   * Method to retrieve the Resource via an ResourcePrimaryKey.
   *
   * @param id Long
   * @return Resource
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public Resource handle(FindResourceQuery query)
      throws ProcessingException, IllegalArgumentException {
    return find(query.getFilter().getResourceId());
  }

  /**
   * Method to retrieve a collection of all Resources
   *
   * @param query FindAllResourceQuery
   * @return List<Resource>
   * @exception ProcessingException Thrown if any problems
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public List<Resource> handle(FindAllResourceQuery query) throws ProcessingException {
    return findAll(query);
  }

  /**
   * emit to subscription queries of type FindResource, but only if the id matches
   *
   * @param entity Resource
   */
  protected void emitFindResource(Resource entity) {
    LOGGER.info("handling emitFindResource");

    queryUpdateEmitter.emit(
        FindResourceQuery.class,
        query -> query.getFilter().getResourceId().equals(entity.getResourceId()),
        entity);
  }

  /**
   * unconditionally emit to subscription queries of type FindAllResource
   *
   * @param entity Resource
   */
  protected void emitFindAllResource(Resource entity) {
    LOGGER.info("handling emitFindAllResource");

    queryUpdateEmitter.emit(FindAllResourceQuery.class, query -> true, entity);
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired private final QueryUpdateEmitter queryUpdateEmitter;
  private static final Logger LOGGER = Logger.getLogger(ResourceProjector.class.getName());
}
