package com.occulue.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import com.occulue.api.*;
import com.occulue.entity.*;
import com.occulue.exception.*;
import java.util.*;
import java.util.logging.Logger;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

/**
 * Aggregate handler for ReportDescriptor as outlined for the CQRS pattern, all write
 * responsibilities related to ReportDescriptor are handled here.
 *
 * @author your_name_here
 */
@Aggregate
public class ReportDescriptorAggregate {

  // -----------------------------------------
  // Axon requires an empty constructor
  // -----------------------------------------
  public ReportDescriptorAggregate() {}

  // ----------------------------------------------
  // intrinsic command handlers
  // ----------------------------------------------
  @CommandHandler
  public ReportDescriptorAggregate(CreateReportDescriptorCommand command) throws Exception {
    LOGGER.info("Handling command CreateReportDescriptorCommand");
    CreateReportDescriptorEvent event =
        new CreateReportDescriptorEvent(
            command.getReportDescriptorId(),
            command.getPayloadType(),
            command.getReadingType(),
            command.getUnits(),
            command.getAggregate(),
            command.getStartInterval(),
            command.getNumIntervals(),
            command.getHistorical(),
            command.getFrequency(),
            command.getRepeat());

    apply(event);
  }

  @CommandHandler
  public void handle(UpdateReportDescriptorCommand command) throws Exception {
    LOGGER.info("handling command UpdateReportDescriptorCommand");
    UpdateReportDescriptorEvent event =
        new UpdateReportDescriptorEvent(
            command.getReportDescriptorId(),
            command.getPayloadType(),
            command.getReadingType(),
            command.getUnits(),
            command.getAggregate(),
            command.getStartInterval(),
            command.getNumIntervals(),
            command.getHistorical(),
            command.getFrequency(),
            command.getRepeat(),
            command.getTargets());

    apply(event);
  }

  @CommandHandler
  public void handle(DeleteReportDescriptorCommand command) throws Exception {
    LOGGER.info("Handling command DeleteReportDescriptorCommand");
    apply(new DeleteReportDescriptorEvent(command.getReportDescriptorId()));
  }

  // ----------------------------------------------
  // association command handlers
  // ----------------------------------------------

  // single association commands
  @CommandHandler
  public void handle(AssignTargetsToReportDescriptorCommand command) throws Exception {
    LOGGER.info("Handling command AssignTargetsToReportDescriptorCommand");

    if (targets != null && targets.getValuesMapId() == command.getAssignment().getValuesMapId())
      throw new ProcessingException(
          "Targets already assigned with id " + command.getAssignment().getValuesMapId());

    apply(
        new AssignTargetsToReportDescriptorEvent(
            command.getReportDescriptorId(), command.getAssignment()));
  }

  @CommandHandler
  public void handle(UnAssignTargetsFromReportDescriptorCommand command) throws Exception {
    LOGGER.info("Handlign command UnAssignTargetsFromReportDescriptorCommand");

    if (targets == null) throw new ProcessingException("Targets already has nothing assigned.");

    apply(new UnAssignTargetsFromReportDescriptorEvent(command.getReportDescriptorId()));
  }

  // multiple association commands

  // ----------------------------------------------
  // intrinsic event source handlers
  // ----------------------------------------------
  @EventSourcingHandler
  void on(CreateReportDescriptorEvent event) {
    LOGGER.info("Event sourcing CreateReportDescriptorEvent");
    this.reportDescriptorId = event.getReportDescriptorId();
    this.payloadType = event.getPayloadType();
    this.readingType = event.getReadingType();
    this.units = event.getUnits();
    this.aggregate = event.getAggregate();
    this.startInterval = event.getStartInterval();
    this.numIntervals = event.getNumIntervals();
    this.historical = event.getHistorical();
    this.frequency = event.getFrequency();
    this.repeat = event.getRepeat();
  }

  @EventSourcingHandler
  void on(UpdateReportDescriptorEvent event) {
    LOGGER.info("Event sourcing classObject.getUpdateEventAlias()}");
    this.payloadType = event.getPayloadType();
    this.readingType = event.getReadingType();
    this.units = event.getUnits();
    this.aggregate = event.getAggregate();
    this.startInterval = event.getStartInterval();
    this.numIntervals = event.getNumIntervals();
    this.historical = event.getHistorical();
    this.frequency = event.getFrequency();
    this.repeat = event.getRepeat();
    this.targets = event.getTargets();
  }

  // ----------------------------------------------
  // association event source handlers
  // ----------------------------------------------
  // single associations
  @EventSourcingHandler
  void on(AssignTargetsToReportDescriptorEvent event) {
    LOGGER.info("Event sourcing AssignTargetsToReportDescriptorEvent");
    this.targets = event.getAssignment();
  }

  @EventSourcingHandler
  void on(UnAssignTargetsFromReportDescriptorEvent event) {
    LOGGER.info("Event sourcing UnAssignTargetsFromReportDescriptorEvent");
    this.targets = null;
  }

  // ------------------------------------------
  // attributes
  // ------------------------------------------

  @AggregateIdentifier private UUID reportDescriptorId;

  private String payloadType;
  private String readingType;
  private String units;
  private Boolean aggregate;
  private Integer startInterval;
  private Integer numIntervals;
  private Boolean historical;
  private Integer frequency;
  private Integer repeat;
  private ValuesMap targets = null;

  private static final Logger LOGGER = Logger.getLogger(ReportDescriptorAggregate.class.getName());
}
