package com.muqing.android.screencapture2gif.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.os.Process;
import android.util.Log;

import com.muqing.android.screencapture2gif.notification.ScreenRecordNotification;
import com.muqing.android.screencapture2gif.util.MyConstants;

public class ScreenCaptureService extends Service {

    private final static String TAG  = MyConstants.TAG_PREFIX + "ScreenCaptureService";
    private Context mContext;
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    private static final int MSG_START_CAPTURE = 1;

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
        HandlerThread thread = new HandlerThread("ScreenCaptureService handler",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "onStart");
        Message msg = mServiceHandler.obtainMessage();
        msg.what = MSG_START_CAPTURE;
        mServiceHandler.sendMessage(msg);
        return START_NOT_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
