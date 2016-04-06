package com.muqing.android.screencapture2gif.activity;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muqing.android.screencapture2gif.R;
import com.muqing.android.screencapture2gif.util.MyConstants;

import java.util.List;

public class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = MyConstants.TAG_PREFIX + "SettingsActivity/"
            + "MyPreferenceFragment";

    private Context mContext;

    public MyPreferenceFragment(Context context) {
        super();
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_key_show_touch_feedback))) {
            boolean showTouchFeedback = sharedPreferences.getBoolean(key, true);
            if (showTouchFeedback) {
                Settings.System.putInt(mContext.getContentResolver(), "show_touches", 1);
                Log.d(TAG, "show touch feed back state change, show");
            } else {
                Log.d(TAG, "show touch feed back state change, show hide");
                Settings.System.putInt(mContext.getContentResolver(), "show_touches", 0);
            }
        } else if (key.equals(getString(R.string.pref_key_gif_name))) {
            String newGifName = sharedPreferences.getString(key,
                    getString(R.string.pref_default_value_gif_name));
            EditTextPreference editTextPreference = (EditTextPreference)findPreference(key);
            editTextPreference.setSummary(newGifName);
            Log.d(TAG, "change gif name, new is " + newGifName);
        } else if (key.equals(getString(R.string.pref_key_save_directory))) {
            String newDirectory = sharedPreferences.getString(key,
                    getString(R.string.pref_default_value_save_directory));
            EditTextPreference editTextPreference = (EditTextPreference)findPreference(key);
            editTextPreference.setSummary(newDirectory);
            Log.d(TAG, "change save directory");
        } else if (key.equals(getString(R.string.pref_key_gif_size))) {
            ListPreference listPreference = (ListPreference)findPreference(key);
            listPreference.setSummary(listPreference.getValue());
            Log.d(TAG, "change gif size");
        } else if (key.equals(getString(R.string.pref_key_video_name))) {
            String newVideoName = sharedPreferences.getString(key,
                    getString(R.string.pref_default_value_video_name));
            EditTextPreference editTextPreference = (EditTextPreference)findPreference(key);
            editTextPreference.setSummary(newVideoName);
            Log.d(TAG, "change video name");
        } else if (key.equals(getString(R.string.pref_default_value_video_frame_rate))) {
            ListPreference listPreference = (ListPreference)findPreference(key);
            listPreference.setSummary(listPreference.getEntry());
            Log.d(TAG, "change video frame rate");
        } else if (key.equals(getString(R.string.pref_key_gif_frame_rate))) {
            ListPreference listPreference = (ListPreference)findPreference(key);
            listPreference.setSummary(listPreference.getEntry());
            Log.d(TAG, "change gif frame rate");
        }
    }
}
