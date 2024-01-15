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
 * Implements Spring Controller query CQRS processing for entity Ven.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/Ven")
public class VenQueryRestController extends BaseSpringRestController {

  /**
   * Handles loading a Ven using a UUID
   *
   * @param UUID venId
   * @return Ven
   */
  @GetMapping("/load")
  public Ven load(@RequestParam(required = true) UUID venId) {
    Ven entity = null;

    try {
      entity = VenBusinessDelegate.getVenInstance().getVen(new VenFetchOneSummary(venId));
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load Ven using Id " + venId);
      return null;
    }

    return entity;
  }

  /**
   * Handles loading all Ven business objects
   *
   * @return Set<Ven>
   */
  @GetMapping("/")
  public List<Ven> loadAll() {
    List<Ven> venList = null;

    try {
      // load the Ven
      venList = VenBusinessDelegate.getVenInstance().getAllVen();

      if (venList != null) LOGGER.log(Level.INFO, "successfully loaded all Vens");
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load all Vens ", exc);
      return null;
    }

    return venList;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected Ven ven = null;
  private static final Logger LOGGER = Logger.getLogger(VenQueryRestController.class.getName());
}
