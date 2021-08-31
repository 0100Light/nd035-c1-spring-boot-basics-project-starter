package com.udacity.jwdnd.course1.cloudstorage.model;

public class User {

//    CREATE TABLE IF NOT EXISTS USERS (
//            userid INT PRIMARY KEY auto_increment,
//            username VARCHAR(20),
//    salt VARCHAR,
//    password VARCHAR,
//    firstname VARCHAR(20),
//    lastname VARCHAR(20)

    private Integer userid;
    private String username;
    private String salt;
    private String firstname;
    private String lastname;
    private String password;

    public User(Integer userid, String username, String salt, String firstname, String lastname, String password) {
        this.userid = userid;
        this.username = username;
        this.salt = salt;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
    }

    public User(String firstname, String lastname, String username, String password) {
        this.userid = null;
        this.username = username;
        this.salt = "";
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
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
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
