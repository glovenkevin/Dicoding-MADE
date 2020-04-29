package picodiploma.dicoding.mysubmissiontwo.SearchMovie;


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

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import picodiploma.dicoding.mysubmissiontwo.Movie;
import picodiploma.dicoding.mysubmissiontwo.R;

import static picodiploma.dicoding.mysubmissiontwo.MainActivity.SEARCH_PARAM;
import static picodiploma.dicoding.mysubmissiontwo.MainActivity.SEARCH_TYPE;
import static picodiploma.dicoding.mysubmissiontwo.homeMovies.ALAMAT_GAMBAR;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMovie extends Fragment {

    public static final String API_KEY = "5997df57f5905889a5060768b1972c18";
    private ArrayList<Movie> listMovie = new ArrayList<>();

    private ProgressBar progressBar;
    searchAdapter adapter;
    private RecyclerView recyclerView;


    public SearchMovie() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.searchMovieListRv);
        progressBar = view.findViewById(R.id.searchProgressBar);

        if (listMovie.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            getData();
        }

        adapter = new  searchAdapter(getActivity(), listMovie);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void processData(String data) {
        try {
            JSONObject dataMovie = new JSONObject(data);
            JSONArray arrayData = dataMovie.getJSONArray("results");

            for (int i = 0; i <arrayData.length(); i++) {
                JSONObject object = arrayData.getJSONObject(i);
                Movie movie = new Movie();

                movie.setName(object.getString("original_title"));
                movie.setDescription(object.getString("overview"));
                movie.setPhoto(ALAMAT_GAMBAR.concat(object.getString("backdrop_path")));
                movie.setRatings(object.getString("vote_average"));
                movie.setReleaseDate(object.getString("release_date"));
                movie.setTipeMovie("home_movie");
                listMovie.add(movie);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void getData() {

        String data = getArguments().getString(SEARCH_TYPE);
        String param = getArguments().getString(SEARCH_PARAM);

        if (data.equals("homeMovies")) {
            Ion.with(getActivity())
                    .load("https://api.themoviedb.org/3/search/movie?api_key="+ API_KEY +"&language=en-US&query=" + param)
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String data) {
                            processData(data);
                            progressBar.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        }
                    });
        } else if (data.equals("tvMovies")) {
            Ion.with(getActivity())
                    .load("https://api.themoviedb.org/3/search/tv?api_key="+ API_KEY +"&language=en-US&query=" + param)
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String data) {
                            processData(data);
                            progressBar.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
