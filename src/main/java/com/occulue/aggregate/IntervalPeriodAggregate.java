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
 * Aggregate handler for IntervalPeriod as outlined for the CQRS pattern, all write responsibilities
 * related to IntervalPeriod are handled here.
 *
 * @author your_name_here
 */
@Aggregate
public class IntervalPeriodAggregate {

  // -----------------------------------------
  // Axon requires an empty constructor
  // -----------------------------------------
  public IntervalPeriodAggregate() {}

  // ----------------------------------------------
  // intrinsic command handlers
  // ----------------------------------------------
  @CommandHandler
  public IntervalPeriodAggregate(CreateIntervalPeriodCommand command) throws Exception {
    LOGGER.info("Handling command CreateIntervalPeriodCommand");
    CreateIntervalPeriodEvent event =
        new CreateIntervalPeriodEvent(
            command.getIntervalPeriodId(),
            command.getStart(),
            command.getDuration(),
            command.getRandomizeStart());

    apply(event);
  }

  @CommandHandler
  public void handle(UpdateIntervalPeriodCommand command) throws Exception {
    LOGGER.info("handling command UpdateIntervalPeriodCommand");
    UpdateIntervalPeriodEvent event =
        new UpdateIntervalPeriodEvent(
            command.getIntervalPeriodId(),
            command.getStart(),
            command.getDuration(),
            command.getRandomizeStart());

    apply(event);
  }

  @CommandHandler
  public void handle(DeleteIntervalPeriodCommand command) throws Exception {
    LOGGER.info("Handling command DeleteIntervalPeriodCommand");
    apply(new DeleteIntervalPeriodEvent(command.getIntervalPeriodId()));
  }

  // ----------------------------------------------
  // association command handlers
  // ----------------------------------------------

  // single association commands

  // multiple association commands

  // ----------------------------------------------
  // intrinsic event source handlers
  // ----------------------------------------------
  @EventSourcingHandler
  void on(CreateIntervalPeriodEvent event) {
    LOGGER.info("Event sourcing CreateIntervalPeriodEvent");
    this.intervalPeriodId = event.getIntervalPeriodId();
    this.start = event.getStart();
    this.duration = event.getDuration();
    this.randomizeStart = event.getRandomizeStart();
  }

  @EventSourcingHandler
  void on(UpdateIntervalPeriodEvent event) {
    LOGGER.info("Event sourcing classObject.getUpdateEventAlias()}");
    this.start = event.getStart();
    this.duration = event.getDuration();
    this.randomizeStart = event.getRandomizeStart();
  }

  // ----------------------------------------------
  // association event source handlers
  // ----------------------------------------------

  // ------------------------------------------
  // attributes
  // ------------------------------------------

  @AggregateIdentifier private UUID intervalPeriodId;

  private Date start;
  private Duration duration;
  private Duration randomizeStart;
  private Program intervalPeriod = null;

  private static final Logger LOGGER = Logger.getLogger(IntervalPeriodAggregate.class.getName());
}
