package com.successfactors.compensation.test;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.successfactors.compensation.bean.CompConfigBean;
import com.successfactors.compensation.service.GetCompensationConfigByFormDataId;
import com.successfactors.compensation.testbase.SCAUnitTestBase;
import com.successfactors.sca.ServiceApplicationException;

public class GetCompensationConfigByFormDataIdTest extends SCAUnitTestBase{
  
  @Test
  public void getCompensationConfigByFormDataIdTest() throws ServiceApplicationException, JSONException{
    
    final String scaName = "GetCompensationConfigByFormDataId";
    GetCompensationConfigByFormDataId cmd = new GetCompensationConfigByFormDataId(25);
    CompConfigBean results = scaHandler.execute(cmd);
    String jsonStr = testUtil.getJsonStrFromObj(scaName, results);
    JSONAssert.assertEquals(testUtil.getJsonStringFromLocal(scaName),jsonStr, false);
    
  }
}
