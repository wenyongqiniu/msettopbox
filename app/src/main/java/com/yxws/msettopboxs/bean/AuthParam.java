package com.yxws.msettopboxs.bean;

public class AuthParam {

    /**
     * OTTUserToken : 19825783251-14:11:5D:F9:11:7C
     * UserID : 19825783251
     * ContentID : pjsag003230100000000000000000061
     * MAC : 14:11:5D:F9:11:7C
     */

    private String OTTUserToken;
    private String UserID;
    private String ContentID;
    private String MAC;

    public String getOTTUserToken() {
        return OTTUserToken;
    }

    public void setOTTUserToken(String OTTUserToken) {
        this.OTTUserToken = OTTUserToken;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getContentID() {
        return ContentID;
    }

    public void setContentID(String ContentID) {
        this.ContentID = ContentID;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }
}
