package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnsResponse {
    @SerializedName("id")
    private String answer_id;
    @SerializedName("user_name")
    private String user_name;
    @SerializedName("user_email")
    private String user_email;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("contents")
    private String contents;
    @SerializedName("questionid")
    private String q_id;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("hos_info")
    private HospitalResponse hos_info;
    public String getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(String answer_id) {
        this.answer_id = answer_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getContents() {
        return contents;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }
    public String getQ_id() {
        return q_id;
    }
    public void setQ_id(String q_id) {
        this.q_id = q_id;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getUser_email() {
        return user_email;
    }
    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public HospitalResponse getHos_info() {
        return hos_info;
    }
    public void setHos_info(HospitalResponse hos_info) {
        this.hos_info = hos_info;
    }
}

