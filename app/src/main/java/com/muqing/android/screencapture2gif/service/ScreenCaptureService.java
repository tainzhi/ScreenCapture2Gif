package com.muqing.android.screencapture2gif.service;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.*;
import android.os.Process;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.muqing.android.screencapture2gif.MainActivity;
import com.muqing.android.screencapture2gif.helper.gifhelper.FFmpegCommandBuilder;
import com.muqing.android.screencapture2gif.helper.gifhelper.FFmpegNativeHelper;
import com.muqing.android.screencapture2gif.helper.gifhelper.GifMerger;
import com.muqing.android.screencapture2gif.notification.ScreenRecordNotification;
import com.muqing.android.screencapture2gif.util.MyConstants;
import com.muqing.android.screencapture2gif.R;

import java.io.File;
import java.io.IOException;

public class ScreenCaptureService extends Service {

    private final static String TAG  = MyConstants.TAG_PREFIX + "ScreenCaptureService";
    private Context mContext;
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    private int mScreenDensity;
    private MediaRecorder mMediaRecorder;
    private MediaProjectionManager mMediaProjectionManager;
    private MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;

    private static final int MSG_START_CAPTURE = 1;
    private static final int MSG_STOP_SERVICE = 2;

    private static String EXTRA_RESULT_CODE = "result-code";
    private static String EXTRA_DATA = "data";

    private SharedPreferences mSharedPreferences;
    private int mVideoWidth;
    private int mVideoHeight;

    private ScreenRecordNotification mScreenRecordNotification;
    private long mRecordBeginTime;
    private long mRecordCurrentTime;
    private boolean mIsScreenRecording = false;

    public static Intent newIntent(Context context, int resultCode, Intent data) {
        Intent intent = new Intent(context, ScreenCaptureService.class);
        intent.putExtra(EXTRA_RESULT_CODE, resultCode);
        intent.putExtra(EXTRA_DATA, data);
        return intent;
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START_CAPTURE:
                    Log.v(TAG, "handleMessage");
                    mScreenRecordNotification = new ScreenRecordNotification(mContext);
                    mScreenRecordNotification.startNotificate();
                    initScreenRecorder();
                    break;
                case MSG_STOP_SERVICE:
                    Log.v(TAG, "stop service");
                    stopCapture();
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

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)mContext.getSystemService(mContext.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;
        mMediaRecorder = new MediaRecorder();
        mMediaProjectionManager = (MediaProjectionManager)getSystemService(mContext.MEDIA_PROJECTION_SERVICE);

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

            int resultCode = intent.getExtras().getInt(EXTRA_RESULT_CODE);
            Intent data = intent.getParcelableExtra(EXTRA_DATA);
            mMediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
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

        Settings.System.putInt(mContext.getContentResolver(), "show_touches", 0);

        super.onDestroy();
    }


    private void setShowFeedback() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        boolean showFeedback = mSharedPreferences.getBoolean(getString(R.string.pref_key_show_touch_feedback),
                true);
        if (showFeedback) {
            Settings.System.putInt(mContext.getContentResolver(), "show_touches", 1);
        } else {
            Settings.System.putInt(mContext.getContentResolver(), "show_touches", 0);
        }
    }

    private void initScreenRecorder() {

        String videoPath = getVideoPath();
        int videoFrameRate = getVideoFrameRate();
        getWindowWidthHeight();
        int videoWidth = mVideoWidth;
        int videoHeight = mVideoHeight;

        try {
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mMediaRecorder.setOutputFile(videoPath);
            mMediaRecorder.setVideoFrameRate(videoFrameRate);
            mMediaRecorder.setVideoSize(mVideoWidth, mVideoHeight);
//            record audio
//            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//            mMediaRecorder.setAudioEncodingBitRate(5121000);
            // TODO: 2016/3/24 mMediaRecorder.setOritation()
            mMediaRecorder.prepare();
            mVirtualDisplay = mMediaProjection.createVirtualDisplay("Capture Screen",
                    mVideoWidth,
                    mVideoHeight,
                    mScreenDensity,
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                    mMediaRecorder.getSurface(), null, null);

            mMediaRecorder.start();

            mRecordBeginTime = ((System.currentTimeMillis()) / 1000);
            mIsScreenRecording = true;
            UpdateNotificationThread updateNotificationThread = new UpdateNotificationThread("UpdateNotificationThread");
            updateNotificationThread.start();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "" + e);
            // FIXME: 2016/3/24
        }
    }

    private void stopCapture() {
        mIsScreenRecording = false;
        mMediaRecorder.stop();
        mMediaRecorder.reset();
        Log.v(TAG, "stop recording");
        if (mVirtualDisplay == null) {
            return;
        }
        mVirtualDisplay.release();
        if (mMediaProjection != null) {
            mMediaProjection.stop();
            mMediaProjection = null;
        }

        videoToGif();
        //mContext.startActivity(new Intent(ScreenCaptureService.this, MainActivity.class));
    }

    private void videoToGif() {

        GifMerger.generateGifProduct("/storage/sdcard0/Download/video.gif"
                , "/storage/sdcard0/Download/video.mp4", 0, 20);
    }

    private static FFmpegNativeHelper mFFmpegNativeHelper = new FFmpegNativeHelper();

    private void getWindowWidthHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager)getSystemService(mContext.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        mVideoWidth = metrics.widthPixels;
        mVideoHeight = metrics.heightPixels;
    }

    private String getVideoPath() {
        String parentDirectory = mSharedPreferences.getString(getString(R.string.pref_key_save_directory),
                getString(R.string.pref_default_value_save_directory));
        String videoName = mSharedPreferences.getString(getString(R.string.pref_key_video_name),
                getString(R.string.pref_default_value_video_name));
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator
                + parentDirectory);
        if (!file.exists()) {
            file.mkdir();
        }
        return file.getAbsolutePath() + File.separator + videoName;
    }

    private int getVideoFrameRate() {
        String videoFrameRateStr = mSharedPreferences.getString(getString(R.string.pref_key_video_frame_rate),
                getString(R.string.pref_default_value_video_frame_rate));
        int videoFrameRate = Integer.parseInt(videoFrameRateStr);
        return videoFrameRate;
    }

    private int[] getVideoSize() {
        String videoSize = mSharedPreferences.getString(getString(R.string.pref_key_gif_size),
                getString(R.string.pref_default_value_gif_size));
        if (videoSize.equals("Original")) {
            return null;
        } else {
            int xindex = videoSize.charAt('x');
            int width = Integer.parseInt(videoSize.substring(0, xindex));
            int height = Integer.parseInt(videoSize.substring(xindex + 1, videoSize.length()));
            return new int[]{width, height};
        }
    }

    private class UpdateNotificationThread extends Thread {
        volatile boolean isRunning = true;
        protected UpdateNotificationThread(String threadName) {
            this.setName(threadName);
            isRunning = true;
        }

        @Override
        public void run() {
            super.run();
            while (mIsScreenRecording && isRunning) {
                try {
                    this.sleep(1000);
                } catch (InterruptedException e) {
                    // FIXME: 2016/4/7
                }
                mRecordCurrentTime = (System.currentTimeMillis()) / 1000;
                long recordTime = mRecordCurrentTime - mRecordBeginTime;
                mScreenRecordNotification.updateNotification("" + recordTime);
            }
        }
    }
}
