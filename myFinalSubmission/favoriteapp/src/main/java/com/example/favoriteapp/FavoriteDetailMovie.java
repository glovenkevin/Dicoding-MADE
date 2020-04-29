package com.example.favoriteapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FavoriteDetailMovie extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    TextView judulMovie, ratingMovie, releaseDate, deskripsiMovie;
    ImageView imgPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_detail_movie);

        judulMovie = findViewById(R.id.txt_Judul);
        ratingMovie = findViewById(R.id.txt_Ratings);
        releaseDate = findViewById(R.id.txt_ReleaseDate);
        deskripsiMovie = findViewById(R.id.txt_Deskripsi);
        imgPoster = findViewById(R.id.img_Poster);

        Movie param = getIntent().getParcelableExtra(EXTRA_MOVIE);
        judulMovie.setText(param.getName());
        ratingMovie.setText("Ratting: " + param.getRatings());
        releaseDate.setText("Rilis: " + param.getReleaseDate());
        deskripsiMovie.setText(param.getDescription());
        Picasso.get().load(param.getPhoto()).into(imgPoster);
    }
}
