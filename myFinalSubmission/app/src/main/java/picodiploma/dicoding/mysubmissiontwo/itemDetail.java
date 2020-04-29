package picodiploma.dicoding.mysubmissiontwo;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import picodiploma.dicoding.mysubmissiontwo.db.movieHelper;

import static picodiploma.dicoding.mysubmissiontwo.db.databaseContract.movieCollumn.DESCRIPTION;
import static picodiploma.dicoding.mysubmissiontwo.db.databaseContract.movieCollumn.NAME;
import static picodiploma.dicoding.mysubmissiontwo.db.databaseContract.movieCollumn.PHOTOS;
import static picodiploma.dicoding.mysubmissiontwo.db.databaseContract.movieCollumn.RATING;
import static picodiploma.dicoding.mysubmissiontwo.db.databaseContract.movieCollumn.RELEASE_DATE;
import static picodiploma.dicoding.mysubmissiontwo.db.databaseContract.movieCollumn.TYPE_MOVIES;

/**
 * A simple {@link Fragment} subclass.
 */
public class itemDetail extends Fragment {

    public static final String DATA_POSTER = "movie";
    String[] dataMovie;
    TextView txtNamaFilm, txtReleaseDate, txtRatings, txtDeskripsi;
    ImageView imgPhoto;
    Button btnSaveFavorite;

    private movieHelper MovieHelper;

    public itemDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MovieHelper = movieHelper.getInstance(view.getContext());
        dataMovie = new String[6];

        txtNamaFilm = view.findViewById(R.id.txt_Judul);
        txtRatings = view.findViewById(R.id.txt_Ratings);
        txtReleaseDate = view.findViewById(R.id.txt_ReleaseDate);
        txtDeskripsi = view.findViewById(R.id.txt_Deskripsi);
        imgPhoto = view.findViewById(R.id.img_Poster);
        btnSaveFavorite = view.findViewById(R.id.btn_save_fav);

        Movie movie = getArguments().getParcelable(DATA_POSTER);
        dataMovie[0] = movie.getName();
        dataMovie[1] = movie.getRatings();
        dataMovie[2] = movie.getReleaseDate();
        dataMovie[3] = movie.getDescription();
        dataMovie[4] = movie.getPhoto();
        dataMovie[5] = movie.getTipeMovie();

        int check = MovieHelper.queryName(dataMovie[0], dataMovie[5]);
        if (check > 0) {
            btnSaveFavorite.setEnabled(false);
        }

        btnSaveFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues data = new ContentValues();
                data.put(NAME, dataMovie[0]);
                data.put(DESCRIPTION, dataMovie[3]);
                data.put(RELEASE_DATE, dataMovie[2]);
                data.put(RATING, dataMovie[1]);
                data.put(PHOTOS, dataMovie[4]);
                data.put(TYPE_MOVIES, dataMovie[5]);

                long result = MovieHelper.insertMovie(data);
                if (result > 0) {
                    Toast.makeText(view.getContext(), "Menambah ke DB " + result, Toast.LENGTH_SHORT).show();
                    btnSaveFavorite.setEnabled(false);

                    // Update Widget
                    int[] ids = AppWidgetManager.getInstance(getContext()).getAppWidgetIds(new ComponentName(getActivity(), FavoriteWidget.class));
                    FavoriteWidget widget = new FavoriteWidget();
                    widget.onUpdate(getContext(), AppWidgetManager.getInstance(getContext()), ids);
                    AppWidgetManager.getInstance(getContext()).notifyAppWidgetViewDataChanged(ids, R.id.stack_view);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Movie movie = getArguments().getParcelable(DATA_POSTER);
        String rating = getString(R.string.rating);
        String rilis = getString(R.string.rilis);

        txtNamaFilm.setText(movie.getName());
        txtReleaseDate.setText(rilis.concat(" ").concat(movie.getReleaseDate()));
        txtRatings.setText(rating.concat(" ").concat(movie.getRatings()));
        txtDeskripsi.setText(movie.getDescription());
        Picasso.get().load(movie.getPhoto()).into(imgPhoto);
    }
}
