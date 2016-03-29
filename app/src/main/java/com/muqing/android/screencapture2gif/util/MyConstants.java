package com.muqing.android.screencapture2gif.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by muqing on 2016/3/19.
 * QFQ061@gmail.com
 */
public class MyConstants {

    public static final String TAG_PREFIX = "ScreenCapture2Gif/";

    public static final String SCREEN_CAPTURE_2_GIF_SHARED_REFERENCE = "default";
    public static final String PREF_DEFAULT_VIDEO_NAME = "video_name";
    public static final String DEFAULT_VIDEO_NAME = "screen_capture.mp4";
    public static final String PREF_DEFAULT_GIF_NAME = "gif_name";
    public static final String DEFAULT_GIF_NAME = "demo.gif";
    


    public static final String STORAGE_DIRECTORY = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + "Downloads"
            + File.separator;


}
