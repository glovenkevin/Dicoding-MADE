package picodiploma.dicoding.mysubmissiontwo.notif;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import picodiploma.dicoding.mysubmissiontwo.MainActivity;
import picodiploma.dicoding.mysubmissiontwo.R;

import static picodiploma.dicoding.mysubmissiontwo.SearchMovie.SearchMovie.API_KEY;
import static picodiploma.dicoding.mysubmissiontwo.homeMovies.ALAMAT_GAMBAR;


public class releaseDaily extends BroadcastReceiver {

    private List<notifItem> stackMovie = new  ArrayList<>();
    private final static int NOTIFICATION_REQUEST_CODE = 200;
    private static final CharSequence CHANNEL_NAME = "Release Today";
    private static final String GROUP_KEY = "Today Release Movie";
    private static final String CHANNEL_ID = "Channel 01";


    @Override
    public void onReceive(Context context, Intent intent) {
        getData(context);
    }

    public void setReleaseTodayAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, releaseDaily.class);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_REQUEST_CODE, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void cancelReleaseTodayAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, dailyReminder.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_REQUEST_CODE, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null)
            alarmManager.cancel(pendingIntent);

        Toast.makeText(context, "Alarm dibatalkan!", Toast.LENGTH_SHORT).show();
    }

    private void setNotifResult(Context context) throws ExecutionException, InterruptedException {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder;

        // Masukan Notif satu satu
        if (!stackMovie.isEmpty()){
            for (notifItem NotifItem : stackMovie){
                builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setContentTitle(NotifItem.getName())
                        .setContentText(NotifItem.getDeskripsi())
                        .setSmallIcon(R.drawable.ic_movie_black_24dp)
                        .setLargeIcon(NotifItem.getPhotoBitmap())
                        .setGroup(GROUP_KEY)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                            CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                    builder.setChannelId(CHANNEL_ID);

                    if (notificationManager != null)
                        notificationManager.createNotificationChannel(channel);
                }

                Notification notification = builder.build();
                if (notificationManager != null)
                    notificationManager.notify(NotifItem.getUuidNotif(), notification);
            }
        }
    }

    private void processData(String data) {
        try {
            JSONObject dataMovie = new JSONObject(data);
            JSONArray arrayData = dataMovie.getJSONArray("results");

            for (int i = 0; i <3; i++) {
                JSONObject object = arrayData.getJSONObject(i);
                notifItem NotifItem = new notifItem();

                NotifItem.setUuidNotif(i);
                NotifItem.setName(object.getString("original_title"));
                NotifItem.setDeskripsi(object.getString("overview"));
                NotifItem.setPhoto(ALAMAT_GAMBAR.concat(object.getString("poster_path")));
                stackMovie.add(NotifItem);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void processBitmap(Context context) {
        if (!stackMovie.isEmpty()){
            for (final notifItem item: stackMovie) {
                Ion.with(context)
                        .load(item.getPhoto())
                        .withBitmap()
                        .asBitmap()
                        .setCallback(new FutureCallback<Bitmap>() {
                            @Override
                            public void onCompleted(Exception e, Bitmap result) {
                                item.setPhotoBitmap(result);
                            }
                        });
            }
        }
    }

    public void getData(final Context context) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());

        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+ API_KEY +"&primary_release_date.gte="+ date
                + "&primary_release_date.lte=" + date;

        Ion.with(context)
                .load(url)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        processData(result);
                        processBitmap(context);
                        try {
                            setNotifResult(context);
                        } catch (ExecutionException ex) {
                            ex.printStackTrace();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }
}
