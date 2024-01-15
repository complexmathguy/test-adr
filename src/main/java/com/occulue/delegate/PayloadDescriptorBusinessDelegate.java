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
 * PayloadDescriptor business delegate class.
 *
 * <p>This class implements the Business Delegate design pattern for the purpose of:
 *
 * <ol>
 *   <li>Reducing coupling between the business tier and a client of the business tier by hiding all
 *       business-tier implementation details
 *   <li>Improving the available of PayloadDescriptor related services in the case of a
 *       PayloadDescriptor business related service failing.
 *   <li>Exposes a simpler, uniform PayloadDescriptor interface to the business tier, making it easy
 *       for clients to consume a simple Java object.
 *   <li>Hides the communication protocol that may be required to fulfill PayloadDescriptor business
 *       related services.
 * </ol>
 *
 * <p>
 *
 * @author your_name_here
 */
public class PayloadDescriptorBusinessDelegate extends BaseBusinessDelegate {
  // ************************************************************************
  // Public Methods
  // ************************************************************************
  /** Default Constructor */
  public PayloadDescriptorBusinessDelegate() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
    commandGateway = applicationContext.getBean(CommandGateway.class);
    queryUpdateEmitter = applicationContext.getBean(QueryUpdateEmitter.class);
  }

  /**
   * PayloadDescriptor Business Delegate Factory Method
   *
   * <p>All methods are expected to be self-sufficient.
   *
   * @return PayloadDescriptorBusinessDelegate
   */
  public static PayloadDescriptorBusinessDelegate getPayloadDescriptorInstance() {
    return (new PayloadDescriptorBusinessDelegate());
  }

  /**
   * Creates the provided command.
   *
   * @param command ${class.getCreateCommandAlias()}
   * @exception ProcessingException
   * @exception IllegalArgumentException
   * @return CompletableFuture<UUID>
   */
  public CompletableFuture<UUID> createPayloadDescriptor(CreatePayloadDescriptorCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<UUID> completableFuture = null;

    try {
      // --------------------------------------
      // assign identity now if none
      // --------------------------------------
      if (command.getPayloadDescriptorId() == null)
        command.setPayloadDescriptorId(UUID.randomUUID());

      // --------------------------------------
      // validate the command
      // --------------------------------------
      PayloadDescriptorValidator.getInstance().validate(command);

      // ---------------------------------------
      // issue the CreatePayloadDescriptorCommand - by convention the future return value for a
      // create command
      // that is handled by the constructor of an aggregate will return the UUID
      // ---------------------------------------
      completableFuture = commandGateway.send(command);

      LOGGER.log(
          Level.INFO,
          "return from Command Gateway for CreatePayloadDescriptorCommand of PayloadDescriptor is "
              + command);

    } catch (Exception exc) {
      final String errMsg = "Unable to create PayloadDescriptor - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Update the provided command.
   *
   * @param command UpdatePayloadDescriptorCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   * @exception IllegalArgumentException
   */
  public CompletableFuture<Void> updatePayloadDescriptor(UpdatePayloadDescriptorCommand command)
      throws ProcessingException, IllegalArgumentException {
    CompletableFuture<Void> completableFuture = null;

    try {

      // --------------------------------------
      // validate
      // --------------------------------------
      PayloadDescriptorValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the UpdatePayloadDescriptorCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to save PayloadDescriptor - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    }

    return completableFuture;
  }

  /**
   * Deletes the associatied value object
   *
   * @param command DeletePayloadDescriptorCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   */
  public CompletableFuture<Void> delete(DeletePayloadDescriptorCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<Void> completableFuture = null;

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      PayloadDescriptorValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the DeletePayloadDescriptorCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg =
          "Unable to delete PayloadDescriptor using Id = " + command.getPayloadDescriptorId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Method to retrieve the PayloadDescriptor via PayloadDescriptorFetchOneSummary
   *
   * @param summary PayloadDescriptorFetchOneSummary
   * @return PayloadDescriptorFetchOneResponse
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  public PayloadDescriptor getPayloadDescriptor(PayloadDescriptorFetchOneSummary summary)
      throws ProcessingException, IllegalArgumentException {

    if (summary == null)
      throw new IllegalArgumentException("PayloadDescriptorFetchOneSummary arg cannot be null");

    PayloadDescriptor entity = null;

    try {
      // --------------------------------------
      // validate the fetch one summary
      // --------------------------------------
      PayloadDescriptorValidator.getInstance().validate(summary);

      // --------------------------------------
      // use queryGateway to send request to Find a PayloadDescriptor
      // --------------------------------------
      CompletableFuture<PayloadDescriptor> futureEntity =
          queryGateway.query(
              new FindPayloadDescriptorQuery(
                  new LoadPayloadDescriptorFilter(summary.getPayloadDescriptorId())),
              ResponseTypes.instanceOf(PayloadDescriptor.class));

      entity = futureEntity.get();
    } catch (Exception exc) {
      final String errMsg =
          "Unable to locate PayloadDescriptor with id " + summary.getPayloadDescriptorId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return entity;
  }

  /**
   * Method to retrieve a collection of all PayloadDescriptors
   *
   * @return List<PayloadDescriptor>
   * @exception ProcessingException Thrown if any problems
   */
  public List<PayloadDescriptor> getAllPayloadDescriptor() throws ProcessingException {
    List<PayloadDescriptor> list = null;

    try {
      CompletableFuture<List<PayloadDescriptor>> futureList =
          queryGateway.query(
              new FindAllPayloadDescriptorQuery(),
              ResponseTypes.multipleInstancesOf(PayloadDescriptor.class));

      list = futureList.get();
    } catch (Exception exc) {
      String errMsg = "Failed to get all PayloadDescriptor";
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
   * @return PayloadDescriptor
   */
  private PayloadDescriptor load(UUID id) throws ProcessingException {
    payloadDescriptor =
        PayloadDescriptorBusinessDelegate.getPayloadDescriptorInstance()
            .getPayloadDescriptor(new PayloadDescriptorFetchOneSummary(id));
    return payloadDescriptor;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  private final QueryGateway queryGateway;
  private final CommandGateway commandGateway;
  private final QueryUpdateEmitter queryUpdateEmitter;
  private PayloadDescriptor payloadDescriptor = null;
  private static final Logger LOGGER =
      Logger.getLogger(PayloadDescriptorBusinessDelegate.class.getName());
}
