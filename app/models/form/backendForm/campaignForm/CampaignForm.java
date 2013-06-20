package models.form.backendForm.campaignForm;

import play.i18n.Messages;

public class CampaignForm {

	public String idUser;
	public String campaignType;
	public String startDate;
	public String endDate;
	public String pricingModel;
	public int impressionLimit;
	public int clickLimit;
	public String description;
	public int bidPrice;
	
	public String validate(){
		if("a".equals("")){
			return Messages.get("validation.required");
		}
		//TODO validasi bid price
		//TODO validasi startDate
		//TODO validasi endDate
		return null;
	}

}
