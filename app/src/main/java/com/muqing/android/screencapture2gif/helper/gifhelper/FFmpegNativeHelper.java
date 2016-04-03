package com.muqing.android.screencapture2gif.helper.gifhelper;

import android.util.Log;

public class FFmpegNativeHelper {
    public FFmpegNativeHelper() {
    }

    static {
        System.loadLibrary("avutil");
        System.loadLibrary("swresample");
        System.loadLibrary("avcodec");
        System.loadLibrary("avformat");
        System.loadLibrary("swscale");
        System.loadLibrary("avfilter");
        System.loadLibrary("avdevice");
        System.loadLibrary("ffmpegjni");
    }

    // success 0, error 1
    public int ffmpegRunCommand(String command) {
        if(command == null || command.length() == 0) {
            return 1;
        }
        String[] args = command.split(" ");
        for(int i = 0; i < args.length; i++) {
            Log.d("ffmpeg-jni", args[i]);

        }
        return ffmpeg_entry(args.length, args);
    }

    // argc maybe dont be needed
    public native int ffmpeg_entry(int argc, String[] args);
}
