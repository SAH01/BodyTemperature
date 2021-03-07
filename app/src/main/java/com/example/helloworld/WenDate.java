package com.example.helloworld;

public class WenDate {
    private String name;
    private String dateandtime;
    private String address;
    private String wendu;
    private String more;

    public WenDate(String name, String dateandtime, String address, String wendu, String more) {
        this.name = name;
        this.dateandtime = dateandtime;
        this.address = address;
        this.wendu = wendu;
        this.more = more;
    }
    public WenDate(){};

    @Override
    public String toString() {
        return "WenDate{" +
                "name='" + name + '\'' +
                ", dateandtime='" + dateandtime + '\'' +
                ", address='" + address + '\'' +
                ", wendu='" + wendu + '\'' +
                ", more='" + more + '\'' +
                '}';
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateandtime() {
        return dateandtime;
    }

    public void setDateandtime(String dateandtime) {
        this.dateandtime = dateandtime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }
}
