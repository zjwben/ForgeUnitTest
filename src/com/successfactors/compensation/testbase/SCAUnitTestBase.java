package com.successfactors.compensation.testbase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import org.apache.log4j.xml.DOMConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.successfactors.compensation.test.util.ScaUnitTestUtil;
import com.successfactors.forge.AutoDetectableSerivceCommandHandler;
import com.successfactors.forge.Forge;
import com.successfactors.forge.rit.RIT;
import com.successfactors.sca.ServiceCommandHandler;
import com.successfactors.xi.util.SeamEnvUtils;

public class SCAUnitTestBase {
  protected static long startTime;
  
  protected static AutoDetectableSerivceCommandHandler scaHandler;
  
  protected static ScaUnitTestUtil testUtil;
  
  protected static Connection dbConnection;
  
  protected Savepoint savePoint;
  
  
  @BeforeClass
  public static void initTest(){
    startTime = System.currentTimeMillis();
    DOMConfigurator.configure(RIT.class.getClassLoader().getResource("forge/forge-log4j.xml"));
    Forge.newInstance();
    
    scaHandler = (AutoDetectableSerivceCommandHandler)SeamEnvUtils.getInstance("scaHandler", false);
    testUtil = new ScaUnitTestUtil();
    dbConnection = (Connection) SeamEnvUtils.getInstance("dbConnection", false);
  }
  
  @Before
  public void startTest(){
    try {
      savePoint = dbConnection.setSavepoint();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  @After
  public void endTest(){
    try {
      dbConnection.rollback(savePoint);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  
  @AfterClass
  public static void destroyTest(){
      long endTime = System.currentTimeMillis();
      System.out.println("===========================");
      System.out.println(((endTime - startTime)/1000.0 ) + " seconds");
      System.out.println("===========================");
  }
}
