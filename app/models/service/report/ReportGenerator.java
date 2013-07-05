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
		List<Campaign> campaigns=Campaign.find.where().eq("id_user", user).findList();
		
		List<Banner> banners=Banner.find.where().eq("campaign", campaign).findList();
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

	private DiagramData generateDiagramData_campaign(ReportData data){
		DiagramData diagramData=new DiagramData();
		
		List<String> kategori=new ArrayList<String>();
		List<Integer> click=new ArrayList<Integer>();
		List<Integer> impresi=new ArrayList<Integer>();
		int currentCampaign=0;
		int click_count=0;
		int impression_count=0;
		int i=0;
		String nama_pengiklan = "";
		for(BannerList bannerList:data.getBannerList()){
			if(i==0){
				currentCampaign=bannerList.getBanner().getCampaign().getId_campaign();
				nama_pengiklan=bannerList.getBanner().getCampaign().getId_user().getFront_name();
			}
			if(bannerList.getBanner().getCampaign().getId_campaign()==currentCampaign){
				click_count=click_count+bannerList.getClick_count();
				impression_count=impression_count+bannerList.getImpresion_count();
			}else{
				currentCampaign=bannerList.getBanner().getCampaign().getId_campaign();
				kategori.add(bannerList.getBanner().getCampaign().getCampaignName());
				click.add(click_count);
				click.add(impression_count);
				click_count=0;
				impression_count=0;
			}
			i++;
		}
		//Jika hanya satu campaign, maka tampilkan berdasarkan banner
		if(kategori.size()==1){
			return generateDiagramData_banner(data);
		}else{
			diagramData.setCategories(kategori);
			diagramData.setClick(click);
			diagramData.setImpresi(impresi);
			diagramData.setText("Grafik Jumlah Impresi Dan Klik Dari Campaign");
			diagramData.setSubtext("Pengiklan: "+nama_pengiklan);
			return diagramData;
		}
		
	}
	private DiagramData generateDiagramData_banner(ReportData data){
		DiagramData diagramData=new DiagramData();
		
		List<String> kategori=new ArrayList<String>();
		List<String> click=new ArrayList<String>();
		List<String> impresi=new ArrayList<String>();
		int currentCampaign=0;
		int click_count=0;
		int impression_count=0;
		int i=0;
		String nama_pengiklan = "";
		for(BannerList bannerList:data.getBannerList()){
			if(i==0){
				nama_pengiklan=bannerList.getBanner().getCampaign().getId_user().getFront_name();
			}
			currentCampaign=bannerList.getBanner().getCampaign().getId_campaign();
			kategori.add(bannerList.getBanner().getName());
			click.add(Integer.toString(bannerList.getClick_count()));
			click.add(Integer.toString(impression_count));

			i++;
		}

		diagramData.setCategories(kategori);
		diagramData.setClick(click);
		diagramData.setImpresi(impresi);
		diagramData.setText("Grafik Jumlah Impresi Dan Klik Dari Campaign");
		diagramData.setSubtext("Pengiklan: "+nama_pengiklan);
		return diagramData;
	
	}
	private DiagramData breakDownByMonth(User user, Campaign campaign, Date from, Date to){
		return null;
	}
	private DiagramData breakDownByDay(User user, Campaign campaign, Date from, Date to){
		return null;
	}	

}
