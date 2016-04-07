package com.muqing.android.screencapture2gif.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.muqing.android.screencapture2gif.R;
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
    private static final int sNotifyId = 9999;
    private static final String ACTION_STOP_SERVICE = "STOP_SERVICE";
    private RemoteViews mRemoteViews;

    public ScreenRecordNotification(Context context) {
        mContext = context;
    }

    public void startNotificate() {
        Log.d(TAG, "startNotification");

        mRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.notification);
        mRemoteViews.setImageViewResource(R.id.notication_image, R.drawable.ic_launcher);
        mRemoteViews.setTextViewText(R.id.notifcation_title,
                mContext.getString(R.string.notification_title));
        mRemoteViews.setTextViewText(R.id.notification_record_time_prefix,
                mContext.getString(R.string.notification_record_time_prefix));
        mRemoteViews.setTextViewText(R.id.notification_recorded_time, "00:00");

        mNotificationManager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(mContext)
                .setOngoing(true)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.notification);
        Intent intent = new Intent(mContext, ScreenCaptureService.class);
        intent.setAction(ACTION_STOP_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, intent, 0);
        mBuilder.setContentIntent(pendingIntent);
        Notification notification = mBuilder.build();
        notification.contentView = mRemoteViews;
        mNotificationManager.notify(sNotifyId, notification);
    }

    public void updateNotification(String time) {
        mRemoteViews.setTextViewText(R.id.notification_recorded_time, time);
        mBuilder.setContent(mRemoteViews);
        mNotificationManager.notify(sNotifyId, mBuilder.build());
    }

}
