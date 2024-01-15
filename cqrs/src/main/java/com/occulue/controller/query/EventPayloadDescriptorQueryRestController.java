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
 * Implements Spring Controller query CQRS processing for entity EventPayloadDescriptor.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/EventPayloadDescriptor")
public class EventPayloadDescriptorQueryRestController extends BaseSpringRestController {

  /**
   * Handles loading a EventPayloadDescriptor using a UUID
   *
   * @param UUID eventPayloadDescriptorId
   * @return EventPayloadDescriptor
   */
  @GetMapping("/load")
  public EventPayloadDescriptor load(@RequestParam(required = true) UUID eventPayloadDescriptorId) {
    EventPayloadDescriptor entity = null;

    try {
      entity =
          EventPayloadDescriptorBusinessDelegate.getEventPayloadDescriptorInstance()
              .getEventPayloadDescriptor(
                  new EventPayloadDescriptorFetchOneSummary(eventPayloadDescriptorId));
    } catch (Throwable exc) {
      LOGGER.log(
          Level.WARNING,
          "failed to load EventPayloadDescriptor using Id " + eventPayloadDescriptorId);
      return null;
    }

    return entity;
  }

  /**
   * Handles loading all EventPayloadDescriptor business objects
   *
   * @return Set<EventPayloadDescriptor>
   */
  @GetMapping("/")
  public List<EventPayloadDescriptor> loadAll() {
    List<EventPayloadDescriptor> eventPayloadDescriptorList = null;

    try {
      // load the EventPayloadDescriptor
      eventPayloadDescriptorList =
          EventPayloadDescriptorBusinessDelegate.getEventPayloadDescriptorInstance()
              .getAllEventPayloadDescriptor();

      if (eventPayloadDescriptorList != null)
        LOGGER.log(Level.INFO, "successfully loaded all EventPayloadDescriptors");
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load all EventPayloadDescriptors ", exc);
      return null;
    }

    return eventPayloadDescriptorList;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected EventPayloadDescriptor eventPayloadDescriptor = null;
  private static final Logger LOGGER =
      Logger.getLogger(EventPayloadDescriptorQueryRestController.class.getName());
}
