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
package com.occulue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.occulue.*")
@EnableJpaRepositories(basePackages = "com.occulue.repository")
public class Application {

  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(Application.class, args);

    System.out.println("=================================");
    System.out.println("Checking in ApplicationContext for discovered handler components:\n");
    System.out.println(
        "- Contains event-handler = " + context.containsBeanDefinition("event-handler"));
    System.out.println(
        "- Contains eventPayloadDescriptor-handler = "
            + context.containsBeanDefinition("eventPayloadDescriptor-handler"));
    System.out.println(
        "- Contains interval-handler = " + context.containsBeanDefinition("interval-handler"));
    System.out.println(
        "- Contains intervalPeriod-handler = "
            + context.containsBeanDefinition("intervalPeriod-handler"));
    System.out.println(
        "- Contains notification-handler = "
            + context.containsBeanDefinition("notification-handler"));
    System.out.println(
        "- Contains notifier-handler = " + context.containsBeanDefinition("notifier-handler"));
    System.out.println(
        "- Contains objectOperation-handler = "
            + context.containsBeanDefinition("objectOperation-handler"));
    System.out.println(
        "- Contains payloadDescriptor-handler = "
            + context.containsBeanDefinition("payloadDescriptor-handler"));
    System.out.println(
        "- Contains problem-handler = " + context.containsBeanDefinition("problem-handler"));
    System.out.println(
        "- Contains program-handler = " + context.containsBeanDefinition("program-handler"));
    System.out.println(
        "- Contains report-handler = " + context.containsBeanDefinition("report-handler"));
    System.out.println(
        "- Contains reportDescriptor-handler = "
            + context.containsBeanDefinition("reportDescriptor-handler"));
    System.out.println(
        "- Contains reportPayloadDescriptor-handler = "
            + context.containsBeanDefinition("reportPayloadDescriptor-handler"));
    System.out.println(
        "- Contains resource-handler = " + context.containsBeanDefinition("resource-handler"));
    System.out.println(
        "- Contains subscription-handler = "
            + context.containsBeanDefinition("subscription-handler"));
    System.out.println(
        "- Contains valuesMap-handler = " + context.containsBeanDefinition("valuesMap-handler"));
    System.out.println("- Contains ven-handler = " + context.containsBeanDefinition("ven-handler"));
    System.out.println("=================================");
  }
}
