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
 * Projector for Program as outlined for the CQRS pattern.
 *
 * <p>Commands are handled by ProgramAggregate
 *
 * @author your_name_here
 */
@Component("program-entity-projector")
public class ProgramEntityProjector {

  // core constructor
  public ProgramEntityProjector(ProgramRepository repository) {
    this.repository = repository;
  }

  /*
   * Insert a Program
   *
   * @param	entity Program
   */
  public Program create(Program entity) {
    LOGGER.info("creating " + entity.toString());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Update a Program
   *
   * @param	entity Program
   */
  public Program update(Program entity) {
    LOGGER.info("updating " + entity.toString());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Delete a Program
   *
   * @param	id		UUID
   */
  public Program delete(UUID id) {
    LOGGER.info("deleting " + id.toString());

    // ------------------------------------------
    // locate the entity by the provided id
    // ------------------------------------------
    Program entity = repository.findById(id).get();

    // ------------------------------------------
    // delete what is discovered
    // ------------------------------------------
    repository.delete(entity);

    return entity;
  }

  /*
   * Assign a IntervalPeriod
   *
   * @param	parentId	UUID
   * @param	assignment 	IntervalPeriod
   * @return	Program
   */
  public Program assignIntervalPeriod(UUID parentId, IntervalPeriod assignment) {
    LOGGER.info("assigning IntervalPeriod as " + assignment.toString());

    Program parentEntity = repository.findById(parentId).get();
    assignment = IntervalPeriodProjector.find(assignment.getIntervalPeriodId());

    // ------------------------------------------
    // assign the IntervalPeriod to the parent entity
    // ------------------------------------------
    parentEntity.setIntervalPeriod(assignment);

    // ------------------------------------------
    // save the parent entity
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /*
   * Unassign the IntervalPeriod
   *
   * @param	parentId		UUID
   * @return	Program
   */
  public Program unAssignIntervalPeriod(UUID parentId) {
    Program parentEntity = repository.findById(parentId).get();

    LOGGER.info("unAssigning IntervalPeriod on " + parentEntity.toString());

    // ------------------------------------------
    // null out the IntervalPeriod on the parent entithy
    // ------------------------------------------
    parentEntity.setIntervalPeriod(null);

    // ------------------------------------------
    // save the parent entity
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /*
   * Add to the PayloadDescriptors
   *
   * @param	parentID	UUID
   * @param	addTo		childType
   * @return	Program
   */
  public Program addToPayloadDescriptors(UUID parentId, PayloadDescriptor addTo) {
    LOGGER.info("handling AssignPayloadDescriptorsToProgramEvent - ");

    Program parentEntity = repository.findById(parentId).get();
    PayloadDescriptor child = PayloadDescriptorProjector.find(addTo.getPayloadDescriptorId());

    parentEntity.getPayloadDescriptors().add(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /*
   * Remove from the PayloadDescriptors
   *
   * @param	parentID	UUID
   * @param	removeFrom	childType
   * @return	Program
   */
  public Program removeFromPayloadDescriptors(UUID parentId, PayloadDescriptor removeFrom) {
    LOGGER.info("handling RemovePayloadDescriptorsFromProgramEvent ");

    Program parentEntity = repository.findById(parentId).get();
    PayloadDescriptor child = PayloadDescriptorProjector.find(removeFrom.getPayloadDescriptorId());

    parentEntity.getPayloadDescriptors().remove(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    update(parentEntity);

    return parentEntity;
  }

  /*
   * Add to the Targets
   *
   * @param	parentID	UUID
   * @param	addTo		childType
   * @return	Program
   */
  public Program addToTargets(UUID parentId, ValuesMap addTo) {
    LOGGER.info("handling AssignTargetsToProgramEvent - ");

    Program parentEntity = repository.findById(parentId).get();
    ValuesMap child = ValuesMapProjector.find(addTo.getValuesMapId());

    parentEntity.getTargets().add(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /*
   * Remove from the Targets
   *
   * @param	parentID	UUID
   * @param	removeFrom	childType
   * @return	Program
   */
  public Program removeFromTargets(UUID parentId, ValuesMap removeFrom) {
    LOGGER.info("handling RemoveTargetsFromProgramEvent ");

    Program parentEntity = repository.findById(parentId).get();
    ValuesMap child = ValuesMapProjector.find(removeFrom.getValuesMapId());

    parentEntity.getTargets().remove(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    update(parentEntity);

    return parentEntity;
  }

  /**
   * Method to retrieve the Program via an FindProgramQuery
   *
   * @return query FindProgramQuery
   */
  @SuppressWarnings("unused")
  public Program find(UUID id) {
    LOGGER.info("handling find using " + id.toString());
    try {
      return repository.findById(id).get();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find a Program - {0}", exc.getMessage());
    }
    return null;
  }

  /**
   * Method to retrieve a collection of all Programs
   *
   * @param query FindAllProgramQuery
   * @return List<Program>
   */
  @SuppressWarnings("unused")
  public List<Program> findAll(FindAllProgramQuery query) {
    LOGGER.info("handling findAll using " + query.toString());
    try {
      return repository.findAll();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find all Program - {0}", exc.getMessage());
    }
    return null;
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired protected final ProgramRepository repository;

  @Autowired
  @Qualifier(value = "payloadDescriptor-entity-projector")
  PayloadDescriptorEntityProjector PayloadDescriptorProjector;

  @Autowired
  @Qualifier(value = "valuesMap-entity-projector")
  ValuesMapEntityProjector ValuesMapProjector;

  @Autowired
  @Qualifier(value = "intervalPeriod-entity-projector")
  IntervalPeriodEntityProjector IntervalPeriodProjector;

  private static final Logger LOGGER = Logger.getLogger(ProgramEntityProjector.class.getName());
}
