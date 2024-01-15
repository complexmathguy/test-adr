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
package com.occulue.delegate;

import com.occulue.api.*;
import com.occulue.entity.*;
import com.occulue.exception.*;
import com.occulue.validator.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryUpdateEmitter;

/**
 * Event business delegate class.
 *
 * <p>This class implements the Business Delegate design pattern for the purpose of:
 *
 * <ol>
 *   <li>Reducing coupling between the business tier and a client of the business tier by hiding all
 *       business-tier implementation details
 *   <li>Improving the available of Event related services in the case of a Event business related
 *       service failing.
 *   <li>Exposes a simpler, uniform Event interface to the business tier, making it easy for clients
 *       to consume a simple Java object.
 *   <li>Hides the communication protocol that may be required to fulfill Event business related
 *       services.
 * </ol>
 *
 * <p>
 *
 * @author your_name_here
 */
public class EventBusinessDelegate extends NotifierBusinessDelegate {
  // ************************************************************************
  // Public Methods
  // ************************************************************************
  /** Default Constructor */
  public EventBusinessDelegate() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
    commandGateway = applicationContext.getBean(CommandGateway.class);
    queryUpdateEmitter = applicationContext.getBean(QueryUpdateEmitter.class);
  }

  /**
   * Event Business Delegate Factory Method
   *
   * <p>All methods are expected to be self-sufficient.
   *
   * @return EventBusinessDelegate
   */
  public static EventBusinessDelegate getEventInstance() {
    return (new EventBusinessDelegate());
  }

  /**
   * Creates the provided command.
   *
   * @param command ${class.getCreateCommandAlias()}
   * @exception ProcessingException
   * @exception IllegalArgumentException
   * @return CompletableFuture<UUID>
   */
  public CompletableFuture<UUID> createEvent(CreateEventCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<UUID> completableFuture = null;

    try {
      // --------------------------------------
      // assign identity now if none
      // --------------------------------------
      if (command.getEventId() == null) command.setEventId(UUID.randomUUID());

      // --------------------------------------
      // validate the command
      // --------------------------------------
      EventValidator.getInstance().validate(command);

      // ---------------------------------------
      // issue the CreateEventCommand - by convention the future return value for a create command
      // that is handled by the constructor of an aggregate will return the UUID
      // ---------------------------------------
      completableFuture = commandGateway.send(command);

      LOGGER.log(
          Level.INFO, "return from Command Gateway for CreateEventCommand of Event is " + command);

    } catch (Exception exc) {
      final String errMsg = "Unable to create Event - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Update the provided command.
   *
   * @param command UpdateEventCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   * @exception IllegalArgumentException
   */
  public CompletableFuture<Void> updateEvent(UpdateEventCommand command)
      throws ProcessingException, IllegalArgumentException {
    CompletableFuture<Void> completableFuture = null;

    try {

      // --------------------------------------
      // validate
      // --------------------------------------
      EventValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the UpdateEventCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to save Event - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    }

    return completableFuture;
  }

  /**
   * Deletes the associatied value object
   *
   * @param command DeleteEventCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   */
  public CompletableFuture<Void> delete(DeleteEventCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<Void> completableFuture = null;

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      EventValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the DeleteEventCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to delete Event using Id = " + command.getEventId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Method to retrieve the Event via EventFetchOneSummary
   *
   * @param summary EventFetchOneSummary
   * @return EventFetchOneResponse
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  public Event getEvent(EventFetchOneSummary summary)
      throws ProcessingException, IllegalArgumentException {

    if (summary == null)
      throw new IllegalArgumentException("EventFetchOneSummary arg cannot be null");

    Event entity = null;

    try {
      // --------------------------------------
      // validate the fetch one summary
      // --------------------------------------
      EventValidator.getInstance().validate(summary);

      // --------------------------------------
      // use queryGateway to send request to Find a Event
      // --------------------------------------
      CompletableFuture<Event> futureEntity =
          queryGateway.query(
              new FindEventQuery(new LoadEventFilter(summary.getEventId())),
              ResponseTypes.instanceOf(Event.class));

      entity = futureEntity.get();
    } catch (Exception exc) {
      final String errMsg = "Unable to locate Event with id " + summary.getEventId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return entity;
  }

  /**
   * Method to retrieve a collection of all Events
   *
   * @return List<Event>
   * @exception ProcessingException Thrown if any problems
   */
  public List<Event> getAllEvent() throws ProcessingException {
    List<Event> list = null;

    try {
      CompletableFuture<List<Event>> futureList =
          queryGateway.query(
              new FindAllEventQuery(), ResponseTypes.multipleInstancesOf(Event.class));

      list = futureList.get();
    } catch (Exception exc) {
      String errMsg = "Failed to get all Event";
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return list;
  }

  /**
   * assign Program on Event
   *
   * @param command AssignProgramToEventCommand
   * @exception ProcessingException
   */
  public void assignProgram(AssignProgramToEventCommand command) throws ProcessingException {

    // --------------------------------------------
    // load the parent
    // --------------------------------------------
    load(command.getEventId());

    ProgramBusinessDelegate childDelegate = ProgramBusinessDelegate.getProgramInstance();
    EventBusinessDelegate parentDelegate = EventBusinessDelegate.getEventInstance();
    UUID childId = command.getAssignment().getProgramId();
    Program child = null;

    try {
      // --------------------------------------
      // best to validate the command now
      // --------------------------------------
      EventValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);

    } catch (Throwable exc) {
      final String msg = "Failed to get Program using id " + childId;
      LOGGER.log(Level.WARNING, msg);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * unAssign Program on Event
   *
   * @param command UnAssignProgramFromEventCommand
   * @exception ProcessingException
   */
  public void unAssignProgram(UnAssignProgramFromEventCommand command) throws ProcessingException {

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      EventValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to unassign Program on Event";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * assign Targets on Event
   *
   * @param command AssignTargetsToEventCommand
   * @exception ProcessingException
   */
  public void assignTargets(AssignTargetsToEventCommand command) throws ProcessingException {

    // --------------------------------------------
    // load the parent
    // --------------------------------------------
    load(command.getEventId());

    ValuesMapBusinessDelegate childDelegate = ValuesMapBusinessDelegate.getValuesMapInstance();
    EventBusinessDelegate parentDelegate = EventBusinessDelegate.getEventInstance();
    UUID childId = command.getAssignment().getValuesMapId();
    ValuesMap child = null;

    try {
      // --------------------------------------
      // best to validate the command now
      // --------------------------------------
      EventValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);

    } catch (Throwable exc) {
      final String msg = "Failed to get ValuesMap using id " + childId;
      LOGGER.log(Level.WARNING, msg);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * unAssign Targets on Event
   *
   * @param command UnAssignTargetsFromEventCommand
   * @exception ProcessingException
   */
  public void unAssignTargets(UnAssignTargetsFromEventCommand command) throws ProcessingException {

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      EventValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to unassign Targets on Event";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * assign IntervalPeriod on Event
   *
   * @param command AssignIntervalPeriodToEventCommand
   * @exception ProcessingException
   */
  public void assignIntervalPeriod(AssignIntervalPeriodToEventCommand command)
      throws ProcessingException {

    // --------------------------------------------
    // load the parent
    // --------------------------------------------
    load(command.getEventId());

    IntervalPeriodBusinessDelegate childDelegate =
        IntervalPeriodBusinessDelegate.getIntervalPeriodInstance();
    EventBusinessDelegate parentDelegate = EventBusinessDelegate.getEventInstance();
    UUID childId = command.getAssignment().getIntervalPeriodId();
    IntervalPeriod child = null;

    try {
      // --------------------------------------
      // best to validate the command now
      // --------------------------------------
      EventValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);

    } catch (Throwable exc) {
      final String msg = "Failed to get IntervalPeriod using id " + childId;
      LOGGER.log(Level.WARNING, msg);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * unAssign IntervalPeriod on Event
   *
   * @param command UnAssignIntervalPeriodFromEventCommand
   * @exception ProcessingException
   */
  public void unAssignIntervalPeriod(UnAssignIntervalPeriodFromEventCommand command)
      throws ProcessingException {

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      EventValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to unassign IntervalPeriod on Event";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * add ReportDescriptor to ReportDescriptors
   *
   * @param command AssignReportDescriptorsToEventCommand
   * @exception ProcessingException
   */
  public void addToReportDescriptors(AssignReportDescriptorsToEventCommand command)
      throws ProcessingException {

    // -------------------------------------------
    // load the parent
    // -------------------------------------------
    load(command.getEventId());

    ReportDescriptorBusinessDelegate childDelegate =
        ReportDescriptorBusinessDelegate.getReportDescriptorInstance();
    EventBusinessDelegate parentDelegate = EventBusinessDelegate.getEventInstance();
    UUID childId = command.getAddTo().getReportDescriptorId();

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      EventValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to add a ReportDescriptor as ReportDescriptors to Event";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * remove ReportDescriptor from ReportDescriptors
   *
   * @param command RemoveReportDescriptorsFromEventCommand
   * @exception ProcessingException
   */
  public void removeFromReportDescriptors(RemoveReportDescriptorsFromEventCommand command)
      throws ProcessingException {

    ReportDescriptorBusinessDelegate childDelegate =
        ReportDescriptorBusinessDelegate.getReportDescriptorInstance();
    UUID childId = command.getRemoveFrom().getReportDescriptorId();

    try {

      // --------------------------------------
      // validate the command
      // --------------------------------------
      EventValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);

    } catch (Exception exc) {
      final String msg = "Failed to remove child using Id " + childId;
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * add PayloadDescriptor to PayloadDescriptors
   *
   * @param command AssignPayloadDescriptorsToEventCommand
   * @exception ProcessingException
   */
  public void addToPayloadDescriptors(AssignPayloadDescriptorsToEventCommand command)
      throws ProcessingException {

    // -------------------------------------------
    // load the parent
    // -------------------------------------------
    load(command.getEventId());

    PayloadDescriptorBusinessDelegate childDelegate =
        PayloadDescriptorBusinessDelegate.getPayloadDescriptorInstance();
    EventBusinessDelegate parentDelegate = EventBusinessDelegate.getEventInstance();
    UUID childId = command.getAddTo().getPayloadDescriptorId();

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      EventValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to add a PayloadDescriptor as PayloadDescriptors to Event";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * remove PayloadDescriptor from PayloadDescriptors
   *
   * @param command RemovePayloadDescriptorsFromEventCommand
   * @exception ProcessingException
   */
  public void removeFromPayloadDescriptors(RemovePayloadDescriptorsFromEventCommand command)
      throws ProcessingException {

    PayloadDescriptorBusinessDelegate childDelegate =
        PayloadDescriptorBusinessDelegate.getPayloadDescriptorInstance();
    UUID childId = command.getRemoveFrom().getPayloadDescriptorId();

    try {

      // --------------------------------------
      // validate the command
      // --------------------------------------
      EventValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);

    } catch (Exception exc) {
      final String msg = "Failed to remove child using Id " + childId;
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * add Interval to Intervals
   *
   * @param command AssignIntervalsToEventCommand
   * @exception ProcessingException
   */
  public void addToIntervals(AssignIntervalsToEventCommand command) throws ProcessingException {

    // -------------------------------------------
    // load the parent
    // -------------------------------------------
    load(command.getEventId());

    IntervalBusinessDelegate childDelegate = IntervalBusinessDelegate.getIntervalInstance();
    EventBusinessDelegate parentDelegate = EventBusinessDelegate.getEventInstance();
    UUID childId = command.getAddTo().getIntervalId();

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      EventValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to add a Interval as Intervals to Event";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * remove Interval from Intervals
   *
   * @param command RemoveIntervalsFromEventCommand
   * @exception ProcessingException
   */
  public void removeFromIntervals(RemoveIntervalsFromEventCommand command)
      throws ProcessingException {

    IntervalBusinessDelegate childDelegate = IntervalBusinessDelegate.getIntervalInstance();
    UUID childId = command.getRemoveFrom().getIntervalId();

    try {

      // --------------------------------------
      // validate the command
      // --------------------------------------
      EventValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);

    } catch (Exception exc) {
      final String msg = "Failed to remove child using Id " + childId;
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * Internal helper method to load the root
   *
   * @param id UUID
   * @return Event
   */
  private Event load(UUID id) throws ProcessingException {
    event = EventBusinessDelegate.getEventInstance().getEvent(new EventFetchOneSummary(id));
    return event;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  private final QueryGateway queryGateway;
  private final CommandGateway commandGateway;
  private final QueryUpdateEmitter queryUpdateEmitter;
  private Event event = null;
  private static final Logger LOGGER = Logger.getLogger(EventBusinessDelegate.class.getName());
}
