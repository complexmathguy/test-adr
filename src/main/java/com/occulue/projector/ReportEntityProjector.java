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
 * Projector for Report as outlined for the CQRS pattern.
 *
 * <p>Commands are handled by ReportAggregate
 *
 * @author your_name_here
 */
@Component("report-entity-projector")
public class ReportEntityProjector {

  // core constructor
  public ReportEntityProjector(ReportRepository repository) {
    this.repository = repository;
  }

  /*
   * Insert a Report
   *
   * @param	entity Report
   */
  public Report create(Report entity) {
    LOGGER.info("creating " + entity.toString());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Update a Report
   *
   * @param	entity Report
   */
  public Report update(Report entity) {
    LOGGER.info("updating " + entity.toString());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Delete a Report
   *
   * @param	id		UUID
   */
  public Report delete(UUID id) {
    LOGGER.info("deleting " + id.toString());

    // ------------------------------------------
    // locate the entity by the provided id
    // ------------------------------------------
    Report entity = repository.findById(id).get();

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
   * @return	Report
   */
  public Report assignProgram(UUID parentId, Program assignment) {
    LOGGER.info("assigning Program as " + assignment.toString());

    Report parentEntity = repository.findById(parentId).get();
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
   * @return	Report
   */
  public Report unAssignProgram(UUID parentId) {
    Report parentEntity = repository.findById(parentId).get();

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
   * Assign a Event
   *
   * @param	parentId	UUID
   * @param	assignment 	Event
   * @return	Report
   */
  public Report assignEvent(UUID parentId, Event assignment) {
    LOGGER.info("assigning Event as " + assignment.toString());

    Report parentEntity = repository.findById(parentId).get();
    assignment = EventProjector.find(assignment.getEventId());

    // ------------------------------------------
    // assign the Event to the parent entity
    // ------------------------------------------
    parentEntity.setEvent(assignment);

    // ------------------------------------------
    // save the parent entity
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /*
   * Unassign the Event
   *
   * @param	parentId		UUID
   * @return	Report
   */
  public Report unAssignEvent(UUID parentId) {
    Report parentEntity = repository.findById(parentId).get();

    LOGGER.info("unAssigning Event on " + parentEntity.toString());

    // ------------------------------------------
    // null out the Event on the parent entithy
    // ------------------------------------------
    parentEntity.setEvent(null);

    // ------------------------------------------
    // save the parent entity
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /*
   * Assign a Intervals
   *
   * @param	parentId	UUID
   * @param	assignment 	Interval
   * @return	Report
   */
  public Report assignIntervals(UUID parentId, Interval assignment) {
    LOGGER.info("assigning Intervals as " + assignment.toString());

    Report parentEntity = repository.findById(parentId).get();
    assignment = IntervalProjector.find(assignment.getIntervalId());

    // ------------------------------------------
    // assign the Intervals to the parent entity
    // ------------------------------------------
    parentEntity.setIntervals(assignment);

    // ------------------------------------------
    // save the parent entity
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /*
   * Unassign the Intervals
   *
   * @param	parentId		UUID
   * @return	Report
   */
  public Report unAssignIntervals(UUID parentId) {
    Report parentEntity = repository.findById(parentId).get();

    LOGGER.info("unAssigning Intervals on " + parentEntity.toString());

    // ------------------------------------------
    // null out the Intervals on the parent entithy
    // ------------------------------------------
    parentEntity.setIntervals(null);

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
   * @return	Report
   */
  public Report assignIntervalPeriod(UUID parentId, IntervalPeriod assignment) {
    LOGGER.info("assigning IntervalPeriod as " + assignment.toString());

    Report parentEntity = repository.findById(parentId).get();
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
   * @return	Report
   */
  public Report unAssignIntervalPeriod(UUID parentId) {
    Report parentEntity = repository.findById(parentId).get();

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
   * @return	Report
   */
  public Report addToPayloadDescriptors(UUID parentId, PayloadDescriptor addTo) {
    LOGGER.info("handling AssignPayloadDescriptorsToReportEvent - ");

    Report parentEntity = repository.findById(parentId).get();
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
   * @return	Report
   */
  public Report removeFromPayloadDescriptors(UUID parentId, PayloadDescriptor removeFrom) {
    LOGGER.info("handling RemovePayloadDescriptorsFromReportEvent ");

    Report parentEntity = repository.findById(parentId).get();
    PayloadDescriptor child = PayloadDescriptorProjector.find(removeFrom.getPayloadDescriptorId());

    parentEntity.getPayloadDescriptors().remove(child);

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
   * @return	Report
   */
  public Report addToResources(UUID parentId, Resource addTo) {
    LOGGER.info("handling AssignResourcesToReportEvent - ");

    Report parentEntity = repository.findById(parentId).get();
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
   * @return	Report
   */
  public Report removeFromResources(UUID parentId, Resource removeFrom) {
    LOGGER.info("handling RemoveResourcesFromReportEvent ");

    Report parentEntity = repository.findById(parentId).get();
    Resource child = ResourceProjector.find(removeFrom.getResourceId());

    parentEntity.getResources().remove(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    update(parentEntity);

    return parentEntity;
  }

  /**
   * Method to retrieve the Report via an FindReportQuery
   *
   * @return query FindReportQuery
   */
  @SuppressWarnings("unused")
  public Report find(UUID id) {
    LOGGER.info("handling find using " + id.toString());
    try {
      return repository.findById(id).get();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find a Report - {0}", exc.getMessage());
    }
    return null;
  }

  /**
   * Method to retrieve a collection of all Reports
   *
   * @param query FindAllReportQuery
   * @return List<Report>
   */
  @SuppressWarnings("unused")
  public List<Report> findAll(FindAllReportQuery query) {
    LOGGER.info("handling findAll using " + query.toString());
    try {
      return repository.findAll();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find all Report - {0}", exc.getMessage());
    }
    return null;
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired protected final ReportRepository repository;

  @Autowired
  @Qualifier(value = "program-entity-projector")
  ProgramEntityProjector ProgramProjector;

  @Autowired
  @Qualifier(value = "event-entity-projector")
  EventEntityProjector EventProjector;

  @Autowired
  @Qualifier(value = "payloadDescriptor-entity-projector")
  PayloadDescriptorEntityProjector PayloadDescriptorProjector;

  @Autowired
  @Qualifier(value = "resource-entity-projector")
  ResourceEntityProjector ResourceProjector;

  @Autowired
  @Qualifier(value = "interval-entity-projector")
  IntervalEntityProjector IntervalProjector;

  @Autowired
  @Qualifier(value = "intervalPeriod-entity-projector")
  IntervalPeriodEntityProjector IntervalPeriodProjector;

  private static final Logger LOGGER = Logger.getLogger(ReportEntityProjector.class.getName());
}
