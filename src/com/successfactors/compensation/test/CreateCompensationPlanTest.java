package com.successfactors.compensation.test;

import junit.framework.Assert;

import org.junit.Test;

import com.successfactors.compensation.bean.CompPayMatrixBean;
import com.successfactors.compensation.bean.CompPlanBean;
import com.successfactors.compensation.service.CreateCompensationPlan;
import com.successfactors.compensation.service.GetCompensationPlanByPlanId;
import com.successfactors.compensation.service.GetCompensationPlanForTest;
import com.successfactors.compensation.service.GetPayMatrix;
import com.successfactors.compensation.testbase.SCAUnitTestBase;
import com.successfactors.sca.ServiceApplicationException;

public class CreateCompensationPlanTest extends SCAUnitTestBase{
  private static long planId;
  @Test
  public void createCompensationPlanTest() throws ServiceApplicationException{
 String scaName = "CreateCompensationPlan";
    
    GetCompensationPlanByPlanId cmd1 = new GetCompensationPlanByPlanId(9, 1, 10, true);
    CompPlanBean compPlan = scaHandler.execute(cmd1);
    compPlan.getPlanTotal().name = "SCA_TEST_COMP_PLAN";
    
    GetPayMatrix cmd2 = new GetPayMatrix("comp_payguide");
    CompPayMatrixBean matrixBean = scaHandler.execute(cmd2);
    
    
    CreateCompensationPlan cmd3 = new CreateCompensationPlan(compPlan,matrixBean , true, true, true);
    this.planId = scaHandler.execute(cmd3);
    
    
    System.out.println(this.planId);
    
    
    GetCompensationPlanForTest cmd4 = new GetCompensationPlanForTest();
    cmd4.setCompPlanId(this.planId);
    CompPlanBean compPlanBean = scaHandler.execute(cmd4);

    
    Assert.assertNotNull(compPlanBean);
  }
  
//GetCompensationPlanForTest is just for transaction test
//If code occurs error please comment it

  @Test
  public void GetCompensationPlanForTest() throws ServiceApplicationException{
    GetCompensationPlanForTest cmd4 = new GetCompensationPlanForTest();
    System.out.println(this.planId);
    cmd4.setCompPlanId(this.planId);
    
    CompPlanBean compPlanBean = scaHandler.execute(cmd4);
    Assert.assertNull(compPlanBean);
  }
}
