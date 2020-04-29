package com.example.favoriteapp.helper;

import android.database.Cursor;

import com.example.favoriteapp.Movie;

import java.util.ArrayList;

import static com.example.favoriteapp.db.databaseContract.movieCollumn.DESCRIPTION;
import static com.example.favoriteapp.db.databaseContract.movieCollumn.NAME;
import static com.example.favoriteapp.db.databaseContract.movieCollumn.PHOTOS;
import static com.example.favoriteapp.db.databaseContract.movieCollumn.RATING;
import static com.example.favoriteapp.db.databaseContract.movieCollumn.RELEASE_DATE;
import static com.example.favoriteapp.db.databaseContract.movieCollumn.TYPE_MOVIES;

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
