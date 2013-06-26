package models.service.campaign;

import java.text.ParseException;
import java.util.Date;

import org.joda.time.DateTime;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;

import play.data.Form;
import models.custom_helper.DateBinder;
import models.data.Banner;
import models.data.Campaign;
import models.data.User;
import models.data.UserRole;
import models.data.Zone;
import models.data.enumeration.CampaignTypeEnum;
import models.data.enumeration.PricingModelEnum;
import models.form.backendForm.campaignForm.CampaignForm;

public class CampaignProcessor {

	DateBinder binder;
	BannerProcessor proc;
	
	public CampaignProcessor(DateBinder binder, BannerProcessor proc){
		this.binder=binder;
		this.proc=proc;
	}
	public Campaign save(Form<CampaignForm> form){
		Campaign campaign=new Campaign();
		User user=User.find.byId(Integer.parseInt(form.get().idUser));
		
		campaign.setBid_price(form.get().bidPrice);
		campaign.setCampaign_type(CampaignTypeEnum.valueOf(form.get().campaignType));
		campaign.setCampaignName(form.get().campaignName);
		
		try {
			campaign.setEnd_date(binder.getFormat().parse(form.get().endDate));
			campaign.setStart_date(binder.getFormat().parse(form.get().startDate));
		} catch (ParseException e) {
			campaign.setStart_date(new Date());
			DateTime endDate=new DateTime();
			endDate=endDate.plusMonths(12);
			campaign.setEnd_date(endDate.toDate());
			e.printStackTrace();
		}
		campaign.setId_user(user);
		campaign.setLimit_click(form.get().clickLimit);
		campaign.setLimit_impression(form.get().impressionLimit);
		campaign.setPricing_model(PricingModelEnum.valueOf(form.get().pricingModel));
		campaign.setDescription(form.get().description);
		campaign.setActivated(false);
		campaign.save();
		
		return campaign;
	}
	public Campaign update(Form<CampaignForm> form){
		Campaign campaign=null;
		try {
			campaign = Campaign.find.byId(Integer.parseInt(form.get().idCampaign));
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
			return null;
		}
		User user=User.find.byId(Integer.parseInt(form.get().idUser));
		
		campaign.setBid_price(form.get().bidPrice);
//		campaign.setCampaign_type(CampaignTypeEnum.valueOf(form.get().campaignType));
		campaign.setCampaignName(form.get().campaignName);
		
		try {
			campaign.setEnd_date(binder.getFormat().parse(form.get().endDate));
			campaign.setStart_date(binder.getFormat().parse(form.get().startDate));
		} catch (ParseException e) {
			campaign.setStart_date(new Date());
			DateTime endDate=new DateTime();
			endDate=endDate.plusMonths(12);
			campaign.setEnd_date(endDate.toDate());
			e.printStackTrace();
		}
//		campaign.setId_user(user);
		campaign.setLimit_click(form.get().clickLimit);
		campaign.setLimit_impression(form.get().impressionLimit);
		campaign.setPricing_model(PricingModelEnum.valueOf(form.get().pricingModel));
		campaign.setDescription(form.get().description);
		campaign.setActivated(false);
		campaign.update();		
		return campaign;
	}	
	public Campaign updateCampaign(Form<Campaign> form, int idCampaign){
		return null;
	}
	
	public Page<Campaign> getAllCampaign(int page, int pageSize, String sortBy, String order, String filter){
        return 
                Campaign.find.where()
                    .ilike("campaignName", "%" + filter + "%")
                    .where()
                    .eq("t0.is_deleted", false) //tidak sedang dihapus (t0 representasi tabel campaign)
                    .orderBy(sortBy + " " + order)
                    .fetch("banner") 
                    .findPagingList(pageSize)
                    .getPage(page);		
	}
	public Page<Campaign> getUserCampaign(int page, User idUser, int pageSize, String sortBy, String order, String filter){
        return 
                Campaign.find.where()
                    .ilike("campaignName", "%" + filter + "%")
                    .where()
                    .eq("t0.is_deleted", false) //tidak sedang dihapus (t0 representasi tabel campaign)
                    .eq("id_user", idUser)
                    .orderBy(sortBy + " " + order)          
                    .fetch("banner")
                    .fetch("id_user")
                    .findPagingList(pageSize)
                    .getPage(page);		
	}	
	public Campaign getSingleCampaign(int idCampaign, User userLogin){
		if(userLogin.getRole().getName().equals("administrator")){
			return Campaign.find.byId(idCampaign);			
		}
		if(userLogin.getRole().getName().equals("manager")){
			return Campaign.find.byId(idCampaign);			
		}
		if(userLogin.getRole().getName().equals("advertiser")){
			Campaign campaign=Campaign.find.byId(idCampaign);
			if(userLogin.getId_user()==campaign.getId_user().getId_user()){
				return Campaign.find.byId(idCampaign);							
			}else{
				return null;
			}
		}
		return null;
	}
	
	public int activate(int idCampaign){
		try {
			int returns;
			Campaign campaign=Campaign.find.byId(idCampaign);
			if(campaign.isActivated()){
				campaign.setActivated(false);
				returns =0;
			}else{
				campaign.setActivated(true);
				returns=1;
			}
			campaign.update();
			return returns;
		} catch (Exception e) {
			e.printStackTrace();
			return 10;
		}
	}
	/*
	 * Set ke mode is deleted
	 * set semua banner ke mode delete
	 */
	public boolean deleteCampaign(int idCampaign){
		Ebean.beginTransaction();
		try {
			Campaign campaign=Campaign.find.byId(idCampaign);
			campaign.setDeleted(true);
			for(Banner banner:campaign.getBanner()){
				proc.delete(banner.getId_banner());				
			}
			campaign.update();
			Ebean.commitTransaction();
			return true;
		} catch (Exception e) {
			Ebean.rollbackTransaction();
			e.printStackTrace();
			return false;
		}finally{
			Ebean.endTransaction();
		}
	}

}
