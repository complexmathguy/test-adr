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
package com.occulue.validator;

import com.occulue.api.*;
import org.springframework.util.Assert;

public class ProblemValidator {

  /** default constructor */
  protected ProblemValidator() {}

  /** factory method */
  public static ProblemValidator getInstance() {
    return new ProblemValidator();
  }

  /** handles creation validation for a Problem */
  public void validate(CreateProblemCommand problem) throws Exception {
    Assert.notNull(problem, "CreateProblemCommand should not be null");
    //		Assert.isNull( problem.getProblemId(), "CreateProblemCommand identifier should be null" );
    Assert.notNull(problem.getType(), "Field CreateProblemCommand.type should not be null");
    Assert.notNull(problem.getTitle(), "Field CreateProblemCommand.title should not be null");
    Assert.notNull(problem.getStatus(), "Field CreateProblemCommand.status should not be null");
    Assert.notNull(problem.getDetail(), "Field CreateProblemCommand.detail should not be null");
    Assert.notNull(problem.getInstance(), "Field CreateProblemCommand.instance should not be null");
  }

  /** handles update validation for a Problem */
  public void validate(UpdateProblemCommand problem) throws Exception {
    Assert.notNull(problem, "UpdateProblemCommand should not be null");
    Assert.notNull(problem.getProblemId(), "UpdateProblemCommand identifier should not be null");
    Assert.notNull(problem.getType(), "Field UpdateProblemCommand.type should not be null");
    Assert.notNull(problem.getTitle(), "Field UpdateProblemCommand.title should not be null");
    Assert.notNull(problem.getStatus(), "Field UpdateProblemCommand.status should not be null");
    Assert.notNull(problem.getDetail(), "Field UpdateProblemCommand.detail should not be null");
    Assert.notNull(problem.getInstance(), "Field UpdateProblemCommand.instance should not be null");
  }

  /** handles delete validation for a Problem */
  public void validate(DeleteProblemCommand problem) throws Exception {
    Assert.notNull(problem, "{commandAlias} should not be null");
    Assert.notNull(problem.getProblemId(), "DeleteProblemCommand identifier should not be null");
  }

  /** handles fetchOne validation for a Problem */
  public void validate(ProblemFetchOneSummary summary) throws Exception {
    Assert.notNull(summary, "ProblemFetchOneSummary should not be null");
    Assert.notNull(summary.getProblemId(), "ProblemFetchOneSummary identifier should not be null");
  }
}
