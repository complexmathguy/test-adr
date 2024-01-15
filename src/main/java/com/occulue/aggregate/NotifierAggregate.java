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
 * Aggregate handler for Notifier as outlined for the CQRS pattern, all write responsibilities
 * related to Notifier are handled here.
 *
 * @author your_name_here
 */
@Aggregate
public class NotifierAggregate {

  // -----------------------------------------
  // Axon requires an empty constructor
  // -----------------------------------------
  public NotifierAggregate() {}

  // ----------------------------------------------
  // intrinsic command handlers
  // ----------------------------------------------
  @CommandHandler
  public NotifierAggregate(CreateNotifierCommand command) throws Exception {
    LOGGER.info("Handling command CreateNotifierCommand");
    CreateNotifierEvent event = new CreateNotifierEvent(command.getNotifierId());

    apply(event);
  }

  @CommandHandler
  public void handle(UpdateNotifierCommand command) throws Exception {
    LOGGER.info("handling command UpdateNotifierCommand");
    UpdateNotifierEvent event = new UpdateNotifierEvent(command.getNotifierId());

    apply(event);
  }

  @CommandHandler
  public void handle(DeleteNotifierCommand command) throws Exception {
    LOGGER.info("Handling command DeleteNotifierCommand");
    apply(new DeleteNotifierEvent(command.getNotifierId()));
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
  void on(CreateNotifierEvent event) {
    LOGGER.info("Event sourcing CreateNotifierEvent");
    this.notifierId = event.getNotifierId();
  }

  @EventSourcingHandler
  void on(UpdateNotifierEvent event) {
    LOGGER.info("Event sourcing classObject.getUpdateEventAlias()}");
  }

  // ----------------------------------------------
  // association event source handlers
  // ----------------------------------------------

  // ------------------------------------------
  // attributes
  // ------------------------------------------

  @AggregateIdentifier private UUID notifierId;

  private Notification notifier = null;

  private static final Logger LOGGER = Logger.getLogger(NotifierAggregate.class.getName());
}
