package com.muqing.android.screencapture2gif;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.muqing.android.screencapture2gif.activity.SettingsActivity;
import com.muqing.android.screencapture2gif.helper.CaptureHelper;
import com.muqing.android.screencapture2gif.service.ScreenCaptureService;
import com.muqing.android.screencapture2gif.util.MyConstants;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MyConstants.TAG_PREFIX + "MainActivity";
    private Context mContext;

    private Button startBtn;

    protected void onCreate(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBtn = (Button) findViewById(R.id.button);
        Settings.System.putInt(mContext.getContentResolver(), "show_touches", 1);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "onClick");
//                mContext.startService(new Intent(MainActivity.this,ScreenCaptureService.class));
                CaptureHelper.fireCaptureIntent(MainActivity.this);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (!CaptureHelper.handleActivityResult(this, requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }

        // back to home screen
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        Settings.System.putInt(mContext.getContentResolver(), "show_touches", 0);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_more:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            default:
                return true;
        }
    }
}

