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
 * Aggregate handler for Problem as outlined for the CQRS pattern, all write responsibilities
 * related to Problem are handled here.
 *
 * @author your_name_here
 */
@Aggregate
public class ProblemAggregate {

  // -----------------------------------------
  // Axon requires an empty constructor
  // -----------------------------------------
  public ProblemAggregate() {}

  // ----------------------------------------------
  // intrinsic command handlers
  // ----------------------------------------------
  @CommandHandler
  public ProblemAggregate(CreateProblemCommand command) throws Exception {
    LOGGER.info("Handling command CreateProblemCommand");
    CreateProblemEvent event =
        new CreateProblemEvent(
            command.getProblemId(),
            command.getType(),
            command.getTitle(),
            command.getStatus(),
            command.getDetail(),
            command.getInstance());

    apply(event);
  }

  @CommandHandler
  public void handle(UpdateProblemCommand command) throws Exception {
    LOGGER.info("handling command UpdateProblemCommand");
    UpdateProblemEvent event =
        new UpdateProblemEvent(
            command.getProblemId(),
            command.getType(),
            command.getTitle(),
            command.getStatus(),
            command.getDetail(),
            command.getInstance());

    apply(event);
  }

  @CommandHandler
  public void handle(DeleteProblemCommand command) throws Exception {
    LOGGER.info("Handling command DeleteProblemCommand");
    apply(new DeleteProblemEvent(command.getProblemId()));
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
  void on(CreateProblemEvent event) {
    LOGGER.info("Event sourcing CreateProblemEvent");
    this.problemId = event.getProblemId();
    this.type = event.getType();
    this.title = event.getTitle();
    this.status = event.getStatus();
    this.detail = event.getDetail();
    this.instance = event.getInstance();
  }

  @EventSourcingHandler
  void on(UpdateProblemEvent event) {
    LOGGER.info("Event sourcing classObject.getUpdateEventAlias()}");
    this.type = event.getType();
    this.title = event.getTitle();
    this.status = event.getStatus();
    this.detail = event.getDetail();
    this.instance = event.getInstance();
  }

  // ----------------------------------------------
  // association event source handlers
  // ----------------------------------------------

  // ------------------------------------------
  // attributes
  // ------------------------------------------

  @AggregateIdentifier private UUID problemId;

  private String type;
  private String title;
  private Integer status;
  private String detail;
  private String instance;

  private static final Logger LOGGER = Logger.getLogger(ProblemAggregate.class.getName());
}
