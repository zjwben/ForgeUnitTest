package com.successfactors.compensation.test;

import java.util.List;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.successfactors.compensation.service.GetCompPlanIds;
import com.successfactors.compensation.testbase.SCAUnitTestBase;
import com.successfactors.sca.ServiceApplicationException;

public class GetCompPlanIdsTest extends SCAUnitTestBase{
  
  @Test
  public void getCompPlanIdsTest() throws ServiceApplicationException, JSONException{
    
    final String scaName = "GetCompPlanIds";
    GetCompPlanIds cmd = new GetCompPlanIds(302);
    List<Long> results = scaHandler.execute(cmd);
    String jsonStr = testUtil.getJsonStrFromObj(scaName, results);
    JSONAssert.assertEquals(testUtil.getJsonStringFromLocal(scaName),jsonStr, false);
    
  }
}
