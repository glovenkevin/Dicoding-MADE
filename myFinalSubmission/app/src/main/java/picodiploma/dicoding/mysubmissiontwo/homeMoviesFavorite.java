package picodiploma.dicoding.mysubmissiontwo;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import java.util.ArrayList;

import picodiploma.dicoding.mysubmissiontwo.db.movieHelper;
import picodiploma.dicoding.mysubmissiontwo.helper.MappingHelper;

import static picodiploma.dicoding.mysubmissiontwo.MainActivity.SEARCH_PARAM;

/**
 * A simple {@link Fragment} subclass.
 */
public class homeMoviesFavorite extends Fragment implements LoadNotesCallback{

    private movieHelper MovieHelper;

    private ProgressBar progressBar;
    homeMoviesFavoriteAdapter adapter;
    private RecyclerView recyclerView;

    public homeMoviesFavorite() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_movies_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MovieHelper = movieHelper.getInstance(getActivity());
        recyclerView = view.findViewById(R.id.listHomeMoviesFavorite);
        progressBar = view.findViewById(R.id.ProgressBarHomeMoviesFavorite);

        new LoadMoviesAsyn(MovieHelper, this).execute();

        adapter = new homeMoviesFavoriteAdapter(getActivity(), new ArrayList<Movie>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        if (movieArrayList.size() > 0) {
            if (getArguments() != null){
                if (!getArguments().getString(SEARCH_PARAM, "").isEmpty()) {
                    String param = getArguments().getString(SEARCH_PARAM);
                    ArrayList<Movie> temp = new ArrayList<>();
                    for (int i=0; i<movieArrayList.size(); i++){
                        if (movieArrayList.get(i).getName().contains(param)){
                            temp.add(movieArrayList.get(i));
                        }
                    }

                    movieArrayList.clear();
                    movieArrayList.addAll(temp);
                }
            }
            adapter.setListMovie(movieArrayList);
        }
    }

    private static class LoadMoviesAsyn extends AsyncTask<Void, Void, ArrayList<Movie>> {

        private final movieHelper MoviesHelper;
        private final LoadNotesCallback loadNotesCallback;

        LoadMoviesAsyn(movieHelper mh, LoadNotesCallback call) {
            this.MoviesHelper = mh;
            this.loadNotesCallback = call;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadNotesCallback.preExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            Cursor dataCursor = MoviesHelper.queryAllHomeMovies();
            return MappingHelper.mapCursorToArray(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            loadNotesCallback.postExecute(movies);
        }
    }
}

interface LoadNotesCallback {
    void preExecute();
    void postExecute(ArrayList<Movie> movieArrayList);
}