package models.dataWrapper.setting;

import java.util.List;

import com.avaje.ebean.OrderBy;

import models.data.BannerSize;
import models.data.SystemPreferences;

public class SettingData {

	//untuk halaman setting biaya
	public List<SystemPreferences> settingList;
	
	//untuk halaman setting banner
	public List<BannerSize> 	settingBanner;
	public SettingData(int page){		
		switch (page) {
		case 1:settingList =SystemPreferences.find.orderBy("key").findList();break;
		case 2:settingBanner=BannerSize.find.orderBy("name").findList();break;
		default:
			break;
		}
	}
}
