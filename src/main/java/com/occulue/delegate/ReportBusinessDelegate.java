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
 * Report business delegate class.
 *
 * <p>This class implements the Business Delegate design pattern for the purpose of:
 *
 * <ol>
 *   <li>Reducing coupling between the business tier and a client of the business tier by hiding all
 *       business-tier implementation details
 *   <li>Improving the available of Report related services in the case of a Report business related
 *       service failing.
 *   <li>Exposes a simpler, uniform Report interface to the business tier, making it easy for
 *       clients to consume a simple Java object.
 *   <li>Hides the communication protocol that may be required to fulfill Report business related
 *       services.
 * </ol>
 *
 * <p>
 *
 * @author your_name_here
 */
public class ReportBusinessDelegate extends NotifierBusinessDelegate {
  // ************************************************************************
  // Public Methods
  // ************************************************************************
  /** Default Constructor */
  public ReportBusinessDelegate() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
    commandGateway = applicationContext.getBean(CommandGateway.class);
    queryUpdateEmitter = applicationContext.getBean(QueryUpdateEmitter.class);
  }

  /**
   * Report Business Delegate Factory Method
   *
   * <p>All methods are expected to be self-sufficient.
   *
   * @return ReportBusinessDelegate
   */
  public static ReportBusinessDelegate getReportInstance() {
    return (new ReportBusinessDelegate());
  }

  /**
   * Creates the provided command.
   *
   * @param command ${class.getCreateCommandAlias()}
   * @exception ProcessingException
   * @exception IllegalArgumentException
   * @return CompletableFuture<UUID>
   */
  public CompletableFuture<UUID> createReport(CreateReportCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<UUID> completableFuture = null;

    try {
      // --------------------------------------
      // assign identity now if none
      // --------------------------------------
      if (command.getReportId() == null) command.setReportId(UUID.randomUUID());

      // --------------------------------------
      // validate the command
      // --------------------------------------
      ReportValidator.getInstance().validate(command);

      // ---------------------------------------
      // issue the CreateReportCommand - by convention the future return value for a create command
      // that is handled by the constructor of an aggregate will return the UUID
      // ---------------------------------------
      completableFuture = commandGateway.send(command);

      LOGGER.log(
          Level.INFO,
          "return from Command Gateway for CreateReportCommand of Report is " + command);

    } catch (Exception exc) {
      final String errMsg = "Unable to create Report - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Update the provided command.
   *
   * @param command UpdateReportCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   * @exception IllegalArgumentException
   */
  public CompletableFuture<Void> updateReport(UpdateReportCommand command)
      throws ProcessingException, IllegalArgumentException {
    CompletableFuture<Void> completableFuture = null;

    try {

      // --------------------------------------
      // validate
      // --------------------------------------
      ReportValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the UpdateReportCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to save Report - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    }

    return completableFuture;
  }

  /**
   * Deletes the associatied value object
   *
   * @param command DeleteReportCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   */
  public CompletableFuture<Void> delete(DeleteReportCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<Void> completableFuture = null;

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      ReportValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the DeleteReportCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to delete Report using Id = " + command.getReportId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Method to retrieve the Report via ReportFetchOneSummary
   *
   * @param summary ReportFetchOneSummary
   * @return ReportFetchOneResponse
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  public Report getReport(ReportFetchOneSummary summary)
      throws ProcessingException, IllegalArgumentException {

    if (summary == null)
      throw new IllegalArgumentException("ReportFetchOneSummary arg cannot be null");

    Report entity = null;

    try {
      // --------------------------------------
      // validate the fetch one summary
      // --------------------------------------
      ReportValidator.getInstance().validate(summary);

      // --------------------------------------
      // use queryGateway to send request to Find a Report
      // --------------------------------------
      CompletableFuture<Report> futureEntity =
          queryGateway.query(
              new FindReportQuery(new LoadReportFilter(summary.getReportId())),
              ResponseTypes.instanceOf(Report.class));

      entity = futureEntity.get();
    } catch (Exception exc) {
      final String errMsg = "Unable to locate Report with id " + summary.getReportId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return entity;
  }

  /**
   * Method to retrieve a collection of all Reports
   *
   * @return List<Report>
   * @exception ProcessingException Thrown if any problems
   */
  public List<Report> getAllReport() throws ProcessingException {
    List<Report> list = null;

    try {
      CompletableFuture<List<Report>> futureList =
          queryGateway.query(
              new FindAllReportQuery(), ResponseTypes.multipleInstancesOf(Report.class));

      list = futureList.get();
    } catch (Exception exc) {
      String errMsg = "Failed to get all Report";
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return list;
  }

  /**
   * assign Program on Report
   *
   * @param command AssignProgramToReportCommand
   * @exception ProcessingException
   */
  public void assignProgram(AssignProgramToReportCommand command) throws ProcessingException {

    // --------------------------------------------
    // load the parent
    // --------------------------------------------
    load(command.getReportId());

    ProgramBusinessDelegate childDelegate = ProgramBusinessDelegate.getProgramInstance();
    ReportBusinessDelegate parentDelegate = ReportBusinessDelegate.getReportInstance();
    UUID childId = command.getAssignment().getProgramId();
    Program child = null;

    try {
      // --------------------------------------
      // best to validate the command now
      // --------------------------------------
      ReportValidator.getInstance().validate(command);

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
   * unAssign Program on Report
   *
   * @param command UnAssignProgramFromReportCommand
   * @exception ProcessingException
   */
  public void unAssignProgram(UnAssignProgramFromReportCommand command) throws ProcessingException {

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      ReportValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to unassign Program on Report";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * assign Event on Report
   *
   * @param command AssignEventToReportCommand
   * @exception ProcessingException
   */
  public void assignEvent(AssignEventToReportCommand command) throws ProcessingException {

    // --------------------------------------------
    // load the parent
    // --------------------------------------------
    load(command.getReportId());

    EventBusinessDelegate childDelegate = EventBusinessDelegate.getEventInstance();
    ReportBusinessDelegate parentDelegate = ReportBusinessDelegate.getReportInstance();
    UUID childId = command.getAssignment().getEventId();
    Event child = null;

    try {
      // --------------------------------------
      // best to validate the command now
      // --------------------------------------
      ReportValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);

    } catch (Throwable exc) {
      final String msg = "Failed to get Event using id " + childId;
      LOGGER.log(Level.WARNING, msg);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * unAssign Event on Report
   *
   * @param command UnAssignEventFromReportCommand
   * @exception ProcessingException
   */
  public void unAssignEvent(UnAssignEventFromReportCommand command) throws ProcessingException {

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      ReportValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to unassign Event on Report";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * assign Intervals on Report
   *
   * @param command AssignIntervalsToReportCommand
   * @exception ProcessingException
   */
  public void assignIntervals(AssignIntervalsToReportCommand command) throws ProcessingException {

    // --------------------------------------------
    // load the parent
    // --------------------------------------------
    load(command.getReportId());

    IntervalBusinessDelegate childDelegate = IntervalBusinessDelegate.getIntervalInstance();
    ReportBusinessDelegate parentDelegate = ReportBusinessDelegate.getReportInstance();
    UUID childId = command.getAssignment().getIntervalId();
    Interval child = null;

    try {
      // --------------------------------------
      // best to validate the command now
      // --------------------------------------
      ReportValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);

    } catch (Throwable exc) {
      final String msg = "Failed to get Interval using id " + childId;
      LOGGER.log(Level.WARNING, msg);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * unAssign Intervals on Report
   *
   * @param command UnAssignIntervalsFromReportCommand
   * @exception ProcessingException
   */
  public void unAssignIntervals(UnAssignIntervalsFromReportCommand command)
      throws ProcessingException {

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      ReportValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to unassign Intervals on Report";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * assign IntervalPeriod on Report
   *
   * @param command AssignIntervalPeriodToReportCommand
   * @exception ProcessingException
   */
  public void assignIntervalPeriod(AssignIntervalPeriodToReportCommand command)
      throws ProcessingException {

    // --------------------------------------------
    // load the parent
    // --------------------------------------------
    load(command.getReportId());

    IntervalPeriodBusinessDelegate childDelegate =
        IntervalPeriodBusinessDelegate.getIntervalPeriodInstance();
    ReportBusinessDelegate parentDelegate = ReportBusinessDelegate.getReportInstance();
    UUID childId = command.getAssignment().getIntervalPeriodId();
    IntervalPeriod child = null;

    try {
      // --------------------------------------
      // best to validate the command now
      // --------------------------------------
      ReportValidator.getInstance().validate(command);

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
   * unAssign IntervalPeriod on Report
   *
   * @param command UnAssignIntervalPeriodFromReportCommand
   * @exception ProcessingException
   */
  public void unAssignIntervalPeriod(UnAssignIntervalPeriodFromReportCommand command)
      throws ProcessingException {

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      ReportValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to unassign IntervalPeriod on Report";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * add PayloadDescriptor to PayloadDescriptors
   *
   * @param command AssignPayloadDescriptorsToReportCommand
   * @exception ProcessingException
   */
  public void addToPayloadDescriptors(AssignPayloadDescriptorsToReportCommand command)
      throws ProcessingException {

    // -------------------------------------------
    // load the parent
    // -------------------------------------------
    load(command.getReportId());

    PayloadDescriptorBusinessDelegate childDelegate =
        PayloadDescriptorBusinessDelegate.getPayloadDescriptorInstance();
    ReportBusinessDelegate parentDelegate = ReportBusinessDelegate.getReportInstance();
    UUID childId = command.getAddTo().getPayloadDescriptorId();

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      ReportValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to add a PayloadDescriptor as PayloadDescriptors to Report";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * remove PayloadDescriptor from PayloadDescriptors
   *
   * @param command RemovePayloadDescriptorsFromReportCommand
   * @exception ProcessingException
   */
  public void removeFromPayloadDescriptors(RemovePayloadDescriptorsFromReportCommand command)
      throws ProcessingException {

    PayloadDescriptorBusinessDelegate childDelegate =
        PayloadDescriptorBusinessDelegate.getPayloadDescriptorInstance();
    UUID childId = command.getRemoveFrom().getPayloadDescriptorId();

    try {

      // --------------------------------------
      // validate the command
      // --------------------------------------
      ReportValidator.getInstance().validate(command);

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
   * @param command AssignResourcesToReportCommand
   * @exception ProcessingException
   */
  public void addToResources(AssignResourcesToReportCommand command) throws ProcessingException {

    // -------------------------------------------
    // load the parent
    // -------------------------------------------
    load(command.getReportId());

    ResourceBusinessDelegate childDelegate = ResourceBusinessDelegate.getResourceInstance();
    ReportBusinessDelegate parentDelegate = ReportBusinessDelegate.getReportInstance();
    UUID childId = command.getAddTo().getResourceId();

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      ReportValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to add a Resource as Resources to Report";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * remove Resource from Resources
   *
   * @param command RemoveResourcesFromReportCommand
   * @exception ProcessingException
   */
  public void removeFromResources(RemoveResourcesFromReportCommand command)
      throws ProcessingException {

    ResourceBusinessDelegate childDelegate = ResourceBusinessDelegate.getResourceInstance();
    UUID childId = command.getRemoveFrom().getResourceId();

    try {

      // --------------------------------------
      // validate the command
      // --------------------------------------
      ReportValidator.getInstance().validate(command);

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
   * @return Report
   */
  private Report load(UUID id) throws ProcessingException {
    report = ReportBusinessDelegate.getReportInstance().getReport(new ReportFetchOneSummary(id));
    return report;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  private final QueryGateway queryGateway;
  private final CommandGateway commandGateway;
  private final QueryUpdateEmitter queryUpdateEmitter;
  private Report report = null;
  private static final Logger LOGGER = Logger.getLogger(ReportBusinessDelegate.class.getName());
}
