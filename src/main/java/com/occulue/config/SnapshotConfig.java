/**
 * ***************************************************************************** Turnstone Biologics
 * Confidential
 *
 * <p>2018 Turnstone Biologics All Rights Reserved.
 *
 * <p>This file is subject to the terms and conditions defined in file 'license.txt', which is part
 * of this source code package.
 *
 * <p>Contributors : Turnstone Biologics - General Release
 * ****************************************************************************
 */
package com.occulue.config;

import org.axonframework.eventsourcing.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnapshotConfig {

  // --------------------------------------------------------
  // define a snapshot trigger for each aggregate,
  // as implicitly defined per class and explicitly defined in the model
  // --------------------------------------------------------
  @Bean
  public SnapshotTriggerDefinition eventAggregateSnapshotTriggerDefinition(
      Snapshotter snapshotter,
      @Value("${axon.aggregate.event.snapshot-threshold:10}") int threshold) {
    return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
  }

  @Bean
  public SnapshotTriggerDefinition eventPayloadDescriptorAggregateSnapshotTriggerDefinition(
      Snapshotter snapshotter,
      @Value("${axon.aggregate.eventPayloadDescriptor.snapshot-threshold:10}") int threshold) {
    return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
  }

  @Bean
  public SnapshotTriggerDefinition intervalAggregateSnapshotTriggerDefinition(
      Snapshotter snapshotter,
      @Value("${axon.aggregate.interval.snapshot-threshold:10}") int threshold) {
    return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
  }

  @Bean
  public SnapshotTriggerDefinition intervalPeriodAggregateSnapshotTriggerDefinition(
      Snapshotter snapshotter,
      @Value("${axon.aggregate.intervalPeriod.snapshot-threshold:10}") int threshold) {
    return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
  }

  @Bean
  public SnapshotTriggerDefinition notificationAggregateSnapshotTriggerDefinition(
      Snapshotter snapshotter,
      @Value("${axon.aggregate.notification.snapshot-threshold:10}") int threshold) {
    return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
  }

  @Bean
  public SnapshotTriggerDefinition notifierAggregateSnapshotTriggerDefinition(
      Snapshotter snapshotter,
      @Value("${axon.aggregate.notifier.snapshot-threshold:10}") int threshold) {
    return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
  }

  @Bean
  public SnapshotTriggerDefinition objectOperationAggregateSnapshotTriggerDefinition(
      Snapshotter snapshotter,
      @Value("${axon.aggregate.objectOperation.snapshot-threshold:10}") int threshold) {
    return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
  }

  @Bean
  public SnapshotTriggerDefinition payloadDescriptorAggregateSnapshotTriggerDefinition(
      Snapshotter snapshotter,
      @Value("${axon.aggregate.payloadDescriptor.snapshot-threshold:10}") int threshold) {
    return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
  }

  @Bean
  public SnapshotTriggerDefinition problemAggregateSnapshotTriggerDefinition(
      Snapshotter snapshotter,
      @Value("${axon.aggregate.problem.snapshot-threshold:10}") int threshold) {
    return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
  }

  @Bean
  public SnapshotTriggerDefinition programAggregateSnapshotTriggerDefinition(
      Snapshotter snapshotter,
      @Value("${axon.aggregate.program.snapshot-threshold:10}") int threshold) {
    return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
  }

  @Bean
  public SnapshotTriggerDefinition reportAggregateSnapshotTriggerDefinition(
      Snapshotter snapshotter,
      @Value("${axon.aggregate.report.snapshot-threshold:10}") int threshold) {
    return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
  }

  @Bean
  public SnapshotTriggerDefinition reportDescriptorAggregateSnapshotTriggerDefinition(
      Snapshotter snapshotter,
      @Value("${axon.aggregate.reportDescriptor.snapshot-threshold:10}") int threshold) {
    return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
  }

  @Bean
  public SnapshotTriggerDefinition reportPayloadDescriptorAggregateSnapshotTriggerDefinition(
      Snapshotter snapshotter,
      @Value("${axon.aggregate.reportPayloadDescriptor.snapshot-threshold:10}") int threshold) {
    return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
  }

  @Bean
  public SnapshotTriggerDefinition resourceAggregateSnapshotTriggerDefinition(
      Snapshotter snapshotter,
      @Value("${axon.aggregate.resource.snapshot-threshold:10}") int threshold) {
    return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
  }

  @Bean
  public SnapshotTriggerDefinition subscriptionAggregateSnapshotTriggerDefinition(
      Snapshotter snapshotter,
      @Value("${axon.aggregate.subscription.snapshot-threshold:10}") int threshold) {
    return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
  }

  @Bean
  public SnapshotTriggerDefinition valuesMapAggregateSnapshotTriggerDefinition(
      Snapshotter snapshotter,
      @Value("${axon.aggregate.valuesMap.snapshot-threshold:10}") int threshold) {
    return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
  }

  @Bean
  public SnapshotTriggerDefinition venAggregateSnapshotTriggerDefinition(
      Snapshotter snapshotter,
      @Value("${axon.aggregate.ven.snapshot-threshold:10}") int threshold) {
    return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
  }
}
