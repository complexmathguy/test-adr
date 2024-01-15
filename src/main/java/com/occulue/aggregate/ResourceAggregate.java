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
 * Aggregate handler for Resource as outlined for the CQRS pattern, all write responsibilities
 * related to Resource are handled here.
 *
 * @author your_name_here
 */
@Aggregate
public class ResourceAggregate {

  // -----------------------------------------
  // Axon requires an empty constructor
  // -----------------------------------------
  public ResourceAggregate() {}

  // ----------------------------------------------
  // intrinsic command handlers
  // ----------------------------------------------
  @CommandHandler
  public ResourceAggregate(CreateResourceCommand command) throws Exception {
    LOGGER.info("Handling command CreateResourceCommand");
    CreateResourceEvent event =
        new CreateResourceEvent(
            command.getResourceId(),
            command.getCreatedDateTime(),
            command.getModificationDateTime(),
            command.getResourceName(),
            command.getObjectType());

    apply(event);
  }

  @CommandHandler
  public void handle(UpdateResourceCommand command) throws Exception {
    LOGGER.info("handling command UpdateResourceCommand");
    UpdateResourceEvent event =
        new UpdateResourceEvent(
            command.getResourceId(),
            command.getCreatedDateTime(),
            command.getModificationDateTime(),
            command.getResourceName(),
            command.getVen(),
            command.getAttributes(),
            command.getTargets(),
            command.getObjectType());

    apply(event);
  }

  @CommandHandler
  public void handle(DeleteResourceCommand command) throws Exception {
    LOGGER.info("Handling command DeleteResourceCommand");
    apply(new DeleteResourceEvent(command.getResourceId()));
  }

  // ----------------------------------------------
  // association command handlers
  // ----------------------------------------------

  // single association commands
  @CommandHandler
  public void handle(AssignVenToResourceCommand command) throws Exception {
    LOGGER.info("Handling command AssignVenToResourceCommand");

    if (ven != null && ven.getVenId() == command.getAssignment().getVenId())
      throw new ProcessingException(
          "Ven already assigned with id " + command.getAssignment().getVenId());

    apply(new AssignVenToResourceEvent(command.getResourceId(), command.getAssignment()));
  }

  @CommandHandler
  public void handle(UnAssignVenFromResourceCommand command) throws Exception {
    LOGGER.info("Handlign command UnAssignVenFromResourceCommand");

    if (ven == null) throw new ProcessingException("Ven already has nothing assigned.");

    apply(new UnAssignVenFromResourceEvent(command.getResourceId()));
  }

  // multiple association commands
  @CommandHandler
  public void handle(AssignAttributesToResourceCommand command) throws Exception {
    LOGGER.info("Handling command AssignAttributesToResourceCommand");

    if (attributes.contains(command.getAddTo()))
      throw new ProcessingException(
          "Attributes already contains an entity with id " + command.getAddTo().getValuesMapId());

    apply(new AssignAttributesToResourceEvent(command.getResourceId(), command.getAddTo()));
  }

  @CommandHandler
  public void handle(RemoveAttributesFromResourceCommand command) throws Exception {
    LOGGER.info("Handling command RemoveAttributesFromResourceCommand");

    if (!attributes.contains(command.getRemoveFrom()))
      throw new ProcessingException(
          "Attributes does not contain an entity with id "
              + command.getRemoveFrom().getValuesMapId());

    apply(new RemoveAttributesFromResourceEvent(command.getResourceId(), command.getRemoveFrom()));
  }

  @CommandHandler
  public void handle(AssignTargetsToResourceCommand command) throws Exception {
    LOGGER.info("Handling command AssignTargetsToResourceCommand");

    if (targets.contains(command.getAddTo()))
      throw new ProcessingException(
          "Targets already contains an entity with id " + command.getAddTo().getValuesMapId());

    apply(new AssignTargetsToResourceEvent(command.getResourceId(), command.getAddTo()));
  }

  @CommandHandler
  public void handle(RemoveTargetsFromResourceCommand command) throws Exception {
    LOGGER.info("Handling command RemoveTargetsFromResourceCommand");

    if (!targets.contains(command.getRemoveFrom()))
      throw new ProcessingException(
          "Targets does not contain an entity with id " + command.getRemoveFrom().getValuesMapId());

    apply(new RemoveTargetsFromResourceEvent(command.getResourceId(), command.getRemoveFrom()));
  }

  // ----------------------------------------------
  // intrinsic event source handlers
  // ----------------------------------------------
  @EventSourcingHandler
  void on(CreateResourceEvent event) {
    LOGGER.info("Event sourcing CreateResourceEvent");
    this.resourceId = event.getResourceId();
    this.createdDateTime = event.getCreatedDateTime();
    this.modificationDateTime = event.getModificationDateTime();
    this.resourceName = event.getResourceName();
    this.objectType = event.getObjectType();
  }

  @EventSourcingHandler
  void on(UpdateResourceEvent event) {
    LOGGER.info("Event sourcing classObject.getUpdateEventAlias()}");
    this.createdDateTime = event.getCreatedDateTime();
    this.modificationDateTime = event.getModificationDateTime();
    this.resourceName = event.getResourceName();
    this.ven = event.getVen();
    this.attributes = event.getAttributes();
    this.targets = event.getTargets();
    this.objectType = event.getObjectType();
  }

  // ----------------------------------------------
  // association event source handlers
  // ----------------------------------------------
  // single associations
  @EventSourcingHandler
  void on(AssignVenToResourceEvent event) {
    LOGGER.info("Event sourcing AssignVenToResourceEvent");
    this.ven = event.getAssignment();
  }

  @EventSourcingHandler
  void on(UnAssignVenFromResourceEvent event) {
    LOGGER.info("Event sourcing UnAssignVenFromResourceEvent");
    this.ven = null;
  }

  // multiple associations
  @EventSourcingHandler
  void on(AssignAttributesToResourceEvent event) {
    LOGGER.info("Event sourcing AssignAttributesToResourceEvent");
    this.attributes.add(event.getAddTo());
  }

  @EventSourcingHandler
  void on(RemoveAttributesFromResourceEvent event) {
    LOGGER.info("Event sourcing RemoveAttributesFromResourceEvent");
    this.attributes.remove(event.getRemoveFrom());
  }

  // multiple associations
  @EventSourcingHandler
  void on(AssignTargetsToResourceEvent event) {
    LOGGER.info("Event sourcing AssignTargetsToResourceEvent");
    this.targets.add(event.getAddTo());
  }

  @EventSourcingHandler
  void on(RemoveTargetsFromResourceEvent event) {
    LOGGER.info("Event sourcing RemoveTargetsFromResourceEvent");
    this.targets.remove(event.getRemoveFrom());
  }

  // ------------------------------------------
  // attributes
  // ------------------------------------------

  @AggregateIdentifier private UUID resourceId;

  private Date createdDateTime;
  private Date modificationDateTime;
  private String resourceName;
  private ObjectType objectType;
  private Ven ven = null;
  private Set<ValuesMap> attributes = new HashSet<>();
  private Set<ValuesMap> targets = new HashSet<>();

  private static final Logger LOGGER = Logger.getLogger(ResourceAggregate.class.getName());
}
