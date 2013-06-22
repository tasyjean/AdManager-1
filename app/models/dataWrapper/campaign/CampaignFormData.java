package models.dataWrapper.campaign;

import java.util.ArrayList;
import java.util.List;

import models.custom_helper.setting.KeyEnum;
import models.custom_helper.setting.SettingManager;
import models.data.User;
import models.data.UserRole;
import models.data.enumeration.CampaignTypeEnum;
import models.data.enumeration.PricingModelEnum;
import models.data.enumeration.RoleEnum;

public class CampaignFormData {
	
	SettingManager pref;
	private int impressionPrice;
	private int clickPrice;
	private int dailyPrice;
	private float discountFactor;
	private List<String[]> campaignType;
	private List<String[]> pricingModel;
	private List<User> userList;
	
	public CampaignFormData(SettingManager pref) {
		this.pref=pref;
		impressionPrice = pref.getInt(KeyEnum.BASE_PRICE_IMPRESSION);
		clickPrice=pref.getInt(KeyEnum.BASE_PRICE_CLICK);
		dailyPrice=pref.getInt(KeyEnum.BASE_PRICE_DAYS);
		discountFactor =pref.getFloat(KeyEnum.DISCOUNT_FACTOR);
		setCampaignType();
		setPricingModel();
		setUser();
	}
	
	private void setCampaignType(){
		campaignType=new ArrayList<String[]>();
		for(CampaignTypeEnum enums:CampaignTypeEnum.values()){
			String[] data={enums.name(),enums.toString(),enums.getDescription(enums)};
			campaignType.add(data);
		}
	}
	private void setPricingModel(){
		pricingModel=new ArrayList<String[]>();
		for(PricingModelEnum enums:PricingModelEnum.values()){
			String[] data={enums.name(),enums.toString(),enums.getDescription(enums)};
			pricingModel.add(data);
		}
	}
	
	private void setUser(){
		UserRole advertiser_role=UserRole.find.where().eq("name", RoleEnum.ADVERTISER).findUnique();
		userList=User.find.where().eq("role", advertiser_role).findList();
	}

	public SettingManager getPref() {
		return pref;
	}

	public int getImpressionPrice() {
		return impressionPrice;
	}

	public int getClickPrice() {
		return clickPrice;
	}

	public int getDailyPrice() {
		return dailyPrice;
	}

	public float getDiscountFactor() {
		return discountFactor;
	}

	public List<String[]> getCampaignType() {
		return campaignType;
	}

	public List<String[]> getPricingModel() {
		return pricingModel;
	}

	public List<User> getUserList() {
		return userList;
	}
	
	

}
