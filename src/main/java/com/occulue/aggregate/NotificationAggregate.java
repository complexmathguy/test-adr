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
 * Aggregate handler for Notification as outlined for the CQRS pattern, all write responsibilities
 * related to Notification are handled here.
 *
 * @author your_name_here
 */
@Aggregate
public class NotificationAggregate {

  // -----------------------------------------
  // Axon requires an empty constructor
  // -----------------------------------------
  public NotificationAggregate() {}

  // ----------------------------------------------
  // intrinsic command handlers
  // ----------------------------------------------
  @CommandHandler
  public NotificationAggregate(CreateNotificationCommand command) throws Exception {
    LOGGER.info("Handling command CreateNotificationCommand");
    CreateNotificationEvent event =
        new CreateNotificationEvent(
            command.getNotificationId(), command.getObjectType(), command.getOperation());

    apply(event);
  }

  @CommandHandler
  public void handle(UpdateNotificationCommand command) throws Exception {
    LOGGER.info("handling command UpdateNotificationCommand");
    UpdateNotificationEvent event =
        new UpdateNotificationEvent(
            command.getNotificationId(),
            command.getTargets(),
            command.getObjectType(),
            command.getOperation(),
            command.getNotifier());

    apply(event);
  }

  @CommandHandler
  public void handle(DeleteNotificationCommand command) throws Exception {
    LOGGER.info("Handling command DeleteNotificationCommand");
    apply(new DeleteNotificationEvent(command.getNotificationId()));
  }

  // ----------------------------------------------
  // association command handlers
  // ----------------------------------------------

  // single association commands
  @CommandHandler
  public void handle(AssignTargetsToNotificationCommand command) throws Exception {
    LOGGER.info("Handling command AssignTargetsToNotificationCommand");

    if (targets != null && targets.getValuesMapId() == command.getAssignment().getValuesMapId())
      throw new ProcessingException(
          "Targets already assigned with id " + command.getAssignment().getValuesMapId());

    apply(
        new AssignTargetsToNotificationEvent(command.getNotificationId(), command.getAssignment()));
  }

  @CommandHandler
  public void handle(UnAssignTargetsFromNotificationCommand command) throws Exception {
    LOGGER.info("Handlign command UnAssignTargetsFromNotificationCommand");

    if (targets == null) throw new ProcessingException("Targets already has nothing assigned.");

    apply(new UnAssignTargetsFromNotificationEvent(command.getNotificationId()));
  }

  @CommandHandler
  public void handle(AssignNotifierToNotificationCommand command) throws Exception {
    LOGGER.info("Handling command AssignNotifierToNotificationCommand");

    if (notifier != null && notifier.getNotifierId() == command.getAssignment().getNotifierId())
      throw new ProcessingException(
          "Notifier already assigned with id " + command.getAssignment().getNotifierId());

    apply(
        new AssignNotifierToNotificationEvent(
            command.getNotificationId(), command.getAssignment()));
  }

  @CommandHandler
  public void handle(UnAssignNotifierFromNotificationCommand command) throws Exception {
    LOGGER.info("Handlign command UnAssignNotifierFromNotificationCommand");

    if (notifier == null) throw new ProcessingException("Notifier already has nothing assigned.");

    apply(new UnAssignNotifierFromNotificationEvent(command.getNotificationId()));
  }

  // multiple association commands

  // ----------------------------------------------
  // intrinsic event source handlers
  // ----------------------------------------------
  @EventSourcingHandler
  void on(CreateNotificationEvent event) {
    LOGGER.info("Event sourcing CreateNotificationEvent");
    this.notificationId = event.getNotificationId();
    this.objectType = event.getObjectType();
    this.operation = event.getOperation();
  }

  @EventSourcingHandler
  void on(UpdateNotificationEvent event) {
    LOGGER.info("Event sourcing classObject.getUpdateEventAlias()}");
    this.targets = event.getTargets();
    this.objectType = event.getObjectType();
    this.operation = event.getOperation();
    this.notifier = event.getNotifier();
  }

  // ----------------------------------------------
  // association event source handlers
  // ----------------------------------------------
  // single associations
  @EventSourcingHandler
  void on(AssignTargetsToNotificationEvent event) {
    LOGGER.info("Event sourcing AssignTargetsToNotificationEvent");
    this.targets = event.getAssignment();
  }

  @EventSourcingHandler
  void on(UnAssignTargetsFromNotificationEvent event) {
    LOGGER.info("Event sourcing UnAssignTargetsFromNotificationEvent");
    this.targets = null;
  }
  // single associations
  @EventSourcingHandler
  void on(AssignNotifierToNotificationEvent event) {
    LOGGER.info("Event sourcing AssignNotifierToNotificationEvent");
    this.notifier = event.getAssignment();
  }

  @EventSourcingHandler
  void on(UnAssignNotifierFromNotificationEvent event) {
    LOGGER.info("Event sourcing UnAssignNotifierFromNotificationEvent");
    this.notifier = null;
  }

  // ------------------------------------------
  // attributes
  // ------------------------------------------

  @AggregateIdentifier private UUID notificationId;

  private ObjectType objectType;
  private Operation operation;
  private ValuesMap targets = null;
  private Notifier notifier = null;

  private static final Logger LOGGER = Logger.getLogger(NotificationAggregate.class.getName());
}
