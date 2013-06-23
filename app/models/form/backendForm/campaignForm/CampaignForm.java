package models.form.backendForm.campaignForm;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Constraint;

import models.custom_helper.DateBinder;
import models.custom_helper.setting.KeyEnum;
import models.custom_helper.setting.SettingManager;
import models.data.enumeration.CampaignTypeEnum;
import models.data.enumeration.PricingModelEnum;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.Required;
import play.i18n.Messages;

public class CampaignForm {

	@Required
	public String idUser;
	
	public String campaignName="";
	public String campaignType="";
	public String startDate="";
	public String endDate="";
	public String pricingModel="";
	public int impressionLimit;
	public int clickLimit;
	public String description="";
	public int bidPrice;
	
	public Date startProc;
	public Date endProc;
	public List<ValidationError> validate(){
		SettingManager pref=new SettingManager();
		DateBinder binder=new DateBinder();
		
		List<ValidationError> error= new ArrayList<ValidationError>();
		
		//validasi campaign
		if(campaignType.equals(CampaignTypeEnum.CONTRACT.name())){
			if(pricingModel.equals(PricingModelEnum.CPA.name())){
				//validasi bid price 
				
				if(bidPrice<pref.getInt(KeyEnum.BASE_PRICE_CLICK)){
					error.add(new ValidationError("bidPrice", 
												   Messages.get("validation.bidPrice",pref.getInt(KeyEnum.BASE_PRICE_CLICK))));
				}
				if(clickLimit<1){
					error.add(new ValidationError("clickLimit",
												   Messages.get("validation.clickLimit")));
				}
			}else if(pricingModel.equals(PricingModelEnum.CPM.name())){
				if(bidPrice<pref.getInt(KeyEnum.BASE_PRICE_IMPRESSION)){
					error.add(new ValidationError("bidPrice", 
							Messages.get("validation.bidPrice",pref.getInt(KeyEnum.BASE_PRICE_IMPRESSION))));					
				}
				if(impressionLimit<1){
					error.add(new ValidationError("impressionLimit",
							   Messages.get("validation.impressionLimit")));
				}
			}else{
				error.add(new ValidationError("wrong",
						   Messages.get("validation.wrongInput")));
			}
			
		}else if(campaignType.equals(CampaignTypeEnum.EXCLUSIVE.name())){
			if(!pricingModel.equals(PricingModelEnum.FLAT)){
				error.add(new ValidationError("wrong",
						   Messages.get("validation.wrongInput")));
			}
			if(bidPrice<pref.getInt(KeyEnum.BASE_PRICE_DAYS)){
				error.add(new ValidationError("bidPrice", 
						   Messages.get("validation.bidPrice",pref.getInt(KeyEnum.BASE_PRICE_IMPRESSION))));
			}
			
			try {
				if(startProc.equals("")){
					error.add(new ValidationError("endDateRequired",
							   Messages.get("validation.startdateRequired")));
				}if(endProc.equals("")){
					error.add(new ValidationError("startDateRequired",
							   Messages.get("validation.enddateRequired")));
				}
				startProc=binder.getFormat().parse(startDate);
				endProc=binder.getFormat().parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
				error.add(new ValidationError("dateFormat",
						   Messages.get("validation.dateFormat")));
			}
//			validasi startDate
			if(!binder.isAfterToday(startProc)){
				error.add(new ValidationError("startDate",
						   Messages.get("validation.startDate")));
			}
			//validasi endDate
			if(binder.getDayLength(startProc, endProc)<0){
				error.add(new ValidationError("endDate",
						   Messages.get("validation.endDate")));
			}
		}else{
			error.add(new ValidationError("bug",
					   Messages.get("validation.bug")));
		}
		return (error.size()==0)? null: error;
	}

}
