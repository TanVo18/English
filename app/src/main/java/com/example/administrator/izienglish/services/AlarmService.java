package com.example.administrator.izienglish.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.SqlHelper;
import com.example.administrator.izienglish.model2.Verbs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class AlarmService extends Service {
    private Handler mHandler;
    private long mSecond;
    private long mTime;
    private SqlHelper mDb;
    private List<Verbs> mIrreVerbs;
    private Verbs mRandomVerb;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDb = new SqlHelper(getBaseContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSecond = 0;
        mIrreVerbs = new ArrayList<>();
        mIrreVerbs = mDb.getData();
        countSecond();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    public void randomVerb(){
        Random ran = new Random();
        int index = ran.nextInt(mIrreVerbs.size());
        mRandomVerb = mIrreVerbs.get(index);
    }

    public void calculateTime(){
        Calendar c = Calendar.getInstance();
        long time = c.getTimeInMillis();
        c.add(Calendar.HOUR,24);
        mTime = c.getTimeInMillis() - time - Calendar.getInstance().get(Calendar.SECOND);
    }

    public void countSecond(){
        randomVerb();
        calculateTime();
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mHandler.removeCallbacksAndMessages(null);
                mSecond += 1000;
                Log.i("second", mSecond + "");
                if (mSecond > mTime) {
                    mSecond = 0;
                    showForegroundNotification(mRandomVerb);
                    showNotification();
                    randomVerb();
                    calculateTime();
                }
                mHandler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private void showNotification() {
        //save data when show
        // Instantiate a Builder object.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("You Have A Word To Learn")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true)//co the cancel
                .setOngoing(true);//khong the keo qua de tat
        builder.setVibrate(new long[]{1000, 1000});

        // Creates an Intent for the Activity
        Intent notifyIntent =
                new Intent(this, AlarmService.class);

        // Creates the PendingIntent
        //co the getBroadcast,getService
        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        // Puts the PendingIntent into the notification builder
        builder.setContentIntent(notifyPendingIntent);
        // Notifications are issued by sending them to the
        // NotificationManager system service.
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Builds an anonymous Notification object from the builder, and
        // passes it to the NotificationManager
        mNotificationManager.notify(0, builder.build());
    }

    private void showForegroundNotification(Verbs verb) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return;
        }
        // Create intent that will bring our app to the front, as if it was tapped in the app
        // launcher
        Intent showTaskIntent = new Intent(getApplicationContext(), AlarmService.class);

        PendingIntent contentIntent = PendingIntent.getActivity(
                this,
                1000,
                showTaskIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle(getString(R.string.app_name))
                .setContentText(verb.getV1()+"---"+verb.getV2()+"---"+verb.getV3())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(false)
                .setOngoing(true)
                .setContentIntent(contentIntent)
                .build();
        startForeground(1, notification);
        //  stopForeground(true);
    }
}
