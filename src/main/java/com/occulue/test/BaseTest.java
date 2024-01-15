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
package com.occulue.test;

import java.util.logging.*;

/**
 * Base class for application Test classes.
 *
 * @author your_name_here
 */
public class BaseTest {
  /** hidden */
  protected BaseTest() {}

  public static void runTheTest(Handler logHandler) {
    try {
      new IntervalTest().setHandler(logHandler).startTest();
      Thread.sleep(timeToWait);
      new IntervalPeriodTest().setHandler(logHandler).startTest();
      Thread.sleep(timeToWait);
      new NotificationTest().setHandler(logHandler).startTest();
      Thread.sleep(timeToWait);
      new NotifierTest().setHandler(logHandler).startTest();
      Thread.sleep(timeToWait);
      new ObjectOperationTest().setHandler(logHandler).startTest();
      Thread.sleep(timeToWait);
      new PayloadDescriptorTest().setHandler(logHandler).startTest();
      Thread.sleep(timeToWait);
      new ProblemTest().setHandler(logHandler).startTest();
      Thread.sleep(timeToWait);
      new ReportDescriptorTest().setHandler(logHandler).startTest();
      Thread.sleep(timeToWait);
      new ValuesMapTest().setHandler(logHandler).startTest();
      Thread.sleep(timeToWait);
    } catch (Throwable exc) {
      exc.printStackTrace();
    }
  }
  // -----------------------------------------------------
  // attributes
  // -----------------------------------------------------
  private static final Integer timeToWait = 5000; // milliseconds
}
