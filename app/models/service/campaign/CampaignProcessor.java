package models.service.campaign;

import java.text.ParseException;
import java.util.Date;

import org.joda.time.DateTime;

import com.avaje.ebean.Page;

import play.data.Form;
import models.custom_helper.DateBinder;
import models.data.Campaign;
import models.data.User;
import models.data.Zone;
import models.data.enumeration.CampaignTypeEnum;
import models.data.enumeration.PricingModelEnum;
import models.form.backendForm.campaignForm.CampaignForm;

public class CampaignProcessor {

	DateBinder binder;
	
	public CampaignProcessor(DateBinder binder){
		this.binder=binder;
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
	
	public Campaign updateCampaign(Form<Campaign> form, int idCampaign){
		return null;
	}
	
	public Page<Campaign> getAllCampaign(int page, int pageSize, String sortBy, boolean active, String order, String filter){
        return 
                Campaign.find.where()
                    .ilike("campaignName", "%" + filter + "%")
                    .where()
                    .eq("t0.isDeleted", false) //tidak sedang dihapus (t0 representasi tabel campaign)
                    .orderBy(sortBy + " " + order)
                    .fetch("banner") 
                    .findPagingList(pageSize)
                    .getPage(page);		
	}
	public Page<Campaign> getUserCampaign(int page, int idUser, int pageSize, String sortBy, boolean active, String order, String filter){
        return 
                Campaign.find.where()
                    .ilike("campaignName", "%" + filter + "%")
                    .where()
                    .eq("t0.isDeleted", false) //tidak sedang dihapus (t0 representasi tabel campaign)
                    .eq("isActive", active)
                    .eq("id_user", idUser)
                    .orderBy(sortBy + " " + order)          
                    .fetch("banner") 
                    .findPagingList(pageSize)
                    .getPage(page);		
	}	
	public Campaign getSingleCampaign(int idCampaign){
		return Campaign.find.byId(idCampaign);
	}

}
