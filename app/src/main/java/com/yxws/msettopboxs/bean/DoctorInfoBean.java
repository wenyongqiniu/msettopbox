package com.yxws.msettopboxs.bean;

import java.io.Serializable;

/**
 * author : Zzy
 * date   : 2020/7/21
 */
public class DoctorInfoBean extends BasePresponce<DoctorInfoBean> implements Serializable {
    public int id;
    public String tips;
    public String name;
    public String introduction;
    public String creatTime;
    public String updateTime;
    public String occupation;
    public String searchField;
}
