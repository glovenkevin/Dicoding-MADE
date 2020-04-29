package picodiploma.dicoding.mysubmissiontwo;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class homeMovies extends Fragment{

    public static final String ALAMAT_GAMBAR = "https://image.tmdb.org/t/p/w500";
    private ArrayList<Movie> listMovie = new ArrayList<>();

    private ProgressBar progressBar;
    homeMoviesAdapter adapter;
    private RecyclerView recyclerView;

    public homeMovies() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_movies, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listMovie.size() != 0) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.listHomeMovies);
        progressBar = view.findViewById(R.id.ProgressBarHomeMovies);

        if (listMovie.size() == 0) {
            progressBar.setVisibility(View.VISIBLE);
            getData();
        }

        adapter = new homeMoviesAdapter(getActivity(), listMovie);

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
                movie.setPhoto(ALAMAT_GAMBAR.concat(object.getString("poster_path")));
                movie.setRatings(object.getString("vote_average"));
                movie.setReleaseDate(object.getString("release_date"));
                movie.setTipeMovie("home_movie");
                listMovie.add(movie);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void getData() {
        Ion.with(getActivity())
                .load("https://api.themoviedb.org/3/movie/343611/recommendations?api_key=5997df57f5905889a5060768b1972c18&language=en-US&page=1")
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
