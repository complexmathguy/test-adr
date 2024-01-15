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
 * Program business delegate class.
 *
 * <p>This class implements the Business Delegate design pattern for the purpose of:
 *
 * <ol>
 *   <li>Reducing coupling between the business tier and a client of the business tier by hiding all
 *       business-tier implementation details
 *   <li>Improving the available of Program related services in the case of a Program business
 *       related service failing.
 *   <li>Exposes a simpler, uniform Program interface to the business tier, making it easy for
 *       clients to consume a simple Java object.
 *   <li>Hides the communication protocol that may be required to fulfill Program business related
 *       services.
 * </ol>
 *
 * <p>
 *
 * @author your_name_here
 */
public class ProgramBusinessDelegate extends NotifierBusinessDelegate {
  // ************************************************************************
  // Public Methods
  // ************************************************************************
  /** Default Constructor */
  public ProgramBusinessDelegate() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
    commandGateway = applicationContext.getBean(CommandGateway.class);
    queryUpdateEmitter = applicationContext.getBean(QueryUpdateEmitter.class);
  }

  /**
   * Program Business Delegate Factory Method
   *
   * <p>All methods are expected to be self-sufficient.
   *
   * @return ProgramBusinessDelegate
   */
  public static ProgramBusinessDelegate getProgramInstance() {
    return (new ProgramBusinessDelegate());
  }

  /**
   * Creates the provided command.
   *
   * @param command ${class.getCreateCommandAlias()}
   * @exception ProcessingException
   * @exception IllegalArgumentException
   * @return CompletableFuture<UUID>
   */
  public CompletableFuture<UUID> createProgram(CreateProgramCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<UUID> completableFuture = null;

    try {
      // --------------------------------------
      // assign identity now if none
      // --------------------------------------
      if (command.getProgramId() == null) command.setProgramId(UUID.randomUUID());

      // --------------------------------------
      // validate the command
      // --------------------------------------
      ProgramValidator.getInstance().validate(command);

      // ---------------------------------------
      // issue the CreateProgramCommand - by convention the future return value for a create command
      // that is handled by the constructor of an aggregate will return the UUID
      // ---------------------------------------
      completableFuture = commandGateway.send(command);

      LOGGER.log(
          Level.INFO,
          "return from Command Gateway for CreateProgramCommand of Program is " + command);

    } catch (Exception exc) {
      final String errMsg = "Unable to create Program - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Update the provided command.
   *
   * @param command UpdateProgramCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   * @exception IllegalArgumentException
   */
  public CompletableFuture<Void> updateProgram(UpdateProgramCommand command)
      throws ProcessingException, IllegalArgumentException {
    CompletableFuture<Void> completableFuture = null;

    try {

      // --------------------------------------
      // validate
      // --------------------------------------
      ProgramValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the UpdateProgramCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to save Program - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    }

    return completableFuture;
  }

  /**
   * Deletes the associatied value object
   *
   * @param command DeleteProgramCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   */
  public CompletableFuture<Void> delete(DeleteProgramCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<Void> completableFuture = null;

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      ProgramValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the DeleteProgramCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to delete Program using Id = " + command.getProgramId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Method to retrieve the Program via ProgramFetchOneSummary
   *
   * @param summary ProgramFetchOneSummary
   * @return ProgramFetchOneResponse
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  public Program getProgram(ProgramFetchOneSummary summary)
      throws ProcessingException, IllegalArgumentException {

    if (summary == null)
      throw new IllegalArgumentException("ProgramFetchOneSummary arg cannot be null");

    Program entity = null;

    try {
      // --------------------------------------
      // validate the fetch one summary
      // --------------------------------------
      ProgramValidator.getInstance().validate(summary);

      // --------------------------------------
      // use queryGateway to send request to Find a Program
      // --------------------------------------
      CompletableFuture<Program> futureEntity =
          queryGateway.query(
              new FindProgramQuery(new LoadProgramFilter(summary.getProgramId())),
              ResponseTypes.instanceOf(Program.class));

      entity = futureEntity.get();
    } catch (Exception exc) {
      final String errMsg = "Unable to locate Program with id " + summary.getProgramId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return entity;
  }

  /**
   * Method to retrieve a collection of all Programs
   *
   * @return List<Program>
   * @exception ProcessingException Thrown if any problems
   */
  public List<Program> getAllProgram() throws ProcessingException {
    List<Program> list = null;

    try {
      CompletableFuture<List<Program>> futureList =
          queryGateway.query(
              new FindAllProgramQuery(), ResponseTypes.multipleInstancesOf(Program.class));

      list = futureList.get();
    } catch (Exception exc) {
      String errMsg = "Failed to get all Program";
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return list;
  }

  /**
   * assign IntervalPeriod on Program
   *
   * @param command AssignIntervalPeriodToProgramCommand
   * @exception ProcessingException
   */
  public void assignIntervalPeriod(AssignIntervalPeriodToProgramCommand command)
      throws ProcessingException {

    // --------------------------------------------
    // load the parent
    // --------------------------------------------
    load(command.getProgramId());

    IntervalPeriodBusinessDelegate childDelegate =
        IntervalPeriodBusinessDelegate.getIntervalPeriodInstance();
    ProgramBusinessDelegate parentDelegate = ProgramBusinessDelegate.getProgramInstance();
    UUID childId = command.getAssignment().getIntervalPeriodId();
    IntervalPeriod child = null;

    try {
      // --------------------------------------
      // best to validate the command now
      // --------------------------------------
      ProgramValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);

    } catch (Throwable exc) {
      final String msg = "Failed to get IntervalPeriod using id " + childId;
      LOGGER.log(Level.WARNING, msg);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * unAssign IntervalPeriod on Program
   *
   * @param command UnAssignIntervalPeriodFromProgramCommand
   * @exception ProcessingException
   */
  public void unAssignIntervalPeriod(UnAssignIntervalPeriodFromProgramCommand command)
      throws ProcessingException {

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      ProgramValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to unassign IntervalPeriod on Program";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * add PayloadDescriptor to PayloadDescriptors
   *
   * @param command AssignPayloadDescriptorsToProgramCommand
   * @exception ProcessingException
   */
  public void addToPayloadDescriptors(AssignPayloadDescriptorsToProgramCommand command)
      throws ProcessingException {

    // -------------------------------------------
    // load the parent
    // -------------------------------------------
    load(command.getProgramId());

    PayloadDescriptorBusinessDelegate childDelegate =
        PayloadDescriptorBusinessDelegate.getPayloadDescriptorInstance();
    ProgramBusinessDelegate parentDelegate = ProgramBusinessDelegate.getProgramInstance();
    UUID childId = command.getAddTo().getPayloadDescriptorId();

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      ProgramValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to add a PayloadDescriptor as PayloadDescriptors to Program";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * remove PayloadDescriptor from PayloadDescriptors
   *
   * @param command RemovePayloadDescriptorsFromProgramCommand
   * @exception ProcessingException
   */
  public void removeFromPayloadDescriptors(RemovePayloadDescriptorsFromProgramCommand command)
      throws ProcessingException {

    PayloadDescriptorBusinessDelegate childDelegate =
        PayloadDescriptorBusinessDelegate.getPayloadDescriptorInstance();
    UUID childId = command.getRemoveFrom().getPayloadDescriptorId();

    try {

      // --------------------------------------
      // validate the command
      // --------------------------------------
      ProgramValidator.getInstance().validate(command);

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
   * @param command AssignTargetsToProgramCommand
   * @exception ProcessingException
   */
  public void addToTargets(AssignTargetsToProgramCommand command) throws ProcessingException {

    // -------------------------------------------
    // load the parent
    // -------------------------------------------
    load(command.getProgramId());

    ValuesMapBusinessDelegate childDelegate = ValuesMapBusinessDelegate.getValuesMapInstance();
    ProgramBusinessDelegate parentDelegate = ProgramBusinessDelegate.getProgramInstance();
    UUID childId = command.getAddTo().getValuesMapId();

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      ProgramValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to add a ValuesMap as Targets to Program";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * remove ValuesMap from Targets
   *
   * @param command RemoveTargetsFromProgramCommand
   * @exception ProcessingException
   */
  public void removeFromTargets(RemoveTargetsFromProgramCommand command)
      throws ProcessingException {

    ValuesMapBusinessDelegate childDelegate = ValuesMapBusinessDelegate.getValuesMapInstance();
    UUID childId = command.getRemoveFrom().getValuesMapId();

    try {

      // --------------------------------------
      // validate the command
      // --------------------------------------
      ProgramValidator.getInstance().validate(command);

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
   * @return Program
   */
  private Program load(UUID id) throws ProcessingException {
    program =
        ProgramBusinessDelegate.getProgramInstance().getProgram(new ProgramFetchOneSummary(id));
    return program;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  private final QueryGateway queryGateway;
  private final CommandGateway commandGateway;
  private final QueryUpdateEmitter queryUpdateEmitter;
  private Program program = null;
  private static final Logger LOGGER = Logger.getLogger(ProgramBusinessDelegate.class.getName());
}
