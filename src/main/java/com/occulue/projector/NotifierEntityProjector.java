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
 * Projector for Notifier as outlined for the CQRS pattern.
 *
 * <p>Commands are handled by NotifierAggregate
 *
 * @author your_name_here
 */
@Component("notifier-entity-projector")
public class NotifierEntityProjector {

  // core constructor
  public NotifierEntityProjector(NotifierRepository repository) {
    this.repository = repository;
  }

  /*
   * Insert a Notifier
   *
   * @param	entity Notifier
   */
  public Notifier create(Notifier entity) {
    LOGGER.info("creating " + entity.toString());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Update a Notifier
   *
   * @param	entity Notifier
   */
  public Notifier update(Notifier entity) {
    LOGGER.info("updating " + entity.toString());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Delete a Notifier
   *
   * @param	id		UUID
   */
  public Notifier delete(UUID id) {
    LOGGER.info("deleting " + id.toString());

    // ------------------------------------------
    // locate the entity by the provided id
    // ------------------------------------------
    Notifier entity = repository.findById(id).get();

    // ------------------------------------------
    // delete what is discovered
    // ------------------------------------------
    repository.delete(entity);

    return entity;
  }

  /**
   * Method to retrieve the Notifier via an FindNotifierQuery
   *
   * @return query FindNotifierQuery
   */
  @SuppressWarnings("unused")
  public Notifier find(UUID id) {
    LOGGER.info("handling find using " + id.toString());
    try {
      return repository.findById(id).get();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find a Notifier - {0}", exc.getMessage());
    }
    return null;
  }

  /**
   * Method to retrieve a collection of all Notifiers
   *
   * @param query FindAllNotifierQuery
   * @return List<Notifier>
   */
  @SuppressWarnings("unused")
  public List<Notifier> findAll(FindAllNotifierQuery query) {
    LOGGER.info("handling findAll using " + query.toString());
    try {
      return repository.findAll();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find all Notifier - {0}", exc.getMessage());
    }
    return null;
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired protected final NotifierRepository repository;

  @Autowired
  @Qualifier(value = "notification-entity-projector")
  NotificationEntityProjector NotificationProjector;

  private static final Logger LOGGER = Logger.getLogger(NotifierEntityProjector.class.getName());
}
