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


public class homeMoviesFavoriteAdapter extends RecyclerView.Adapter<homeMoviesFavoriteAdapter.homeMoviesFavoriteHolder> {

    private Context context;
    private ArrayList<Movie> listMovieFavorite;

    public homeMoviesFavoriteAdapter (Context context, ArrayList<Movie> listMovieFavorite){
        this.context = context;
        this.listMovieFavorite = listMovieFavorite;
    }

    public void setListMovie(ArrayList<Movie> ListMovie) {
        if (listMovieFavorite.size() > 0) {
            listMovieFavorite.clear();
        }

        listMovieFavorite.addAll(ListMovie);
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getListMovieFavorite () {return this.listMovieFavorite;}

    @NonNull
    @Override
    public homeMoviesFavoriteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new homeMoviesFavoriteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull homeMoviesFavoriteHolder homeMoviesFavoriteHolder, final int i) {
        homeMoviesFavoriteHolder.txtJudul.setText(getListMovieFavorite().get(i).getName());
        Picasso.get().load(getListMovieFavorite().get(i).getPhoto()).into(homeMoviesFavoriteHolder.imgPhoto);
        homeMoviesFavoriteHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment itemDetilFav = new itemDetailFavorite();

                Bundle bundle = new Bundle();
                bundle.putParcelable(itemDetailFavorite.DATA_POSTER, getListMovieFavorite().get(i));
                itemDetilFav.setArguments(bundle);

                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_layout_favorit, itemDetilFav)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.listMovieFavorite.size();
    }

    public class homeMoviesFavoriteHolder extends RecyclerView.ViewHolder{
        TextView txtJudul;
        ImageView imgPhoto;
        ConstraintLayout constraintLayout;

        public homeMoviesFavoriteHolder (@NonNull View itemView) {
            super(itemView);

            txtJudul = itemView.findViewById(R.id.txt_nama_movie);
            imgPhoto = itemView.findViewById(R.id.img_movie);
            constraintLayout = itemView.findViewById(R.id.itemLayout);
        }
    }
}
