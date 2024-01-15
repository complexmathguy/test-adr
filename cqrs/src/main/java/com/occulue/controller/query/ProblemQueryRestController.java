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
package com.occulue.controller.query;

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
 * Implements Spring Controller query CQRS processing for entity Problem.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/Problem")
public class ProblemQueryRestController extends BaseSpringRestController {

  /**
   * Handles loading a Problem using a UUID
   *
   * @param UUID problemId
   * @return Problem
   */
  @GetMapping("/load")
  public Problem load(@RequestParam(required = true) UUID problemId) {
    Problem entity = null;

    try {
      entity =
          ProblemBusinessDelegate.getProblemInstance()
              .getProblem(new ProblemFetchOneSummary(problemId));
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load Problem using Id " + problemId);
      return null;
    }

    return entity;
  }

  /**
   * Handles loading all Problem business objects
   *
   * @return Set<Problem>
   */
  @GetMapping("/")
  public List<Problem> loadAll() {
    List<Problem> problemList = null;

    try {
      // load the Problem
      problemList = ProblemBusinessDelegate.getProblemInstance().getAllProblem();

      if (problemList != null) LOGGER.log(Level.INFO, "successfully loaded all Problems");
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load all Problems ", exc);
      return null;
    }

    return problemList;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected Problem problem = null;
  private static final Logger LOGGER = Logger.getLogger(ProblemQueryRestController.class.getName());
}
