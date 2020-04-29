package picodiploma.dicoding.mysubmissiontwo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import static picodiploma.dicoding.mysubmissiontwo.db.databaseContract.movieCollumn;

public class databaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbmovie";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format(
            "CREATE TABLE %s"
            + "(%s INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " %s TEXT NOT NULL, "
            + " %s TEXT NOT NULL, "
            + " %s TEXT NOT NULL, "
            + " %s TEXT NOT NULL, "
            + " %s TEXT NOT NULL, "
            + " %s TEXT NOT NULL)",
            movieCollumn.TABLE_MOVIE,
            movieCollumn._ID,
            movieCollumn.NAME,
            movieCollumn.DESCRIPTION,
            movieCollumn.RATING,
            movieCollumn.RELEASE_DATE,
            movieCollumn.PHOTOS,
            movieCollumn.TYPE_MOVIES
    );

    public databaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + movieCollumn.TABLE_MOVIE);
        onCreate(db);
    }
}
