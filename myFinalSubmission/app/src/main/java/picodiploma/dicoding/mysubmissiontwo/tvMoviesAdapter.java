package picodiploma.dicoding.mysubmissiontwo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class tvMoviesAdapter extends RecyclerView.Adapter<tvMoviesAdapter.tvMoviesHolder> {

    private Context context;
    private ArrayList<Movie> movies;

    public tvMoviesAdapter(Context context, ArrayList<Movie> list) {
        this.context = context;
        this.movies = list;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    @NonNull
    @Override
    public tvMoviesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new tvMoviesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull tvMoviesHolder tvMoviesHolder, final int i) {
        tvMoviesHolder.txtJudul.setText(getMovies().get(i).getName());
        Picasso.get().load(getMovies().get(i).getPhoto()).into(tvMoviesHolder.imgPhoto);
        tvMoviesHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment itemDetailMovie = new itemDetail();

                Bundle bundle = new Bundle();
                bundle.putParcelable(itemDetail.DATA_POSTER, getMovies().get(i));
                itemDetailMovie.setArguments(bundle);

                AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_layout, itemDetailMovie)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }

    public class tvMoviesHolder extends RecyclerView.ViewHolder{

        TextView txtJudul;
        ImageView imgPhoto;
        ConstraintLayout constraintLayout;

        public tvMoviesHolder(@NonNull View itemView) {
            super(itemView);

            txtJudul = itemView.findViewById(R.id.txt_nama_movie);
            imgPhoto = itemView.findViewById(R.id.img_movie);
            constraintLayout = itemView.findViewById(R.id.itemLayout);
        }
    }
}
