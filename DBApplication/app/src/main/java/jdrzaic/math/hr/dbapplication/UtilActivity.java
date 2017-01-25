package jdrzaic.math.hr.dbapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class UtilActivity extends AppCompatActivity {

    private int notificationID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util);
    }


    public void onClick(View view) {
        displayNotification();
    }


    protected void displayNotification()
    {
        //---PendingIntent to launch activity if the user selects
        // this notification---
        Intent i = new Intent(this, MainActivity.class);

        i.putExtra("notificationID", notificationID);


        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, i, 0);

        long[] vibrate = new long[] { 100, 250, 100, 500};

        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);


        Notification notif = new Notification.Builder(this)
                .setTicker("Reminder: meeting starts in 5 minutes")
                .setContentTitle("Meeting with customer at 3pm...")
                .setContentText("this is the second row")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .setContentIntent(pendingIntent)
                .setVibrate(vibrate)
                .build();


        nm.notify(notificationID, notif);
    }

}
