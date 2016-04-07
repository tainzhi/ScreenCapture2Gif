package com.muqing.android.screencapture2gif.helper.gifhelper;

import android.text.TextUtils;
import android.util.Log;

import com.muqing.android.screencapture2gif.util.MyConstants;

public class FFmpegNativeHelper {

    final static String TAG = MyConstants.TAG_PREFIX + "FFmpegNativeHelper";

    private String mInputFile;
    private String mOutputFile;
    private String mGifFrameRate;
    private String mGifSize;

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

    public FFmpegNativeHelper setIntputFile(String file) {
        mInputFile = file;
        return this;
    }

    public FFmpegNativeHelper setOutputFile(String file) {
        mOutputFile = file;
        return this;
    }

    public FFmpegNativeHelper setGifFrameRate(String rate) {
        mGifFrameRate = rate;
        return this;
    }

    public FFmpegNativeHelper setGifSize(String size) {
        mGifSize = size;
        return this;
    }

    public void build() {
        String command;
//        if (null != mInputFile && !TextUtils.isEmpty(mInputFile)) {
//            mInputFile = " " + mInputFile;
//        } else {
//            mInputFile = "";
//        }

        if (null != mGifFrameRate && !TextUtils.isEmpty(mGifFrameRate)) {
            mGifFrameRate = " -r " + mGifFrameRate;
        } else {
            mGifFrameRate = "";
        }
        if (null != mGifSize && !TextUtils.isEmpty(mGifSize)) {
            mGifSize = " -s " + mGifSize;
        } else {
            mGifSize = "";
        }

        command = ("ffmpeg -i" + " " + mInputFile
                + " -gifflags"
                + " -transdiff"
                + mGifFrameRate + " "
                + mGifSize
                + " -y"
                + " "
                + mOutputFile);
        ffmpegRunCommand(command);
    }

    // success 0, error 1
    public int ffmpegRunCommand(String command) {
        if(command == null || command.length() == 0) {
            return 1;
        }
        Log.d(TAG, "ffmpeg command: " + command);
        String[] args = command.split(" ");
        return ffmpeg_entry(args.length, args);
    }

    // argc maybe dont be needed
    public native int ffmpeg_entry(int argc, String[] args);
}
