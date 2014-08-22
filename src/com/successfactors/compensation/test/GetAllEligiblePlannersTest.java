package com.successfactors.compensation.test;

import java.util.List;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.successfactors.compensation.bean.CompConfigBean;
import com.successfactors.compensation.service.GetAllEligiblePlanners;
import com.successfactors.compensation.service.GetCompensationConfigByFormDataId;
import com.successfactors.compensation.testbase.SCAUnitTestBase;
import com.successfactors.sca.ServiceApplicationException;

public class GetAllEligiblePlannersTest extends SCAUnitTestBase {
  
  
  @Test
  public void getAllEligiblePlannersTest() throws ServiceApplicationException, JSONException{
    final String scaName = "GetAllEligiblePlanners";
    GetCompensationConfigByFormDataId cmd1 = new GetCompensationConfigByFormDataId(25);
    CompConfigBean configBean = scaHandler.execute(cmd1);
    
    GetAllEligiblePlanners cmd2 = new GetAllEligiblePlanners("AdminAU", configBean);
    List results = scaHandler.execute(cmd2);
    
    String jsonStr = testUtil.getJsonStrFromObj(scaName, results);
    JSONAssert.assertEquals(testUtil.getJsonStringFromLocal(scaName),jsonStr, false);
  }
}
