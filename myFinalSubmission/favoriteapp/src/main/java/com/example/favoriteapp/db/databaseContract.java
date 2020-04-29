package com.example.favoriteapp.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class databaseContract {
    public static final String AUTHORITY = "picodiploma.dicoding.mysubmissiontwo";
    private static final String SCHEME = "content";

    public static final  class  movieCollumn implements BaseColumns {
        public static String TABLE_MOVIE = "movie";
        public static String NAME = "name";
        public static String DESCRIPTION = "description";
        public static String RATING = "rating";
        public static String RELEASE_DATE = "release_date";
        public static String PHOTOS = "photo";
        public static String TYPE_MOVIES = "type_movies";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
    }

}
