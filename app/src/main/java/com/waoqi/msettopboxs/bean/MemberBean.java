package com.waoqi.msettopboxs.bean;

public class MemberBean {
    private String name;
    private String qrCode;
    private float money;

    public MemberBean(String name, String qrCode,float money) {
        this.name = name;
        this.qrCode = qrCode;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
