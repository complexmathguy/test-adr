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
 * Interval business delegate class.
 *
 * <p>This class implements the Business Delegate design pattern for the purpose of:
 *
 * <ol>
 *   <li>Reducing coupling between the business tier and a client of the business tier by hiding all
 *       business-tier implementation details
 *   <li>Improving the available of Interval related services in the case of a Interval business
 *       related service failing.
 *   <li>Exposes a simpler, uniform Interval interface to the business tier, making it easy for
 *       clients to consume a simple Java object.
 *   <li>Hides the communication protocol that may be required to fulfill Interval business related
 *       services.
 * </ol>
 *
 * <p>
 *
 * @author your_name_here
 */
public class IntervalBusinessDelegate extends BaseBusinessDelegate {
  // ************************************************************************
  // Public Methods
  // ************************************************************************
  /** Default Constructor */
  public IntervalBusinessDelegate() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
    commandGateway = applicationContext.getBean(CommandGateway.class);
    queryUpdateEmitter = applicationContext.getBean(QueryUpdateEmitter.class);
  }

  /**
   * Interval Business Delegate Factory Method
   *
   * <p>All methods are expected to be self-sufficient.
   *
   * @return IntervalBusinessDelegate
   */
  public static IntervalBusinessDelegate getIntervalInstance() {
    return (new IntervalBusinessDelegate());
  }

  /**
   * Creates the provided command.
   *
   * @param command ${class.getCreateCommandAlias()}
   * @exception ProcessingException
   * @exception IllegalArgumentException
   * @return CompletableFuture<UUID>
   */
  public CompletableFuture<UUID> createInterval(CreateIntervalCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<UUID> completableFuture = null;

    try {
      // --------------------------------------
      // assign identity now if none
      // --------------------------------------
      if (command.getIntervalId() == null) command.setIntervalId(UUID.randomUUID());

      // --------------------------------------
      // validate the command
      // --------------------------------------
      IntervalValidator.getInstance().validate(command);

      // ---------------------------------------
      // issue the CreateIntervalCommand - by convention the future return value for a create
      // command
      // that is handled by the constructor of an aggregate will return the UUID
      // ---------------------------------------
      completableFuture = commandGateway.send(command);

      LOGGER.log(
          Level.INFO,
          "return from Command Gateway for CreateIntervalCommand of Interval is " + command);

    } catch (Exception exc) {
      final String errMsg = "Unable to create Interval - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Update the provided command.
   *
   * @param command UpdateIntervalCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   * @exception IllegalArgumentException
   */
  public CompletableFuture<Void> updateInterval(UpdateIntervalCommand command)
      throws ProcessingException, IllegalArgumentException {
    CompletableFuture<Void> completableFuture = null;

    try {

      // --------------------------------------
      // validate
      // --------------------------------------
      IntervalValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the UpdateIntervalCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to save Interval - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    }

    return completableFuture;
  }

  /**
   * Deletes the associatied value object
   *
   * @param command DeleteIntervalCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   */
  public CompletableFuture<Void> delete(DeleteIntervalCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<Void> completableFuture = null;

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      IntervalValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the DeleteIntervalCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to delete Interval using Id = " + command.getIntervalId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Method to retrieve the Interval via IntervalFetchOneSummary
   *
   * @param summary IntervalFetchOneSummary
   * @return IntervalFetchOneResponse
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  public Interval getInterval(IntervalFetchOneSummary summary)
      throws ProcessingException, IllegalArgumentException {

    if (summary == null)
      throw new IllegalArgumentException("IntervalFetchOneSummary arg cannot be null");

    Interval entity = null;

    try {
      // --------------------------------------
      // validate the fetch one summary
      // --------------------------------------
      IntervalValidator.getInstance().validate(summary);

      // --------------------------------------
      // use queryGateway to send request to Find a Interval
      // --------------------------------------
      CompletableFuture<Interval> futureEntity =
          queryGateway.query(
              new FindIntervalQuery(new LoadIntervalFilter(summary.getIntervalId())),
              ResponseTypes.instanceOf(Interval.class));

      entity = futureEntity.get();
    } catch (Exception exc) {
      final String errMsg = "Unable to locate Interval with id " + summary.getIntervalId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return entity;
  }

  /**
   * Method to retrieve a collection of all Intervals
   *
   * @return List<Interval>
   * @exception ProcessingException Thrown if any problems
   */
  public List<Interval> getAllInterval() throws ProcessingException {
    List<Interval> list = null;

    try {
      CompletableFuture<List<Interval>> futureList =
          queryGateway.query(
              new FindAllIntervalQuery(), ResponseTypes.multipleInstancesOf(Interval.class));

      list = futureList.get();
    } catch (Exception exc) {
      String errMsg = "Failed to get all Interval";
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return list;
  }

  /**
   * assign IntervalPeriod on Interval
   *
   * @param command AssignIntervalPeriodToIntervalCommand
   * @exception ProcessingException
   */
  public void assignIntervalPeriod(AssignIntervalPeriodToIntervalCommand command)
      throws ProcessingException {

    // --------------------------------------------
    // load the parent
    // --------------------------------------------
    load(command.getIntervalId());

    IntervalPeriodBusinessDelegate childDelegate =
        IntervalPeriodBusinessDelegate.getIntervalPeriodInstance();
    IntervalBusinessDelegate parentDelegate = IntervalBusinessDelegate.getIntervalInstance();
    UUID childId = command.getAssignment().getIntervalPeriodId();
    IntervalPeriod child = null;

    try {
      // --------------------------------------
      // best to validate the command now
      // --------------------------------------
      IntervalValidator.getInstance().validate(command);

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
   * unAssign IntervalPeriod on Interval
   *
   * @param command UnAssignIntervalPeriodFromIntervalCommand
   * @exception ProcessingException
   */
  public void unAssignIntervalPeriod(UnAssignIntervalPeriodFromIntervalCommand command)
      throws ProcessingException {

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      IntervalValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to unassign IntervalPeriod on Interval";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * add ValuesMap to Payloads
   *
   * @param command AssignPayloadsToIntervalCommand
   * @exception ProcessingException
   */
  public void addToPayloads(AssignPayloadsToIntervalCommand command) throws ProcessingException {

    // -------------------------------------------
    // load the parent
    // -------------------------------------------
    load(command.getIntervalId());

    ValuesMapBusinessDelegate childDelegate = ValuesMapBusinessDelegate.getValuesMapInstance();
    IntervalBusinessDelegate parentDelegate = IntervalBusinessDelegate.getIntervalInstance();
    UUID childId = command.getAddTo().getValuesMapId();

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      IntervalValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to add a ValuesMap as Payloads to Interval";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * remove ValuesMap from Payloads
   *
   * @param command RemovePayloadsFromIntervalCommand
   * @exception ProcessingException
   */
  public void removeFromPayloads(RemovePayloadsFromIntervalCommand command)
      throws ProcessingException {

    ValuesMapBusinessDelegate childDelegate = ValuesMapBusinessDelegate.getValuesMapInstance();
    UUID childId = command.getRemoveFrom().getValuesMapId();

    try {

      // --------------------------------------
      // validate the command
      // --------------------------------------
      IntervalValidator.getInstance().validate(command);

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
   * @return Interval
   */
  private Interval load(UUID id) throws ProcessingException {
    interval =
        IntervalBusinessDelegate.getIntervalInstance().getInterval(new IntervalFetchOneSummary(id));
    return interval;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  private final QueryGateway queryGateway;
  private final CommandGateway commandGateway;
  private final QueryUpdateEmitter queryUpdateEmitter;
  private Interval interval = null;
  private static final Logger LOGGER = Logger.getLogger(IntervalBusinessDelegate.class.getName());
}
