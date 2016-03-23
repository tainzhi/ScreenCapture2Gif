package com.muqing.android.screencapture2gif.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.media.projection.MediaProjectionManager;
import android.os.*;
import android.os.Process;
import android.util.DisplayMetrics;
import android.util.Log;

import com.muqing.android.screencapture2gif.notification.ScreenRecordNotification;
import com.muqing.android.screencapture2gif.util.MyConstants;

public class ScreenCaptureService extends Service {

    private final static String TAG  = MyConstants.TAG_PREFIX + "ScreenCaptureService";
    private Context mContext;
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    private int mScreenDensity;
    private MediaRecorder mMediaRecorder;
    private MediaProjectionManager mMediaProjectionManager;

    private static final int MSG_START_CAPTURE = 1;
    private static final int MSG_STOP_SERVICE = 2;


    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START_CAPTURE:
                    Log.v(TAG, "handleMessage");
                    ScreenRecordNotification screenRecordNotification = new ScreenRecordNotification(mContext);
                    screenRecordNotification.startNotificate();
                    break;
                case MSG_STOP_SERVICE:
                    Log.v(TAG, "stop service");
                    stopSelf();
                default:
                    break;
            }
        }
    }
    public ScreenCaptureService() {
    }

    @Override
    public void onCreate() {
        Log.v(TAG, "onCreate");
        mContext = getApplicationContext();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowmanager().getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;

        HandlerThread thread = new HandlerThread("ScreenCaptureService handler",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "onStart");

        String action = intent.getAction();
        if (action != null && action.equals("STOP_SERVICE")) {
            Message msg = mServiceHandler.obtainMessage();
            msg.what = MSG_STOP_SERVICE;
            mServiceHandler.sendMessage(msg);
        } else {
            Message msg = mServiceHandler.obtainMessage();
            msg.what = MSG_START_CAPTURE;
            mServiceHandler.sendMessage(msg);
        }
        return START_NOT_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        Log.v(TAG, "onDestroy");
        super.onDestroy();
    }
}
