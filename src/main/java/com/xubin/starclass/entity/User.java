package com.xubin.starclass.entity;
// default package



/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User  implements java.io.Serializable {


    // Fields    

     private Integer uid;
     private String account;
     private String pwd;
     private String nick;
     private String photoUrl;
     private Integer start;
     private Integer onlineDay;
     private String lastOnlineDate;


    // Constructors

    /** default constructor */
    public User() {
    }

	/** minimal constructor */
    public User(String account, String pwd) {
        this.account = account;
        this.pwd = pwd;
    }
    
    /** full constructor */
    public User(String account, String pwd, String nick, String photoUrl, Integer start, Integer onlineDay, String lastOnlineDate) {
        this.account = account;
        this.pwd = pwd;
        this.nick = nick;
        this.photoUrl = photoUrl;
        this.start = start;
        this.onlineDay = onlineDay;
        this.lastOnlineDate = lastOnlineDate;
    }

    public Integer getUid() {
        return this.uid;
    }
    
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return this.account;
    }
    
    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return this.pwd;
    }
    
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNick() {
        return this.nick;
    }
    
    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }
    
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getStart() {
        return this.start;
    }
    
    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getOnlineDay() {
        return this.onlineDay;
    }
    
    public void setOnlineDay(Integer onlineDay) {
        this.onlineDay = onlineDay;
    }

    public String getLastOnlineDate() {
        return this.lastOnlineDate;
    }
    
    public void setLastOnlineDate(String lastOnlineDate) {
        this.lastOnlineDate = lastOnlineDate;
    }
   








}