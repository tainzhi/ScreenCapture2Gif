package com.muqing.android.screencapture2gif.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.muqing.android.screencapture2gif.activity.SettingsActivity;
import com.muqing.android.screencapture2gif.service.ScreenCaptureService;
import com.muqing.android.screencapture2gif.util.MyConstants;

/**
 * Created by muqing on 2016/3/22.
 * QFQ061@gmail.com
 */
public class ScreenRecordNotification {
    private final static String TAG = MyConstants.TAG_PREFIX + "ScreenRecordNotification";
    private Context mContext;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private static final int sNotifyId = 1;
    private static final String ACTION_STOP_SERVICE = "STOP_SERVICE";

    public ScreenRecordNotification(Context context) {
        mContext = context;
    }

    public void startNotificate() {
        mNotificationManager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(mContext)
                .setContentTitle("apple")
                .setContentText("pear")
                .setOngoing(true)
                .setAutoCancel(true)
                .setSmallIcon(android.support.v7.appcompat.R.drawable.notification_template_icon_bg);
        Intent intent = new Intent(mContext, ScreenCaptureService.class);
        intent.setAction(ACTION_STOP_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, intent, 0);
        mBuilder.setContentIntent(pendingIntent);
        mNotificationManager.notify(sNotifyId, mBuilder.build());
    }

}
