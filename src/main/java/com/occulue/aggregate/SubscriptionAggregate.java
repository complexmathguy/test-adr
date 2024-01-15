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
 * Aggregate handler for Subscription as outlined for the CQRS pattern, all write responsibilities
 * related to Subscription are handled here.
 *
 * @author your_name_here
 */
@Aggregate
public class SubscriptionAggregate {

  // -----------------------------------------
  // Axon requires an empty constructor
  // -----------------------------------------
  public SubscriptionAggregate() {}

  // ----------------------------------------------
  // intrinsic command handlers
  // ----------------------------------------------
  @CommandHandler
  public SubscriptionAggregate(CreateSubscriptionCommand command) throws Exception {
    LOGGER.info("Handling command CreateSubscriptionCommand");
    CreateSubscriptionEvent event =
        new CreateSubscriptionEvent(
            command.getSubscriptionId(),
            command.getCreatedDateTime(),
            command.getModificationDateTime(),
            command.getClientName(),
            command.getObjectType());

    apply(event);
  }

  @CommandHandler
  public void handle(UpdateSubscriptionCommand command) throws Exception {
    LOGGER.info("handling command UpdateSubscriptionCommand");
    UpdateSubscriptionEvent event =
        new UpdateSubscriptionEvent(
            command.getSubscriptionId(),
            command.getCreatedDateTime(),
            command.getModificationDateTime(),
            command.getClientName(),
            command.getProgram(),
            command.getObjectOperations(),
            command.getTargets(),
            command.getObjectType());

    apply(event);
  }

  @CommandHandler
  public void handle(DeleteSubscriptionCommand command) throws Exception {
    LOGGER.info("Handling command DeleteSubscriptionCommand");
    apply(new DeleteSubscriptionEvent(command.getSubscriptionId()));
  }

  // ----------------------------------------------
  // association command handlers
  // ----------------------------------------------

  // single association commands
  @CommandHandler
  public void handle(AssignProgramToSubscriptionCommand command) throws Exception {
    LOGGER.info("Handling command AssignProgramToSubscriptionCommand");

    if (program != null && program.getProgramId() == command.getAssignment().getProgramId())
      throw new ProcessingException(
          "Program already assigned with id " + command.getAssignment().getProgramId());

    apply(
        new AssignProgramToSubscriptionEvent(command.getSubscriptionId(), command.getAssignment()));
  }

  @CommandHandler
  public void handle(UnAssignProgramFromSubscriptionCommand command) throws Exception {
    LOGGER.info("Handlign command UnAssignProgramFromSubscriptionCommand");

    if (program == null) throw new ProcessingException("Program already has nothing assigned.");

    apply(new UnAssignProgramFromSubscriptionEvent(command.getSubscriptionId()));
  }

  @CommandHandler
  public void handle(AssignTargetsToSubscriptionCommand command) throws Exception {
    LOGGER.info("Handling command AssignTargetsToSubscriptionCommand");

    if (targets != null && targets.getValuesMapId() == command.getAssignment().getValuesMapId())
      throw new ProcessingException(
          "Targets already assigned with id " + command.getAssignment().getValuesMapId());

    apply(
        new AssignTargetsToSubscriptionEvent(command.getSubscriptionId(), command.getAssignment()));
  }

  @CommandHandler
  public void handle(UnAssignTargetsFromSubscriptionCommand command) throws Exception {
    LOGGER.info("Handlign command UnAssignTargetsFromSubscriptionCommand");

    if (targets == null) throw new ProcessingException("Targets already has nothing assigned.");

    apply(new UnAssignTargetsFromSubscriptionEvent(command.getSubscriptionId()));
  }

