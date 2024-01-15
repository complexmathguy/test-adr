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
 * Subscription business delegate class.
 *
 * <p>This class implements the Business Delegate design pattern for the purpose of:
 *
 * <ol>
 *   <li>Reducing coupling between the business tier and a client of the business tier by hiding all
 *       business-tier implementation details
 *   <li>Improving the available of Subscription related services in the case of a Subscription
 *       business related service failing.
 *   <li>Exposes a simpler, uniform Subscription interface to the business tier, making it easy for
 *       clients to consume a simple Java object.
 *   <li>Hides the communication protocol that may be required to fulfill Subscription business
 *       related services.
 * </ol>
 *
 * <p>
 *
 * @author your_name_here
 */
public class SubscriptionBusinessDelegate extends NotifierBusinessDelegate {
  // ************************************************************************
  // Public Methods
  // ************************************************************************
  /** Default Constructor */
  public SubscriptionBusinessDelegate() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
    commandGateway = applicationContext.getBean(CommandGateway.class);
    queryUpdateEmitter = applicationContext.getBean(QueryUpdateEmitter.class);
  }

  /**
   * Subscription Business Delegate Factory Method
   *
   * <p>All methods are expected to be self-sufficient.
   *
   * @return SubscriptionBusinessDelegate
   */
  public static SubscriptionBusinessDelegate getSubscriptionInstance() {
    return (new SubscriptionBusinessDelegate());
  }

  /**
   * Creates the provided command.
   *
   * @param command ${class.getCreateCommandAlias()}
   * @exception ProcessingException
   * @exception IllegalArgumentException
   * @return CompletableFuture<UUID>
   */
  public CompletableFuture<UUID> createSubscription(CreateSubscriptionCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<UUID> completableFuture = null;

    try {
      // --------------------------------------
      // assign identity now if none
      // --------------------------------------
      if (command.getSubscriptionId() == null) command.setSubscriptionId(UUID.randomUUID());

      // --------------------------------------
      // validate the command
      // --------------------------------------
      SubscriptionValidator.getInstance().validate(command);

      // ---------------------------------------
      // issue the CreateSubscriptionCommand - by convention the future return value for a create
      // command
      // that is handled by the constructor of an aggregate will return the UUID
      // ---------------------------------------
      completableFuture = commandGateway.send(command);

      LOGGER.log(
          Level.INFO,
          "return from Command Gateway for CreateSubscriptionCommand of Subscription is "
              + command);

    } catch (Exception exc) {
      final String errMsg = "Unable to create Subscription - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Update the provided command.
   *
   * @param command UpdateSubscriptionCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   * @exception IllegalArgumentException
   */
  public CompletableFuture<Void> updateSubscription(UpdateSubscriptionCommand command)
      throws ProcessingException, IllegalArgumentException {
    CompletableFuture<Void> completableFuture = null;

    try {

      // --------------------------------------
      // validate
      // --------------------------------------
      SubscriptionValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the UpdateSubscriptionCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to save Subscription - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    }

    return completableFuture;
  }

  /**
   * Deletes the associatied value object
   *
   * @param command DeleteSubscriptionCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   */
  public CompletableFuture<Void> delete(DeleteSubscriptionCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<Void> completableFuture = null;

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      SubscriptionValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the DeleteSubscriptionCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg =
          "Unable to delete Subscription using Id = " + command.getSubscriptionId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Method to retrieve the Subscription via SubscriptionFetchOneSummary
   *
   * @param summary SubscriptionFetchOneSummary
   * @return SubscriptionFetchOneResponse
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  public Subscription getSubscription(SubscriptionFetchOneSummary summary)
      throws ProcessingException, IllegalArgumentException {

    if (summary == null)
      throw new IllegalArgumentException("SubscriptionFetchOneSummary arg cannot be null");

    Subscription entity = null;

    try {
      // --------------------------------------
      // validate the fetch one summary
      // --------------------------------------
      SubscriptionValidator.getInstance().validate(summary);

      // --------------------------------------
      // use queryGateway to send request to Find a Subscription
      // --------------------------------------
      CompletableFuture<Subscription> futureEntity =
          queryGateway.query(
              new FindSubscriptionQuery(new LoadSubscriptionFilter(summary.getSubscriptionId())),
              ResponseTypes.instanceOf(Subscription.class));

      entity = futureEntity.get();
    } catch (Exception exc) {
      final String errMsg = "Unable to locate Subscription with id " + summary.getSubscriptionId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return entity;
  }

  /**
   * Method to retrieve a collection of all Subscriptions
   *
   * @return List<Subscription>
   * @exception ProcessingException Thrown if any problems
   */
  public List<Subscription> getAllSubscription() throws ProcessingException {
    List<Subscription> list = null;

    try {
      CompletableFuture<List<Subscription>> futureList =
          queryGateway.query(
              new FindAllSubscriptionQuery(),
              ResponseTypes.multipleInstancesOf(Subscription.class));

      list = futureList.get();
    } catch (Exception exc) {
      String errMsg = "Failed to get all Subscription";
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return list;
  }

  /**
   * assign Program on Subscription
   *
   * @param command AssignProgramToSubscriptionCommand
   * @exception ProcessingException
   */
  public void assignProgram(AssignProgramToSubscriptionCommand command) throws ProcessingException {

    // --------------------------------------------
    // load the parent
    // --------------------------------------------
    load(command.getSubscriptionId());

    ProgramBusinessDelegate childDelegate = ProgramBusinessDelegate.getProgramInstance();
    SubscriptionBusinessDelegate parentDelegate =
        SubscriptionBusinessDelegate.getSubscriptionInstance();
    UUID childId = command.getAssignment().getProgramId();
    Program child = null;

    try {
      // --------------------------------------
      // best to validate the command now
      // --------------------------------------
      SubscriptionValidator.getInstance().validate(command);

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
   * unAssign Program on Subscription
   *
   * @param command UnAssignProgramFromSubscriptionCommand
   * @exception ProcessingException
   */
  public void unAssignProgram(UnAssignProgramFromSubscriptionCommand command)
      throws ProcessingException {

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      SubscriptionValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to unassign Program on Subscription";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * assign Targets on Subscription
   *
   * @param command AssignTargetsToSubscriptionCommand
   * @exception ProcessingException
   */
  public void assignTargets(AssignTargetsToSubscriptionCommand command) throws ProcessingException {

    // --------------------------------------------
    // load the parent
    // --------------------------------------------
    load(command.getSubscriptionId());

    ValuesMapBusinessDelegate childDelegate = ValuesMapBusinessDelegate.getValuesMapInstance();
    SubscriptionBusinessDelegate parentDelegate =
        SubscriptionBusinessDelegate.getSubscriptionInstance();
    UUID childId = command.getAssignment().getValuesMapId();
    ValuesMap child = null;

    try {
      // --------------------------------------
      // best to validate the command now
      // --------------------------------------
      SubscriptionValidator.getInstance().validate(command);

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
   * unAssign Targets on Subscription
   *
   * @param command UnAssignTargetsFromSubscriptionCommand
   * @exception ProcessingException
   */
  public void unAssignTargets(UnAssignTargetsFromSubscriptionCommand command)
      throws ProcessingException {

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      SubscriptionValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to unassign Targets on Subscription";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * add ObjectOperation to ObjectOperations
   *
   * @param command AssignObjectOperationsToSubscriptionCommand
   * @exception ProcessingException
   */
  public void addToObjectOperations(AssignObjectOperationsToSubscriptionCommand command)
      throws ProcessingException {

    // -------------------------------------------
    // load the parent
    // -------------------------------------------
    load(command.getSubscriptionId());

    ObjectOperationBusinessDelegate childDelegate =
        ObjectOperationBusinessDelegate.getObjectOperationInstance();
    SubscriptionBusinessDelegate parentDelegate =
        SubscriptionBusinessDelegate.getSubscriptionInstance();
    UUID childId = command.getAddTo().getObjectOperationId();

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      SubscriptionValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to add a ObjectOperation as ObjectOperations to Subscription";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * remove ObjectOperation from ObjectOperations
   *
   * @param command RemoveObjectOperationsFromSubscriptionCommand
   * @exception ProcessingException
   */
  public void removeFromObjectOperations(RemoveObjectOperationsFromSubscriptionCommand command)
      throws ProcessingException {

    ObjectOperationBusinessDelegate childDelegate =
        ObjectOperationBusinessDelegate.getObjectOperationInstance();
    UUID childId = command.getRemoveFrom().getObjectOperationId();

    try {

      // --------------------------------------
      // validate the command
      // --------------------------------------
      SubscriptionValidator.getInstance().validate(command);

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
   * @return Subscription
   */
  private Subscription load(UUID id) throws ProcessingException {
    subscription =
        SubscriptionBusinessDelegate.getSubscriptionInstance()
            .getSubscription(new SubscriptionFetchOneSummary(id));
    return subscription;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  private final QueryGateway queryGateway;
  private final CommandGateway commandGateway;
  private final QueryUpdateEmitter queryUpdateEmitter;
  private Subscription subscription = null;
  private static final Logger LOGGER =
      Logger.getLogger(SubscriptionBusinessDelegate.class.getName());
}
