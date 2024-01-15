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
 * Projector for ValuesMap as outlined for the CQRS pattern. All event handling and query handling
 * related to ValuesMap are invoked here and dispersed as an event to be handled elsewhere.
 *
 * <p>Commands are handled by ValuesMapAggregate
 *
 * @author your_name_here
 */
// @ProcessingGroup("valuesMap")
@Component("valuesMap-projector")
public class ValuesMapProjector extends ValuesMapEntityProjector {

  // core constructor
  public ValuesMapProjector(ValuesMapRepository repository, QueryUpdateEmitter queryUpdateEmitter) {
    super(repository);
    this.queryUpdateEmitter = queryUpdateEmitter;
  }

  /*
   * @param	event CreateValuesMapEvent
   */
  @EventHandler(payloadType = CreateValuesMapEvent.class)
  public void handle(CreateValuesMapEvent event) {
    LOGGER.info("handling CreateValuesMapEvent - " + event);
    ValuesMap entity = new ValuesMap();
    entity.setValuesMapId(event.getValuesMapId());
    entity.setType(event.getType());
    entity.setValues(event.getValues());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    create(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllValuesMap(entity);
  }

  /*
   * @param	event UpdateValuesMapEvent
   */
  @EventHandler(payloadType = UpdateValuesMapEvent.class)
  public void handle(UpdateValuesMapEvent event) {
    LOGGER.info("handling UpdateValuesMapEvent - " + event);

    ValuesMap entity = new ValuesMap();
    entity.setValuesMapId(event.getValuesMapId());
    entity.setType(event.getType());
    entity.setValues(event.getValues());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    update(entity);

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindValuesMap(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllValuesMap(entity);
  }

  /*
   * @param	event DeleteValuesMapEvent
   */
  @EventHandler(payloadType = DeleteValuesMapEvent.class)
  public void handle(DeleteValuesMapEvent event) {
    LOGGER.info("handling DeleteValuesMapEvent - " + event);

    // ------------------------------------------
    // delete delegation
    // ------------------------------------------
    ValuesMap entity = delete(event.getValuesMapId());

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllValuesMap(entity);
  }

  /**
   * Method to retrieve the ValuesMap via an ValuesMapPrimaryKey.
   *
   * @param id Long
   * @return ValuesMap
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public ValuesMap handle(FindValuesMapQuery query)
      throws ProcessingException, IllegalArgumentException {
    return find(query.getFilter().getValuesMapId());
  }

  /**
   * Method to retrieve a collection of all ValuesMaps
   *
   * @param query FindAllValuesMapQuery
   * @return List<ValuesMap>
   * @exception ProcessingException Thrown if any problems
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public List<ValuesMap> handle(FindAllValuesMapQuery query) throws ProcessingException {
    return findAll(query);
  }

  /**
   * emit to subscription queries of type FindValuesMap, but only if the id matches
   *
   * @param entity ValuesMap
   */
  protected void emitFindValuesMap(ValuesMap entity) {
    LOGGER.info("handling emitFindValuesMap");

    queryUpdateEmitter.emit(
        FindValuesMapQuery.class,
        query -> query.getFilter().getValuesMapId().equals(entity.getValuesMapId()),
        entity);
  }

  /**
   * unconditionally emit to subscription queries of type FindAllValuesMap
   *
   * @param entity ValuesMap
   */
  protected void emitFindAllValuesMap(ValuesMap entity) {
    LOGGER.info("handling emitFindAllValuesMap");

    queryUpdateEmitter.emit(FindAllValuesMapQuery.class, query -> true, entity);
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired private final QueryUpdateEmitter queryUpdateEmitter;
  private static final Logger LOGGER = Logger.getLogger(ValuesMapProjector.class.getName());
}
