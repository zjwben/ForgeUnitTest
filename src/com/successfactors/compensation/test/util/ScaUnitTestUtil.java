package com.successfactors.compensation.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.python.modules.newmodule;


import com.successfactors.youcalc.gson.Gson;
import com.successfactors.youcalc.gson.JsonElement;
import com.successfactors.youcalc.gson.JsonObject;
import com.successfactors.youcalc.gson.JsonParser;
import com.successfactors.youcalc.gson.stream.JsonReader;







public class ScaUnitTestUtil {
    public static final String FILESUFFIX = ".json";
  
    public static final String CHARSET = "UTF-8";
    
    public  CharsetEncoder encoder = Charset.forName(CHARSET).newEncoder();
    
    public  CharsetDecoder decoder = Charset.forName(CHARSET).newDecoder();
    
    public static final String BASEPATH = Thread.currentThread().getContextClassLoader()  
        .getResource("") + "com/successfactors/compensation/test/json";
    
    public static final String USERDIR = System.getProperty("user.dir") + "/resources/com/successfactors/compensation/test/json";
    
    private Gson json = new Gson();
    
    private JsonParser jsonParser = new JsonParser();
    
    
    
    public static void test(){
        System.out.println(Thread.currentThread().getContextClassLoader()  
            .getResource(""));
    }
    
    public  JsonElement getJsonFromObj(String scaName,Object obj){
      String objStr  = json.toJson(obj);
      if(!isExistResultJson(scaName)){
        saveFile(objStr, (USERDIR + "/" + scaName + FILESUFFIX));
      }
      return jsonParser.parse(objStr);
       
    }
    public String getJsonStrFromObj(String scaName,Object obj){
      String objStr  = json.toJson(obj);
      if(!isExistResultJson(scaName)){
        saveFile(objStr, (USERDIR + "/" + scaName + FILESUFFIX));
      }
      return objStr;
    }
    
    public JsonElement getJsonElementFromLocal(String scaName){
      String jsonPath = getFullFilePath(scaName);
      File file = new File(jsonPath);
      String jsonStr = "";
      if(file.exists()){
         jsonPath = getFullFilePath(scaName);
      }else{
         jsonPath =  USERDIR + "/" + scaName + FILESUFFIX;
      }
      jsonStr = readFile(jsonPath);
      return jsonParser.parse(jsonStr);
    }
    public String getJsonStringFromLocal(String scaName){
      String jsonPath = getFullFilePath(scaName);
      File file = new File(jsonPath);
      String jsonStr = "";
      if(file.exists()){
         jsonPath = getFullFilePath(scaName);
      }else{
         jsonPath =  USERDIR + "/" + scaName + FILESUFFIX;
      }
      jsonStr = readFile(jsonPath);
      return jsonStr;
    }
    
    private String getFullFilePath(String scaName){
      String fullPath = BASEPATH + "/" + scaName + FILESUFFIX;
      fullPath = fullPath.replace("file:/", "");
      fullPath=fullPath.replaceAll( "file:",   " "); //linux,unix  
      return  fullPath;
    }
    private boolean isExistResultJson(String scaName){
      String jsonPath = USERDIR + "/" + scaName + FILESUFFIX;
      File file = new File(jsonPath);
      if(file.exists()){
        return true;
      }else{
        return false;
      }
    }
    
    public void saveFile(String json,String filename){
        FileChannel channel;
        
        try {
          channel = new FileOutputStream(filename).getChannel();
          ByteBuffer bb = strConvertToByteBuffer(json);
          channel.write(bb);
          channel.close();
          channel = null;
        } catch (FileNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
    }
    private  ByteBuffer strConvertToByteBuffer(String content){
        try {
          return encoder.encode(CharBuffer.wrap(content));
        } catch (CharacterCodingException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
          return null;
        }
    }
    public String readFile(String filename){
        FileChannel channel;
        MappedByteBuffer buf = null;
        
        try {
          channel = new FileInputStream(filename).getChannel();
          buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
          channel.close();
          channel = null;
        } catch (FileNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        
        return byteBufferConvertToStr(buf);        
    }

    private String byteBufferConvertToStr(ByteBuffer buf){
        String data = "";
        try {
          int old_position = buf.position();
          data = decoder.decode(buf).toString();
          buf.position(old_position);
        } catch (CharacterCodingException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
          return "";
        }
        return data;
        
    }
}
