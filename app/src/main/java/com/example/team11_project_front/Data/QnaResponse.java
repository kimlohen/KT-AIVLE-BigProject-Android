package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QnaResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("answer_set")
    private List<Answer_set> answer_set;
    @SerializedName("answer_count")
    private String answer_count;
    @SerializedName("pictureid")
    private String photo;
    @SerializedName("model_result")
    private String model_request;
    @SerializedName("is_question")
    private String is_question;
    @SerializedName("title")
    private String title;
    @SerializedName("contents")
    private String contents;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("userid")
    private String userid;
    @SerializedName("user_name")
    private String user_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer_count() {
        return answer_count;
    }

    public void setAnswer_count(String answer_count) {
        this.answer_count = answer_count;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getModel_request() {
        return model_request;
    }

    public void setModel_request(String model_request) {
        this.model_request = model_request;
    }

    public String getIs_question() {
        return is_question;
    }

    public void setIs_question(String is_question) {
        this.is_question = is_question;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public List<Answer_set> getAnswer_set() {
        return answer_set;
    }

    public void setAnswer_set(List<Answer_set> answer_set) {
        this.answer_set = answer_set;
    }

    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}

