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
 * Resource business delegate class.
 *
 * <p>This class implements the Business Delegate design pattern for the purpose of:
 *
 * <ol>
 *   <li>Reducing coupling between the business tier and a client of the business tier by hiding all
 *       business-tier implementation details
 *   <li>Improving the available of Resource related services in the case of a Resource business
 *       related service failing.
 *   <li>Exposes a simpler, uniform Resource interface to the business tier, making it easy for
 *       clients to consume a simple Java object.
 *   <li>Hides the communication protocol that may be required to fulfill Resource business related
 *       services.
 * </ol>
 *
 * <p>
 *
 * @author your_name_here
 */
public class ResourceBusinessDelegate extends NotifierBusinessDelegate {
  // ************************************************************************
  // Public Methods
  // ************************************************************************
  /** Default Constructor */
  public ResourceBusinessDelegate() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
    commandGateway = applicationContext.getBean(CommandGateway.class);
    queryUpdateEmitter = applicationContext.getBean(QueryUpdateEmitter.class);
  }

  /**
   * Resource Business Delegate Factory Method
   *
   * <p>All methods are expected to be self-sufficient.
   *
   * @return ResourceBusinessDelegate
   */
  public static ResourceBusinessDelegate getResourceInstance() {
    return (new ResourceBusinessDelegate());
  }

  /**
   * Creates the provided command.
   *
   * @param command ${class.getCreateCommandAlias()}
   * @exception ProcessingException
   * @exception IllegalArgumentException
   * @return CompletableFuture<UUID>
   */
  public CompletableFuture<UUID> createResource(CreateResourceCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<UUID> completableFuture = null;

    try {
      // --------------------------------------
      // assign identity now if none
      // --------------------------------------
      if (command.getResourceId() == null) command.setResourceId(UUID.randomUUID());

      // --------------------------------------
      // validate the command
      // --------------------------------------
      ResourceValidator.getInstance().validate(command);

      // ---------------------------------------
      // issue the CreateResourceCommand - by convention the future return value for a create
      // command
      // that is handled by the constructor of an aggregate will return the UUID
      // ---------------------------------------
      completableFuture = commandGateway.send(command);

      LOGGER.log(
          Level.INFO,
          "return from Command Gateway for CreateResourceCommand of Resource is " + command);

    } catch (Exception exc) {
      final String errMsg = "Unable to create Resource - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Update the provided command.
   *
   * @param command UpdateResourceCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   * @exception IllegalArgumentException
   */
  public CompletableFuture<Void> updateResource(UpdateResourceCommand command)
      throws ProcessingException, IllegalArgumentException {
    CompletableFuture<Void> completableFuture = null;

    try {

      // --------------------------------------
      // validate
      // --------------------------------------
      ResourceValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the UpdateResourceCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to save Resource - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    }

    return completableFuture;
  }

  /**
   * Deletes the associatied value object
   *
   * @param command DeleteResourceCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   */
  public CompletableFuture<Void> delete(DeleteResourceCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<Void> completableFuture = null;

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      ResourceValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the DeleteResourceCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to delete Resource using Id = " + command.getResourceId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Method to retrieve the Resource via ResourceFetchOneSummary
   *
   * @param summary ResourceFetchOneSummary
   * @return ResourceFetchOneResponse
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  public Resource getResource(ResourceFetchOneSummary summary)
      throws ProcessingException, IllegalArgumentException {

    if (summary == null)
      throw new IllegalArgumentException("ResourceFetchOneSummary arg cannot be null");

    Resource entity = null;

    try {
      // --------------------------------------
      // validate the fetch one summary
      // --------------------------------------
      ResourceValidator.getInstance().validate(summary);

      // --------------------------------------
      // use queryGateway to send request to Find a Resource
      // --------------------------------------
      CompletableFuture<Resource> futureEntity =
          queryGateway.query(
              new FindResourceQuery(new LoadResourceFilter(summary.getResourceId())),
              ResponseTypes.instanceOf(Resource.class));

      entity = futureEntity.get();
    } catch (Exception exc) {
      final String errMsg = "Unable to locate Resource with id " + summary.getResourceId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return entity;
  }

  /**
   * Method to retrieve a collection of all Resources
   *
   * @return List<Resource>
   * @exception ProcessingException Thrown if any problems
   */
  public List<Resource> getAllResource() throws ProcessingException {
    List<Resource> list = null;

    try {
      CompletableFuture<List<Resource>> futureList =
          queryGateway.query(
              new FindAllResourceQuery(), ResponseTypes.multipleInstancesOf(Resource.class));

      list = futureList.get();
    } catch (Exception exc) {
      String errMsg = "Failed to get all Resource";
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return list;
  }

  /**
   * assign Ven on Resource
   *
   * @param command AssignVenToResourceCommand
   * @exception ProcessingException
   */
  public void assignVen(AssignVenToResourceCommand command) throws ProcessingException {

    // --------------------------------------------
    // load the parent
    // --------------------------------------------
    load(command.getResourceId());

    VenBusinessDelegate childDelegate = VenBusinessDelegate.getVenInstance();
    ResourceBusinessDelegate parentDelegate = ResourceBusinessDelegate.getResourceInstance();
    UUID childId = command.getAssignment().getVenId();
    Ven child = null;

    try {
      // --------------------------------------
      // best to validate the command now
      // --------------------------------------
      ResourceValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);

    } catch (Throwable exc) {
      final String msg = "Failed to get Ven using id " + childId;
      LOGGER.log(Level.WARNING, msg);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * unAssign Ven on Resource
   *
   * @param command UnAssignVenFromResourceCommand
   * @exception ProcessingException
   */
  public void unAssignVen(UnAssignVenFromResourceCommand command) throws ProcessingException {

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      ResourceValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to unassign Ven on Resource";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * add ValuesMap to Attributes
   *
   * @param command AssignAttributesToResourceCommand
   * @exception ProcessingException
   */
  public void addToAttributes(AssignAttributesToResourceCommand command)
      throws ProcessingException {

    // -------------------------------------------
    // load the parent
    // -------------------------------------------
    load(command.getResourceId());

    ValuesMapBusinessDelegate childDelegate = ValuesMapBusinessDelegate.getValuesMapInstance();
    ResourceBusinessDelegate parentDelegate = ResourceBusinessDelegate.getResourceInstance();
    UUID childId = command.getAddTo().getValuesMapId();

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      ResourceValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to add a ValuesMap as Attributes to Resource";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * remove ValuesMap from Attributes
   *
   * @param command RemoveAttributesFromResourceCommand
   * @exception ProcessingException
   */
  public void removeFromAttributes(RemoveAttributesFromResourceCommand command)
      throws ProcessingException {

    ValuesMapBusinessDelegate childDelegate = ValuesMapBusinessDelegate.getValuesMapInstance();
    UUID childId = command.getRemoveFrom().getValuesMapId();

    try {

      // --------------------------------------
      // validate the command
      // --------------------------------------
      ResourceValidator.getInstance().validate(command);

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
   * @param command AssignTargetsToResourceCommand
   * @exception ProcessingException
   */
  public void addToTargets(AssignTargetsToResourceCommand command) throws ProcessingException {

    // -------------------------------------------
    // load the parent
    // -------------------------------------------
    load(command.getResourceId());

    ValuesMapBusinessDelegate childDelegate = ValuesMapBusinessDelegate.getValuesMapInstance();
    ResourceBusinessDelegate parentDelegate = ResourceBusinessDelegate.getResourceInstance();
    UUID childId = command.getAddTo().getValuesMapId();

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      ResourceValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to add a ValuesMap as Targets to Resource";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * remove ValuesMap from Targets
   *
   * @param command RemoveTargetsFromResourceCommand
   * @exception ProcessingException
   */
  public void removeFromTargets(RemoveTargetsFromResourceCommand command)
      throws ProcessingException {

    ValuesMapBusinessDelegate childDelegate = ValuesMapBusinessDelegate.getValuesMapInstance();
    UUID childId = command.getRemoveFrom().getValuesMapId();

    try {

      // --------------------------------------
      // validate the command
      // --------------------------------------
      ResourceValidator.getInstance().validate(command);

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
   * @return Resource
   */
  private Resource load(UUID id) throws ProcessingException {
    resource =
        ResourceBusinessDelegate.getResourceInstance().getResource(new ResourceFetchOneSummary(id));
    return resource;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  private final QueryGateway queryGateway;
  private final CommandGateway commandGateway;
  private final QueryUpdateEmitter queryUpdateEmitter;
  private Resource resource = null;
  private static final Logger LOGGER = Logger.getLogger(ResourceBusinessDelegate.class.getName());
}
