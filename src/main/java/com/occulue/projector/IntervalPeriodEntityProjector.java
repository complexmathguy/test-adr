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
 * Projector for IntervalPeriod as outlined for the CQRS pattern.
 *
 * <p>Commands are handled by IntervalPeriodAggregate
 *
 * @author your_name_here
 */
@Component("intervalPeriod-entity-projector")
public class IntervalPeriodEntityProjector {

  // core constructor
  public IntervalPeriodEntityProjector(IntervalPeriodRepository repository) {
    this.repository = repository;
  }

  /*
   * Insert a IntervalPeriod
   *
   * @param	entity IntervalPeriod
   */
  public IntervalPeriod create(IntervalPeriod entity) {
    LOGGER.info("creating " + entity.toString());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Update a IntervalPeriod
   *
   * @param	entity IntervalPeriod
   */
  public IntervalPeriod update(IntervalPeriod entity) {
    LOGGER.info("updating " + entity.toString());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Delete a IntervalPeriod
   *
   * @param	id		UUID
   */
  public IntervalPeriod delete(UUID id) {
    LOGGER.info("deleting " + id.toString());

    // ------------------------------------------
    // locate the entity by the provided id
    // ------------------------------------------
    IntervalPeriod entity = repository.findById(id).get();

    // ------------------------------------------
    // delete what is discovered
    // ------------------------------------------
    repository.delete(entity);

    return entity;
  }

  /**
   * Method to retrieve the IntervalPeriod via an FindIntervalPeriodQuery
   *
   * @return query FindIntervalPeriodQuery
   */
  @SuppressWarnings("unused")
  public IntervalPeriod find(UUID id) {
    LOGGER.info("handling find using " + id.toString());
    try {
      return repository.findById(id).get();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find a IntervalPeriod - {0}", exc.getMessage());
    }
    return null;
  }

  /**
   * Method to retrieve a collection of all IntervalPeriods
   *
   * @param query FindAllIntervalPeriodQuery
   * @return List<IntervalPeriod>
   */
  @SuppressWarnings("unused")
  public List<IntervalPeriod> findAll(FindAllIntervalPeriodQuery query) {
    LOGGER.info("handling findAll using " + query.toString());
    try {
      return repository.findAll();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find all IntervalPeriod - {0}", exc.getMessage());
    }
    return null;
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired protected final IntervalPeriodRepository repository;

  @Autowired
  @Qualifier(value = "program-entity-projector")
  ProgramEntityProjector ProgramProjector;

  private static final Logger LOGGER =
      Logger.getLogger(IntervalPeriodEntityProjector.class.getName());
}
