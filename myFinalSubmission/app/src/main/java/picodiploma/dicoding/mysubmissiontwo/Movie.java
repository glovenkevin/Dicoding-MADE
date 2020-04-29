package picodiploma.dicoding.mysubmissiontwo;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String Photo;
    private String Name;
    private String Description;
    private String Ratings;
    private String ReleaseDate;
    private String TipeMovie;

    public String getRatings() {
        return Ratings;
    }

    public void setRatings(String ratings) {
        Ratings = ratings;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }

    public void setTipeMovie(String tipeMovie) { TipeMovie = tipeMovie; }

    public String getTipeMovie() { return TipeMovie;}

    public static Creator<Movie> getCREATOR() {
        return CREATOR;
    }

    protected Movie(Parcel in) {
        Photo = in.readString();
        Name = in.readString();
        Description = in.readString();
        ReleaseDate = in.readString();
        Ratings = in.readString();
        TipeMovie = in.readString();
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

    public Movie() {

    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Photo);
        dest.writeString(Name);
        dest.writeString(Description);
        dest.writeString(ReleaseDate);
        dest.writeString(Ratings);
        dest.writeString(TipeMovie);
    }
}
