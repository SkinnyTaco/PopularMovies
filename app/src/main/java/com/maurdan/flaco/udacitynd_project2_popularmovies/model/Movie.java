package com.maurdan.flaco.udacitynd_project2_popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("poster_path")
    @Expose
    private String poster;

    @SerializedName("vote_average")
    @Expose
    private String voteAverage;

    @SerializedName("overview")
    @Expose
    private String synopsis;

    @SerializedName("backdrop_path")
    @Expose
    private String banner;

    public Movie(Integer id, String title, String releaseDate, String poster, String voteAverage,
                 String synopsis, String banner) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.poster = poster;

        this.voteAverage = voteAverage;
        this.synopsis = synopsis;
        this.banner = banner;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPoster() {
        return poster;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
