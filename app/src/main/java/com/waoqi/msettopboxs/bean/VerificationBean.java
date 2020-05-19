package com.waoqi.msettopboxs.bean;

import com.waoqi.mvp.net.IModel;

public class VerificationBean  extends BaseModel {
    String UserID;
    String Result;
    String Description;
    String OTTUserToken;
    String expiredTime;
    String TimeOut;


    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getOTTUserToken() {
        return OTTUserToken;
    }

    public void setOTTUserToken(String OTTUserToken) {
        this.OTTUserToken = OTTUserToken;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getTimeOut() {
        return TimeOut;
    }

    public void setTimeOut(String timeOut) {
        TimeOut = timeOut;
    }
}
