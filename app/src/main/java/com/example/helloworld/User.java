package com.example.helloworld;

class User {
    private String username;
    private String userid;
    private String userphone;
    private String useraddress;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userid='" + userid + '\'' +
                ", userphone='" + userphone + '\'' +
                ", useraddress='" + useraddress + '\'' +
                '}';
    }
    public User(String username, String userid, String userphone, String useraddress) {
        this.username = username;
        this.userid = userid;
        this.userphone = userphone;
        this.useraddress = useraddress;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public void setUseraddress(String useraddress) {
        this.useraddress = useraddress;
    }

    public String getUsername() {
        return username;
    }

    public String getUserid() {
        return userid;
    }

    public String getUserphone() {
        return userphone;
    }

    public String getUseraddress() {
        return useraddress;
    }

}
