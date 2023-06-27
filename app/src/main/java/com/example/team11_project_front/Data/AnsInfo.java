package com.example.team11_project_front.Data;

public class AnsInfo {
    private String writer;
    private String date;
    private String content;
    private String connect;
    private String hos_name;
    private String hos_intro;
    private String hos_profile;

    public AnsInfo(String writer, String date, String content, String connect, String hos_name, String hos_intro, String hos_profile) {
        this.writer = writer;
        this.date = date;
        this.connect = connect;
        this.content = content;
        this.hos_name = hos_name;
        this.hos_intro = hos_intro;
        this.hos_profile = hos_profile;
    }

    public String getWriter() {
        return writer;
    }
    public void setWriter(String writer) {
        this.writer = writer;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public String getHos_name() {
        return hos_name;
    }

    public void setHos_name(String hos_name) {
        this.hos_name = hos_name;
    }

    public String getHos_intro() {
        return hos_intro;
    }

    public void setHos_intro(String hos_intro) {
        this.hos_intro = hos_intro;
    }

    public String getHos_profile() {
        return hos_profile;
    }

    public void setHos_profile(String hos_profile) {
        this.hos_profile = hos_profile;
    }

}
