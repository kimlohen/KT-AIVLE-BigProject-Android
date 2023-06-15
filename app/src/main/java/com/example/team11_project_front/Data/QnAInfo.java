package com.example.team11_project_front.Data;

public class QnAInfo {
    private String title;
    private String writer;
    private String date;
    private String ansNum;

    public QnAInfo(String title, String writer, String date, String ansNum) {
        this.title = title;
        this.writer = writer;
        this.date = date;
        this.ansNum = ansNum;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
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
    public String getAnsNum() {
        return ansNum;
    }
    public void setAnsNum(String ansNum) {
        this.ansNum = ansNum;
    }

}
