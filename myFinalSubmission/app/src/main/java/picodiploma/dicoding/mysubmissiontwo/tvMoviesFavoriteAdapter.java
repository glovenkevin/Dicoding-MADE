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

public class tvMoviesFavoriteAdapter extends RecyclerView.Adapter<tvMoviesFavoriteAdapter.tvMoviesFavoriteHolder> {

    private Context context;
    private ArrayList<Movie> listTvMovieFavorite;

    public tvMoviesFavoriteAdapter(Context context, ArrayList<Movie> arrayList) {
        this.context = context;
        this.listTvMovieFavorite = arrayList;
    }

    public void setListMovie(ArrayList<Movie> ListMovie) {
        if (listTvMovieFavorite.size() > 0) {
            listTvMovieFavorite.clear();
        }

        listTvMovieFavorite.addAll(ListMovie);
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getListTvMovieFavorite() {return this.listTvMovieFavorite;}

    @NonNull
    @Override
    public tvMoviesFavoriteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new tvMoviesFavoriteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull tvMoviesFavoriteHolder tvMoviesFavoriteHolder, final int i) {
        tvMoviesFavoriteHolder.txtJudul.setText(getListTvMovieFavorite().get(i).getName());
        Picasso.get().load(getListTvMovieFavorite().get(i).getPhoto()).into(tvMoviesFavoriteHolder.imgPhoto);
        tvMoviesFavoriteHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment itemDetilFav = new itemDetailFavorite();

                Bundle bundle = new Bundle();
                bundle.putParcelable(itemDetailFavorite.DATA_POSTER, getListTvMovieFavorite().get(i));
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
        return listTvMovieFavorite.size();
    }

    public class tvMoviesFavoriteHolder extends RecyclerView.ViewHolder{
        TextView txtJudul;
        ImageView imgPhoto;
        ConstraintLayout constraintLayout;

        public tvMoviesFavoriteHolder (@NonNull View itemView) {
            super(itemView);

            txtJudul = itemView.findViewById(R.id.txt_nama_movie);
            imgPhoto = itemView.findViewById(R.id.img_movie);
            constraintLayout = itemView.findViewById(R.id.itemLayout);
        }
    }
}
