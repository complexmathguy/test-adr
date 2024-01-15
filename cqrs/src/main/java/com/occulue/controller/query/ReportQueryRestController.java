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
 * Implements Spring Controller query CQRS processing for entity Report.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/Report")
public class ReportQueryRestController extends BaseSpringRestController {

  /**
   * Handles loading a Report using a UUID
   *
   * @param UUID reportId
   * @return Report
   */
  @GetMapping("/load")
  public Report load(@RequestParam(required = true) UUID reportId) {
    Report entity = null;

    try {
      entity =
          ReportBusinessDelegate.getReportInstance().getReport(new ReportFetchOneSummary(reportId));
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load Report using Id " + reportId);
      return null;
    }

    return entity;
  }

  /**
   * Handles loading all Report business objects
   *
   * @return Set<Report>
   */
  @GetMapping("/")
  public List<Report> loadAll() {
    List<Report> reportList = null;

    try {
      // load the Report
      reportList = ReportBusinessDelegate.getReportInstance().getAllReport();

      if (reportList != null) LOGGER.log(Level.INFO, "successfully loaded all Reports");
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load all Reports ", exc);
      return null;
    }

    return reportList;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected Report report = null;
  private static final Logger LOGGER = Logger.getLogger(ReportQueryRestController.class.getName());
}
