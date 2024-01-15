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
 * Projector for Ven as outlined for the CQRS pattern. All event handling and query handling related
 * to Ven are invoked here and dispersed as an event to be handled elsewhere.
 *
 * <p>Commands are handled by VenAggregate
 *
 * @author your_name_here
 */
// @ProcessingGroup("ven")
@Component("ven-projector")
public class VenProjector extends VenEntityProjector {

  // core constructor
  public VenProjector(VenRepository repository, QueryUpdateEmitter queryUpdateEmitter) {
    super(repository);
    this.queryUpdateEmitter = queryUpdateEmitter;
  }

  /*
   * @param	event CreateVenEvent
   */
  @EventHandler(payloadType = CreateVenEvent.class)
  public void handle(CreateVenEvent event) {
    LOGGER.info("handling CreateVenEvent - " + event);
    Ven entity = new Ven();
    entity.setVenId(event.getVenId());
    entity.setCreatedDateTime(event.getCreatedDateTime());
    entity.setModificationDateTime(event.getModificationDateTime());
    entity.setVenName(event.getVenName());
    entity.setObjectType(event.getObjectType());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    create(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllVen(entity);
  }

  /*
   * @param	event UpdateVenEvent
   */
  @EventHandler(payloadType = UpdateVenEvent.class)
  public void handle(UpdateVenEvent event) {
    LOGGER.info("handling UpdateVenEvent - " + event);

    Ven entity = new Ven();
    entity.setVenId(event.getVenId());
    entity.setCreatedDateTime(event.getCreatedDateTime());
    entity.setModificationDateTime(event.getModificationDateTime());
    entity.setVenName(event.getVenName());
    entity.setAttributes(event.getAttributes());
    entity.setTargets(event.getTargets());
    entity.setResources(event.getResources());
    entity.setObjectType(event.getObjectType());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    update(entity);

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindVen(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllVen(entity);
  }

  /*
   * @param	event DeleteVenEvent
   */
  @EventHandler(payloadType = DeleteVenEvent.class)
  public void handle(DeleteVenEvent event) {
    LOGGER.info("handling DeleteVenEvent - " + event);

    // ------------------------------------------
    // delete delegation
    // ------------------------------------------
    Ven entity = delete(event.getVenId());

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllVen(entity);
  }

  /*
   * @param	event AssignAttributesToVenEvent
   */
  @EventHandler(payloadType = AssignAttributesToVenEvent.class)
  public void handle(AssignAttributesToVenEvent event) {
    LOGGER.info("handling AssignAttributesToVenEvent - " + event);

    // ------------------------------------------
    // delegate to addTo
    // ------------------------------------------
    Ven entity = addToAttributes(event.getVenId(), event.getAddTo());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindVen(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllVen(entity);
  }

  /*
   * @param	event RemoveAttributesFromVenEvent
   */
  @EventHandler(payloadType = RemoveAttributesFromVenEvent.class)
  public void handle(RemoveAttributesFromVenEvent event) {
    LOGGER.info("handling RemoveAttributesFromVenEvent - " + event);

    Ven entity = removeFromAttributes(event.getVenId(), event.getRemoveFrom());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindVen(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllVen(entity);
  }

  /*
   * @param	event AssignTargetsToVenEvent
   */
  @EventHandler(payloadType = AssignTargetsToVenEvent.class)
  public void handle(AssignTargetsToVenEvent event) {
    LOGGER.info("handling AssignTargetsToVenEvent - " + event);

    // ------------------------------------------
    // delegate to addTo
    // ------------------------------------------
    Ven entity = addToTargets(event.getVenId(), event.getAddTo());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindVen(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllVen(entity);
  }

  /*
   * @param	event RemoveTargetsFromVenEvent
   */
  @EventHandler(payloadType = RemoveTargetsFromVenEvent.class)
  public void handle(RemoveTargetsFromVenEvent event) {
    LOGGER.info("handling RemoveTargetsFromVenEvent - " + event);

    Ven entity = removeFromTargets(event.getVenId(), event.getRemoveFrom());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindVen(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllVen(entity);
  }

  /*
   * @param	event AssignResourcesToVenEvent
   */
  @EventHandler(payloadType = AssignResourcesToVenEvent.class)
  public void handle(AssignResourcesToVenEvent event) {
    LOGGER.info("handling AssignResourcesToVenEvent - " + event);

    // ------------------------------------------
    // delegate to addTo
    // ------------------------------------------
    Ven entity = addToResources(event.getVenId(), event.getAddTo());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindVen(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllVen(entity);
  }

  /*
   * @param	event RemoveResourcesFromVenEvent
   */
  @EventHandler(payloadType = RemoveResourcesFromVenEvent.class)
  public void handle(RemoveResourcesFromVenEvent event) {
    LOGGER.info("handling RemoveResourcesFromVenEvent - " + event);

    Ven entity = removeFromResources(event.getVenId(), event.getRemoveFrom());

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindVen(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllVen(entity);
  }

  /**
   * Method to retrieve the Ven via an VenPrimaryKey.
   *
   * @param id Long
   * @return Ven
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public Ven handle(FindVenQuery query) throws ProcessingException, IllegalArgumentException {
    return find(query.getFilter().getVenId());
  }

  /**
   * Method to retrieve a collection of all Vens
   *
   * @param query FindAllVenQuery
   * @return List<Ven>
   * @exception ProcessingException Thrown if any problems
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public List<Ven> handle(FindAllVenQuery query) throws ProcessingException {
    return findAll(query);
  }

  /**
   * emit to subscription queries of type FindVen, but only if the id matches
   *
   * @param entity Ven
   */
  protected void emitFindVen(Ven entity) {
    LOGGER.info("handling emitFindVen");

    queryUpdateEmitter.emit(
        FindVenQuery.class,
        query -> query.getFilter().getVenId().equals(entity.getVenId()),
        entity);
  }

  /**
   * unconditionally emit to subscription queries of type FindAllVen
   *
   * @param entity Ven
   */
  protected void emitFindAllVen(Ven entity) {
    LOGGER.info("handling emitFindAllVen");

    queryUpdateEmitter.emit(FindAllVenQuery.class, query -> true, entity);
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired private final QueryUpdateEmitter queryUpdateEmitter;
  private static final Logger LOGGER = Logger.getLogger(VenProjector.class.getName());
}
