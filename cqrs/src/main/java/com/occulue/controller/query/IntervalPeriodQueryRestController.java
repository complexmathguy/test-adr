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
 * Implements Spring Controller query CQRS processing for entity IntervalPeriod.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/IntervalPeriod")
public class IntervalPeriodQueryRestController extends BaseSpringRestController {

  /**
   * Handles loading a IntervalPeriod using a UUID
   *
   * @param UUID intervalPeriodId
   * @return IntervalPeriod
   */
  @GetMapping("/load")
  public IntervalPeriod load(@RequestParam(required = true) UUID intervalPeriodId) {
    IntervalPeriod entity = null;

    try {
      entity =
          IntervalPeriodBusinessDelegate.getIntervalPeriodInstance()
              .getIntervalPeriod(new IntervalPeriodFetchOneSummary(intervalPeriodId));
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load IntervalPeriod using Id " + intervalPeriodId);
      return null;
    }

    return entity;
  }

  /**
   * Handles loading all IntervalPeriod business objects
   *
   * @return Set<IntervalPeriod>
   */
  @GetMapping("/")
  public List<IntervalPeriod> loadAll() {
    List<IntervalPeriod> intervalPeriodList = null;

    try {
      // load the IntervalPeriod
      intervalPeriodList =
          IntervalPeriodBusinessDelegate.getIntervalPeriodInstance().getAllIntervalPeriod();

      if (intervalPeriodList != null)
        LOGGER.log(Level.INFO, "successfully loaded all IntervalPeriods");
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load all IntervalPeriods ", exc);
      return null;
    }

    return intervalPeriodList;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected IntervalPeriod intervalPeriod = null;
  private static final Logger LOGGER =
      Logger.getLogger(IntervalPeriodQueryRestController.class.getName());
}
