package jdrzaic.math.hr.dbapplication;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NotificationActivity extends Activity {

    private int notificationID = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //---look up the notification manager service---
        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        //---cancel the notification that we started---
        nm.cancel(getIntent().getExtras().getInt("notificationID"));
    }
}
