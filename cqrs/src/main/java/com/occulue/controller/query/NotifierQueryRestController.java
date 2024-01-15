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
 * Implements Spring Controller query CQRS processing for entity Notifier.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/Notifier")
public class NotifierQueryRestController extends BaseSpringRestController {

  /**
   * Handles loading a Notifier using a UUID
   *
   * @param UUID notifierId
   * @return Notifier
   */
  @GetMapping("/load")
  public Notifier load(@RequestParam(required = true) UUID notifierId) {
    Notifier entity = null;

    try {
      entity =
          NotifierBusinessDelegate.getNotifierInstance()
              .getNotifier(new NotifierFetchOneSummary(notifierId));
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load Notifier using Id " + notifierId);
      return null;
    }

    return entity;
  }

  /**
   * Handles loading all Notifier business objects
   *
   * @return Set<Notifier>
   */
  @GetMapping("/")
  public List<Notifier> loadAll() {
    List<Notifier> notifierList = null;

    try {
      // load the Notifier
      notifierList = NotifierBusinessDelegate.getNotifierInstance().getAllNotifier();

      if (notifierList != null) LOGGER.log(Level.INFO, "successfully loaded all Notifiers");
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load all Notifiers ", exc);
      return null;
    }

    return notifierList;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected Notifier notifier = null;
  private static final Logger LOGGER =
      Logger.getLogger(NotifierQueryRestController.class.getName());
}
