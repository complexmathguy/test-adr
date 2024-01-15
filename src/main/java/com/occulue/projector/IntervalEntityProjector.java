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
 * Projector for Interval as outlined for the CQRS pattern.
 *
 * <p>Commands are handled by IntervalAggregate
 *
 * @author your_name_here
 */
@Component("interval-entity-projector")
public class IntervalEntityProjector {

  // core constructor
  public IntervalEntityProjector(IntervalRepository repository) {
    this.repository = repository;
  }

  /*
   * Insert a Interval
   *
   * @param	entity Interval
   */
  public Interval create(Interval entity) {
    LOGGER.info("creating " + entity.toString());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Update a Interval
   *
   * @param	entity Interval
   */
  public Interval update(Interval entity) {
    LOGGER.info("updating " + entity.toString());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Delete a Interval
   *
   * @param	id		UUID
   */
  public Interval delete(UUID id) {
    LOGGER.info("deleting " + id.toString());

    // ------------------------------------------
    // locate the entity by the provided id
    // ------------------------------------------
    Interval entity = repository.findById(id).get();

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
   * @return	Interval
   */
  public Interval assignIntervalPeriod(UUID parentId, IntervalPeriod assignment) {
    LOGGER.info("assigning IntervalPeriod as " + assignment.toString());

    Interval parentEntity = repository.findById(parentId).get();
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
   * @return	Interval
   */
  public Interval unAssignIntervalPeriod(UUID parentId) {
    Interval parentEntity = repository.findById(parentId).get();

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
   * Add to the Payloads
   *
   * @param	parentID	UUID
   * @param	addTo		childType
   * @return	Interval
   */
  public Interval addToPayloads(UUID parentId, ValuesMap addTo) {
    LOGGER.info("handling AssignPayloadsToIntervalEvent - ");

    Interval parentEntity = repository.findById(parentId).get();
    ValuesMap child = ValuesMapProjector.find(addTo.getValuesMapId());

    parentEntity.getPayloads().add(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /*
   * Remove from the Payloads
   *
   * @param	parentID	UUID
   * @param	removeFrom	childType
   * @return	Interval
   */
  public Interval removeFromPayloads(UUID parentId, ValuesMap removeFrom) {
    LOGGER.info("handling RemovePayloadsFromIntervalEvent ");

    Interval parentEntity = repository.findById(parentId).get();
    ValuesMap child = ValuesMapProjector.find(removeFrom.getValuesMapId());

    parentEntity.getPayloads().remove(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    update(parentEntity);

    return parentEntity;
  }

  /**
   * Method to retrieve the Interval via an FindIntervalQuery
   *
   * @return query FindIntervalQuery
   */
  @SuppressWarnings("unused")
  public Interval find(UUID id) {
    LOGGER.info("handling find using " + id.toString());
    try {
      return repository.findById(id).get();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find a Interval - {0}", exc.getMessage());
    }
    return null;
  }

  /**
   * Method to retrieve a collection of all Intervals
   *
   * @param query FindAllIntervalQuery
   * @return List<Interval>
   */
  @SuppressWarnings("unused")
  public List<Interval> findAll(FindAllIntervalQuery query) {
    LOGGER.info("handling findAll using " + query.toString());
    try {
      return repository.findAll();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find all Interval - {0}", exc.getMessage());
    }
    return null;
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired protected final IntervalRepository repository;

  @Autowired
  @Qualifier(value = "valuesMap-entity-projector")
  ValuesMapEntityProjector ValuesMapProjector;

  @Autowired
  @Qualifier(value = "intervalPeriod-entity-projector")
  IntervalPeriodEntityProjector IntervalPeriodProjector;

  private static final Logger LOGGER = Logger.getLogger(IntervalEntityProjector.class.getName());
}
