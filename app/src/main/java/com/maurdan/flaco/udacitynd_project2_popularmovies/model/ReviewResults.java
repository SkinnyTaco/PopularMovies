package com.maurdan.flaco.udacitynd_project2_popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewResults {

    @Expose
    @SerializedName("content")
    private String content;

    public ReviewResults(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
