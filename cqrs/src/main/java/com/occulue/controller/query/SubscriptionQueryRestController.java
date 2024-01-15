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
 * Implements Spring Controller query CQRS processing for entity Subscription.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/Subscription")
public class SubscriptionQueryRestController extends BaseSpringRestController {

  /**
   * Handles loading a Subscription using a UUID
   *
   * @param UUID subscriptionId
   * @return Subscription
   */
  @GetMapping("/load")
  public Subscription load(@RequestParam(required = true) UUID subscriptionId) {
    Subscription entity = null;

    try {
      entity =
          SubscriptionBusinessDelegate.getSubscriptionInstance()
              .getSubscription(new SubscriptionFetchOneSummary(subscriptionId));
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load Subscription using Id " + subscriptionId);
      return null;
    }

    return entity;
  }

  /**
   * Handles loading all Subscription business objects
   *
   * @return Set<Subscription>
   */
  @GetMapping("/")
  public List<Subscription> loadAll() {
    List<Subscription> subscriptionList = null;

    try {
      // load the Subscription
      subscriptionList =
          SubscriptionBusinessDelegate.getSubscriptionInstance().getAllSubscription();

      if (subscriptionList != null) LOGGER.log(Level.INFO, "successfully loaded all Subscriptions");
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load all Subscriptions ", exc);
      return null;
    }

    return subscriptionList;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected Subscription subscription = null;
  private static final Logger LOGGER =
      Logger.getLogger(SubscriptionQueryRestController.class.getName());
}
