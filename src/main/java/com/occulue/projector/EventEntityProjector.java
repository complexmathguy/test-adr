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
 * Projector for Event as outlined for the CQRS pattern.
 *
 * <p>Commands are handled by EventAggregate
 *
 * @author your_name_here
 */
@Component("event-entity-projector")
public class EventEntityProjector {

  // core constructor
  public EventEntityProjector(EventRepository repository) {
    this.repository = repository;
  }

  /*
   * Insert a Event
   *
   * @param	entity Event
   */
  public Event create(Event entity) {
    LOGGER.info("creating " + entity.toString());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Update a Event
   *
   * @param	entity Event
   */
  public Event update(Event entity) {
    LOGGER.info("updating " + entity.toString());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Delete a Event
   *
   * @param	id		UUID
   */
  public Event delete(UUID id) {
    LOGGER.info("deleting " + id.toString());

    // ------------------------------------------
    // locate the entity by the provided id
    // ------------------------------------------
    Event entity = repository.findById(id).get();

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
   * @return	Event
   */
  public Event assignProgram(UUID parentId, Program assignment) {
    LOGGER.info("assigning Program as " + assignment.toString());

    Event parentEntity = repository.findById(parentId).get();
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
   * @return	Event
   */
  public Event unAssignProgram(UUID parentId) {
    Event parentEntity = repository.findById(parentId).get();

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
   * @return	Event
   */
  public Event assignTargets(UUID parentId, ValuesMap assignment) {
    LOGGER.info("assigning Targets as " + assignment.toString());

    Event parentEntity = repository.findById(parentId).get();
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
   * @return	Event
   */
  public Event unAssignTargets(UUID parentId) {
    Event parentEntity = repository.findById(parentId).get();

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
   * Assign a IntervalPeriod
   *
   * @param	parentId	UUID
   * @param	assignment 	IntervalPeriod
   * @return	Event
   */
  public Event assignIntervalPeriod(UUID parentId, IntervalPeriod assignment) {
    LOGGER.info("assigning IntervalPeriod as " + assignment.toString());

    Event parentEntity = repository.findById(parentId).get();
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
   * @return	Event
   */
  public Event unAssignIntervalPeriod(UUID parentId) {
    Event parentEntity = repository.findById(parentId).get();

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
   * Add to the ReportDescriptors
   *
   * @param	parentID	UUID
   * @param	addTo		childType
   * @return	Event
   */
  public Event addToReportDescriptors(UUID parentId, ReportDescriptor addTo) {
    LOGGER.info("handling AssignReportDescriptorsToEventEvent - ");

    Event parentEntity = repository.findById(parentId).get();
    ReportDescriptor child = ReportDescriptorProjector.find(addTo.getReportDescriptorId());

    parentEntity.getReportDescriptors().add(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /*
   * Remove from the ReportDescriptors
   *
   * @param	parentID	UUID
   * @param	removeFrom	childType
   * @return	Event
   */
  public Event removeFromReportDescriptors(UUID parentId, ReportDescriptor removeFrom) {
    LOGGER.info("handling RemoveReportDescriptorsFromEventEvent ");

    Event parentEntity = repository.findById(parentId).get();
    ReportDescriptor child = ReportDescriptorProjector.find(removeFrom.getReportDescriptorId());

    parentEntity.getReportDescriptors().remove(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    update(parentEntity);

    return parentEntity;
  }

  /*
   * Add to the PayloadDescriptors
   *
   * @param	parentID	UUID
   * @param	addTo		childType
   * @return	Event
   */
  public Event addToPayloadDescriptors(UUID parentId, PayloadDescriptor addTo) {
    LOGGER.info("handling AssignPayloadDescriptorsToEventEvent - ");

    Event parentEntity = repository.findById(parentId).get();
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
   * @return	Event
   */
  public Event removeFromPayloadDescriptors(UUID parentId, PayloadDescriptor removeFrom) {
    LOGGER.info("handling RemovePayloadDescriptorsFromEventEvent ");

    Event parentEntity = repository.findById(parentId).get();
    PayloadDescriptor child = PayloadDescriptorProjector.find(removeFrom.getPayloadDescriptorId());

    parentEntity.getPayloadDescriptors().remove(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    update(parentEntity);

    return parentEntity;
  }

  /*
   * Add to the Intervals
   *
   * @param	parentID	UUID
   * @param	addTo		childType
   * @return	Event
   */
  public Event addToIntervals(UUID parentId, Interval addTo) {
    LOGGER.info("handling AssignIntervalsToEventEvent - ");

    Event parentEntity = repository.findById(parentId).get();
    Interval child = IntervalProjector.find(addTo.getIntervalId());

    parentEntity.getIntervals().add(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /*
   * Remove from the Intervals
   *
   * @param	parentID	UUID
   * @param	removeFrom	childType
   * @return	Event
   */
  public Event removeFromIntervals(UUID parentId, Interval removeFrom) {
    LOGGER.info("handling RemoveIntervalsFromEventEvent ");

    Event parentEntity = repository.findById(parentId).get();
    Interval child = IntervalProjector.find(removeFrom.getIntervalId());

    parentEntity.getIntervals().remove(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    update(parentEntity);

    return parentEntity;
  }

  /**
   * Method to retrieve the Event via an FindEventQuery
   *
   * @return query FindEventQuery
   */
  @SuppressWarnings("unused")
  public Event find(UUID id) {
    LOGGER.info("handling find using " + id.toString());
    try {
      return repository.findById(id).get();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find a Event - {0}", exc.getMessage());
    }
    return null;
  }

  /**
   * Method to retrieve a collection of all Events
   *
   * @param query FindAllEventQuery
   * @return List<Event>
   */
  @SuppressWarnings("unused")
  public List<Event> findAll(FindAllEventQuery query) {
    LOGGER.info("handling findAll using " + query.toString());
    try {
      return repository.findAll();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find all Event - {0}", exc.getMessage());
    }
    return null;
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired protected final EventRepository repository;

  @Autowired
  @Qualifier(value = "program-entity-projector")
  ProgramEntityProjector ProgramProjector;

  @Autowired
  @Qualifier(value = "valuesMap-entity-projector")
  ValuesMapEntityProjector ValuesMapProjector;

  @Autowired
  @Qualifier(value = "reportDescriptor-entity-projector")
  ReportDescriptorEntityProjector ReportDescriptorProjector;

  @Autowired
  @Qualifier(value = "payloadDescriptor-entity-projector")
  PayloadDescriptorEntityProjector PayloadDescriptorProjector;

  @Autowired
  @Qualifier(value = "interval-entity-projector")
  IntervalEntityProjector IntervalProjector;

  @Autowired
  @Qualifier(value = "intervalPeriod-entity-projector")
  IntervalPeriodEntityProjector IntervalPeriodProjector;

  private static final Logger LOGGER = Logger.getLogger(EventEntityProjector.class.getName());
}
