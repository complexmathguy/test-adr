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
 * Implements Spring Controller query CQRS processing for entity Event.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/Event")
public class EventQueryRestController extends BaseSpringRestController {

  /**
   * Handles loading a Event using a UUID
   *
   * @param UUID eventId
   * @return Event
   */
  @GetMapping("/load")
  public Event load(@RequestParam(required = true) UUID eventId) {
    Event entity = null;

    try {
      entity = EventBusinessDelegate.getEventInstance().getEvent(new EventFetchOneSummary(eventId));
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load Event using Id " + eventId);
      return null;
    }

    return entity;
  }

  /**
   * Handles loading all Event business objects
   *
   * @return Set<Event>
   */
  @GetMapping("/")
  public List<Event> loadAll() {
    List<Event> eventList = null;

    try {
      // load the Event
      eventList = EventBusinessDelegate.getEventInstance().getAllEvent();

      if (eventList != null) LOGGER.log(Level.INFO, "successfully loaded all Events");
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load all Events ", exc);
      return null;
    }

    return eventList;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected Event event = null;
  private static final Logger LOGGER = Logger.getLogger(EventQueryRestController.class.getName());
}
