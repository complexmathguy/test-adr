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
 * Projector for Resource as outlined for the CQRS pattern.
 *
 * <p>Commands are handled by ResourceAggregate
 *
 * @author your_name_here
 */
@Component("resource-entity-projector")
public class ResourceEntityProjector {

  // core constructor
  public ResourceEntityProjector(ResourceRepository repository) {
    this.repository = repository;
  }

  /*
   * Insert a Resource
   *
   * @param	entity Resource
   */
  public Resource create(Resource entity) {
    LOGGER.info("creating " + entity.toString());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Update a Resource
   *
   * @param	entity Resource
   */
  public Resource update(Resource entity) {
    LOGGER.info("updating " + entity.toString());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Delete a Resource
   *
   * @param	id		UUID
   */
  public Resource delete(UUID id) {
    LOGGER.info("deleting " + id.toString());

    // ------------------------------------------
    // locate the entity by the provided id
    // ------------------------------------------
    Resource entity = repository.findById(id).get();

    // ------------------------------------------
    // delete what is discovered
    // ------------------------------------------
    repository.delete(entity);

    return entity;
  }

  /*
   * Assign a Ven
   *
   * @param	parentId	UUID
   * @param	assignment 	Ven
   * @return	Resource
   */
  public Resource assignVen(UUID parentId, Ven assignment) {
    LOGGER.info("assigning Ven as " + assignment.toString());

    Resource parentEntity = repository.findById(parentId).get();
    assignment = VenProjector.find(assignment.getVenId());

    // ------------------------------------------
    // assign the Ven to the parent entity
    // ------------------------------------------
    parentEntity.setVen(assignment);

    // ------------------------------------------
    // save the parent entity
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /*
   * Unassign the Ven
   *
   * @param	parentId		UUID
   * @return	Resource
   */
  public Resource unAssignVen(UUID parentId) {
    Resource parentEntity = repository.findById(parentId).get();

    LOGGER.info("unAssigning Ven on " + parentEntity.toString());

    // ------------------------------------------
    // null out the Ven on the parent entithy
    // ------------------------------------------
    parentEntity.setVen(null);

    // ------------------------------------------
    // save the parent entity
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /*
   * Add to the Attributes
   *
   * @param	parentID	UUID
   * @param	addTo		childType
   * @return	Resource
   */
  public Resource addToAttributes(UUID parentId, ValuesMap addTo) {
    LOGGER.info("handling AssignAttributesToResourceEvent - ");

    Resource parentEntity = repository.findById(parentId).get();
    ValuesMap child = ValuesMapProjector.find(addTo.getValuesMapId());

    parentEntity.getAttributes().add(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /*
   * Remove from the Attributes
   *
   * @param	parentID	UUID
   * @param	removeFrom	childType
   * @return	Resource
   */
  public Resource removeFromAttributes(UUID parentId, ValuesMap removeFrom) {
    LOGGER.info("handling RemoveAttributesFromResourceEvent ");

    Resource parentEntity = repository.findById(parentId).get();
    ValuesMap child = ValuesMapProjector.find(removeFrom.getValuesMapId());

    parentEntity.getAttributes().remove(child);

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
   * @return	Resource
   */
  public Resource addToTargets(UUID parentId, ValuesMap addTo) {
    LOGGER.info("handling AssignTargetsToResourceEvent - ");

    Resource parentEntity = repository.findById(parentId).get();
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
   * @return	Resource
   */
  public Resource removeFromTargets(UUID parentId, ValuesMap removeFrom) {
    LOGGER.info("handling RemoveTargetsFromResourceEvent ");

    Resource parentEntity = repository.findById(parentId).get();
    ValuesMap child = ValuesMapProjector.find(removeFrom.getValuesMapId());

    parentEntity.getTargets().remove(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    update(parentEntity);

    return parentEntity;
  }

  /**
   * Method to retrieve the Resource via an FindResourceQuery
   *
   * @return query FindResourceQuery
   */
  @SuppressWarnings("unused")
  public Resource find(UUID id) {
    LOGGER.info("handling find using " + id.toString());
    try {
      return repository.findById(id).get();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find a Resource - {0}", exc.getMessage());
    }
    return null;
  }

  /**
   * Method to retrieve a collection of all Resources
   *
   * @param query FindAllResourceQuery
   * @return List<Resource>
   */
  @SuppressWarnings("unused")
  public List<Resource> findAll(FindAllResourceQuery query) {
    LOGGER.info("handling findAll using " + query.toString());
    try {
      return repository.findAll();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find all Resource - {0}", exc.getMessage());
    }
    return null;
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired protected final ResourceRepository repository;

  @Autowired
  @Qualifier(value = "ven-entity-projector")
  VenEntityProjector VenProjector;

  @Autowired
  @Qualifier(value = "valuesMap-entity-projector")
  ValuesMapEntityProjector ValuesMapProjector;

  private static final Logger LOGGER = Logger.getLogger(ResourceEntityProjector.class.getName());
}
