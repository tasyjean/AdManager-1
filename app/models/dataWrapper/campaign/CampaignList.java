package models.dataWrapper.campaign;

import java.util.ArrayList;
import java.util.List;

import models.data.Campaign;
import models.data.User;

public class CampaignList {


	List<Campaign> campaign=new ArrayList<Campaign>();
	
	public CampaignList() {
		campaign=Campaign.find.all();
	}
	public CampaignList(User user) {
		campaign=Campaign.find.where().eq("id_user", user).findList();
	}
	
	public List<Campaign> getList(){
		return campaign;
	}
}
