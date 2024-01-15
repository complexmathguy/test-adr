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
 * Notifier business delegate class.
 *
 * <p>This class implements the Business Delegate design pattern for the purpose of:
 *
 * <ol>
 *   <li>Reducing coupling between the business tier and a client of the business tier by hiding all
 *       business-tier implementation details
 *   <li>Improving the available of Notifier related services in the case of a Notifier business
 *       related service failing.
 *   <li>Exposes a simpler, uniform Notifier interface to the business tier, making it easy for
 *       clients to consume a simple Java object.
 *   <li>Hides the communication protocol that may be required to fulfill Notifier business related
 *       services.
 * </ol>
 *
 * <p>
 *
 * @author your_name_here
 */
public class NotifierBusinessDelegate extends BaseBusinessDelegate {
  // ************************************************************************
  // Public Methods
  // ************************************************************************
  /** Default Constructor */
  public NotifierBusinessDelegate() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
    commandGateway = applicationContext.getBean(CommandGateway.class);
    queryUpdateEmitter = applicationContext.getBean(QueryUpdateEmitter.class);
  }

  /**
   * Notifier Business Delegate Factory Method
   *
   * <p>All methods are expected to be self-sufficient.
   *
   * @return NotifierBusinessDelegate
   */
  public static NotifierBusinessDelegate getNotifierInstance() {
    return (new NotifierBusinessDelegate());
  }

  /**
   * Creates the provided command.
   *
   * @param command ${class.getCreateCommandAlias()}
   * @exception ProcessingException
   * @exception IllegalArgumentException
   * @return CompletableFuture<UUID>
   */
  public CompletableFuture<UUID> createNotifier(CreateNotifierCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<UUID> completableFuture = null;

    try {
      // --------------------------------------
      // assign identity now if none
      // --------------------------------------
      if (command.getNotifierId() == null) command.setNotifierId(UUID.randomUUID());

      // --------------------------------------
      // validate the command
      // --------------------------------------
      NotifierValidator.getInstance().validate(command);

      // ---------------------------------------
      // issue the CreateNotifierCommand - by convention the future return value for a create
      // command
      // that is handled by the constructor of an aggregate will return the UUID
      // ---------------------------------------
      completableFuture = commandGateway.send(command);

      LOGGER.log(
          Level.INFO,
          "return from Command Gateway for CreateNotifierCommand of Notifier is " + command);

    } catch (Exception exc) {
      final String errMsg = "Unable to create Notifier - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Update the provided command.
   *
   * @param command UpdateNotifierCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   * @exception IllegalArgumentException
   */
  public CompletableFuture<Void> updateNotifier(UpdateNotifierCommand command)
      throws ProcessingException, IllegalArgumentException {
    CompletableFuture<Void> completableFuture = null;

    try {

      // --------------------------------------
      // validate
      // --------------------------------------
      NotifierValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the UpdateNotifierCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to save Notifier - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    }

    return completableFuture;
  }

  /**
   * Deletes the associatied value object
   *
   * @param command DeleteNotifierCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   */
  public CompletableFuture<Void> delete(DeleteNotifierCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<Void> completableFuture = null;

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      NotifierValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the DeleteNotifierCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to delete Notifier using Id = " + command.getNotifierId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Method to retrieve the Notifier via NotifierFetchOneSummary
   *
   * @param summary NotifierFetchOneSummary
   * @return NotifierFetchOneResponse
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  public Notifier getNotifier(NotifierFetchOneSummary summary)
      throws ProcessingException, IllegalArgumentException {

    if (summary == null)
      throw new IllegalArgumentException("NotifierFetchOneSummary arg cannot be null");

    Notifier entity = null;

    try {
      // --------------------------------------
      // validate the fetch one summary
      // --------------------------------------
      NotifierValidator.getInstance().validate(summary);

      // --------------------------------------
      // use queryGateway to send request to Find a Notifier
      // --------------------------------------
      CompletableFuture<Notifier> futureEntity =
          queryGateway.query(
              new FindNotifierQuery(new LoadNotifierFilter(summary.getNotifierId())),
              ResponseTypes.instanceOf(Notifier.class));

      entity = futureEntity.get();
    } catch (Exception exc) {
      final String errMsg = "Unable to locate Notifier with id " + summary.getNotifierId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return entity;
  }

  /**
   * Method to retrieve a collection of all Notifiers
   *
   * @return List<Notifier>
   * @exception ProcessingException Thrown if any problems
   */
  public List<Notifier> getAllNotifier() throws ProcessingException {
    List<Notifier> list = null;

    try {
      CompletableFuture<List<Notifier>> futureList =
          queryGateway.query(
              new FindAllNotifierQuery(), ResponseTypes.multipleInstancesOf(Notifier.class));

      list = futureList.get();
    } catch (Exception exc) {
      String errMsg = "Failed to get all Notifier";
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
   * @return Notifier
   */
  private Notifier load(UUID id) throws ProcessingException {
    notifier =
        NotifierBusinessDelegate.getNotifierInstance().getNotifier(new NotifierFetchOneSummary(id));
    return notifier;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  private final QueryGateway queryGateway;
  private final CommandGateway commandGateway;
  private final QueryUpdateEmitter queryUpdateEmitter;
  private Notifier notifier = null;
  private static final Logger LOGGER = Logger.getLogger(NotifierBusinessDelegate.class.getName());
}
