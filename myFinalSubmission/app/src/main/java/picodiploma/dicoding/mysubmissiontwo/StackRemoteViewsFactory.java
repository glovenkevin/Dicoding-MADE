package picodiploma.dicoding.mysubmissiontwo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import picodiploma.dicoding.mysubmissiontwo.db.movieHelper;
import static picodiploma.dicoding.mysubmissiontwo.db.databaseContract.movieCollumn.PHOTOS;

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private  List<String> linkImage = new ArrayList<>();
    private final Context mContext;
    private movieHelper MovieHelper;

    public StackRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {
        MovieHelper = movieHelper.getInstance(mContext);
    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        Cursor cursor = MovieHelper.queryAllHomeMoviesPoster();
        if (cursor != null) {
            linkImage= cursorPhotos(cursor);
        }
        Binder.restoreCallingIdentity(identityToken);

        if (!linkImage.isEmpty()) {
            for (String data : linkImage){
                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(mContext)
                                    .asBitmap()
                                    .load(data)
                                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                    .get();
                    mWidgetItems.add(bitmap);
                } catch (Exception e) {}
            }
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.favorite_movie_widget_detail);
        rv.setImageViewBitmap(R.id.favorite_image_view_widget, mWidgetItems.get(position));

        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.favorite_image_view_widget, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public ArrayList<String> cursorPhotos(Cursor cursor) {
        ArrayList<String> temp = new ArrayList<>();
        while (cursor.moveToNext()){
            String data = cursor.getString(cursor.getColumnIndexOrThrow(PHOTOS));
            temp.add(data);
        }
        return temp;
    }
}
