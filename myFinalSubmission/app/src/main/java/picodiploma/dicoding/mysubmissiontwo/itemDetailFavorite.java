package picodiploma.dicoding.mysubmissiontwo;


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

import com.squareup.picasso.Picasso;

import picodiploma.dicoding.mysubmissiontwo.db.movieHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class itemDetailFavorite extends Fragment {

    public static final String DATA_POSTER = "movie";
    private movieHelper MovieHelper;
    String[] dataMovie;
    TextView txtNamaFilm, txtReleaseDate, txtRatings, txtDeskripsi;
    ImageView imgPhoto;
    Button btnDelFav;

    public itemDetailFavorite() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_detail_favorite, container, false);
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
        btnDelFav = view.findViewById(R.id.btn_delete_fav);

        Movie movie = getArguments().getParcelable(DATA_POSTER);
        dataMovie[0] = movie.getName();
        dataMovie[1] = movie.getRatings();
        dataMovie[2] = movie.getReleaseDate();
        dataMovie[3] = movie.getDescription();
        dataMovie[4] = movie.getPhoto();
        dataMovie[5] = movie.getTipeMovie();

        btnDelFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieHelper.deleteByName(dataMovie[0], dataMovie[5]);
                btnDelFav.setEnabled(false);
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
