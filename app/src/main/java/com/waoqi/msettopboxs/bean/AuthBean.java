package com.waoqi.msettopboxs.bean;

public class AuthBean extends BaseModel {

    /**
     * Result : 0
     * UserID : 19825783251
     * Balance : 0
     * AuthCode : accountinfo=U8SAskBajorj3AH2liD2c6b0scW44T003Qj%2Bs4QGONyYZlkWG8NVxqij3M%2BHZKY6Mjh9ZBCtU9LQFi6TPzPxIhzrYUy3gIB%2BkIrH49PbrQn69CisB3AWhegQfAu1WxLLZWbQLM4Pa1fvAHtvFe9eJQ%3D%3D%3A20200526113315%2C19825783251%2C223.70.244.11%2C20200526113315%2Cpjsag003230100000000000000000061%2C2DBC495BC72DE757777024C2EF5D9860%2C%2C1%2C0%2C-1%2C%2C1%2C%2C-1%2C-3%2C1%2CEND&GuardEncType=2
     */

    private String Result;
    private String UserID;
    private int Balance;
    private String AuthCode;

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public int getBalance() {
        return Balance;
    }

    public void setBalance(int Balance) {
        this.Balance = Balance;
    }

    public String getAuthCode() {
        return AuthCode;
    }

    public void setAuthCode(String AuthCode) {
        this.AuthCode = AuthCode;
    }
}
