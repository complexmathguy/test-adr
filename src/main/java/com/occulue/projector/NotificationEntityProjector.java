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
 * Projector for Notification as outlined for the CQRS pattern.
 *
 * <p>Commands are handled by NotificationAggregate
 *
 * @author your_name_here
 */
@Component("notification-entity-projector")
public class NotificationEntityProjector {

  // core constructor
  public NotificationEntityProjector(NotificationRepository repository) {
    this.repository = repository;
  }

  /*
   * Insert a Notification
   *
   * @param	entity Notification
   */
  public Notification create(Notification entity) {
    LOGGER.info("creating " + entity.toString());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Update a Notification
   *
   * @param	entity Notification
   */
  public Notification update(Notification entity) {
    LOGGER.info("updating " + entity.toString());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Delete a Notification
   *
   * @param	id		UUID
   */
  public Notification delete(UUID id) {
    LOGGER.info("deleting " + id.toString());

    // ------------------------------------------
    // locate the entity by the provided id
    // ------------------------------------------
    Notification entity = repository.findById(id).get();

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
   * @return	Notification
   */
  public Notification assignTargets(UUID parentId, ValuesMap assignment) {
    LOGGER.info("assigning Targets as " + assignment.toString());

    Notification parentEntity = repository.findById(parentId).get();
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
   * @return	Notification
   */
  public Notification unAssignTargets(UUID parentId) {
    Notification parentEntity = repository.findById(parentId).get();

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
   * Assign a Notifier
   *
   * @param	parentId	UUID
   * @param	assignment 	Notifier
   * @return	Notification
   */
  public Notification assignNotifier(UUID parentId, Notifier assignment) {
    LOGGER.info("assigning Notifier as " + assignment.toString());

    Notification parentEntity = repository.findById(parentId).get();
    assignment = NotifierProjector.find(assignment.getNotifierId());

    // ------------------------------------------
    // assign the Notifier to the parent entity
    // ------------------------------------------
    parentEntity.setNotifier(assignment);

    // ------------------------------------------
    // save the parent entity
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /*
   * Unassign the Notifier
   *
   * @param	parentId		UUID
   * @return	Notification
   */
  public Notification unAssignNotifier(UUID parentId) {
    Notification parentEntity = repository.findById(parentId).get();

    LOGGER.info("unAssigning Notifier on " + parentEntity.toString());

    // ------------------------------------------
    // null out the Notifier on the parent entithy
    // ------------------------------------------
    parentEntity.setNotifier(null);

    // ------------------------------------------
    // save the parent entity
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /**
   * Method to retrieve the Notification via an FindNotificationQuery
   *
   * @return query FindNotificationQuery
   */
  @SuppressWarnings("unused")
  public Notification find(UUID id) {
    LOGGER.info("handling find using " + id.toString());
    try {
      return repository.findById(id).get();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find a Notification - {0}", exc.getMessage());
    }
    return null;
  }

  /**
   * Method to retrieve a collection of all Notifications
   *
   * @param query FindAllNotificationQuery
   * @return List<Notification>
   */
  @SuppressWarnings("unused")
  public List<Notification> findAll(FindAllNotificationQuery query) {
    LOGGER.info("handling findAll using " + query.toString());
    try {
      return repository.findAll();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find all Notification - {0}", exc.getMessage());
    }
    return null;
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired protected final NotificationRepository repository;

  @Autowired
  @Qualifier(value = "valuesMap-entity-projector")
  ValuesMapEntityProjector ValuesMapProjector;

  @Autowired
  @Qualifier(value = "notifier-entity-projector")
  NotifierEntityProjector NotifierProjector;

  private static final Logger LOGGER =
      Logger.getLogger(NotificationEntityProjector.class.getName());
}
