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
 * Implements Spring Controller query CQRS processing for entity ValuesMap.
 *
 * @author your_name_here
 */
@CrossOrigin
@RestController
@RequestMapping("/ValuesMap")
public class ValuesMapQueryRestController extends BaseSpringRestController {

  /**
   * Handles loading a ValuesMap using a UUID
   *
   * @param UUID valuesMapId
   * @return ValuesMap
   */
  @GetMapping("/load")
  public ValuesMap load(@RequestParam(required = true) UUID valuesMapId) {
    ValuesMap entity = null;

    try {
      entity =
          ValuesMapBusinessDelegate.getValuesMapInstance()
              .getValuesMap(new ValuesMapFetchOneSummary(valuesMapId));
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load ValuesMap using Id " + valuesMapId);
      return null;
    }

    return entity;
  }

  /**
   * Handles loading all ValuesMap business objects
   *
   * @return Set<ValuesMap>
   */
  @GetMapping("/")
  public List<ValuesMap> loadAll() {
    List<ValuesMap> valuesMapList = null;

    try {
      // load the ValuesMap
      valuesMapList = ValuesMapBusinessDelegate.getValuesMapInstance().getAllValuesMap();

      if (valuesMapList != null) LOGGER.log(Level.INFO, "successfully loaded all ValuesMaps");
    } catch (Throwable exc) {
      LOGGER.log(Level.WARNING, "failed to load all ValuesMaps ", exc);
      return null;
    }

    return valuesMapList;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  protected ValuesMap valuesMap = null;
  private static final Logger LOGGER =
      Logger.getLogger(ValuesMapQueryRestController.class.getName());
}
