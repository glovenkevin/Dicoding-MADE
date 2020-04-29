package com.example.favoriteapp;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.favoriteapp.db.databaseContract;
import com.example.favoriteapp.helper.MappingHelper;

import java.util.ArrayList;

import static com.example.favoriteapp.FavoriteDetailMovie.EXTRA_MOVIE;

public class MainActivity extends AppCompatActivity implements LoadMovieCallBack {

    ListView favMovieListView;
    ProgressBar progressBar;
    MovieAdapter adapter;
    ArrayList<Movie> listMovie = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progresBarContProv);
        favMovieListView = findViewById(R.id.favoriteMovieListView);

        adapter = new MovieAdapter(this);
        favMovieListView.setAdapter(adapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(databaseContract.movieCollumn.CONTENT_URI, true, myObserver);

        new LoadMovieAsyn(this, this).execute();

        favMovieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, FavoriteDetailMovie.class);
                intent.putExtra(EXTRA_MOVIE, listMovie.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void preExecute() {
        new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        };
    }

    @Override
    public void postExecute(ArrayList<Movie> movieArrayList) {
        progressBar.setVisibility(View.INVISIBLE);
        if (!movieArrayList.isEmpty()) {
            if (!listMovie.isEmpty())
                listMovie.clear();
            listMovie.addAll(movieArrayList);
            adapter.setListMovie(movieArrayList);
            adapter.notifyDataSetChanged();
        }
    }

    private static class LoadMovieAsyn extends AsyncTask<Void, Void, ArrayList<Movie>> {

        private final Context context;
        private final LoadMovieCallBack loadMovieCallBack;

        private LoadMovieAsyn(Context context, LoadMovieCallBack loadMovieCallBack) {
            this.context = context;
            this.loadMovieCallBack = loadMovieCallBack;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadMovieCallBack.preExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            Cursor cursor = context.getContentResolver().query(databaseContract.movieCollumn.CONTENT_URI,
                    null, null, null, null);
            return MappingHelper.mapCursorToArray(cursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            loadMovieCallBack.postExecute(movies);
        }
    }

    public static class DataObserver extends ContentObserver {

        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadMovieAsyn(context, (LoadMovieCallBack) context).execute();
        }
    }
}
