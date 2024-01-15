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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Projector for PayloadDescriptor as outlined for the CQRS pattern.
 *
 * <p>Commands are handled by PayloadDescriptorAggregate
 *
 * @author your_name_here
 */
@Component("payloadDescriptor-entity-projector")
public class PayloadDescriptorEntityProjector {

  // core constructor
  public PayloadDescriptorEntityProjector(PayloadDescriptorRepository repository) {
    this.repository = repository;
  }

  /*
   * Insert a PayloadDescriptor
   *
   * @param	entity PayloadDescriptor
   */
  public PayloadDescriptor create(PayloadDescriptor entity) {
    LOGGER.info("creating " + entity.toString());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Update a PayloadDescriptor
   *
   * @param	entity PayloadDescriptor
   */
  public PayloadDescriptor update(PayloadDescriptor entity) {
    LOGGER.info("updating " + entity.toString());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Delete a PayloadDescriptor
   *
   * @param	id		UUID
   */
  public PayloadDescriptor delete(UUID id) {
    LOGGER.info("deleting " + id.toString());

    // ------------------------------------------
    // locate the entity by the provided id
    // ------------------------------------------
    PayloadDescriptor entity = repository.findById(id).get();

    // ------------------------------------------
    // delete what is discovered
    // ------------------------------------------
    repository.delete(entity);

    return entity;
  }

  /**
   * Method to retrieve the PayloadDescriptor via an FindPayloadDescriptorQuery
   *
   * @return query FindPayloadDescriptorQuery
   */
  @SuppressWarnings("unused")
  public PayloadDescriptor find(UUID id) {
    LOGGER.info("handling find using " + id.toString());
    try {
      return repository.findById(id).get();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find a PayloadDescriptor - {0}", exc.getMessage());
    }
    return null;
  }

  /**
   * Method to retrieve a collection of all PayloadDescriptors
   *
   * @param query FindAllPayloadDescriptorQuery
   * @return List<PayloadDescriptor>
   */
  @SuppressWarnings("unused")
  public List<PayloadDescriptor> findAll(FindAllPayloadDescriptorQuery query) {
    LOGGER.info("handling findAll using " + query.toString());
    try {
      return repository.findAll();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find all PayloadDescriptor - {0}", exc.getMessage());
    }
    return null;
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired protected final PayloadDescriptorRepository repository;

  private static final Logger LOGGER =
      Logger.getLogger(PayloadDescriptorEntityProjector.class.getName());
}
