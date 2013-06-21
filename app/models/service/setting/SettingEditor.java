package models.service.setting;

import java.util.List;

import com.avaje.ebean.Ebean;

import models.custom_helper.setting.KeyEnum;
import models.custom_helper.setting.SettingDefault;
import models.custom_helper.setting.SettingManager;
import models.data.BannerSize;
import play.data.DynamicForm;
import play.i18n.Messages;

public class SettingEditor {

	SettingManager settingManager;
	SettingDefault settingDefault;
	public SettingEditor(SettingManager settingManager, SettingDefault settingDefault){
		this.settingManager=settingManager;
		this.settingDefault=settingDefault;
	}
	public String savePrice(DynamicForm form){
		int base_click;
		int base_impression;
		int base_days;
		double discount_factor;
		try{
			base_click=Integer.parseInt(form.get(KeyEnum.BASE_PRICE_CLICK.name()));
			base_impression=Integer.parseInt(form.get(KeyEnum.BASE_PRICE_IMPRESSION.name()));
			base_days=Integer.parseInt(form.get(KeyEnum.BASE_PRICE_DAYS.name()));
			discount_factor =Double.parseDouble(form.get(KeyEnum.DISCOUNT_FACTOR.name()));
			
			if(discount_factor>1.0){
				return Messages.get("constraint.discount_factor");
			}
			settingManager.editPref(KeyEnum.BASE_PRICE_CLICK, Integer.toString(base_click));
			settingManager.editPref(KeyEnum.BASE_PRICE_DAYS, Integer.toString(base_days));
			settingManager.editPref(KeyEnum.BASE_PRICE_IMPRESSION, Integer.toString(base_impression));
			settingManager.editPref(KeyEnum.DISCOUNT_FACTOR, Double.toString(discount_factor));
				
			return "sukses";
			
		}catch(NumberFormatException error){ //validasi angka
			error.printStackTrace();
			return Messages.get("constraint.wrong_number");
		}catch(Exception e){
			return Messages.get("constraint.salah");
		}
		//validasi discount factor

			
	}
	public String saveBanner(DynamicForm form){
		try {
			List<BannerSize> bannerSize=BannerSize.find.all();
			for(BannerSize size:bannerSize){
				size.setDefault_code(form.get(size.getName()));
				size.update();
			}
			return "sukses";
		} catch (Exception e) {
			
			e.printStackTrace();
			return Messages.get("failure.setting_save");
		}
	}
	
	public void setDefault(){
		settingDefault.setAll();
	}

}
