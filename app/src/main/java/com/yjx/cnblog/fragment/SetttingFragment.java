package com.yjx.cnblog.fragment;/**
 * Created by yjx on 15/5/4.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.yjx.cnblog.R;

/**
 * User: YJX
 * Date: 2015-05-04
 * Time: 20:27
 */
public class SetttingFragment extends PreferenceFragment {
    Preference wifi, g, night;
    SharedPreferences prefs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settting);
        wifi = getPreferenceManager().findPreference("wifidown");
        g = getPreferenceManager().findPreference("3g");
        night = getPreferenceManager().findPreference("night");
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity()) ;
        wifi.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                 prefs.edit().putBoolean("wifidown", (boolean) newValue).commit();
                return true;
            }
        });
        g.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                prefs.edit().putBoolean("3g",(boolean)newValue).commit();
                return true;
            }
        });
        night.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                return true;
            }
        });

    }


    public static boolean isWifiDown(Context context){
        SharedPreferences  prefs = PreferenceManager.getDefaultSharedPreferences(context) ;
        return  prefs.getBoolean("wifidown",true);
    }

    public static boolean isShowImg(Context context){
        SharedPreferences  prefs = PreferenceManager.getDefaultSharedPreferences(context) ;
        return  prefs.getBoolean("3g",true);
    }
}
