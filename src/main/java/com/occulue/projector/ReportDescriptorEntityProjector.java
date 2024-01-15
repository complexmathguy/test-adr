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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Projector for ReportDescriptor as outlined for the CQRS pattern.
 *
 * <p>Commands are handled by ReportDescriptorAggregate
 *
 * @author your_name_here
 */
@Component("reportDescriptor-entity-projector")
public class ReportDescriptorEntityProjector {

  // core constructor
  public ReportDescriptorEntityProjector(ReportDescriptorRepository repository) {
    this.repository = repository;
  }

  /*
   * Insert a ReportDescriptor
   *
   * @param	entity ReportDescriptor
   */
  public ReportDescriptor create(ReportDescriptor entity) {
    LOGGER.info("creating " + entity.toString());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Update a ReportDescriptor
   *
   * @param	entity ReportDescriptor
   */
  public ReportDescriptor update(ReportDescriptor entity) {
    LOGGER.info("updating " + entity.toString());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Delete a ReportDescriptor
   *
   * @param	id		UUID
   */
  public ReportDescriptor delete(UUID id) {
    LOGGER.info("deleting " + id.toString());

    // ------------------------------------------
    // locate the entity by the provided id
    // ------------------------------------------
    ReportDescriptor entity = repository.findById(id).get();

    // ------------------------------------------
    // delete what is discovered
    // ------------------------------------------
    repository.delete(entity);

    return entity;
  }

  /*
   * Assign a Targets
   *
   * @param	parentId	UUID
   * @param	assignment 	ValuesMap
   * @return	ReportDescriptor
   */
  public ReportDescriptor assignTargets(UUID parentId, ValuesMap assignment) {
    LOGGER.info("assigning Targets as " + assignment.toString());

    ReportDescriptor parentEntity = repository.findById(parentId).get();
    assignment = ValuesMapProjector.find(assignment.getValuesMapId());

    // ------------------------------------------
    // assign the Targets to the parent entity
    // ------------------------------------------
    parentEntity.setTargets(assignment);

    // ------------------------------------------
    // save the parent entity
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /*
   * Unassign the Targets
   *
   * @param	parentId		UUID
   * @return	ReportDescriptor
   */
  public ReportDescriptor unAssignTargets(UUID parentId) {
    ReportDescriptor parentEntity = repository.findById(parentId).get();

    LOGGER.info("unAssigning Targets on " + parentEntity.toString());

    // ------------------------------------------
    // null out the Targets on the parent entithy
    // ------------------------------------------
    parentEntity.setTargets(null);

    // ------------------------------------------
    // save the parent entity
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /**
   * Method to retrieve the ReportDescriptor via an FindReportDescriptorQuery
   *
   * @return query FindReportDescriptorQuery
   */
  @SuppressWarnings("unused")
  public ReportDescriptor find(UUID id) {
    LOGGER.info("handling find using " + id.toString());
    try {
      return repository.findById(id).get();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find a ReportDescriptor - {0}", exc.getMessage());
    }
    return null;
  }

  /**
   * Method to retrieve a collection of all ReportDescriptors
   *
   * @param query FindAllReportDescriptorQuery
   * @return List<ReportDescriptor>
   */
  @SuppressWarnings("unused")
  public List<ReportDescriptor> findAll(FindAllReportDescriptorQuery query) {
    LOGGER.info("handling findAll using " + query.toString());
    try {
      return repository.findAll();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find all ReportDescriptor - {0}", exc.getMessage());
    }
    return null;
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired protected final ReportDescriptorRepository repository;

  @Autowired
  @Qualifier(value = "valuesMap-entity-projector")
  ValuesMapEntityProjector ValuesMapProjector;

  private static final Logger LOGGER =
      Logger.getLogger(ReportDescriptorEntityProjector.class.getName());
}
