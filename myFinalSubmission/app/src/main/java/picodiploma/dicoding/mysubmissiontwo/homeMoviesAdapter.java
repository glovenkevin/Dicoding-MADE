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

public class homeMoviesAdapter extends RecyclerView.Adapter<homeMoviesAdapter.homeMoviesHolder> {

    private Context context;
    private ArrayList<Movie> listMovie;

    public homeMoviesAdapter(Context context, ArrayList<Movie> list) {
        this.context = context;
        this.listMovie = list;
    }

    public ArrayList<Movie> getListMovie() {
        return listMovie;
    }

    @NonNull
    @Override
    public homeMoviesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new homeMoviesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull homeMoviesHolder MoviesHolder, final int i) {
        MoviesHolder.txtJudul.setText(getListMovie().get(i).getName());
        Picasso.get().load(getListMovie().get(i).getPhoto()).into(MoviesHolder.imgPhoto);
        MoviesHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment itemDetailMovie = new itemDetail();

                Bundle bundle = new Bundle();
                bundle.putParcelable(itemDetail.DATA_POSTER, getListMovie().get(i));
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
        return this.listMovie.size();
    }

    public class homeMoviesHolder extends RecyclerView.ViewHolder {

        TextView txtJudul;
        ImageView imgPhoto;
        ConstraintLayout constraintLayout;

        public homeMoviesHolder(@NonNull View itemView) {
            super(itemView);

            txtJudul = itemView.findViewById(R.id.txt_nama_movie);
            imgPhoto = itemView.findViewById(R.id.img_movie);
            constraintLayout = itemView.findViewById(R.id.itemLayout);
        }
    }
}
