package models.form.backendForm.campaignForm;

import java.text.ParseException;
import java.util.Date;

import javax.validation.Constraint;

import models.custom_helper.DateBinder;
import models.custom_helper.setting.KeyEnum;
import models.custom_helper.setting.SettingManager;
import models.data.enumeration.CampaignTypeEnum;
import models.data.enumeration.PricingModelEnum;
import play.data.validation.Constraints.Required;
import play.i18n.Messages;

public class CampaignForm {

	public String idUser;
	@Required
	public String campaignName;
	public String campaignType;
	public String startDate;
	public String endDate;
	public String pricingModel="";
	public int impressionLimit;
	public int clickLimit;
	public String description="";
	public int bidPrice;
	
	public Date startProc;
	public Date endProc;
	public String validate(){
		SettingManager pref=new SettingManager();
		DateBinder binder=new DateBinder();
		
		//validasi campaign
		if(campaignType.equals(CampaignTypeEnum.CONTRACT.name())){
			if(pricingModel.equals(PricingModelEnum.CPA.name())){
				//validasi bid price untuk 
				if(bidPrice<pref.getInt(KeyEnum.BASE_PRICE_CLICK)){
					return Messages.get("validation.bidPrice",pref.getInt(KeyEnum.BASE_PRICE_IMPRESSION));
				}
				if(clickLimit<1){
					return Messages.get("validation.impressionLimit");
				}
			}else if(pricingModel.equals(PricingModelEnum.CPM.name())){
				if(bidPrice<pref.getInt(KeyEnum.BASE_PRICE_IMPRESSION)){
					return Messages.get("validation.bidPrice",pref.getInt(KeyEnum.BASE_PRICE_IMPRESSION));
				}
				if(impressionLimit<1){
					return Messages.get("validation.impressionLimit");
				}
			}else{
				return Messages.get("validaton.wrongInput");
			}
			
		}else if(campaignType.equals(CampaignTypeEnum.EXCLUSIVE.name())){
			if(!pricingModel.equals(PricingModelEnum.FLAT)){
				return Messages.get("validaton.wrongInput");
			}
			if(bidPrice<pref.getInt(KeyEnum.BASE_PRICE_DAYS)){
				return Messages.get("validation.bidPrice",pref.getInt(KeyEnum.BASE_PRICE_DAYS));
			}
			try {
				startProc=binder.getFormat().parse(startDate);
				endProc=binder.getFormat().parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
				return Messages.get("validation.dateFormat");
			}
//			validasi startDate
			if(!binder.isAfterToday(startProc)){
				return Messages.get("validation.startDate");
			}
			//validasi endDate
			if(binder.getDayLength(startProc, endProc)<0){
				return Messages.get("validation.endDate");				
			}
		}else{
			return Messages.get("validation.bug");
		}
		return null;
	}

}
