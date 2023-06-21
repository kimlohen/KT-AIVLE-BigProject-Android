package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QnaListResponse {
    @SerializedName("count")
    private String count;
    @SerializedName("next")
    private String next;
    @SerializedName("previous")
    private String previous;
    @SerializedName("results")
    private List<QnaResponse> qnaResponses;
    @SerializedName("detail")
    private String detail;

    public String getCount() {
        return count;
    }
    public void setCount(String count) {
        this.count = count;
    }
    public String getNext() {
        return next;
    }
    public void setNext(String next) {
        this.next = next;
    }
    public String getPrevious() {
        return previous;
    }
    public void setPrevious(String previous) {
        this.previous = previous;
    }
    public List<QnaResponse> getQnaResponses() {
        return qnaResponses;
    }
    public void setQnaResponses(List<QnaResponse> qnaResponses) {
        this.qnaResponses = qnaResponses;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
}

