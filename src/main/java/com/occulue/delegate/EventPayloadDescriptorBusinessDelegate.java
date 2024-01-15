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
 * EventPayloadDescriptor business delegate class.
 *
 * <p>This class implements the Business Delegate design pattern for the purpose of:
 *
 * <ol>
 *   <li>Reducing coupling between the business tier and a client of the business tier by hiding all
 *       business-tier implementation details
 *   <li>Improving the available of EventPayloadDescriptor related services in the case of a
 *       EventPayloadDescriptor business related service failing.
 *   <li>Exposes a simpler, uniform EventPayloadDescriptor interface to the business tier, making it
 *       easy for clients to consume a simple Java object.
 *   <li>Hides the communication protocol that may be required to fulfill EventPayloadDescriptor
 *       business related services.
 * </ol>
 *
 * <p>
 *
 * @author your_name_here
 */
public class EventPayloadDescriptorBusinessDelegate extends PayloadDescriptorBusinessDelegate {
  // ************************************************************************
  // Public Methods
  // ************************************************************************
  /** Default Constructor */
  public EventPayloadDescriptorBusinessDelegate() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
    commandGateway = applicationContext.getBean(CommandGateway.class);
    queryUpdateEmitter = applicationContext.getBean(QueryUpdateEmitter.class);
  }

  /**
   * EventPayloadDescriptor Business Delegate Factory Method
   *
   * <p>All methods are expected to be self-sufficient.
   *
   * @return EventPayloadDescriptorBusinessDelegate
   */
  public static EventPayloadDescriptorBusinessDelegate getEventPayloadDescriptorInstance() {
    return (new EventPayloadDescriptorBusinessDelegate());
  }

  /**
   * Creates the provided command.
   *
   * @param command ${class.getCreateCommandAlias()}
   * @exception ProcessingException
   * @exception IllegalArgumentException
   * @return CompletableFuture<UUID>
   */
  public CompletableFuture<UUID> createEventPayloadDescriptor(
      CreateEventPayloadDescriptorCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<UUID> completableFuture = null;

    try {
      // --------------------------------------
      // assign identity now if none
      // --------------------------------------
      if (command.getEventPayloadDescriptorId() == null)
        command.setEventPayloadDescriptorId(UUID.randomUUID());

      // --------------------------------------
      // validate the command
      // --------------------------------------
      EventPayloadDescriptorValidator.getInstance().validate(command);

      // ---------------------------------------
      // issue the CreateEventPayloadDescriptorCommand - by convention the future return value for a
      // create command
      // that is handled by the constructor of an aggregate will return the UUID
      // ---------------------------------------
      completableFuture = commandGateway.send(command);

      LOGGER.log(
          Level.INFO,
          "return from Command Gateway for CreateEventPayloadDescriptorCommand of EventPayloadDescriptor is "
              + command);

    } catch (Exception exc) {
      final String errMsg = "Unable to create EventPayloadDescriptor - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Update the provided command.
   *
   * @param command UpdateEventPayloadDescriptorCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   * @exception IllegalArgumentException
   */
  public CompletableFuture<Void> updateEventPayloadDescriptor(
      UpdateEventPayloadDescriptorCommand command)
      throws ProcessingException, IllegalArgumentException {
    CompletableFuture<Void> completableFuture = null;

    try {

      // --------------------------------------
      // validate
      // --------------------------------------
      EventPayloadDescriptorValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the UpdateEventPayloadDescriptorCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to save EventPayloadDescriptor - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    }

    return completableFuture;
  }

  /**
   * Deletes the associatied value object
   *
   * @param command DeleteEventPayloadDescriptorCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   */
  public CompletableFuture<Void> delete(DeleteEventPayloadDescriptorCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<Void> completableFuture = null;

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      EventPayloadDescriptorValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the DeleteEventPayloadDescriptorCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg =
          "Unable to delete EventPayloadDescriptor using Id = "
              + command.getEventPayloadDescriptorId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Method to retrieve the EventPayloadDescriptor via EventPayloadDescriptorFetchOneSummary
   *
   * @param summary EventPayloadDescriptorFetchOneSummary
   * @return EventPayloadDescriptorFetchOneResponse
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  public EventPayloadDescriptor getEventPayloadDescriptor(
      EventPayloadDescriptorFetchOneSummary summary)
      throws ProcessingException, IllegalArgumentException {

    if (summary == null)
      throw new IllegalArgumentException(
          "EventPayloadDescriptorFetchOneSummary arg cannot be null");

    EventPayloadDescriptor entity = null;

    try {
      // --------------------------------------
      // validate the fetch one summary
      // --------------------------------------
      EventPayloadDescriptorValidator.getInstance().validate(summary);

      // --------------------------------------
      // use queryGateway to send request to Find a EventPayloadDescriptor
      // --------------------------------------
      CompletableFuture<EventPayloadDescriptor> futureEntity =
          queryGateway.query(
              new FindEventPayloadDescriptorQuery(
                  new LoadEventPayloadDescriptorFilter(summary.getEventPayloadDescriptorId())),
              ResponseTypes.instanceOf(EventPayloadDescriptor.class));

      entity = futureEntity.get();
    } catch (Exception exc) {
      final String errMsg =
          "Unable to locate EventPayloadDescriptor with id "
              + summary.getEventPayloadDescriptorId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return entity;
  }

  /**
   * Method to retrieve a collection of all EventPayloadDescriptors
   *
   * @return List<EventPayloadDescriptor>
   * @exception ProcessingException Thrown if any problems
   */
  public List<EventPayloadDescriptor> getAllEventPayloadDescriptor() throws ProcessingException {
    List<EventPayloadDescriptor> list = null;

    try {
      CompletableFuture<List<EventPayloadDescriptor>> futureList =
          queryGateway.query(
              new FindAllEventPayloadDescriptorQuery(),
              ResponseTypes.multipleInstancesOf(EventPayloadDescriptor.class));

      list = futureList.get();
    } catch (Exception exc) {
      String errMsg = "Failed to get all EventPayloadDescriptor";
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return list;
  }

  /**
   * Internal helper method to load the root
   *
   * @param id UUID
   * @return EventPayloadDescriptor
   */
  private EventPayloadDescriptor load(UUID id) throws ProcessingException {
    eventPayloadDescriptor =
        EventPayloadDescriptorBusinessDelegate.getEventPayloadDescriptorInstance()
            .getEventPayloadDescriptor(new EventPayloadDescriptorFetchOneSummary(id));
    return eventPayloadDescriptor;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  private final QueryGateway queryGateway;
  private final CommandGateway commandGateway;
  private final QueryUpdateEmitter queryUpdateEmitter;
  private EventPayloadDescriptor eventPayloadDescriptor = null;
  private static final Logger LOGGER =
      Logger.getLogger(EventPayloadDescriptorBusinessDelegate.class.getName());
}
