package com.maurdan.flaco.udacitynd_project2_popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("results")
    @Expose
    private List<Movie> results;

    public Result(List<Movie> movies) {
        this.results = movies;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
