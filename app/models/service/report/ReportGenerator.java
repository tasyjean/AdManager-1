package models.service.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import scala.Array;

import models.custom_helper.DateBinder;
import models.data.Banner;
import models.data.Campaign;
import models.data.User;
import models.dataWrapper.report.BannerList;
import models.dataWrapper.report.DiagramData;
import models.dataWrapper.report.ReportData;

public class ReportGenerator {

	DateBinder binder;
	public ReportGenerator(DateBinder binder){
		this.binder=binder;
	}
	public ReportData getReport(User user, Campaign campaign, Date from, Date to){
		if(campaign==null){
			return getReportAllCampaign(user, from, to);
		}else{
			return getReportByCampaign(user, campaign, from, to);
		}
	}
	
	private ReportData getReportAllCampaign(User user, Date from, Date to){
		ReportData data=new ReportData();
		List<Campaign> campaigns=Campaign.find.where().eq("id_user", user).findList();
		List<Banner> banners=Banner.find.where().in("campaign", campaigns).findList();
		List<BannerList> bannerLists=new ArrayList<BannerList>();
		for(Banner banner:banners){
			BannerList bannerList=new BannerList(banner, from, to);
			bannerLists.add(bannerList);
		}
		data.setBannerList(bannerLists);
		data.setCampaignList(campaigns);
		data.setSelectedUser(user);
		data.setTo(to);
		data.setFrom(from);
		return data;
	}
	private ReportData getReportByCampaign(User user, Campaign campaign, Date from, Date to){
		ReportData data=new ReportData();
		List<Campaign> campaigns=new ArrayList<Campaign>();
		campaigns.add(campaign);
		
		List<Banner> banners=Banner.find.where().eq("campaign", campaign).findList();
		List<BannerList> bannerLists=new ArrayList<BannerList>();
		for(Banner banner:banners){
			BannerList bannerList=new BannerList(banner, from, to);
			bannerLists.add(bannerList);
		}
		data.setBannerList(bannerLists);
		data.setCampaignList(campaigns);
		data.setSelectedUser(user);
		
		return data;		
	}
	private DiagramData generateDiagramData(User user, Campaign campaign, Date from, Date to){
		if(binder.getDayLength(from, to)>30){
			
		}
		return null;
	}
	
	private DiagramData breakDownByMonth(User user, Campaign campaign, Date from, Date to){
		return null;
	}
	private DiagramData breakDownByDay(User user, Campaign campaign, Date from, Date to){
		return null;
	}	

}
