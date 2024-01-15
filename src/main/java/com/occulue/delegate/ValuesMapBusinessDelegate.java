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
 * ValuesMap business delegate class.
 *
 * <p>This class implements the Business Delegate design pattern for the purpose of:
 *
 * <ol>
 *   <li>Reducing coupling between the business tier and a client of the business tier by hiding all
 *       business-tier implementation details
 *   <li>Improving the available of ValuesMap related services in the case of a ValuesMap business
 *       related service failing.
 *   <li>Exposes a simpler, uniform ValuesMap interface to the business tier, making it easy for
 *       clients to consume a simple Java object.
 *   <li>Hides the communication protocol that may be required to fulfill ValuesMap business related
 *       services.
 * </ol>
 *
 * <p>
 *
 * @author your_name_here
 */
public class ValuesMapBusinessDelegate extends BaseBusinessDelegate {
  // ************************************************************************
  // Public Methods
  // ************************************************************************
  /** Default Constructor */
  public ValuesMapBusinessDelegate() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
    commandGateway = applicationContext.getBean(CommandGateway.class);
    queryUpdateEmitter = applicationContext.getBean(QueryUpdateEmitter.class);
  }

  /**
   * ValuesMap Business Delegate Factory Method
   *
   * <p>All methods are expected to be self-sufficient.
   *
   * @return ValuesMapBusinessDelegate
   */
  public static ValuesMapBusinessDelegate getValuesMapInstance() {
    return (new ValuesMapBusinessDelegate());
  }

  /**
   * Creates the provided command.
   *
   * @param command ${class.getCreateCommandAlias()}
   * @exception ProcessingException
   * @exception IllegalArgumentException
   * @return CompletableFuture<UUID>
   */
  public CompletableFuture<UUID> createValuesMap(CreateValuesMapCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<UUID> completableFuture = null;

    try {
      // --------------------------------------
      // assign identity now if none
      // --------------------------------------
      if (command.getValuesMapId() == null) command.setValuesMapId(UUID.randomUUID());

      // --------------------------------------
      // validate the command
      // --------------------------------------
      ValuesMapValidator.getInstance().validate(command);

      // ---------------------------------------
      // issue the CreateValuesMapCommand - by convention the future return value for a create
      // command
      // that is handled by the constructor of an aggregate will return the UUID
      // ---------------------------------------
      completableFuture = commandGateway.send(command);

      LOGGER.log(
          Level.INFO,
          "return from Command Gateway for CreateValuesMapCommand of ValuesMap is " + command);

    } catch (Exception exc) {
      final String errMsg = "Unable to create ValuesMap - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Update the provided command.
   *
   * @param command UpdateValuesMapCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   * @exception IllegalArgumentException
   */
  public CompletableFuture<Void> updateValuesMap(UpdateValuesMapCommand command)
      throws ProcessingException, IllegalArgumentException {
    CompletableFuture<Void> completableFuture = null;

    try {

      // --------------------------------------
      // validate
      // --------------------------------------
      ValuesMapValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the UpdateValuesMapCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to save ValuesMap - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    }

    return completableFuture;
  }

  /**
   * Deletes the associatied value object
   *
   * @param command DeleteValuesMapCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   */
  public CompletableFuture<Void> delete(DeleteValuesMapCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<Void> completableFuture = null;

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      ValuesMapValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the DeleteValuesMapCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to delete ValuesMap using Id = " + command.getValuesMapId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Method to retrieve the ValuesMap via ValuesMapFetchOneSummary
   *
   * @param summary ValuesMapFetchOneSummary
   * @return ValuesMapFetchOneResponse
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  public ValuesMap getValuesMap(ValuesMapFetchOneSummary summary)
      throws ProcessingException, IllegalArgumentException {

    if (summary == null)
      throw new IllegalArgumentException("ValuesMapFetchOneSummary arg cannot be null");

    ValuesMap entity = null;

    try {
      // --------------------------------------
      // validate the fetch one summary
      // --------------------------------------
      ValuesMapValidator.getInstance().validate(summary);

      // --------------------------------------
      // use queryGateway to send request to Find a ValuesMap
      // --------------------------------------
      CompletableFuture<ValuesMap> futureEntity =
          queryGateway.query(
              new FindValuesMapQuery(new LoadValuesMapFilter(summary.getValuesMapId())),
              ResponseTypes.instanceOf(ValuesMap.class));

      entity = futureEntity.get();
    } catch (Exception exc) {
      final String errMsg = "Unable to locate ValuesMap with id " + summary.getValuesMapId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return entity;
  }

  /**
   * Method to retrieve a collection of all ValuesMaps
   *
   * @return List<ValuesMap>
   * @exception ProcessingException Thrown if any problems
   */
  public List<ValuesMap> getAllValuesMap() throws ProcessingException {
    List<ValuesMap> list = null;

    try {
      CompletableFuture<List<ValuesMap>> futureList =
          queryGateway.query(
              new FindAllValuesMapQuery(), ResponseTypes.multipleInstancesOf(ValuesMap.class));

      list = futureList.get();
    } catch (Exception exc) {
      String errMsg = "Failed to get all ValuesMap";
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
   * @return ValuesMap
   */
  private ValuesMap load(UUID id) throws ProcessingException {
    valuesMap =
        ValuesMapBusinessDelegate.getValuesMapInstance()
            .getValuesMap(new ValuesMapFetchOneSummary(id));
    return valuesMap;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  private final QueryGateway queryGateway;
  private final CommandGateway commandGateway;
  private final QueryUpdateEmitter queryUpdateEmitter;
  private ValuesMap valuesMap = null;
  private static final Logger LOGGER = Logger.getLogger(ValuesMapBusinessDelegate.class.getName());
}
