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
package com.occulue.controller.command;

import com.occulue.api.*;
import com.occulue.controller.*;
import com.occulue.delegate.*;
import com.occulue.entity.*;
import com.occulue.exception.*;
import com.occulue.projector.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.*;

/**
 * Implements Spring Controller command CQRS processing for entity ReportPayloadDescriptor.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/ReportPayloadDescriptor")
public class ReportPayloadDescriptorCommandRestController extends BaseSpringRestController {

  /**
   * Handles create a ReportPayloadDescriptor. if not key provided, calls create, otherwise calls
   * save
   *
   * @param ReportPayloadDescriptor reportPayloadDescriptor
   * @return CompletableFuture<UUID>
   */
  @PostMapping("/create")
  public CompletableFuture<UUID> create(
      @RequestBody(required = true) CreateReportPayloadDescriptorCommand command) {
    CompletableFuture<UUID> completableFuture = null;
    try {

      completableFuture =
          ReportPayloadDescriptorBusinessDelegate.getReportPayloadDescriptorInstance()
              .createReportPayloadDescriptor(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage(), exc);
    }

    return completableFuture;
  }

  /**
   * Handles updating a ReportPayloadDescriptor. if no key provided, calls create, otherwise calls
   * save
   *
   * @param ReportPayloadDescriptor reportPayloadDescriptor
   * @return CompletableFuture<Void>
   */
  @PutMapping("/update")
  public CompletableFuture<Void> update(
      @RequestBody(required = true) UpdateReportPayloadDescriptorCommand command) {
    CompletableFuture<Void> completableFuture = null;
    try {
      // -----------------------------------------------
      // delegate the UpdateReportPayloadDescriptorCommand
      // -----------------------------------------------
      completableFuture =
          ReportPayloadDescriptorBusinessDelegate.getReportPayloadDescriptorInstance()
              .updateReportPayloadDescriptor(command);
      ;
    } catch (Throwable exc) {
      LOGGER.log(
          Level.WARNING,
          "ReportPayloadDescriptorController:update() - successfully update ReportPayloadDescriptor - "
              + exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * Handles deleting a ReportPayloadDescriptor entity
   *
   * @param command ${class.getDeleteCommandAlias()}
   * @return CompletableFuture<Void>
   */
  @DeleteMapping("/delete")
  public CompletableFuture<Void> delete(
      @RequestParam(required = true) UUID reportPayloadDescriptorId) {
    CompletableFuture<Void> completableFuture = null;
    DeleteReportPayloadDescriptorCommand command =
        new DeleteReportPayloadDescriptorCommand(reportPayloadDescriptorId);

    try {
      ReportPayloadDescriptorBusinessDelegate delegate =
          ReportPayloadDescriptorBusinessDelegate.getReportPayloadDescriptorInstance();

      completableFuture = delegate.delete(command);
      LOGGER.log(
          Level.WARNING,
          "Successfully deleted ReportPayloadDescriptor with key "
              + command.getReportPayloadDescriptorId());
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage());
    }

    return completableFuture;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected ReportPayloadDescriptor reportPayloadDescriptor = null;
  private static final Logger LOGGER =
      Logger.getLogger(ReportPayloadDescriptorCommandRestController.class.getName());
}