  // multiple association commands
  @CommandHandler
  public void handle(AssignObjectOperationsToSubscriptionCommand command) throws Exception {
    LOGGER.info("Handling command AssignObjectOperationsToSubscriptionCommand");

    if (objectOperations.contains(command.getAddTo()))
      throw new ProcessingException(
          "ObjectOperations already contains an entity with id "
              + command.getAddTo().getObjectOperationId());

    apply(
        new AssignObjectOperationsToSubscriptionEvent(
            command.getSubscriptionId(), command.getAddTo()));
  }

  @CommandHandler
  public void handle(RemoveObjectOperationsFromSubscriptionCommand command) throws Exception {
    LOGGER.info("Handling command RemoveObjectOperationsFromSubscriptionCommand");

    if (!objectOperations.contains(command.getRemoveFrom()))
      throw new ProcessingException(
          "ObjectOperations does not contain an entity with id "
              + command.getRemoveFrom().getObjectOperationId());

    apply(
        new RemoveObjectOperationsFromSubscriptionEvent(
            command.getSubscriptionId(), command.getRemoveFrom()));
  }

  // ----------------------------------------------
  // intrinsic event source handlers
  // ----------------------------------------------
  @EventSourcingHandler
  void on(CreateSubscriptionEvent event) {
    LOGGER.info("Event sourcing CreateSubscriptionEvent");
    this.subscriptionId = event.getSubscriptionId();
    this.createdDateTime = event.getCreatedDateTime();
    this.modificationDateTime = event.getModificationDateTime();
    this.clientName = event.getClientName();
    this.objectType = event.getObjectType();
  }

  @EventSourcingHandler
  void on(UpdateSubscriptionEvent event) {
    LOGGER.info("Event sourcing classObject.getUpdateEventAlias()}");
    this.createdDateTime = event.getCreatedDateTime();
    this.modificationDateTime = event.getModificationDateTime();
    this.clientName = event.getClientName();
    this.program = event.getProgram();
    this.objectOperations = event.getObjectOperations();
    this.targets = event.getTargets();
    this.objectType = event.getObjectType();
  }

  // ----------------------------------------------
  // association event source handlers
  // ----------------------------------------------
  // single associations
  @EventSourcingHandler
  void on(AssignProgramToSubscriptionEvent event) {
    LOGGER.info("Event sourcing AssignProgramToSubscriptionEvent");
    this.program = event.getAssignment();
  }

  @EventSourcingHandler
  void on(UnAssignProgramFromSubscriptionEvent event) {
    LOGGER.info("Event sourcing UnAssignProgramFromSubscriptionEvent");
    this.program = null;
  }
  // single associations
  @EventSourcingHandler
  void on(AssignTargetsToSubscriptionEvent event) {
    LOGGER.info("Event sourcing AssignTargetsToSubscriptionEvent");
    this.targets = event.getAssignment();
  }

  @EventSourcingHandler
  void on(UnAssignTargetsFromSubscriptionEvent event) {
    LOGGER.info("Event sourcing UnAssignTargetsFromSubscriptionEvent");
    this.targets = null;
  }

  // multiple associations
  @EventSourcingHandler
  void on(AssignObjectOperationsToSubscriptionEvent event) {
    LOGGER.info("Event sourcing AssignObjectOperationsToSubscriptionEvent");
    this.objectOperations.add(event.getAddTo());
  }

  @EventSourcingHandler
  void on(RemoveObjectOperationsFromSubscriptionEvent event) {
    LOGGER.info("Event sourcing RemoveObjectOperationsFromSubscriptionEvent");
    this.objectOperations.remove(event.getRemoveFrom());
  }

  // ------------------------------------------
  // attributes
  // ------------------------------------------

  @AggregateIdentifier private UUID subscriptionId;

  private Date createdDateTime;
  private Date modificationDateTime;
  private String clientName;
  private ObjectType objectType;
  private Program program = null;
  private Set<ObjectOperation> objectOperations = new HashSet<>();
  private ValuesMap targets = null;

  private static final Logger LOGGER = Logger.getLogger(SubscriptionAggregate.class.getName());
}
