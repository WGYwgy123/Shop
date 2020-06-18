package com.wgy.shop.entity;

import javax.validation.constraints.Pattern;
import java.util.Date;

public class Consumer {
    private Integer userid;

    @Pattern(regexp = "(^[a-zA-Z0-9_-]{3,16}$)|(^[\\u2e80-\\u9FFF]{2,5})",
            message = "用户名必须是2-5位中文或者3-16位英文和数字的组合")
    private String username;

    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$",
            message = "由数字和字母组成，并且要同时含有数字和字母，且长度要在6-16位之间")
    private String password;

    private Date regtime;

    @Pattern(regexp = "^([a-zA-Z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$",
            message = "邮箱格式不正确")
    private String email;

    @Pattern(regexp = "^((13\\d{9}$)|(15[0,1,2,3,5,6,7,8,9]\\d{8}$)|(18[0,2,5,6,7,8,9]\\d{8}$)|(147\\d{8})$)",
            message = "手机号码格式不正确")
    private String telephone;

    public Consumer(){}

    public Consumer(String username, String password, Date regtime, String email, String telephone) {
        this.username = username;
        this.password = password;
        this.regtime = regtime;
        this.email = email;
        this.telephone = telephone;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getRegtime() {
        return regtime;
    }

    public void setRegtime(Date regtime) {
        this.regtime = regtime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }
}