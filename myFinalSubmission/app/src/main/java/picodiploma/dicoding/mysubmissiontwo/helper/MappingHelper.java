package picodiploma.dicoding.mysubmissiontwo.helper;

import android.database.Cursor;

import java.util.ArrayList;

import picodiploma.dicoding.mysubmissiontwo.Movie;
import static picodiploma.dicoding.mysubmissiontwo.db.databaseContract.movieCollumn.*;

public class MappingHelper {
    public static ArrayList<Movie> mapCursorToArray (Cursor movieCursor) {
        ArrayList<Movie> movieArrayList = new ArrayList<>();

        while (movieCursor.moveToNext()){
            Movie movie = new Movie();
            movie.setName(movieCursor.getString(movieCursor.getColumnIndexOrThrow(NAME)));
            movie.setRatings(movieCursor.getString(movieCursor.getColumnIndexOrThrow(RATING)));
            movie.setReleaseDate(movieCursor.getString(movieCursor.getColumnIndexOrThrow(RELEASE_DATE)));
            movie.setDescription(movieCursor.getString(movieCursor.getColumnIndexOrThrow(DESCRIPTION)));
            movie.setPhoto(movieCursor.getString(movieCursor.getColumnIndexOrThrow(PHOTOS)));
            movie.setTipeMovie(movieCursor.getString(movieCursor.getColumnIndexOrThrow(TYPE_MOVIES)));
            movieArrayList.add(movie);
        }
        return  movieArrayList;
    }
}
