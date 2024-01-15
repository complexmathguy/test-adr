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
 * Implements Spring Controller query CQRS processing for entity ReportPayloadDescriptor.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/ReportPayloadDescriptor")
public class ReportPayloadDescriptorQueryRestController extends BaseSpringRestController {

  /**
   * Handles loading a ReportPayloadDescriptor using a UUID
   *
   * @param UUID reportPayloadDescriptorId
   * @return ReportPayloadDescriptor
   */
  @GetMapping("/load")
  public ReportPayloadDescriptor load(
      @RequestParam(required = true) UUID reportPayloadDescriptorId) {
    ReportPayloadDescriptor entity = null;

    try {
      entity =
          ReportPayloadDescriptorBusinessDelegate.getReportPayloadDescriptorInstance()
              .getReportPayloadDescriptor(
                  new ReportPayloadDescriptorFetchOneSummary(reportPayloadDescriptorId));
    } catch (Throwable exc) {
      LOGGER.log(
          Level.WARNING,
          "failed to load ReportPayloadDescriptor using Id " + reportPayloadDescriptorId);
      return null;
    }

    return entity;
  }

  /**
   * Handles loading all ReportPayloadDescriptor business objects
   *
   * @return Set<ReportPayloadDescriptor>
   */
  @GetMapping("/")
  public List<ReportPayloadDescriptor> loadAll() {
    List<ReportPayloadDescriptor> reportPayloadDescriptorList = null;

    try {
      // load the ReportPayloadDescriptor
      reportPayloadDescriptorList =
          ReportPayloadDescriptorBusinessDelegate.getReportPayloadDescriptorInstance()
              .getAllReportPayloadDescriptor();

      if (reportPayloadDescriptorList != null)
        LOGGER.log(Level.INFO, "successfully loaded all ReportPayloadDescriptors");
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load all ReportPayloadDescriptors ", exc);
      return null;
    }

    return reportPayloadDescriptorList;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected ReportPayloadDescriptor reportPayloadDescriptor = null;
  private static final Logger LOGGER =
      Logger.getLogger(ReportPayloadDescriptorQueryRestController.class.getName());
}
