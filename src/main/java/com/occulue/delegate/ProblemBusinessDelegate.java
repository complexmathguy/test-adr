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
 * Problem business delegate class.
 *
 * <p>This class implements the Business Delegate design pattern for the purpose of:
 *
 * <ol>
 *   <li>Reducing coupling between the business tier and a client of the business tier by hiding all
 *       business-tier implementation details
 *   <li>Improving the available of Problem related services in the case of a Problem business
 *       related service failing.
 *   <li>Exposes a simpler, uniform Problem interface to the business tier, making it easy for
 *       clients to consume a simple Java object.
 *   <li>Hides the communication protocol that may be required to fulfill Problem business related
 *       services.
 * </ol>
 *
 * <p>
 *
 * @author your_name_here
 */
public class ProblemBusinessDelegate extends BaseBusinessDelegate {
  // ************************************************************************
  // Public Methods
  // ************************************************************************
  /** Default Constructor */
  public ProblemBusinessDelegate() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
    commandGateway = applicationContext.getBean(CommandGateway.class);
    queryUpdateEmitter = applicationContext.getBean(QueryUpdateEmitter.class);
  }

  /**
   * Problem Business Delegate Factory Method
   *
   * <p>All methods are expected to be self-sufficient.
   *
   * @return ProblemBusinessDelegate
   */
  public static ProblemBusinessDelegate getProblemInstance() {
    return (new ProblemBusinessDelegate());
  }

  /**
   * Creates the provided command.
   *
   * @param command ${class.getCreateCommandAlias()}
   * @exception ProcessingException
   * @exception IllegalArgumentException
   * @return CompletableFuture<UUID>
   */
  public CompletableFuture<UUID> createProblem(CreateProblemCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<UUID> completableFuture = null;

    try {
      // --------------------------------------
      // assign identity now if none
      // --------------------------------------
      if (command.getProblemId() == null) command.setProblemId(UUID.randomUUID());

      // --------------------------------------
      // validate the command
      // --------------------------------------
      ProblemValidator.getInstance().validate(command);

      // ---------------------------------------
      // issue the CreateProblemCommand - by convention the future return value for a create command
      // that is handled by the constructor of an aggregate will return the UUID
      // ---------------------------------------
      completableFuture = commandGateway.send(command);

      LOGGER.log(
          Level.INFO,
          "return from Command Gateway for CreateProblemCommand of Problem is " + command);

    } catch (Exception exc) {
      final String errMsg = "Unable to create Problem - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Update the provided command.
   *
   * @param command UpdateProblemCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   * @exception IllegalArgumentException
   */
  public CompletableFuture<Void> updateProblem(UpdateProblemCommand command)
      throws ProcessingException, IllegalArgumentException {
    CompletableFuture<Void> completableFuture = null;

    try {

      // --------------------------------------
      // validate
      // --------------------------------------
      ProblemValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the UpdateProblemCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to save Problem - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    }

    return completableFuture;
  }

  /**
   * Deletes the associatied value object
   *
   * @param command DeleteProblemCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   */
  public CompletableFuture<Void> delete(DeleteProblemCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<Void> completableFuture = null;

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      ProblemValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the DeleteProblemCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to delete Problem using Id = " + command.getProblemId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Method to retrieve the Problem via ProblemFetchOneSummary
   *
   * @param summary ProblemFetchOneSummary
   * @return ProblemFetchOneResponse
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  public Problem getProblem(ProblemFetchOneSummary summary)
      throws ProcessingException, IllegalArgumentException {

    if (summary == null)
      throw new IllegalArgumentException("ProblemFetchOneSummary arg cannot be null");

    Problem entity = null;

    try {
      // --------------------------------------
      // validate the fetch one summary
      // --------------------------------------
      ProblemValidator.getInstance().validate(summary);

      // --------------------------------------
      // use queryGateway to send request to Find a Problem
      // --------------------------------------
      CompletableFuture<Problem> futureEntity =
          queryGateway.query(
              new FindProblemQuery(new LoadProblemFilter(summary.getProblemId())),
              ResponseTypes.instanceOf(Problem.class));

      entity = futureEntity.get();
    } catch (Exception exc) {
      final String errMsg = "Unable to locate Problem with id " + summary.getProblemId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return entity;
  }

  /**
   * Method to retrieve a collection of all Problems
   *
   * @return List<Problem>
   * @exception ProcessingException Thrown if any problems
   */
  public List<Problem> getAllProblem() throws ProcessingException {
    List<Problem> list = null;

    try {
      CompletableFuture<List<Problem>> futureList =
          queryGateway.query(
              new FindAllProblemQuery(), ResponseTypes.multipleInstancesOf(Problem.class));

      list = futureList.get();
    } catch (Exception exc) {
      String errMsg = "Failed to get all Problem";
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
   * @return Problem
   */
  private Problem load(UUID id) throws ProcessingException {
    problem =
        ProblemBusinessDelegate.getProblemInstance().getProblem(new ProblemFetchOneSummary(id));
    return problem;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  private final QueryGateway queryGateway;
  private final CommandGateway commandGateway;
  private final QueryUpdateEmitter queryUpdateEmitter;
  private Problem problem = null;
  private static final Logger LOGGER = Logger.getLogger(ProblemBusinessDelegate.class.getName());
}
