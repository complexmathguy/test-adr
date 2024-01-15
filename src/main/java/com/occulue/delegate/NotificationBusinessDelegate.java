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
 * Notification business delegate class.
 *
 * <p>This class implements the Business Delegate design pattern for the purpose of:
 *
 * <ol>
 *   <li>Reducing coupling between the business tier and a client of the business tier by hiding all
 *       business-tier implementation details
 *   <li>Improving the available of Notification related services in the case of a Notification
 *       business related service failing.
 *   <li>Exposes a simpler, uniform Notification interface to the business tier, making it easy for
 *       clients to consume a simple Java object.
 *   <li>Hides the communication protocol that may be required to fulfill Notification business
 *       related services.
 * </ol>
 *
 * <p>
 *
 * @author your_name_here
 */
public class NotificationBusinessDelegate extends BaseBusinessDelegate {
  // ************************************************************************
  // Public Methods
  // ************************************************************************
  /** Default Constructor */
  public NotificationBusinessDelegate() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
    commandGateway = applicationContext.getBean(CommandGateway.class);
    queryUpdateEmitter = applicationContext.getBean(QueryUpdateEmitter.class);
  }

  /**
   * Notification Business Delegate Factory Method
   *
   * <p>All methods are expected to be self-sufficient.
   *
   * @return NotificationBusinessDelegate
   */
  public static NotificationBusinessDelegate getNotificationInstance() {
    return (new NotificationBusinessDelegate());
  }

  /**
   * Creates the provided command.
   *
   * @param command ${class.getCreateCommandAlias()}
   * @exception ProcessingException
   * @exception IllegalArgumentException
   * @return CompletableFuture<UUID>
   */
  public CompletableFuture<UUID> createNotification(CreateNotificationCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<UUID> completableFuture = null;

    try {
      // --------------------------------------
      // assign identity now if none
      // --------------------------------------
      if (command.getNotificationId() == null) command.setNotificationId(UUID.randomUUID());

      // --------------------------------------
      // validate the command
      // --------------------------------------
      NotificationValidator.getInstance().validate(command);

      // ---------------------------------------
      // issue the CreateNotificationCommand - by convention the future return value for a create
      // command
      // that is handled by the constructor of an aggregate will return the UUID
      // ---------------------------------------
      completableFuture = commandGateway.send(command);

      LOGGER.log(
          Level.INFO,
          "return from Command Gateway for CreateNotificationCommand of Notification is "
              + command);

    } catch (Exception exc) {
      final String errMsg = "Unable to create Notification - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Update the provided command.
   *
   * @param command UpdateNotificationCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   * @exception IllegalArgumentException
   */
  public CompletableFuture<Void> updateNotification(UpdateNotificationCommand command)
      throws ProcessingException, IllegalArgumentException {
    CompletableFuture<Void> completableFuture = null;

    try {

      // --------------------------------------
      // validate
      // --------------------------------------
      NotificationValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the UpdateNotificationCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to save Notification - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    }

    return completableFuture;
  }

  /**
   * Deletes the associatied value object
   *
   * @param command DeleteNotificationCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   */
  public CompletableFuture<Void> delete(DeleteNotificationCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<Void> completableFuture = null;

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      NotificationValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the DeleteNotificationCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg =
          "Unable to delete Notification using Id = " + command.getNotificationId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Method to retrieve the Notification via NotificationFetchOneSummary
   *
   * @param summary NotificationFetchOneSummary
   * @return NotificationFetchOneResponse
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  public Notification getNotification(NotificationFetchOneSummary summary)
      throws ProcessingException, IllegalArgumentException {

    if (summary == null)
      throw new IllegalArgumentException("NotificationFetchOneSummary arg cannot be null");

    Notification entity = null;

    try {
      // --------------------------------------
      // validate the fetch one summary
      // --------------------------------------
      NotificationValidator.getInstance().validate(summary);

      // --------------------------------------
      // use queryGateway to send request to Find a Notification
      // --------------------------------------
      CompletableFuture<Notification> futureEntity =
          queryGateway.query(
              new FindNotificationQuery(new LoadNotificationFilter(summary.getNotificationId())),
              ResponseTypes.instanceOf(Notification.class));

      entity = futureEntity.get();
    } catch (Exception exc) {
      final String errMsg = "Unable to locate Notification with id " + summary.getNotificationId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return entity;
  }

  /**
   * Method to retrieve a collection of all Notifications
   *
   * @return List<Notification>
   * @exception ProcessingException Thrown if any problems
   */
  public List<Notification> getAllNotification() throws ProcessingException {
    List<Notification> list = null;

    try {
      CompletableFuture<List<Notification>> futureList =
          queryGateway.query(
              new FindAllNotificationQuery(),
              ResponseTypes.multipleInstancesOf(Notification.class));

      list = futureList.get();
    } catch (Exception exc) {
      String errMsg = "Failed to get all Notification";
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return list;
  }

  /**
   * assign Targets on Notification
   *
   * @param command AssignTargetsToNotificationCommand
   * @exception ProcessingException
   */
  public void assignTargets(AssignTargetsToNotificationCommand command) throws ProcessingException {

    // --------------------------------------------
    // load the parent
    // --------------------------------------------
    load(command.getNotificationId());

    ValuesMapBusinessDelegate childDelegate = ValuesMapBusinessDelegate.getValuesMapInstance();
    NotificationBusinessDelegate parentDelegate =
        NotificationBusinessDelegate.getNotificationInstance();
    UUID childId = command.getAssignment().getValuesMapId();
    ValuesMap child = null;

    try {
      // --------------------------------------
      // best to validate the command now
      // --------------------------------------
      NotificationValidator.getInstance().validate(command);

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
   * unAssign Targets on Notification
   *
   * @param command UnAssignTargetsFromNotificationCommand
   * @exception ProcessingException
   */
  public void unAssignTargets(UnAssignTargetsFromNotificationCommand command)
      throws ProcessingException {

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      NotificationValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to unassign Targets on Notification";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * assign Notifier on Notification
   *
   * @param command AssignNotifierToNotificationCommand
   * @exception ProcessingException
   */
  public void assignNotifier(AssignNotifierToNotificationCommand command)
      throws ProcessingException {

    // --------------------------------------------
    // load the parent
    // --------------------------------------------
    load(command.getNotificationId());

    NotifierBusinessDelegate childDelegate = NotifierBusinessDelegate.getNotifierInstance();
    NotificationBusinessDelegate parentDelegate =
        NotificationBusinessDelegate.getNotificationInstance();
    UUID childId = command.getAssignment().getNotifierId();
    Notifier child = null;

    try {
      // --------------------------------------
      // best to validate the command now
      // --------------------------------------
      NotificationValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);

    } catch (Throwable exc) {
      final String msg = "Failed to get Notifier using id " + childId;
      LOGGER.log(Level.WARNING, msg);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * unAssign Notifier on Notification
   *
   * @param command UnAssignNotifierFromNotificationCommand
   * @exception ProcessingException
   */
  public void unAssignNotifier(UnAssignNotifierFromNotificationCommand command)
      throws ProcessingException {

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      NotificationValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to unassign Notifier on Notification";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * Internal helper method to load the root
   *
   * @param id UUID
   * @return Notification
   */
  private Notification load(UUID id) throws ProcessingException {
    notification =
        NotificationBusinessDelegate.getNotificationInstance()
            .getNotification(new NotificationFetchOneSummary(id));
    return notification;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  private final QueryGateway queryGateway;
  private final CommandGateway commandGateway;
  private final QueryUpdateEmitter queryUpdateEmitter;
  private Notification notification = null;
  private static final Logger LOGGER =
      Logger.getLogger(NotificationBusinessDelegate.class.getName());
}
