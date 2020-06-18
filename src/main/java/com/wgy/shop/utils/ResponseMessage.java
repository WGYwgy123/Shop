package com.wgy.shop.utils;

import java.util.HashMap;
import java.util.Map;

//响应数据的封装
public class ResponseMessage {
    //错误码
    private String errorCode;
    //错误描述
    private String errorMsg;
    //返回数据
    private Map<String, Object> objectMap = new HashMap<String, Object>();

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Map<String, Object> getObjectMap() {
        return objectMap;
    }

    public void setObjectMap(Map<String, Object> objectMap) {
        this.objectMap = objectMap;
    }

    //往hashmap里面增加数据的方法
    public ResponseMessage addObject(String key, Object value){
        this.objectMap.put(key,value);
        return this;
    }

    //处理成功的方法
    public static ResponseMessage success(){
        ResponseMessage rm = new ResponseMessage();
        rm.setErrorCode("100");
        rm.setErrorMsg("处理成功");
        return rm;
    }

    //处理失败的方法
    public static ResponseMessage error(){
        ResponseMessage rm = new ResponseMessage();
        rm.setErrorCode("200");
        rm.setErrorMsg("处理失败");
        return rm;
    }
}
