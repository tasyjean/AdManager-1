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
		List<String> settingUnSelect=new ArrayList<String>();
		settingUnSelect.add(KeyEnum.BASE_PRICE_CLICK.name());
		settingUnSelect.add(KeyEnum.BASE_PRICE_DAYS.name());
		settingUnSelect.add(KeyEnum.BASE_PRICE_IMPRESSION.name());
		settingUnSelect.add(KeyEnum.DISCOUNT_FACTOR.name());
		settingUnSelect.add(KeyEnum.PAYMENT_INSTRUCTION.name());

		
		switch (page) {
		case 1:settingList =SystemPreferences.find.where().in("key", settingUnSelect).orderBy("key").findList();break;
		case 2:settingBanner=BannerSize.find.orderBy("name").findList();break;
		case 3:contactValue=manager.getString(KeyEnum.CONTACT_PAGE);
			   aboutValue=manager.getString(KeyEnum.ABOUT_PAGE);
			   helpValue=manager.getString(KeyEnum.HELP_PAGE);
			   break;
		default:
			break;
		}
		if(settingList!=null){
			for(SystemPreferences pref:settingList){
				if(pref.getKey()==KeyEnum.HELP_PAGE.name()){
					settingList.remove(pref);		
				}
				if(pref.getKey()==KeyEnum.ABOUT_PAGE.name() ){
					settingList.remove(pref);		
				}
				if(pref.getKey()==KeyEnum.CONTACT_PAGE.name() ){
					settingList.remove(pref);		
				}
			}			
		} 

	}
}
