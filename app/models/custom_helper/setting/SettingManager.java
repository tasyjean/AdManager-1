package models.custom_helper.setting;

import java.util.List;

import play.Logger;

import models.data.SystemPreferences;

public class SettingManager {

	SystemPreferences pref;
	SettingDefault settingDefault;
	
	public String getString(KeyEnum key){
		try {
			pref = SystemPreferences.find.
					where().
					eq("key",key.name()).findUnique();
			return pref.getValue();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public int getInt(KeyEnum key){
		try {
			pref = SystemPreferences.find.
					where().
					eq("key",key.name()).findUnique();
			return Integer.parseInt(pref.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}		
	public float getFloat(KeyEnum key){
		try {
			pref = SystemPreferences.find.
					where().
					eq("key",key.name()).findUnique();
			return Float.parseFloat(pref.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}	
	public SystemPreferences getPref(KeyEnum key){
		try {
			return SystemPreferences.find.
					where().
					eq("key",key.name()).findUnique();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public boolean editPref(KeyEnum key, String value) throws Exception{ 
		try {
			Logger.debug("Set value" + value);
			SystemPreferences pref=getPref(key);
			pref.setValue(value);
			pref.update();
			Logger.debug("Set value" + pref.getValue());
		
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}		
	}
	public List<SystemPreferences> getAll(){
		return SystemPreferences.find.all();
	}
}
