package com.yjx.cnblog.fragment;/**
 * Created by yjx on 15/5/4.
 */

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.yjx.cnblog.R;

/**
 * User: YJX
 * Date: 2015-05-04
 * Time: 20:27
 */
public class SetttingFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settting);
    }
}
