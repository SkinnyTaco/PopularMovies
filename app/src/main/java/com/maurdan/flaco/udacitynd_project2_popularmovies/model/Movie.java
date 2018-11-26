package com.maurdan.flaco.udacitynd_project2_popularmovies.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.maurdan.flaco.udacitynd_project2_popularmovies.data.MovieDatabase;

@Entity(tableName = "movie")
public class Movie implements Parcelable {

    @SerializedName("id")
    @PrimaryKey
    @Expose
    private Integer id;

    @SerializedName("title")
    @ColumnInfo(name = "title")
    @Expose
    private String title;

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    @Expose
    private String releaseDate;

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    @Expose
    private String poster;

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    @Expose
    private String voteAverage;

    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    @Expose
    private String synopsis;

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    @Expose
    private String banner;

    @Ignore
    public Movie(String title, String releaseDate, String poster, String voteAverage,
                 String synopsis, String banner) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.poster = poster;

        this.voteAverage = voteAverage;
        this.synopsis = synopsis;
        this.banner = banner;
    }

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

    protected Movie(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
        releaseDate = in.readString();
        poster = in.readString();
        voteAverage = in.readString();
        synopsis = in.readString();
        banner = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeString(poster);
        dest.writeString(voteAverage);
        dest.writeString(synopsis);
        dest.writeString(banner);
    }
}
