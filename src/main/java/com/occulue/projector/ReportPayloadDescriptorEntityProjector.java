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
 * Projector for ReportPayloadDescriptor as outlined for the CQRS pattern.
 *
 * <p>Commands are handled by ReportPayloadDescriptorAggregate
 *
 * @author your_name_here
 */
@Component("reportPayloadDescriptor-entity-projector")
public class ReportPayloadDescriptorEntityProjector {

  // core constructor
  public ReportPayloadDescriptorEntityProjector(ReportPayloadDescriptorRepository repository) {
    this.repository = repository;
  }

  /*
   * Insert a ReportPayloadDescriptor
   *
   * @param	entity ReportPayloadDescriptor
   */
  public ReportPayloadDescriptor create(ReportPayloadDescriptor entity) {
    LOGGER.info("creating " + entity.toString());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Update a ReportPayloadDescriptor
   *
   * @param	entity ReportPayloadDescriptor
   */
  public ReportPayloadDescriptor update(ReportPayloadDescriptor entity) {
    LOGGER.info("updating " + entity.toString());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Delete a ReportPayloadDescriptor
   *
   * @param	id		UUID
   */
  public ReportPayloadDescriptor delete(UUID id) {
    LOGGER.info("deleting " + id.toString());

    // ------------------------------------------
    // locate the entity by the provided id
    // ------------------------------------------
    ReportPayloadDescriptor entity = repository.findById(id).get();

    // ------------------------------------------
    // delete what is discovered
    // ------------------------------------------
    repository.delete(entity);

    return entity;
  }

  /**
   * Method to retrieve the ReportPayloadDescriptor via an FindReportPayloadDescriptorQuery
   *
   * @return query FindReportPayloadDescriptorQuery
   */
  @SuppressWarnings("unused")
  public ReportPayloadDescriptor find(UUID id) {
    LOGGER.info("handling find using " + id.toString());
    try {
      return repository.findById(id).get();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find a ReportPayloadDescriptor - {0}", exc.getMessage());
    }
    return null;
  }

  /**
   * Method to retrieve a collection of all ReportPayloadDescriptors
   *
   * @param query FindAllReportPayloadDescriptorQuery
   * @return List<ReportPayloadDescriptor>
   */
  @SuppressWarnings("unused")
  public List<ReportPayloadDescriptor> findAll(FindAllReportPayloadDescriptorQuery query) {
    LOGGER.info("handling findAll using " + query.toString());
    try {
      return repository.findAll();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(
          Level.WARNING, "Failed to find all ReportPayloadDescriptor - {0}", exc.getMessage());
    }
    return null;
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired protected final ReportPayloadDescriptorRepository repository;

  private static final Logger LOGGER =
      Logger.getLogger(ReportPayloadDescriptorEntityProjector.class.getName());
}
