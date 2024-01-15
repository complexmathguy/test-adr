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

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.common.caching.Cache;
import org.axonframework.common.caching.WeakReferenceCache;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.interceptors.LoggingInterceptor;
import org.axonframework.queryhandling.QueryBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class AxonConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build();
  }

  @Bean
  public LoggingInterceptor<Message<?>> loggingInterceptor() {
    return new LoggingInterceptor<>();
  }

  @Autowired
  public void configureLoggingInterceptorFor(
      CommandBus commandBus, LoggingInterceptor<Message<?>> loggingInterceptor) {
    commandBus.registerDispatchInterceptor(loggingInterceptor);
    commandBus.registerHandlerInterceptor(loggingInterceptor);
  }

  @Autowired
  public void configureLoggingInterceptorFor(
      EventBus eventBus, LoggingInterceptor<Message<?>> loggingInterceptor) {
    eventBus.registerDispatchInterceptor(loggingInterceptor);
  }

  @Autowired
  public void configureLoggingInterceptorFor(
      EventProcessingConfigurer eventProcessingConfigurer,
      LoggingInterceptor<Message<?>> loggingInterceptor) {
    eventProcessingConfigurer.registerDefaultHandlerInterceptor(
        (config, processorName) -> loggingInterceptor);
  }

  @Autowired
  public void configureLoggingInterceptorFor(
      QueryBus queryBus, LoggingInterceptor<Message<?>> loggingInterceptor) {
    queryBus.registerDispatchInterceptor(loggingInterceptor);
    queryBus.registerHandlerInterceptor(loggingInterceptor);
  }

  // ------------------------------------------------
  // create a cache for each command type
  // ------------------------------------------------

  @Bean
  public Cache eventCache() {
    return new WeakReferenceCache();
  }

  @Bean
  public Cache eventPayloadDescriptorCache() {
    return new WeakReferenceCache();
  }

  @Bean
  public Cache intervalCache() {
    return new WeakReferenceCache();
  }

  @Bean
  public Cache intervalPeriodCache() {
    return new WeakReferenceCache();
  }

  @Bean
  public Cache notificationCache() {
    return new WeakReferenceCache();
  }

  @Bean
  public Cache notifierCache() {
    return new WeakReferenceCache();
  }

  @Bean
  public Cache objectOperationCache() {
    return new WeakReferenceCache();
  }

  @Bean
  public Cache payloadDescriptorCache() {
    return new WeakReferenceCache();
  }

  @Bean
  public Cache problemCache() {
    return new WeakReferenceCache();
  }

  @Bean
  public Cache programCache() {
    return new WeakReferenceCache();
  }

  @Bean
  public Cache reportCache() {
    return new WeakReferenceCache();
  }

  @Bean
  public Cache reportDescriptorCache() {
    return new WeakReferenceCache();
  }

  @Bean
  public Cache reportPayloadDescriptorCache() {
    return new WeakReferenceCache();
  }

  @Bean
  public Cache resourceCache() {
    return new WeakReferenceCache();
  }

  @Bean
  public Cache subscriptionCache() {
    return new WeakReferenceCache();
  }

  @Bean
  public Cache valuesMapCache() {
    return new WeakReferenceCache();
  }

  @Bean
  public Cache venCache() {
    return new WeakReferenceCache();
  }
}
