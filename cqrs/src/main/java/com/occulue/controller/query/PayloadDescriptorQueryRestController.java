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
 * Implements Spring Controller query CQRS processing for entity PayloadDescriptor.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/PayloadDescriptor")
public class PayloadDescriptorQueryRestController extends BaseSpringRestController {

  /**
   * Handles loading a PayloadDescriptor using a UUID
   *
   * @param UUID payloadDescriptorId
   * @return PayloadDescriptor
   */
  @GetMapping("/load")
  public PayloadDescriptor load(@RequestParam(required = true) UUID payloadDescriptorId) {
    PayloadDescriptor entity = null;

    try {
      entity =
          PayloadDescriptorBusinessDelegate.getPayloadDescriptorInstance()
              .getPayloadDescriptor(new PayloadDescriptorFetchOneSummary(payloadDescriptorId));
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load PayloadDescriptor using Id " + payloadDescriptorId);
      return null;
    }

    return entity;
  }

  /**
   * Handles loading all PayloadDescriptor business objects
   *
   * @return Set<PayloadDescriptor>
   */
  @GetMapping("/")
  public List<PayloadDescriptor> loadAll() {
    List<PayloadDescriptor> payloadDescriptorList = null;

    try {
      // load the PayloadDescriptor
      payloadDescriptorList =
          PayloadDescriptorBusinessDelegate.getPayloadDescriptorInstance()
              .getAllPayloadDescriptor();

      if (payloadDescriptorList != null)
        LOGGER.log(Level.INFO, "successfully loaded all PayloadDescriptors");
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load all PayloadDescriptors ", exc);
      return null;
    }

    return payloadDescriptorList;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected PayloadDescriptor payloadDescriptor = null;
  private static final Logger LOGGER =
      Logger.getLogger(PayloadDescriptorQueryRestController.class.getName());
}
