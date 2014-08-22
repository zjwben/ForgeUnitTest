package com.successfactors.compensation.test;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.successfactors.compensation.bean.CompPlanBean;
import com.successfactors.compensation.service.GetCompensationPlanByPlanId;
import com.successfactors.compensation.testbase.SCAUnitTestBase;
import com.successfactors.sca.ServiceApplicationException;

public class GetCompensationPlanByPlanIdTest extends SCAUnitTestBase{
  
  @Test
  public void getCompensationPlanByPlanIdTest() throws ServiceApplicationException, JSONException{
    String scaName = "GetCompensationPlanByPlanId";
    GetCompensationPlanByPlanId cmd = new GetCompensationPlanByPlanId(9, 1, 10, true);
    CompPlanBean compPlan = scaHandler.execute(cmd);
    
    String jsonStr =testUtil.getJsonStrFromObj(scaName, compPlan);
    JSONAssert.assertEquals( testUtil.getJsonStringFromLocal(scaName), jsonStr, false);
  }
}
