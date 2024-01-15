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
 * Implements Spring Controller command CQRS processing for entity Problem.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/Problem")
public class ProblemCommandRestController extends BaseSpringRestController {

  /**
   * Handles create a Problem. if not key provided, calls create, otherwise calls save
   *
   * @param Problem problem
   * @return CompletableFuture<UUID>
   */
  @PostMapping("/create")
  public CompletableFuture<UUID> create(
      @RequestBody(required = true) CreateProblemCommand command) {
    CompletableFuture<UUID> completableFuture = null;
    try {

      completableFuture = ProblemBusinessDelegate.getProblemInstance().createProblem(command);
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage(), exc);
    }

    return completableFuture;
  }

  /**
   * Handles updating a Problem. if no key provided, calls create, otherwise calls save
   *
   * @param Problem problem
   * @return CompletableFuture<Void>
   */
  @PutMapping("/update")
  public CompletableFuture<Void> update(
      @RequestBody(required = true) UpdateProblemCommand command) {
    CompletableFuture<Void> completableFuture = null;
    try {
      // -----------------------------------------------
      // delegate the UpdateProblemCommand
      // -----------------------------------------------
      completableFuture = ProblemBusinessDelegate.getProblemInstance().updateProblem(command);
      ;
    } catch (Throwable exc) {
      LOGGER.log(
          Level.WARNING,
          "ProblemController:update() - successfully update Problem - " + exc.getMessage());
    }

    return completableFuture;
  }

  /**
   * Handles deleting a Problem entity
   *
   * @param command ${class.getDeleteCommandAlias()}
   * @return CompletableFuture<Void>
   */
  @DeleteMapping("/delete")
  public CompletableFuture<Void> delete(@RequestParam(required = true) UUID problemId) {
    CompletableFuture<Void> completableFuture = null;
    DeleteProblemCommand command = new DeleteProblemCommand(problemId);

    try {
      ProblemBusinessDelegate delegate = ProblemBusinessDelegate.getProblemInstance();

      completableFuture = delegate.delete(command);
      LOGGER.log(Level.WARNING, "Successfully deleted Problem with key " + command.getProblemId());
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, exc.getMessage());
    }

    return completableFuture;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected Problem problem = null;
  private static final Logger LOGGER =
      Logger.getLogger(ProblemCommandRestController.class.getName());
}
