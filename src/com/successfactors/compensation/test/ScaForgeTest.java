package com.successfactors.compensation.test;

import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.xml.DOMConfigurator;
import org.json.JSONException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.successfactors.compensation.bean.CompConfigBean;
import com.successfactors.compensation.bean.CompPayMatrixBean;
import com.successfactors.compensation.bean.CompPlanBean;
import com.successfactors.compensation.service.CreateCompensationPlan;
import com.successfactors.compensation.service.GetAllEligiblePlanners;
import com.successfactors.compensation.service.GetCompEligHierarchySize;
import com.successfactors.compensation.service.GetCompPlanIds;
import com.successfactors.compensation.service.GetCompensationConfigByFormDataId;
import com.successfactors.compensation.service.GetCompensationPlanByPlanId;
import com.successfactors.compensation.service.GetCompensationPlanForTest;
import com.successfactors.compensation.service.GetPayMatrix;
import com.successfactors.compensation.test.util.ScaUnitTestUtil;
import com.successfactors.compensation.testbase.SCAUnitTestBase;

import com.successfactors.forge.Forge;
import com.successfactors.forge.rit.RIT;
import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.ServiceCommandHandler;
import com.successfactors.xi.util.SeamEnvUtils;

public class ScaForgeTest extends SCAUnitTestBase{
  private static long planId;
  
  @Test
  public void getCompEligHierarchySizeTest() throws ServiceApplicationException{
    GetCompEligHierarchySize cmd = new GetCompEligHierarchySize();
    cmd.setImportKey("ADDR1");
    cmd.setManagerHierarchy(3);
    cmd.setUserId("ramana4_jwilson1");
    Assert.assertEquals(1, scaHandler.execute(cmd).intValue());
  }
  
  @Test
  public void getCompensationConfigByFormDataIdTest() throws ServiceApplicationException, Exception, IllegalAccessException{
       final String scaName = "GetCompensationConfigByFormDataId";
       GetCompensationConfigByFormDataId cmd = new GetCompensationConfigByFormDataId(25);
       CompConfigBean results = scaHandler.execute(cmd);
       
//       JsonElement jsonElement =  testUtil.getJsonFromObj(scaName, results);
//       Assert.assertEquals(testUtil.getJsonElementFromLocal(scaName), jsonElement);
       
       String jsonStr = testUtil.getJsonStrFromObj(scaName, results);
       JSONAssert.assertEquals(testUtil.getJsonStringFromLocal(scaName),jsonStr, false);
      
  }
  
  @Test
  public void getCompPlanIdsTest() throws ServiceApplicationException, org.json.JSONException{
      final String scaName = "GetCompPlanIds";
      GetCompPlanIds cmd = new GetCompPlanIds(302);
      List<Long> results = scaHandler.execute(cmd);
//      JsonElement jsonElement =  testUtil.getJsonFromObj(scaName, results);
//      Assert.assertEquals(testUtil.getJsonElementFromLocal(SCANAME), jsonElement);
      
      String jsonStr = testUtil.getJsonStrFromObj(scaName, results);
      JSONAssert.assertEquals(testUtil.getJsonStringFromLocal(scaName),jsonStr, false);
      
  }
  
  @Test
  public void getAllEligiblePlanners() throws ServiceApplicationException, org.json.JSONException{
      final String scaName = "GetAllEligiblePlanners";
      GetCompensationConfigByFormDataId cmd1 = new GetCompensationConfigByFormDataId(25);
      CompConfigBean configBean = scaHandler.execute(cmd1);
      
      GetAllEligiblePlanners cmd2 = new GetAllEligiblePlanners("AdminAU", configBean);
      List results = scaHandler.execute(cmd2);
      
      
//      JsonElement jsonElement =  testUtil.getJsonFromObj(scaName, results);
//      Assert.assertEquals(testUtil.getJsonElementFromLocal(SCANAME), jsonElement);
      String jsonStr = testUtil.getJsonStrFromObj(scaName, results);
      JSONAssert.assertEquals(testUtil.getJsonStringFromLocal(scaName),jsonStr, false);
  }
  @Test
  public void getCompensationPlanByPlanId() throws ServiceApplicationException, org.json.JSONException{
    String scaName = "GetCompensationPlanByPlanId";
    GetCompensationPlanByPlanId cmd = new GetCompensationPlanByPlanId(9, 1, 10, true);
    CompPlanBean compPlan = scaHandler.execute(cmd);
    
    String jsonStr =testUtil.getJsonStrFromObj(scaName, compPlan);
    JSONAssert.assertEquals( testUtil.getJsonStringFromLocal(scaName), jsonStr, false);
  }
  @Test
  public void getPayMatrix() throws ServiceApplicationException, JSONException{
    String scaName = "GetPayMatrix";
    GetPayMatrix cmd = new GetPayMatrix("comp_payguide");
    CompPayMatrixBean result = scaHandler.execute(cmd);
    
    String jsonStr =testUtil.getJsonStrFromObj(scaName, result);
    JSONAssert.assertEquals( testUtil.getJsonStringFromLocal(scaName), jsonStr, false);
  }
  
  //WRITE SCA
  @Test
  public void createCompensationPlan() throws ServiceApplicationException, JSONException{
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
  
  //Test if in same transaction
  
  @Test
  public void GetCompensationPlanForTest() throws ServiceApplicationException{
    GetCompensationPlanForTest cmd4 = new GetCompensationPlanForTest();
    System.out.println(this.planId);
    cmd4.setCompPlanId(this.planId);
    
    CompPlanBean compPlanBean = scaHandler.execute(cmd4);
    Assert.assertNull(compPlanBean);
  }
  
}
