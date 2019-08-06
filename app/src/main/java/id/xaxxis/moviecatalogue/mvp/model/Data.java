package id.xaxxis.moviecatalogue.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data implements Parcelable {
    private int id;

    private String title;

    @SerializedName("name")
    private String name;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("first_air_date")
    private String lastAirDate;

    private float popularity;

    private String overview;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("vote_average")
    private float voteAverage;

    @SerializedName("genres")
    private List<DataGenre> genres;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public List<DataGenre> getGenres() {
        return genres;
    }

    public void setGenres(List<DataGenre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", lastAirDate='" + lastAirDate + '\'' +
                ", popularity=" + popularity +
                ", overview='" + overview + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", voteAverage=" + voteAverage +
                ", genres=" + genres +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.name);
        dest.writeString(this.releaseDate);
        dest.writeString(this.lastAirDate);
        dest.writeFloat(this.popularity);
        dest.writeString(this.overview);
        dest.writeString(this.backdropPath);
        dest.writeString(this.posterPath);
        dest.writeFloat(this.voteAverage);
    }

    public Data() {
    }

    protected Data(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.name = in.readString();
        this.releaseDate = in.readString();
        this.lastAirDate = in.readString();
        this.popularity = in.readFloat();
        this.overview = in.readString();
        this.backdropPath = in.readString();
        this.posterPath = in.readString();
        this.voteAverage = in.readFloat();
        this.genres = new ArrayList<DataGenre>();
        in.readList(this.genres, DataGenre.class.getClassLoader());
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
