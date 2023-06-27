package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class GPTRequest {
    @SerializedName("gpt_explain")
    public String gpt_explain;

    public String getGpt_explain() {
        return gpt_explain;
    }
    public void setGpt_explain(String gpt_explain) {
        this.gpt_explain = gpt_explain;
    }
    public GPTRequest(String gpt_explain){
        this.gpt_explain = gpt_explain;
    }
}