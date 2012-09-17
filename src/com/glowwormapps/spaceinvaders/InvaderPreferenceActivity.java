/*
 *  Space Invaders
 *
 *  Copyright (C) 2012 Glow Worm Applications
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Street #330, Boston, MA 02111-1307, USA.
 */

package com.glowwormapps.spaceinvaders;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;



public class InvaderPreferenceActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

		for(int i=0;i<getPreferenceScreen().getPreferenceCount();i++){
			initSummary(getPreferenceScreen().getPreference(i));
		}
	}

	@Override 
	protected void onResume(){
		super.onResume();
		// Set up a listener whenever a key changes             
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

	}

	@Override 
	protected void onPause() {
		super.onPause();
		// Unregister the listener whenever a key changes             
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);  
		//startActivity(new Intent(this, DoublePendulumActivity.class));

	} 

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		updatePrefSummary(findPreference(key));
	}

	private void initSummary(Preference p){
		if (p instanceof PreferenceCategory){
			PreferenceCategory pCat = (PreferenceCategory)p;
			for(int i=0;i<pCat.getPreferenceCount();i++){
				initSummary(pCat.getPreference(i));
			}
		}else{
			updatePrefSummary(p);
		}

	}

	private void updatePrefSummary(Preference p){
		if (p instanceof ListPreference) {
			ListPreference listPref = (ListPreference) p; 
			p.setSummary(listPref.getEntry()); 
		}
		if (p instanceof EditTextPreference) {

			EditTextPreference editTextPref = (EditTextPreference) p; 
			p.setSummary(editTextPref.getText()); 
		}
	}
} 
