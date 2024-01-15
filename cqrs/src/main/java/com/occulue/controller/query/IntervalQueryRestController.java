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
 * Implements Spring Controller query CQRS processing for entity Interval.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/Interval")
public class IntervalQueryRestController extends BaseSpringRestController {

  /**
   * Handles loading a Interval using a UUID
   *
   * @param UUID intervalId
   * @return Interval
   */
  @GetMapping("/load")
  public Interval load(@RequestParam(required = true) UUID intervalId) {
    Interval entity = null;

    try {
      entity =
          IntervalBusinessDelegate.getIntervalInstance()
              .getInterval(new IntervalFetchOneSummary(intervalId));
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load Interval using Id " + intervalId);
      return null;
    }

    return entity;
  }

  /**
   * Handles loading all Interval business objects
   *
   * @return Set<Interval>
   */
  @GetMapping("/")
  public List<Interval> loadAll() {
    List<Interval> intervalList = null;

    try {
      // load the Interval
      intervalList = IntervalBusinessDelegate.getIntervalInstance().getAllInterval();

      if (intervalList != null) LOGGER.log(Level.INFO, "successfully loaded all Intervals");
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load all Intervals ", exc);
      return null;
    }

    return intervalList;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected Interval interval = null;
  private static final Logger LOGGER =
      Logger.getLogger(IntervalQueryRestController.class.getName());
}
