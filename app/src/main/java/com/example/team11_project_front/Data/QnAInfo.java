package com.example.team11_project_front.Data;

public class QnAInfo {
    private String id;
    private String title;
    private String writer;
    private String date;
    private String ansNum;
    private String photo;
    private String content;

    public QnAInfo(String id, String title, String writer, String date, String ansNum, String photo, String content) {
        this.title = title;
        this.writer = writer;
        this.date = date;
        this.ansNum = ansNum;
        this.photo = photo;
        this.content = content;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
