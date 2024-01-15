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
 * Aggregate handler for Ven as outlined for the CQRS pattern, all write responsibilities related to
 * Ven are handled here.
 *
 * @author your_name_here
 */
@Aggregate
public class VenAggregate {

  // -----------------------------------------
  // Axon requires an empty constructor
  // -----------------------------------------
  public VenAggregate() {}

  // ----------------------------------------------
  // intrinsic command handlers
  // ----------------------------------------------
  @CommandHandler
  public VenAggregate(CreateVenCommand command) throws Exception {
    LOGGER.info("Handling command CreateVenCommand");
    CreateVenEvent event =
        new CreateVenEvent(
            command.getVenId(),
            command.getCreatedDateTime(),
            command.getModificationDateTime(),
            command.getVenName(),
            command.getObjectType());

    apply(event);
  }

  @CommandHandler
  public void handle(UpdateVenCommand command) throws Exception {
    LOGGER.info("handling command UpdateVenCommand");
    UpdateVenEvent event =
        new UpdateVenEvent(
            command.getVenId(),
            command.getCreatedDateTime(),
            command.getModificationDateTime(),
            command.getVenName(),
            command.getAttributes(),
            command.getTargets(),
            command.getResources(),
            command.getObjectType());

    apply(event);
  }

  @CommandHandler
  public void handle(DeleteVenCommand command) throws Exception {
    LOGGER.info("Handling command DeleteVenCommand");
    apply(new DeleteVenEvent(command.getVenId()));
  }

  // ----------------------------------------------
  // association command handlers
  // ----------------------------------------------

  // single association commands

  // multiple association commands
  @CommandHandler
  public void handle(AssignAttributesToVenCommand command) throws Exception {
    LOGGER.info("Handling command AssignAttributesToVenCommand");

    if (attributes.contains(command.getAddTo()))
      throw new ProcessingException(
          "Attributes already contains an entity with id " + command.getAddTo().getValuesMapId());

    apply(new AssignAttributesToVenEvent(command.getVenId(), command.getAddTo()));
  }

  @CommandHandler
  public void handle(RemoveAttributesFromVenCommand command) throws Exception {
    LOGGER.info("Handling command RemoveAttributesFromVenCommand");

    if (!attributes.contains(command.getRemoveFrom()))
      throw new ProcessingException(
          "Attributes does not contain an entity with id "
              + command.getRemoveFrom().getValuesMapId());

    apply(new RemoveAttributesFromVenEvent(command.getVenId(), command.getRemoveFrom()));
  }

  @CommandHandler
  public void handle(AssignTargetsToVenCommand command) throws Exception {
    LOGGER.info("Handling command AssignTargetsToVenCommand");

    if (targets.contains(command.getAddTo()))
      throw new ProcessingException(
          "Targets already contains an entity with id " + command.getAddTo().getValuesMapId());

    apply(new AssignTargetsToVenEvent(command.getVenId(), command.getAddTo()));
  }

  @CommandHandler
  public void handle(RemoveTargetsFromVenCommand command) throws Exception {
    LOGGER.info("Handling command RemoveTargetsFromVenCommand");

    if (!targets.contains(command.getRemoveFrom()))
      throw new ProcessingException(
          "Targets does not contain an entity with id " + command.getRemoveFrom().getValuesMapId());

    apply(new RemoveTargetsFromVenEvent(command.getVenId(), command.getRemoveFrom()));
  }

  @CommandHandler
  public void handle(AssignResourcesToVenCommand command) throws Exception {
    LOGGER.info("Handling command AssignResourcesToVenCommand");

    if (resources.contains(command.getAddTo()))
      throw new ProcessingException(
          "Resources already contains an entity with id " + command.getAddTo().getResourceId());

    apply(new AssignResourcesToVenEvent(command.getVenId(), command.getAddTo()));
  }

  @CommandHandler
  public void handle(RemoveResourcesFromVenCommand command) throws Exception {
    LOGGER.info("Handling command RemoveResourcesFromVenCommand");

    if (!resources.contains(command.getRemoveFrom()))
      throw new ProcessingException(
          "Resources does not contain an entity with id "
              + command.getRemoveFrom().getResourceId());

    apply(new RemoveResourcesFromVenEvent(command.getVenId(), command.getRemoveFrom()));
  }

  // ----------------------------------------------
  // intrinsic event source handlers
  // ----------------------------------------------
  @EventSourcingHandler
  void on(CreateVenEvent event) {
    LOGGER.info("Event sourcing CreateVenEvent");
    this.venId = event.getVenId();
    this.createdDateTime = event.getCreatedDateTime();
    this.modificationDateTime = event.getModificationDateTime();
    this.venName = event.getVenName();
    this.objectType = event.getObjectType();
  }

  @EventSourcingHandler
  void on(UpdateVenEvent event) {
    LOGGER.info("Event sourcing classObject.getUpdateEventAlias()}");
    this.createdDateTime = event.getCreatedDateTime();
    this.modificationDateTime = event.getModificationDateTime();
    this.venName = event.getVenName();
    this.attributes = event.getAttributes();
    this.targets = event.getTargets();
    this.resources = event.getResources();
    this.objectType = event.getObjectType();
  }

  // ----------------------------------------------
  // association event source handlers
  // ----------------------------------------------

  // multiple associations
  @EventSourcingHandler
  void on(AssignAttributesToVenEvent event) {
    LOGGER.info("Event sourcing AssignAttributesToVenEvent");
    this.attributes.add(event.getAddTo());
  }

  @EventSourcingHandler
  void on(RemoveAttributesFromVenEvent event) {
    LOGGER.info("Event sourcing RemoveAttributesFromVenEvent");
    this.attributes.remove(event.getRemoveFrom());
  }

  // multiple associations
  @EventSourcingHandler
  void on(AssignTargetsToVenEvent event) {
    LOGGER.info("Event sourcing AssignTargetsToVenEvent");
    this.targets.add(event.getAddTo());
  }

  @EventSourcingHandler
  void on(RemoveTargetsFromVenEvent event) {
    LOGGER.info("Event sourcing RemoveTargetsFromVenEvent");
    this.targets.remove(event.getRemoveFrom());
  }

  // multiple associations
  @EventSourcingHandler
  void on(AssignResourcesToVenEvent event) {
    LOGGER.info("Event sourcing AssignResourcesToVenEvent");
    this.resources.add(event.getAddTo());
  }

  @EventSourcingHandler
  void on(RemoveResourcesFromVenEvent event) {
    LOGGER.info("Event sourcing RemoveResourcesFromVenEvent");
    this.resources.remove(event.getRemoveFrom());
  }

  // ------------------------------------------
  // attributes
  // ------------------------------------------

  @AggregateIdentifier private UUID venId;

  private Date createdDateTime;
  private Date modificationDateTime;
  private String venName;
  private ObjectType objectType;
  private Set<ValuesMap> attributes = new HashSet<>();
  private Set<ValuesMap> targets = new HashSet<>();
  private Set<Resource> resources = new HashSet<>();

  private static final Logger LOGGER = Logger.getLogger(VenAggregate.class.getName());
}
