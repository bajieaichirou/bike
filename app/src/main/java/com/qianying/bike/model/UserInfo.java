package com.qianying.bike.model;

import java.io.Serializable;

/**
 * Created by zhangshengwen on 09/06/2017.
 * * id *
 * mobi *
 * name *
 * avt *
 * avatar 头像
 * <p>
 * idno * 身份证号
 * certif * 是否实名认证
 * bal * 账户余额
 * llip * 上次登入ip
 * llt * 上次登入时间戳
 * regt * 注册时间戳
 */

public class UserInfo implements Serializable{

    /**
     * errno : 0
     * errmsg :
     * data : {"user":{"id":"3","mobi":"17621185603","name":"","idno":"","certif":"0","bal":"0.00","avt":"","llip":"","llt":"0","regt":"1497251973"}}
     */

    private int errno;
    private String errmsg;
    private DataEntity data;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity implements Serializable{
        /**
         * user : {"id":"3","mobi":"17621185603","name":"","idno":"","certif":"0","bal":"0.00","avt":"","llip":"","llt":"0","regt":"1497251973"}
         */

        private UserEntity user;

        public UserEntity getUser() {
            return user;
        }

        public void setUser(UserEntity user) {
            this.user = user;
        }

        public static class UserEntity implements Serializable{
            /**
             * id : 3
             * mobi : 17621185603
             * name :
             * idno :
             * certif : 0
             * bal : 0.00
             * avt :
             * llip :
             * llt : 0
             * regt : 1497251973
             */

            private String id;
            private String mobi;
            private String name;
            private String idno;
            private String certif;
            private String bal;
            private String avt;
            private String llip;
            private String llt;
            private String regt;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMobi() {
                return mobi;
            }

            public void setMobi(String mobi) {
                this.mobi = mobi;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIdno() {
                return idno;
            }

            public void setIdno(String idno) {
                this.idno = idno;
            }

            public String getCertif() {
                return certif;
            }

            public void setCertif(String certif) {
                this.certif = certif;
            }

            public String getBal() {
                return bal;
            }

            public void setBal(String bal) {
                this.bal = bal;
            }

            public String getAvt() {
                return avt;
            }

            public void setAvt(String avt) {
                this.avt = avt;
            }

            public String getLlip() {
                return llip;
            }

            public void setLlip(String llip) {
                this.llip = llip;
            }

            public String getLlt() {
                return llt;
            }

            public void setLlt(String llt) {
                this.llt = llt;
            }

            public String getRegt() {
                return regt;
            }

            public void setRegt(String regt) {
                this.regt = regt;
            }
        }
    }
}
