package picodiploma.dicoding.mysubmissiontwo.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import picodiploma.dicoding.mysubmissiontwo.db.movieHelper;
import static picodiploma.dicoding.mysubmissiontwo.db.databaseContract.AUTHORITY;
import static picodiploma.dicoding.mysubmissiontwo.db.databaseContract.movieCollumn.TABLE_MOVIE;

public class MovieProvider extends ContentProvider {

    private static final int NOTE = 1;
    private static final int NOTE_ID = 2;
    private movieHelper movieHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE, NOTE);
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE + "/#",
                NOTE_ID);
    }

    public MovieProvider() {}

    @Override
    public boolean onCreate() {
        movieHelper = picodiploma.dicoding.mysubmissiontwo.db.movieHelper.getInstance(getContext());
        movieHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor result;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                result = movieHelper.queryAllFavoriteMovie();
                break;
            case NOTE_ID:
                result = movieHelper.queryOneMovie(selectionArgs);
                break;
            default:
                result = null;
                break;
        }
        return result;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return Integer.parseInt(null);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return Integer.parseInt(null);
    }
}
