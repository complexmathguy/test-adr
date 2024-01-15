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
 * Implements Spring Controller query CQRS processing for entity ObjectOperation.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/ObjectOperation")
public class ObjectOperationQueryRestController extends BaseSpringRestController {

  /**
   * Handles loading a ObjectOperation using a UUID
   *
   * @param UUID objectOperationId
   * @return ObjectOperation
   */
  @GetMapping("/load")
  public ObjectOperation load(@RequestParam(required = true) UUID objectOperationId) {
    ObjectOperation entity = null;

    try {
      entity =
          ObjectOperationBusinessDelegate.getObjectOperationInstance()
              .getObjectOperation(new ObjectOperationFetchOneSummary(objectOperationId));
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load ObjectOperation using Id " + objectOperationId);
      return null;
    }

    return entity;
  }

  /**
   * Handles loading all ObjectOperation business objects
   *
   * @return Set<ObjectOperation>
   */
  @GetMapping("/")
  public List<ObjectOperation> loadAll() {
    List<ObjectOperation> objectOperationList = null;

    try {
      // load the ObjectOperation
      objectOperationList =
          ObjectOperationBusinessDelegate.getObjectOperationInstance().getAllObjectOperation();

      if (objectOperationList != null)
        LOGGER.log(Level.INFO, "successfully loaded all ObjectOperations");
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load all ObjectOperations ", exc);
      return null;
    }

    return objectOperationList;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected ObjectOperation objectOperation = null;
  private static final Logger LOGGER =
      Logger.getLogger(ObjectOperationQueryRestController.class.getName());
}
