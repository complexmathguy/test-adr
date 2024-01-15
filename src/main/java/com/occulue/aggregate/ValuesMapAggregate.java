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
 * Aggregate handler for ValuesMap as outlined for the CQRS pattern, all write responsibilities
 * related to ValuesMap are handled here.
 *
 * @author your_name_here
 */
@Aggregate
public class ValuesMapAggregate {

  // -----------------------------------------
  // Axon requires an empty constructor
  // -----------------------------------------
  public ValuesMapAggregate() {}

  // ----------------------------------------------
  // intrinsic command handlers
  // ----------------------------------------------
  @CommandHandler
  public ValuesMapAggregate(CreateValuesMapCommand command) throws Exception {
    LOGGER.info("Handling command CreateValuesMapCommand");
    CreateValuesMapEvent event =
        new CreateValuesMapEvent(command.getValuesMapId(), command.getType(), command.getValues());

    apply(event);
  }

  @CommandHandler
  public void handle(UpdateValuesMapCommand command) throws Exception {
    LOGGER.info("handling command UpdateValuesMapCommand");
    UpdateValuesMapEvent event =
        new UpdateValuesMapEvent(command.getValuesMapId(), command.getType(), command.getValues());

    apply(event);
  }

  @CommandHandler
  public void handle(DeleteValuesMapCommand command) throws Exception {
    LOGGER.info("Handling command DeleteValuesMapCommand");
    apply(new DeleteValuesMapEvent(command.getValuesMapId()));
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
  void on(CreateValuesMapEvent event) {
    LOGGER.info("Event sourcing CreateValuesMapEvent");
    this.valuesMapId = event.getValuesMapId();
    this.type = event.getType();
    this.values = event.getValues();
  }

  @EventSourcingHandler
  void on(UpdateValuesMapEvent event) {
    LOGGER.info("Event sourcing classObject.getUpdateEventAlias()}");
    this.type = event.getType();
    this.values = event.getValues();
  }

  // ----------------------------------------------
  // association event source handlers
  // ----------------------------------------------

  // ------------------------------------------
  // attributes
  // ------------------------------------------

  @AggregateIdentifier private UUID valuesMapId;

  private String type;
  private ArrayList<String> values;

  private static final Logger LOGGER = Logger.getLogger(ValuesMapAggregate.class.getName());
}
