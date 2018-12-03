package com.maurdan.flaco.udacitynd_project2_popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Review {

    @Expose
    @SerializedName("results")
    private List<ReviewResults> reviews;

    public Review(List<ReviewResults> reviews) {
        this.reviews = reviews;
    }

    public List<ReviewResults> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewResults> reviews) {
        this.reviews = reviews;
    }
}
