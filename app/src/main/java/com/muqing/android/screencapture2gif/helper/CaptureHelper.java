package com.muqing.android.screencapture2gif.helper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.media.projection.MediaProjectionManager;
import android.provider.MediaStore;
import android.util.Log;

import com.muqing.android.screencapture2gif.service.ScreenCaptureService;
import com.muqing.android.screencapture2gif.util.MyConstants;

/**
 * Created by muqing on 2016/3/24.
 * QFQ061@gmail.com
 */
public class CaptureHelper {
    private static final String TAG = MyConstants.TAG_PREFIX + "CaptureHelper";
    private static final int CREATE_SCREEN_CAPTUE = 9161; /* just a arbitrary number */

    public CaptureHelper() {
        Log.v(TAG, "CaptureHelper, creator");
    }

    public static void fireCaptureIntent(Activity activity) {
        Log.v(TAG, "fireCaptureIntent");
        MediaProjectionManager manager =
                (MediaProjectionManager)activity.getSystemService(activity.MEDIA_PROJECTION_SERVICE);
        Intent intent = manager.createScreenCaptureIntent();
        activity.startActivityForResult(intent, CREATE_SCREEN_CAPTUE);
    }

    public static boolean handleActivityResult(Activity activity,
                                     int requestCode,
                                     int resultCode,
                                     Intent data) {
        if (requestCode != CREATE_SCREEN_CAPTUE) {
            return false;
        }
        if (resultCode == activity.RESULT_OK) {
            activity.startService(ScreenCaptureService.newIntent(activity, resultCode, data));
        }
        return true;
    }
}
