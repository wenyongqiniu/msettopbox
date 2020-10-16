package com.yxws.msettopboxs.bean;

import com.yxws.mvp.net.IModel;

import java.util.List;

public class Bean implements IModel {


    /**
     * retcode : 0
     * msg : 操作成功
     * data : [{"id":"1","favoriteType":0,"favoriteName":"小猫","favoriteUrl":"/loadFile/default/2020/04/07/a69198ff-a667-4916-a31f-4f1f4428f108.jpg","favoriteStatus":0,"isDelete":0,"createTime":"2020-05-11 16:51:23","updateTime":"2020-05-11 16:51:26"}]
     * success : true
     */

    private int retcode;
    private String msg;
    private boolean success;
    private List<DataBean> data;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isAuthError() {
        return false;
    }

    @Override
    public boolean isBizError() {
        return false;
    }

    @Override
    public String getErrorMsg() {
        return null;
    }

    public static class DataBean {
        /**
         * id : 1
         * favoriteType : 0
         * favoriteName : 小猫
         * favoriteUrl : /loadFile/default/2020/04/07/a69198ff-a667-4916-a31f-4f1f4428f108.jpg
         * favoriteStatus : 0
         * isDelete : 0
         * createTime : 2020-05-11 16:51:23
         * updateTime : 2020-05-11 16:51:26
         */

        private String id;
        private int favoriteType;
        private String favoriteName;
        private String favoriteUrl;
        private int favoriteStatus;
        private int isDelete;
        private String createTime;
        private String updateTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getFavoriteType() {
            return favoriteType;
        }

        public void setFavoriteType(int favoriteType) {
            this.favoriteType = favoriteType;
        }

        public String getFavoriteName() {
            return favoriteName;
        }

        public void setFavoriteName(String favoriteName) {
            this.favoriteName = favoriteName;
        }

        public String getFavoriteUrl() {
            return favoriteUrl;
        }

        public void setFavoriteUrl(String favoriteUrl) {
            this.favoriteUrl = favoriteUrl;
        }

        public int getFavoriteStatus() {
            return favoriteStatus;
        }

        public void setFavoriteStatus(int favoriteStatus) {
            this.favoriteStatus = favoriteStatus;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
