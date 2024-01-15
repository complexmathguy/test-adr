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
 * Projector for Subscription as outlined for the CQRS pattern.
 *
 * <p>Commands are handled by SubscriptionAggregate
 *
 * @author your_name_here
 */
@Component("subscription-entity-projector")
public class SubscriptionEntityProjector {

  // core constructor
  public SubscriptionEntityProjector(SubscriptionRepository repository) {
    this.repository = repository;
  }

  /*
   * Insert a Subscription
   *
   * @param	entity Subscription
   */
  public Subscription create(Subscription entity) {
    LOGGER.info("creating " + entity.toString());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Update a Subscription
   *
   * @param	entity Subscription
   */
  public Subscription update(Subscription entity) {
    LOGGER.info("updating " + entity.toString());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Delete a Subscription
   *
   * @param	id		UUID
   */
  public Subscription delete(UUID id) {
    LOGGER.info("deleting " + id.toString());

    // ------------------------------------------
    // locate the entity by the provided id
    // ------------------------------------------
    Subscription entity = repository.findById(id).get();

    // ------------------------------------------
    // delete what is discovered
    // ------------------------------------------
    repository.delete(entity);

    return entity;
  }

  /*
   * Assign a Program
   *
   * @param	parentId	UUID
   * @param	assignment 	Program
   * @return	Subscription
   */
  public Subscription assignProgram(UUID parentId, Program assignment) {
    LOGGER.info("assigning Program as " + assignment.toString());

    Subscription parentEntity = repository.findById(parentId).get();
    assignment = ProgramProjector.find(assignment.getProgramId());

    // ------------------------------------------
    // assign the Program to the parent entity
    // ------------------------------------------
    parentEntity.setProgram(assignment);

    // ------------------------------------------
    // save the parent entity
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /*
   * Unassign the Program
   *
   * @param	parentId		UUID
   * @return	Subscription
   */
  public Subscription unAssignProgram(UUID parentId) {
    Subscription parentEntity = repository.findById(parentId).get();

    LOGGER.info("unAssigning Program on " + parentEntity.toString());

    // ------------------------------------------
    // null out the Program on the parent entithy
    // ------------------------------------------
    parentEntity.setProgram(null);

    // ------------------------------------------
    // save the parent entity
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /*
   * Assign a Targets
   *
   * @param	parentId	UUID
   * @param	assignment 	ValuesMap
   * @return	Subscription
   */
  public Subscription assignTargets(UUID parentId, ValuesMap assignment) {
    LOGGER.info("assigning Targets as " + assignment.toString());

    Subscription parentEntity = repository.findById(parentId).get();
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
   * @return	Subscription
   */
  public Subscription unAssignTargets(UUID parentId) {
    Subscription parentEntity = repository.findById(parentId).get();

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

  /*
   * Add to the ObjectOperations
   *
   * @param	parentID	UUID
   * @param	addTo		childType
   * @return	Subscription
   */
  public Subscription addToObjectOperations(UUID parentId, ObjectOperation addTo) {
    LOGGER.info("handling AssignObjectOperationsToSubscriptionEvent - ");

    Subscription parentEntity = repository.findById(parentId).get();
    ObjectOperation child = ObjectOperationProjector.find(addTo.getObjectOperationId());

    parentEntity.getObjectOperations().add(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /*
   * Remove from the ObjectOperations
   *
   * @param	parentID	UUID
   * @param	removeFrom	childType
   * @return	Subscription
   */
  public Subscription removeFromObjectOperations(UUID parentId, ObjectOperation removeFrom) {
    LOGGER.info("handling RemoveObjectOperationsFromSubscriptionEvent ");

    Subscription parentEntity = repository.findById(parentId).get();
    ObjectOperation child = ObjectOperationProjector.find(removeFrom.getObjectOperationId());

    parentEntity.getObjectOperations().remove(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    update(parentEntity);

    return parentEntity;
  }

  /**
   * Method to retrieve the Subscription via an FindSubscriptionQuery
   *
   * @return query FindSubscriptionQuery
   */
  @SuppressWarnings("unused")
  public Subscription find(UUID id) {
    LOGGER.info("handling find using " + id.toString());
    try {
      return repository.findById(id).get();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find a Subscription - {0}", exc.getMessage());
    }
    return null;
  }

  /**
   * Method to retrieve a collection of all Subscriptions
   *
   * @param query FindAllSubscriptionQuery
   * @return List<Subscription>
   */
  @SuppressWarnings("unused")
  public List<Subscription> findAll(FindAllSubscriptionQuery query) {
    LOGGER.info("handling findAll using " + query.toString());
    try {
      return repository.findAll();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find all Subscription - {0}", exc.getMessage());
    }
    return null;
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired protected final SubscriptionRepository repository;

  @Autowired
  @Qualifier(value = "program-entity-projector")
  ProgramEntityProjector ProgramProjector;

  @Autowired
  @Qualifier(value = "objectOperation-entity-projector")
  ObjectOperationEntityProjector ObjectOperationProjector;

  @Autowired
  @Qualifier(value = "valuesMap-entity-projector")
  ValuesMapEntityProjector ValuesMapProjector;

  private static final Logger LOGGER =
      Logger.getLogger(SubscriptionEntityProjector.class.getName());
}
