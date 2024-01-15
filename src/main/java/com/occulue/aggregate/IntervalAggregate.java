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
 * Aggregate handler for Interval as outlined for the CQRS pattern, all write responsibilities
 * related to Interval are handled here.
 *
 * @author your_name_here
 */
@Aggregate
public class IntervalAggregate {

  // -----------------------------------------
  // Axon requires an empty constructor
  // -----------------------------------------
  public IntervalAggregate() {}

  // ----------------------------------------------
  // intrinsic command handlers
  // ----------------------------------------------
  @CommandHandler
  public IntervalAggregate(CreateIntervalCommand command) throws Exception {
    LOGGER.info("Handling command CreateIntervalCommand");
    CreateIntervalEvent event = new CreateIntervalEvent(command.getIntervalId());

    apply(event);
  }

  @CommandHandler
  public void handle(UpdateIntervalCommand command) throws Exception {
    LOGGER.info("handling command UpdateIntervalCommand");
    UpdateIntervalEvent event =
        new UpdateIntervalEvent(
            command.getIntervalId(), command.getPayloads(), command.getIntervalPeriod());

    apply(event);
  }

  @CommandHandler
  public void handle(DeleteIntervalCommand command) throws Exception {
    LOGGER.info("Handling command DeleteIntervalCommand");
    apply(new DeleteIntervalEvent(command.getIntervalId()));
  }

  // ----------------------------------------------
  // association command handlers
  // ----------------------------------------------

  // single association commands
  @CommandHandler
  public void handle(AssignIntervalPeriodToIntervalCommand command) throws Exception {
    LOGGER.info("Handling command AssignIntervalPeriodToIntervalCommand");

    if (intervalPeriod != null
        && intervalPeriod.getIntervalPeriodId() == command.getAssignment().getIntervalPeriodId())
      throw new ProcessingException(
          "IntervalPeriod already assigned with id "
              + command.getAssignment().getIntervalPeriodId());

    apply(
        new AssignIntervalPeriodToIntervalEvent(command.getIntervalId(), command.getAssignment()));
  }

  @CommandHandler
  public void handle(UnAssignIntervalPeriodFromIntervalCommand command) throws Exception {
    LOGGER.info("Handlign command UnAssignIntervalPeriodFromIntervalCommand");

    if (intervalPeriod == null)
      throw new ProcessingException("IntervalPeriod already has nothing assigned.");

    apply(new UnAssignIntervalPeriodFromIntervalEvent(command.getIntervalId()));
  }

  // multiple association commands
  @CommandHandler
  public void handle(AssignPayloadsToIntervalCommand command) throws Exception {
    LOGGER.info("Handling command AssignPayloadsToIntervalCommand");

    if (payloads.contains(command.getAddTo()))
      throw new ProcessingException(
          "Payloads already contains an entity with id " + command.getAddTo().getValuesMapId());

    apply(new AssignPayloadsToIntervalEvent(command.getIntervalId(), command.getAddTo()));
  }

  @CommandHandler
  public void handle(RemovePayloadsFromIntervalCommand command) throws Exception {
    LOGGER.info("Handling command RemovePayloadsFromIntervalCommand");

    if (!payloads.contains(command.getRemoveFrom()))
      throw new ProcessingException(
          "Payloads does not contain an entity with id "
              + command.getRemoveFrom().getValuesMapId());

    apply(new RemovePayloadsFromIntervalEvent(command.getIntervalId(), command.getRemoveFrom()));
  }

  // ----------------------------------------------
  // intrinsic event source handlers
  // ----------------------------------------------
  @EventSourcingHandler
  void on(CreateIntervalEvent event) {
    LOGGER.info("Event sourcing CreateIntervalEvent");
    this.intervalId = event.getIntervalId();
  }

  @EventSourcingHandler
  void on(UpdateIntervalEvent event) {
    LOGGER.info("Event sourcing classObject.getUpdateEventAlias()}");
    this.payloads = event.getPayloads();
    this.intervalPeriod = event.getIntervalPeriod();
  }

  // ----------------------------------------------
  // association event source handlers
  // ----------------------------------------------
  // single associations
  @EventSourcingHandler
  void on(AssignIntervalPeriodToIntervalEvent event) {
    LOGGER.info("Event sourcing AssignIntervalPeriodToIntervalEvent");
    this.intervalPeriod = event.getAssignment();
  }

  @EventSourcingHandler
  void on(UnAssignIntervalPeriodFromIntervalEvent event) {
    LOGGER.info("Event sourcing UnAssignIntervalPeriodFromIntervalEvent");
    this.intervalPeriod = null;
  }

  // multiple associations
  @EventSourcingHandler
  void on(AssignPayloadsToIntervalEvent event) {
    LOGGER.info("Event sourcing AssignPayloadsToIntervalEvent");
    this.payloads.add(event.getAddTo());
  }

  @EventSourcingHandler
  void on(RemovePayloadsFromIntervalEvent event) {
    LOGGER.info("Event sourcing RemovePayloadsFromIntervalEvent");
    this.payloads.remove(event.getRemoveFrom());
  }

  // ------------------------------------------
  // attributes
  // ------------------------------------------

  @AggregateIdentifier private UUID intervalId;

  private Set<ValuesMap> payloads = new HashSet<>();
  private IntervalPeriod intervalPeriod = null;

  private static final Logger LOGGER = Logger.getLogger(IntervalAggregate.class.getName());
}
