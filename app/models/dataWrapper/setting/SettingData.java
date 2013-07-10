package models.dataWrapper.setting;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.OrderBy;

import models.custom_helper.setting.KeyEnum;
import models.custom_helper.setting.SettingManager;
import models.data.BannerSize;
import models.data.SystemPreferences;

public class SettingData {

	//untuk halaman setting biaya
	public List<SystemPreferences> settingList;
	
	//untuk halaman setting banner
	public List<BannerSize> 	settingBanner;
	public String contactValue;
	public String aboutValue;
	public String helpValue;
	SettingManager manager;
	public SettingData(int page, SettingManager manager){
		this.manager=manager;
		List<String> settingSelect=new ArrayList<String>();
		
		for(KeyEnum enums:KeyEnum.values()){
			if(enums!=KeyEnum.ABOUT_PAGE || 
    		   enums!=KeyEnum.ABOUT_PAGE ||
			   enums!=KeyEnum.ABOUT_PAGE){
				settingSelect.add(enums.name());
			}
		}
		switch (page) {
		case 1:settingList =SystemPreferences.find.where().in("key", settingSelect).orderBy("key").findList();break;
		case 2:settingBanner=BannerSize.find.orderBy("name").findList();break;
		case 3:contactValue=manager.getString(KeyEnum.CONTACT_PAGE);
			   aboutValue=manager.getString(KeyEnum.ABOUT_PAGE);
			   helpValue=manager.getString(KeyEnum.ABOUT_PAGE);
			   break;
		default:
			break;
		}
/*		if(settingList!=null){
			for(SystemPreferences pref:settingList){
				if(pref.getKey()==KeyEnum.CONTACT_PAGE.name() ||
				   pref.getKey()==KeyEnum.HELP_PAGE.name() ||	
				   pref.getKey()==KeyEnum.ABOUT_PAGE.name() ){
					settingList.remove(pref);		
				}
			}			
		} */

	}
}
