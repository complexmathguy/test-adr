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
 * IntervalPeriod business delegate class.
 *
 * <p>This class implements the Business Delegate design pattern for the purpose of:
 *
 * <ol>
 *   <li>Reducing coupling between the business tier and a client of the business tier by hiding all
 *       business-tier implementation details
 *   <li>Improving the available of IntervalPeriod related services in the case of a IntervalPeriod
 *       business related service failing.
 *   <li>Exposes a simpler, uniform IntervalPeriod interface to the business tier, making it easy
 *       for clients to consume a simple Java object.
 *   <li>Hides the communication protocol that may be required to fulfill IntervalPeriod business
 *       related services.
 * </ol>
 *
 * <p>
 *
 * @author your_name_here
 */
public class IntervalPeriodBusinessDelegate extends BaseBusinessDelegate {
  // ************************************************************************
  // Public Methods
  // ************************************************************************
  /** Default Constructor */
  public IntervalPeriodBusinessDelegate() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
    commandGateway = applicationContext.getBean(CommandGateway.class);
    queryUpdateEmitter = applicationContext.getBean(QueryUpdateEmitter.class);
  }

  /**
   * IntervalPeriod Business Delegate Factory Method
   *
   * <p>All methods are expected to be self-sufficient.
   *
   * @return IntervalPeriodBusinessDelegate
   */
  public static IntervalPeriodBusinessDelegate getIntervalPeriodInstance() {
    return (new IntervalPeriodBusinessDelegate());
  }

  /**
   * Creates the provided command.
   *
   * @param command ${class.getCreateCommandAlias()}
   * @exception ProcessingException
   * @exception IllegalArgumentException
   * @return CompletableFuture<UUID>
   */
  public CompletableFuture<UUID> createIntervalPeriod(CreateIntervalPeriodCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<UUID> completableFuture = null;

    try {
      // --------------------------------------
      // assign identity now if none
      // --------------------------------------
      if (command.getIntervalPeriodId() == null) command.setIntervalPeriodId(UUID.randomUUID());

      // --------------------------------------
      // validate the command
      // --------------------------------------
      IntervalPeriodValidator.getInstance().validate(command);

      // ---------------------------------------
      // issue the CreateIntervalPeriodCommand - by convention the future return value for a create
      // command
      // that is handled by the constructor of an aggregate will return the UUID
      // ---------------------------------------
      completableFuture = commandGateway.send(command);

      LOGGER.log(
          Level.INFO,
          "return from Command Gateway for CreateIntervalPeriodCommand of IntervalPeriod is "
              + command);

    } catch (Exception exc) {
      final String errMsg = "Unable to create IntervalPeriod - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Update the provided command.
   *
   * @param command UpdateIntervalPeriodCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   * @exception IllegalArgumentException
   */
  public CompletableFuture<Void> updateIntervalPeriod(UpdateIntervalPeriodCommand command)
      throws ProcessingException, IllegalArgumentException {
    CompletableFuture<Void> completableFuture = null;

    try {

      // --------------------------------------
      // validate
      // --------------------------------------
      IntervalPeriodValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the UpdateIntervalPeriodCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to save IntervalPeriod - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    }

    return completableFuture;
  }

  /**
   * Deletes the associatied value object
   *
   * @param command DeleteIntervalPeriodCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   */
  public CompletableFuture<Void> delete(DeleteIntervalPeriodCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<Void> completableFuture = null;

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      IntervalPeriodValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the DeleteIntervalPeriodCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg =
          "Unable to delete IntervalPeriod using Id = " + command.getIntervalPeriodId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Method to retrieve the IntervalPeriod via IntervalPeriodFetchOneSummary
   *
   * @param summary IntervalPeriodFetchOneSummary
   * @return IntervalPeriodFetchOneResponse
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  public IntervalPeriod getIntervalPeriod(IntervalPeriodFetchOneSummary summary)
      throws ProcessingException, IllegalArgumentException {

    if (summary == null)
      throw new IllegalArgumentException("IntervalPeriodFetchOneSummary arg cannot be null");

    IntervalPeriod entity = null;

    try {
      // --------------------------------------
      // validate the fetch one summary
      // --------------------------------------
      IntervalPeriodValidator.getInstance().validate(summary);

      // --------------------------------------
      // use queryGateway to send request to Find a IntervalPeriod
      // --------------------------------------
      CompletableFuture<IntervalPeriod> futureEntity =
          queryGateway.query(
              new FindIntervalPeriodQuery(
                  new LoadIntervalPeriodFilter(summary.getIntervalPeriodId())),
              ResponseTypes.instanceOf(IntervalPeriod.class));

      entity = futureEntity.get();
    } catch (Exception exc) {
      final String errMsg =
          "Unable to locate IntervalPeriod with id " + summary.getIntervalPeriodId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return entity;
  }

  /**
   * Method to retrieve a collection of all IntervalPeriods
   *
   * @return List<IntervalPeriod>
   * @exception ProcessingException Thrown if any problems
   */
  public List<IntervalPeriod> getAllIntervalPeriod() throws ProcessingException {
    List<IntervalPeriod> list = null;

    try {
      CompletableFuture<List<IntervalPeriod>> futureList =
          queryGateway.query(
              new FindAllIntervalPeriodQuery(),
              ResponseTypes.multipleInstancesOf(IntervalPeriod.class));

      list = futureList.get();
    } catch (Exception exc) {
      String errMsg = "Failed to get all IntervalPeriod";
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
   * @return IntervalPeriod
   */
  private IntervalPeriod load(UUID id) throws ProcessingException {
    intervalPeriod =
        IntervalPeriodBusinessDelegate.getIntervalPeriodInstance()
            .getIntervalPeriod(new IntervalPeriodFetchOneSummary(id));
    return intervalPeriod;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  private final QueryGateway queryGateway;
  private final CommandGateway commandGateway;
  private final QueryUpdateEmitter queryUpdateEmitter;
  private IntervalPeriod intervalPeriod = null;
  private static final Logger LOGGER =
      Logger.getLogger(IntervalPeriodBusinessDelegate.class.getName());
}
