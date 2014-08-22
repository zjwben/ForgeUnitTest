package com.successfactors.compensation.test;

import org.junit.Test;

import junit.framework.Assert;

import com.successfactors.compensation.service.GetCompEligHierarchySize;
import com.successfactors.compensation.testbase.SCAUnitTestBase;
import com.successfactors.sca.ServiceApplicationException;

public class GetCompEligHierarchySizeTest extends SCAUnitTestBase {
  @Test  
  public void getCompEligHierarchySizeTest() throws ServiceApplicationException{
      GetCompEligHierarchySize cmd = new GetCompEligHierarchySize();
      cmd.setImportKey("ADDR1");
      cmd.setManagerHierarchy(3);
      cmd.setUserId("ramana4_jwilson1");
      Assert.assertEquals(1, scaHandler.execute(cmd).intValue());
    }
}
