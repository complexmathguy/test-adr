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
 * Projector for Problem as outlined for the CQRS pattern.
 *
 * <p>Commands are handled by ProblemAggregate
 *
 * @author your_name_here
 */
@Component("problem-entity-projector")
public class ProblemEntityProjector {

  // core constructor
  public ProblemEntityProjector(ProblemRepository repository) {
    this.repository = repository;
  }

  /*
   * Insert a Problem
   *
   * @param	entity Problem
   */
  public Problem create(Problem entity) {
    LOGGER.info("creating " + entity.toString());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Update a Problem
   *
   * @param	entity Problem
   */
  public Problem update(Problem entity) {
    LOGGER.info("updating " + entity.toString());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Delete a Problem
   *
   * @param	id		UUID
   */
  public Problem delete(UUID id) {
    LOGGER.info("deleting " + id.toString());

    // ------------------------------------------
    // locate the entity by the provided id
    // ------------------------------------------
    Problem entity = repository.findById(id).get();

    // ------------------------------------------
    // delete what is discovered
    // ------------------------------------------
    repository.delete(entity);

    return entity;
  }

  /**
   * Method to retrieve the Problem via an FindProblemQuery
   *
   * @return query FindProblemQuery
   */
  @SuppressWarnings("unused")
  public Problem find(UUID id) {
    LOGGER.info("handling find using " + id.toString());
    try {
      return repository.findById(id).get();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find a Problem - {0}", exc.getMessage());
    }
    return null;
  }

  /**
   * Method to retrieve a collection of all Problems
   *
   * @param query FindAllProblemQuery
   * @return List<Problem>
   */
  @SuppressWarnings("unused")
  public List<Problem> findAll(FindAllProblemQuery query) {
    LOGGER.info("handling findAll using " + query.toString());
    try {
      return repository.findAll();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find all Problem - {0}", exc.getMessage());
    }
    return null;
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired protected final ProblemRepository repository;

  private static final Logger LOGGER = Logger.getLogger(ProblemEntityProjector.class.getName());
}
