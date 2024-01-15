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
 * ReportPayloadDescriptor business delegate class.
 *
 * <p>This class implements the Business Delegate design pattern for the purpose of:
 *
 * <ol>
 *   <li>Reducing coupling between the business tier and a client of the business tier by hiding all
 *       business-tier implementation details
 *   <li>Improving the available of ReportPayloadDescriptor related services in the case of a
 *       ReportPayloadDescriptor business related service failing.
 *   <li>Exposes a simpler, uniform ReportPayloadDescriptor interface to the business tier, making
 *       it easy for clients to consume a simple Java object.
 *   <li>Hides the communication protocol that may be required to fulfill ReportPayloadDescriptor
 *       business related services.
 * </ol>
 *
 * <p>
 *
 * @author your_name_here
 */
public class ReportPayloadDescriptorBusinessDelegate extends PayloadDescriptorBusinessDelegate {
  // ************************************************************************
  // Public Methods
  // ************************************************************************
  /** Default Constructor */
  public ReportPayloadDescriptorBusinessDelegate() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
    commandGateway = applicationContext.getBean(CommandGateway.class);
    queryUpdateEmitter = applicationContext.getBean(QueryUpdateEmitter.class);
  }

  /**
   * ReportPayloadDescriptor Business Delegate Factory Method
   *
   * <p>All methods are expected to be self-sufficient.
   *
   * @return ReportPayloadDescriptorBusinessDelegate
   */
  public static ReportPayloadDescriptorBusinessDelegate getReportPayloadDescriptorInstance() {
    return (new ReportPayloadDescriptorBusinessDelegate());
  }

  /**
   * Creates the provided command.
   *
   * @param command ${class.getCreateCommandAlias()}
   * @exception ProcessingException
   * @exception IllegalArgumentException
   * @return CompletableFuture<UUID>
   */
  public CompletableFuture<UUID> createReportPayloadDescriptor(
      CreateReportPayloadDescriptorCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<UUID> completableFuture = null;

    try {
      // --------------------------------------
      // assign identity now if none
      // --------------------------------------
      if (command.getReportPayloadDescriptorId() == null)
        command.setReportPayloadDescriptorId(UUID.randomUUID());

      // --------------------------------------
      // validate the command
      // --------------------------------------
      ReportPayloadDescriptorValidator.getInstance().validate(command);

      // ---------------------------------------
      // issue the CreateReportPayloadDescriptorCommand - by convention the future return value for
      // a create command
      // that is handled by the constructor of an aggregate will return the UUID
      // ---------------------------------------
      completableFuture = commandGateway.send(command);

      LOGGER.log(
          Level.INFO,
          "return from Command Gateway for CreateReportPayloadDescriptorCommand of ReportPayloadDescriptor is "
              + command);

    } catch (Exception exc) {
      final String errMsg = "Unable to create ReportPayloadDescriptor - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Update the provided command.
   *
   * @param command UpdateReportPayloadDescriptorCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   * @exception IllegalArgumentException
   */
  public CompletableFuture<Void> updateReportPayloadDescriptor(
      UpdateReportPayloadDescriptorCommand command)
      throws ProcessingException, IllegalArgumentException {
    CompletableFuture<Void> completableFuture = null;

    try {

      // --------------------------------------
      // validate
      // --------------------------------------
      ReportPayloadDescriptorValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the UpdateReportPayloadDescriptorCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to save ReportPayloadDescriptor - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    }

    return completableFuture;
  }

  /**
   * Deletes the associatied value object
   *
   * @param command DeleteReportPayloadDescriptorCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   */
  public CompletableFuture<Void> delete(DeleteReportPayloadDescriptorCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<Void> completableFuture = null;

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      ReportPayloadDescriptorValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the DeleteReportPayloadDescriptorCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg =
          "Unable to delete ReportPayloadDescriptor using Id = "
              + command.getReportPayloadDescriptorId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Method to retrieve the ReportPayloadDescriptor via ReportPayloadDescriptorFetchOneSummary
   *
   * @param summary ReportPayloadDescriptorFetchOneSummary
   * @return ReportPayloadDescriptorFetchOneResponse
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  public ReportPayloadDescriptor getReportPayloadDescriptor(
      ReportPayloadDescriptorFetchOneSummary summary)
      throws ProcessingException, IllegalArgumentException {

    if (summary == null)
      throw new IllegalArgumentException(
          "ReportPayloadDescriptorFetchOneSummary arg cannot be null");

    ReportPayloadDescriptor entity = null;

    try {
      // --------------------------------------
      // validate the fetch one summary
      // --------------------------------------
      ReportPayloadDescriptorValidator.getInstance().validate(summary);

      // --------------------------------------
      // use queryGateway to send request to Find a ReportPayloadDescriptor
      // --------------------------------------
      CompletableFuture<ReportPayloadDescriptor> futureEntity =
          queryGateway.query(
              new FindReportPayloadDescriptorQuery(
                  new LoadReportPayloadDescriptorFilter(summary.getReportPayloadDescriptorId())),
              ResponseTypes.instanceOf(ReportPayloadDescriptor.class));

      entity = futureEntity.get();
    } catch (Exception exc) {
      final String errMsg =
          "Unable to locate ReportPayloadDescriptor with id "
              + summary.getReportPayloadDescriptorId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return entity;
  }

  /**
   * Method to retrieve a collection of all ReportPayloadDescriptors
   *
   * @return List<ReportPayloadDescriptor>
   * @exception ProcessingException Thrown if any problems
   */
  public List<ReportPayloadDescriptor> getAllReportPayloadDescriptor() throws ProcessingException {
    List<ReportPayloadDescriptor> list = null;

    try {
      CompletableFuture<List<ReportPayloadDescriptor>> futureList =
          queryGateway.query(
              new FindAllReportPayloadDescriptorQuery(),
              ResponseTypes.multipleInstancesOf(ReportPayloadDescriptor.class));

      list = futureList.get();
    } catch (Exception exc) {
      String errMsg = "Failed to get all ReportPayloadDescriptor";
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
   * @return ReportPayloadDescriptor
   */
  private ReportPayloadDescriptor load(UUID id) throws ProcessingException {
    reportPayloadDescriptor =
        ReportPayloadDescriptorBusinessDelegate.getReportPayloadDescriptorInstance()
            .getReportPayloadDescriptor(new ReportPayloadDescriptorFetchOneSummary(id));
    return reportPayloadDescriptor;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  private final QueryGateway queryGateway;
  private final CommandGateway commandGateway;
  private final QueryUpdateEmitter queryUpdateEmitter;
  private ReportPayloadDescriptor reportPayloadDescriptor = null;
  private static final Logger LOGGER =
      Logger.getLogger(ReportPayloadDescriptorBusinessDelegate.class.getName());
}
