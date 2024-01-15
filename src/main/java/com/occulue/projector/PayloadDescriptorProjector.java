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
 * Projector for PayloadDescriptor as outlined for the CQRS pattern. All event handling and query
 * handling related to PayloadDescriptor are invoked here and dispersed as an event to be handled
 * elsewhere.
 *
 * <p>Commands are handled by PayloadDescriptorAggregate
 *
 * @author your_name_here
 */
// @ProcessingGroup("payloadDescriptor")
@Component("payloadDescriptor-projector")
public class PayloadDescriptorProjector extends PayloadDescriptorEntityProjector {

  // core constructor
  public PayloadDescriptorProjector(
      PayloadDescriptorRepository repository, QueryUpdateEmitter queryUpdateEmitter) {
    super(repository);
    this.queryUpdateEmitter = queryUpdateEmitter;
  }

  /*
   * @param	event CreatePayloadDescriptorEvent
   */
  @EventHandler(payloadType = CreatePayloadDescriptorEvent.class)
  public void handle(CreatePayloadDescriptorEvent event) {
    LOGGER.info("handling CreatePayloadDescriptorEvent - " + event);
    PayloadDescriptor entity = new PayloadDescriptor();
    entity.setPayloadDescriptorId(event.getPayloadDescriptorId());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    create(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllPayloadDescriptor(entity);
  }

  /*
   * @param	event UpdatePayloadDescriptorEvent
   */
  @EventHandler(payloadType = UpdatePayloadDescriptorEvent.class)
  public void handle(UpdatePayloadDescriptorEvent event) {
    LOGGER.info("handling UpdatePayloadDescriptorEvent - " + event);

    PayloadDescriptor entity = new PayloadDescriptor();
    entity.setPayloadDescriptorId(event.getPayloadDescriptorId());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    update(entity);

    // ------------------------------------------
    // emit to subscribers that find one
    // ------------------------------------------
    emitFindPayloadDescriptor(entity);

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllPayloadDescriptor(entity);
  }

  /*
   * @param	event DeletePayloadDescriptorEvent
   */
  @EventHandler(payloadType = DeletePayloadDescriptorEvent.class)
  public void handle(DeletePayloadDescriptorEvent event) {
    LOGGER.info("handling DeletePayloadDescriptorEvent - " + event);

    // ------------------------------------------
    // delete delegation
    // ------------------------------------------
    PayloadDescriptor entity = delete(event.getPayloadDescriptorId());

    // ------------------------------------------
    // emit to subscribers that find all
    // ------------------------------------------
    emitFindAllPayloadDescriptor(entity);
  }

  /**
   * Method to retrieve the PayloadDescriptor via an PayloadDescriptorPrimaryKey.
   *
   * @param id Long
   * @return PayloadDescriptor
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public PayloadDescriptor handle(FindPayloadDescriptorQuery query)
      throws ProcessingException, IllegalArgumentException {
    return find(query.getFilter().getPayloadDescriptorId());
  }

  /**
   * Method to retrieve a collection of all PayloadDescriptors
   *
   * @param query FindAllPayloadDescriptorQuery
   * @return List<PayloadDescriptor>
   * @exception ProcessingException Thrown if any problems
   */
  @SuppressWarnings("unused")
  @QueryHandler
  public List<PayloadDescriptor> handle(FindAllPayloadDescriptorQuery query)
      throws ProcessingException {
    return findAll(query);
  }

  /**
   * emit to subscription queries of type FindPayloadDescriptor, but only if the id matches
   *
   * @param entity PayloadDescriptor
   */
  protected void emitFindPayloadDescriptor(PayloadDescriptor entity) {
    LOGGER.info("handling emitFindPayloadDescriptor");

    queryUpdateEmitter.emit(
        FindPayloadDescriptorQuery.class,
        query -> query.getFilter().getPayloadDescriptorId().equals(entity.getPayloadDescriptorId()),
        entity);
  }

  /**
   * unconditionally emit to subscription queries of type FindAllPayloadDescriptor
   *
   * @param entity PayloadDescriptor
   */
  protected void emitFindAllPayloadDescriptor(PayloadDescriptor entity) {
    LOGGER.info("handling emitFindAllPayloadDescriptor");

    queryUpdateEmitter.emit(FindAllPayloadDescriptorQuery.class, query -> true, entity);
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired private final QueryUpdateEmitter queryUpdateEmitter;
  private static final Logger LOGGER = Logger.getLogger(PayloadDescriptorProjector.class.getName());
}
