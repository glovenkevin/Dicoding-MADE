package picodiploma.dicoding.mysubmissiontwo.notif;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import picodiploma.dicoding.mysubmissiontwo.R;

public class settingNotif extends AppCompatActivity implements View.OnClickListener{

    Switch dailySwitch, releaseSwitch;
    private SharedPreferences preference;
    public final static String DOKUMEN = "katalog_film";
    public final static String DAILY_REMINDER = "dailyReminder";
    public final static String RELEASE_REMINDER = "releaseReminder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_notif);
        preference = getApplicationContext().getSharedPreferences(DOKUMEN, Context.MODE_PRIVATE);

        dailySwitch = findViewById(R.id.switchDailyReminder);
        releaseSwitch = findViewById(R.id.switchReleaseReminder);

        dailySwitch.setOnClickListener(this);
        releaseSwitch.setOnClickListener(this);

        if(preference.getBoolean(DAILY_REMINDER, false))
            dailySwitch.setChecked(true);
        if(preference.getBoolean(RELEASE_REMINDER, false))
            releaseSwitch.setChecked(true);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = preference.edit();

        switch (v.getId()){
            case R.id.switchDailyReminder:
                dailyReminder dailyReminder = new dailyReminder();
                if (dailySwitch.isChecked()) {
                    dailyReminder.setAlarm(this);
                    editor.putBoolean(DAILY_REMINDER, true);
                    editor.apply();
                } else {
                    dailyReminder.cancelDailyReminder(this);
                    editor.putBoolean(DAILY_REMINDER, false);
                    editor.apply();
                }
                break;
            case R.id.switchReleaseReminder:
                releaseDaily releaseDaily = new releaseDaily();
                if (releaseSwitch.isChecked()) {
                    releaseDaily.setReleaseTodayAlarm(getApplicationContext());
                    editor.putBoolean(RELEASE_REMINDER, true);
                    editor.apply();
                } else {
                    releaseDaily.cancelReleaseTodayAlarm(getApplicationContext());
                    editor.putBoolean(RELEASE_REMINDER, false);
                    editor.apply();
                }
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
