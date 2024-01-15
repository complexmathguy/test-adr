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
 * Implements Spring Controller query CQRS processing for entity Program.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/Program")
public class ProgramQueryRestController extends BaseSpringRestController {

  /**
   * Handles loading a Program using a UUID
   *
   * @param UUID programId
   * @return Program
   */
  @GetMapping("/load")
  public Program load(@RequestParam(required = true) UUID programId) {
    Program entity = null;

    try {
      entity =
          ProgramBusinessDelegate.getProgramInstance()
              .getProgram(new ProgramFetchOneSummary(programId));
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load Program using Id " + programId);
      return null;
    }

    return entity;
  }

  /**
   * Handles loading all Program business objects
   *
   * @return Set<Program>
   */
  @GetMapping("/")
  public List<Program> loadAll() {
    List<Program> programList = null;

    try {
      // load the Program
      programList = ProgramBusinessDelegate.getProgramInstance().getAllProgram();

      if (programList != null) LOGGER.log(Level.INFO, "successfully loaded all Programs");
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load all Programs ", exc);
      return null;
    }

    return programList;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected Program program = null;
  private static final Logger LOGGER = Logger.getLogger(ProgramQueryRestController.class.getName());
}
