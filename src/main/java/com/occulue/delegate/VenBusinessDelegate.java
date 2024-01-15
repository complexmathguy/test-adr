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
 * Ven business delegate class.
 *
 * <p>This class implements the Business Delegate design pattern for the purpose of:
 *
 * <ol>
 *   <li>Reducing coupling between the business tier and a client of the business tier by hiding all
 *       business-tier implementation details
 *   <li>Improving the available of Ven related services in the case of a Ven business related
 *       service failing.
 *   <li>Exposes a simpler, uniform Ven interface to the business tier, making it easy for clients
 *       to consume a simple Java object.
 *   <li>Hides the communication protocol that may be required to fulfill Ven business related
 *       services.
 * </ol>
 *
 * <p>
 *
 * @author your_name_here
 */
public class VenBusinessDelegate extends NotifierBusinessDelegate {
  // ************************************************************************
  // Public Methods
  // ************************************************************************
  /** Default Constructor */
  public VenBusinessDelegate() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
    commandGateway = applicationContext.getBean(CommandGateway.class);
    queryUpdateEmitter = applicationContext.getBean(QueryUpdateEmitter.class);
  }

  /**
   * Ven Business Delegate Factory Method
   *
   * <p>All methods are expected to be self-sufficient.
   *
   * @return VenBusinessDelegate
   */
  public static VenBusinessDelegate getVenInstance() {
    return (new VenBusinessDelegate());
  }

  /**
   * Creates the provided command.
   *
   * @param command ${class.getCreateCommandAlias()}
   * @exception ProcessingException
   * @exception IllegalArgumentException
   * @return CompletableFuture<UUID>
   */
  public CompletableFuture<UUID> createVen(CreateVenCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<UUID> completableFuture = null;

    try {
      // --------------------------------------
      // assign identity now if none
      // --------------------------------------
      if (command.getVenId() == null) command.setVenId(UUID.randomUUID());

      // --------------------------------------
      // validate the command
      // --------------------------------------
      VenValidator.getInstance().validate(command);

      // ---------------------------------------
      // issue the CreateVenCommand - by convention the future return value for a create command
      // that is handled by the constructor of an aggregate will return the UUID
      // ---------------------------------------
      completableFuture = commandGateway.send(command);

      LOGGER.log(
          Level.INFO, "return from Command Gateway for CreateVenCommand of Ven is " + command);

    } catch (Exception exc) {
      final String errMsg = "Unable to create Ven - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Update the provided command.
   *
   * @param command UpdateVenCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   * @exception IllegalArgumentException
   */
  public CompletableFuture<Void> updateVen(UpdateVenCommand command)
      throws ProcessingException, IllegalArgumentException {
    CompletableFuture<Void> completableFuture = null;

    try {

      // --------------------------------------
      // validate
      // --------------------------------------
      VenValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the UpdateVenCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to save Ven - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    }

    return completableFuture;
  }

  /**
   * Deletes the associatied value object
   *
   * @param command DeleteVenCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   */
  public CompletableFuture<Void> delete(DeleteVenCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<Void> completableFuture = null;

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      VenValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the DeleteVenCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to delete Ven using Id = " + command.getVenId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Method to retrieve the Ven via VenFetchOneSummary
   *
   * @param summary VenFetchOneSummary
   * @return VenFetchOneResponse
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  public Ven getVen(VenFetchOneSummary summary)
      throws ProcessingException, IllegalArgumentException {

    if (summary == null)
      throw new IllegalArgumentException("VenFetchOneSummary arg cannot be null");

    Ven entity = null;

    try {
      // --------------------------------------
      // validate the fetch one summary
      // --------------------------------------
      VenValidator.getInstance().validate(summary);

      // --------------------------------------
      // use queryGateway to send request to Find a Ven
      // --------------------------------------
      CompletableFuture<Ven> futureEntity =
          queryGateway.query(
              new FindVenQuery(new LoadVenFilter(summary.getVenId())),
              ResponseTypes.instanceOf(Ven.class));

      entity = futureEntity.get();
    } catch (Exception exc) {
      final String errMsg = "Unable to locate Ven with id " + summary.getVenId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return entity;
  }

  /**
   * Method to retrieve a collection of all Vens
   *
   * @return List<Ven>
   * @exception ProcessingException Thrown if any problems
   */
  public List<Ven> getAllVen() throws ProcessingException {
    List<Ven> list = null;

    try {
      CompletableFuture<List<Ven>> futureList =
          queryGateway.query(new FindAllVenQuery(), ResponseTypes.multipleInstancesOf(Ven.class));

      list = futureList.get();
    } catch (Exception exc) {
      String errMsg = "Failed to get all Ven";
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return list;
  }

  /**
   * add ValuesMap to Attributes
   *
   * @param command AssignAttributesToVenCommand
   * @exception ProcessingException
   */
  public void addToAttributes(AssignAttributesToVenCommand command) throws ProcessingException {

    // -------------------------------------------
    // load the parent
    // -------------------------------------------
    load(command.getVenId());

    ValuesMapBusinessDelegate childDelegate = ValuesMapBusinessDelegate.getValuesMapInstance();
    VenBusinessDelegate parentDelegate = VenBusinessDelegate.getVenInstance();
    UUID childId = command.getAddTo().getValuesMapId();

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      VenValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to add a ValuesMap as Attributes to Ven";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * remove ValuesMap from Attributes
   *
   * @param command RemoveAttributesFromVenCommand
   * @exception ProcessingException
   */
  public void removeFromAttributes(RemoveAttributesFromVenCommand command)
      throws ProcessingException {

    ValuesMapBusinessDelegate childDelegate = ValuesMapBusinessDelegate.getValuesMapInstance();
    UUID childId = command.getRemoveFrom().getValuesMapId();

    try {

      // --------------------------------------
      // validate the command
      // --------------------------------------
      VenValidator.getInstance().validate(command);

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
   * add ValuesMap to Targets
   *
   * @param command AssignTargetsToVenCommand
   * @exception ProcessingException
   */
  public void addToTargets(AssignTargetsToVenCommand command) throws ProcessingException {

    // -------------------------------------------
    // load the parent
    // -------------------------------------------
    load(command.getVenId());

    ValuesMapBusinessDelegate childDelegate = ValuesMapBusinessDelegate.getValuesMapInstance();
    VenBusinessDelegate parentDelegate = VenBusinessDelegate.getVenInstance();
    UUID childId = command.getAddTo().getValuesMapId();

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      VenValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to add a ValuesMap as Targets to Ven";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * remove ValuesMap from Targets
   *
   * @param command RemoveTargetsFromVenCommand
   * @exception ProcessingException
   */
  public void removeFromTargets(RemoveTargetsFromVenCommand command) throws ProcessingException {

    ValuesMapBusinessDelegate childDelegate = ValuesMapBusinessDelegate.getValuesMapInstance();
    UUID childId = command.getRemoveFrom().getValuesMapId();

    try {

      // --------------------------------------
      // validate the command
      // --------------------------------------
      VenValidator.getInstance().validate(command);

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
   * add Resource to Resources
   *
   * @param command AssignResourcesToVenCommand
   * @exception ProcessingException
   */
  public void addToResources(AssignResourcesToVenCommand command) throws ProcessingException {

    // -------------------------------------------
    // load the parent
    // -------------------------------------------
    load(command.getVenId());

    ResourceBusinessDelegate childDelegate = ResourceBusinessDelegate.getResourceInstance();
    VenBusinessDelegate parentDelegate = VenBusinessDelegate.getVenInstance();
    UUID childId = command.getAddTo().getResourceId();

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      VenValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to add a Resource as Resources to Ven";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * remove Resource from Resources
   *
   * @param command RemoveResourcesFromVenCommand
   * @exception ProcessingException
   */
  public void removeFromResources(RemoveResourcesFromVenCommand command)
      throws ProcessingException {

    ResourceBusinessDelegate childDelegate = ResourceBusinessDelegate.getResourceInstance();
    UUID childId = command.getRemoveFrom().getResourceId();

    try {

      // --------------------------------------
      // validate the command
      // --------------------------------------
      VenValidator.getInstance().validate(command);

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
   * @return Ven
   */
  private Ven load(UUID id) throws ProcessingException {
    ven = VenBusinessDelegate.getVenInstance().getVen(new VenFetchOneSummary(id));
    return ven;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  private final QueryGateway queryGateway;
  private final CommandGateway commandGateway;
  private final QueryUpdateEmitter queryUpdateEmitter;
  private Ven ven = null;
  private static final Logger LOGGER = Logger.getLogger(VenBusinessDelegate.class.getName());
}
