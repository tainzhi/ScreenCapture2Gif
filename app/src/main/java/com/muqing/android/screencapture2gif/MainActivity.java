package com.muqing.android.screencapture2gif;

import android.content.Context;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.muqing.android.screencapture2gif.util.MyConstants;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MyConstants.TAG_PREFIX + "MainActivity";
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Settings.System.putInt(mContext.getContentResolver(), "show_touches", 1);
    }

    @Override
    protected void onDestroy() {
        Settings.System.putInt(mContext.getContentResolver(), "show_touches", 0);
        super.onDestroy();
    }
}
