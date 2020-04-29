package picodiploma.dicoding.mysubmissiontwo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static picodiploma.dicoding.mysubmissiontwo.db.databaseContract.movieCollumn.NAME;
import static picodiploma.dicoding.mysubmissiontwo.db.databaseContract.movieCollumn.PHOTOS;
import static picodiploma.dicoding.mysubmissiontwo.db.databaseContract.movieCollumn.TYPE_MOVIES;

public class movieHelper {

    private static final String DATABASE_TABLE = databaseContract.movieCollumn.TABLE_MOVIE;
    private static databaseHelper DatabaseHelper;
    private static movieHelper INSTANCE;

    private static SQLiteDatabase database;

    private movieHelper (Context context){
        DatabaseHelper = new databaseHelper(context);
    }

    public static movieHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new movieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException{
        database = DatabaseHelper.getWritableDatabase();
    }

    public void close() {
        DatabaseHelper.close();

        if (database.isOpen()){
            database.close();
        }
    }

    public Cursor queryAllHomeMovies() {
        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + TYPE_MOVIES + "=?";
        Cursor cursor = database.rawQuery(query, new String[]{"home_movie"});
        return cursor;
    }

    public Cursor queryAllHomeMoviesPoster() {
        String query = "SELECT " + PHOTOS +" FROM " + DATABASE_TABLE + " WHERE " + TYPE_MOVIES + "=?";
        Cursor cursor = database.rawQuery(query, new String[]{"home_movie"});
        return cursor;
    }

    public Cursor queryAllTvMovies() {
        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + TYPE_MOVIES + "=?";
        Cursor cursor = database.rawQuery(query, new String[]{"tv_movies"});
        return cursor;
    }

    public int queryName(String movieName, String typeMovies) {
        String query = "SELECT * FROM " + DATABASE_TABLE +
                " WHERE " + databaseContract.movieCollumn.NAME + "=? "
                + " AND " + databaseContract.movieCollumn.TYPE_MOVIES + "=?";
        Cursor cursor = database.rawQuery(query, new String[] {movieName, typeMovies});
        return cursor.getCount();
    }

    public long insertMovie(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteByName(String name, String typeMovies) {
        return database.delete(DATABASE_TABLE, NAME + " = ?" + " AND " + TYPE_MOVIES + " =?",
                new String[]{name, typeMovies});
    }

    public Cursor queryAllFavoriteMovie() {
        String query = "SELECT * FROM " + DATABASE_TABLE;
        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }

    public Cursor queryOneMovie(String[] data) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + DATABASE_TABLE +
                " WHERE " + NAME + " =? AND " + TYPE_MOVIES + " =?", data);
        return cursor;
    }
}
