package com.successfactors.compensation.test;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.successfactors.compensation.bean.CompPayMatrixBean;
import com.successfactors.compensation.service.GetPayMatrix;
import com.successfactors.compensation.service.sca.ScaForgeUnitTest;
import com.successfactors.compensation.testbase.SCAUnitTestBase;
import com.successfactors.sca.ServiceApplicationException;

public class GetPayMatrixTest extends SCAUnitTestBase {
  
  
  @Test
  public void getPayMatrixTest() throws ServiceApplicationException, JSONException{
    String scaName = "GetPayMatrix";
    GetPayMatrix cmd = new GetPayMatrix("comp_payguide");
    CompPayMatrixBean result = scaHandler.execute(cmd);
    
    String jsonStr =testUtil.getJsonStrFromObj(scaName, result);
    JSONAssert.assertEquals( testUtil.getJsonStringFromLocal(scaName), jsonStr, false);
  }
}
