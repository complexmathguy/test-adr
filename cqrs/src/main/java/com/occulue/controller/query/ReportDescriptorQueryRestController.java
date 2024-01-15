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
 * Implements Spring Controller query CQRS processing for entity ReportDescriptor.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/ReportDescriptor")
public class ReportDescriptorQueryRestController extends BaseSpringRestController {

  /**
   * Handles loading a ReportDescriptor using a UUID
   *
   * @param UUID reportDescriptorId
   * @return ReportDescriptor
   */
  @GetMapping("/load")
  public ReportDescriptor load(@RequestParam(required = true) UUID reportDescriptorId) {
    ReportDescriptor entity = null;

    try {
      entity =
          ReportDescriptorBusinessDelegate.getReportDescriptorInstance()
              .getReportDescriptor(new ReportDescriptorFetchOneSummary(reportDescriptorId));
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load ReportDescriptor using Id " + reportDescriptorId);
      return null;
    }

    return entity;
  }

  /**
   * Handles loading all ReportDescriptor business objects
   *
   * @return Set<ReportDescriptor>
   */
  @GetMapping("/")
  public List<ReportDescriptor> loadAll() {
    List<ReportDescriptor> reportDescriptorList = null;

    try {
      // load the ReportDescriptor
      reportDescriptorList =
          ReportDescriptorBusinessDelegate.getReportDescriptorInstance().getAllReportDescriptor();

      if (reportDescriptorList != null)
        LOGGER.log(Level.INFO, "successfully loaded all ReportDescriptors");
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load all ReportDescriptors ", exc);
      return null;
    }

    return reportDescriptorList;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected ReportDescriptor reportDescriptor = null;
  private static final Logger LOGGER =
      Logger.getLogger(ReportDescriptorQueryRestController.class.getName());
}
