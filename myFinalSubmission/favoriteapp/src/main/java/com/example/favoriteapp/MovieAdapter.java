package com.example.favoriteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Movie> listMovie = new ArrayList<>();

    public MovieAdapter(Context context){
        this.context = context;
    }

    public MovieAdapter(ArrayList<Movie> listMovie){
        this.listMovie = listMovie;
    }

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
    }

    @Override
    public int getCount() {
        return listMovie.size();
    }

    @Override
    public Object getItem(int position) {
        return listMovie.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View itemView = view;
        if (itemView == null)
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_movie, parent, false);

        viewHolder ViewHolder = new viewHolder(itemView);
        Movie movie = (Movie) getItem(position);
        ViewHolder.bind(movie);
        return itemView;
    }

    private class viewHolder {
        private TextView txtName, txtTypeMovies;
        private ImageView imagePhoto;

        viewHolder(View view) {
            txtName = view.findViewById(R.id.txt_nama_movie);
            txtTypeMovies = view.findViewById(R.id.txt_tipe_movies);
            imagePhoto = view.findViewById(R.id.img_movie);
        }

        void bind(Movie movie) {
            txtName.setText(movie.getName());
            txtTypeMovies.setText((movie.getTipeMovie().equals("home_movie")? "Home Movie" : "Tv Movies"));
            Picasso.get().load(movie.getPhoto())
                    .into(imagePhoto);
        }
    }
}
