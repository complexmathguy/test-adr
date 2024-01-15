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
 * Projector for Ven as outlined for the CQRS pattern.
 *
 * <p>Commands are handled by VenAggregate
 *
 * @author your_name_here
 */
@Component("ven-entity-projector")
public class VenEntityProjector {

  // core constructor
  public VenEntityProjector(VenRepository repository) {
    this.repository = repository;
  }

  /*
   * Insert a Ven
   *
   * @param	entity Ven
   */
  public Ven create(Ven entity) {
    LOGGER.info("creating " + entity.toString());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Update a Ven
   *
   * @param	entity Ven
   */
  public Ven update(Ven entity) {
    LOGGER.info("updating " + entity.toString());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Delete a Ven
   *
   * @param	id		UUID
   */
  public Ven delete(UUID id) {
    LOGGER.info("deleting " + id.toString());

    // ------------------------------------------
    // locate the entity by the provided id
    // ------------------------------------------
    Ven entity = repository.findById(id).get();

    // ------------------------------------------
    // delete what is discovered
    // ------------------------------------------
    repository.delete(entity);

    return entity;
  }

  /*
   * Add to the Attributes
   *
   * @param	parentID	UUID
   * @param	addTo		childType
   * @return	Ven
   */
  public Ven addToAttributes(UUID parentId, ValuesMap addTo) {
    LOGGER.info("handling AssignAttributesToVenEvent - ");

    Ven parentEntity = repository.findById(parentId).get();
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
   * @return	Ven
   */
  public Ven removeFromAttributes(UUID parentId, ValuesMap removeFrom) {
    LOGGER.info("handling RemoveAttributesFromVenEvent ");

    Ven parentEntity = repository.findById(parentId).get();
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
   * @return	Ven
   */
  public Ven addToTargets(UUID parentId, ValuesMap addTo) {
    LOGGER.info("handling AssignTargetsToVenEvent - ");

    Ven parentEntity = repository.findById(parentId).get();
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
   * @return	Ven
   */
  public Ven removeFromTargets(UUID parentId, ValuesMap removeFrom) {
    LOGGER.info("handling RemoveTargetsFromVenEvent ");

    Ven parentEntity = repository.findById(parentId).get();
    ValuesMap child = ValuesMapProjector.find(removeFrom.getValuesMapId());

    parentEntity.getTargets().remove(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    update(parentEntity);

    return parentEntity;
  }

  /*
   * Add to the Resources
   *
   * @param	parentID	UUID
   * @param	addTo		childType
   * @return	Ven
   */
  public Ven addToResources(UUID parentId, Resource addTo) {
    LOGGER.info("handling AssignResourcesToVenEvent - ");

    Ven parentEntity = repository.findById(parentId).get();
    Resource child = ResourceProjector.find(addTo.getResourceId());

    parentEntity.getResources().add(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /*
   * Remove from the Resources
   *
   * @param	parentID	UUID
   * @param	removeFrom	childType
   * @return	Ven
   */
  public Ven removeFromResources(UUID parentId, Resource removeFrom) {
    LOGGER.info("handling RemoveResourcesFromVenEvent ");

    Ven parentEntity = repository.findById(parentId).get();
    Resource child = ResourceProjector.find(removeFrom.getResourceId());

    parentEntity.getResources().remove(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    update(parentEntity);

    return parentEntity;
  }

  /**
   * Method to retrieve the Ven via an FindVenQuery
   *
   * @return query FindVenQuery
   */
  @SuppressWarnings("unused")
  public Ven find(UUID id) {
    LOGGER.info("handling find using " + id.toString());
    try {
      return repository.findById(id).get();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find a Ven - {0}", exc.getMessage());
    }
    return null;
  }

  /**
   * Method to retrieve a collection of all Vens
   *
   * @param query FindAllVenQuery
   * @return List<Ven>
   */
  @SuppressWarnings("unused")
  public List<Ven> findAll(FindAllVenQuery query) {
    LOGGER.info("handling findAll using " + query.toString());
    try {
      return repository.findAll();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find all Ven - {0}", exc.getMessage());
    }
    return null;
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired protected final VenRepository repository;

  @Autowired
  @Qualifier(value = "valuesMap-entity-projector")
  ValuesMapEntityProjector ValuesMapProjector;

  @Autowired
  @Qualifier(value = "resource-entity-projector")
  ResourceEntityProjector ResourceProjector;

  private static final Logger LOGGER = Logger.getLogger(VenEntityProjector.class.getName());
}
