package com.muqing.android.screencapture2gif.helper.gifhelper;

import java.util.ArrayList;

public class FFmpegCommandBuilder {

    private final static String FFMPEG_TAG = "ffmpeg";
    private final static String COMMAND_SPACE = " ";

    private ArrayList<String> mParameterList;

    public FFmpegCommandBuilder() {
        mParameterList = new ArrayList<String>();
        mParameterList.add(FFMPEG_TAG);
    }

    public void append(String parameter) {
        mParameterList.add(parameter);
    }

    public void append(int parameter) {
        mParameterList.add(String.valueOf(parameter));
    }

    public String getCommand() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (; i < (mParameterList.size() - 1); i++) {
            sb.append(mParameterList.get(i));
            sb.append(COMMAND_SPACE);
        }

        sb.append(mParameterList.get(i));

        return sb.toString();
    }
}
