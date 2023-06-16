package com.example.team11_project_front.Data;

public class AnsInfo {
    private String writer;
    private String date;
    private String content;
    private String connect;

    public AnsInfo(String writer, String date, String content, String connect) {
        this.writer = writer;
        this.date = date;
        this.connect = connect;
        this.content = content;
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

}
