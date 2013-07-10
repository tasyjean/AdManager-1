package models.service.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.Logger;

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
		data.setDiagramData(generateDiagramData_campaign(data));
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
		data.setDiagramData(generateDiagramData_campaign(data));
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
		int x=0;
		String nama_pengiklan = "";
		boolean status=true;
		for(BannerList bannerList:data.getBannerList()){
			if(i==0){
				click.add(bannerList.getClick_count());
				impresi.add(bannerList.getImpresion_count());
				kategori.add(bannerList.getBanner().getCampaign().getCampaignName());
				Logger.debug("kategori size" +kategori.size()+kategori.get(0));
				nama_pengiklan=bannerList.getBanner().getCampaign().getId_user().getFront_name();
				x++;
			}else{
				status=false;
				if(kategori.get(x-1).equals(bannerList.getBanner().getCampaign().getCampaignName())){
					int f=click.get(x-1);
					click.set(x-1, f+bannerList.getClick_count());
					
					int y=impresi.get(x-1);
					impresi.set(x-1, y+bannerList.getImpresion_count());
					Logger.debug("Impresi "+kategori.size()+kategori.get(x-1));
				}else{
					click.add(bannerList.getClick_count());
					impresi.add(bannerList.getImpresion_count());
					kategori.add(bannerList.getBanner().getCampaign().getCampaignName());
					x++;
					Logger.debug("kategori add" +kategori.size()+kategori.get(x-1));
				}
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
			diagramData.setText("Jumlah Impresi Dan Klik Dari Campaign");
			diagramData.setSubtext("Pengiklan: "+nama_pengiklan);
			return diagramData;
		}
	}
	private DiagramData generateDiagramData_banner(ReportData data){
		DiagramData diagramData=new DiagramData();
		
		List<String> kategori=new ArrayList<String>();
		List<Integer> click=new ArrayList<Integer>();
		List<Integer> impresi=new ArrayList<Integer>();
		int i=0;
		String nama_pengiklan = "";
		for(BannerList bannerList:data.getBannerList()){
			if(i==0){
				nama_pengiklan=bannerList.getBanner().getCampaign().getId_user().getFront_name();
			}
			kategori.add(bannerList.getBanner().getName());
			click.add(bannerList.getClick_count());
			impresi.add(bannerList.getImpresion_count());
			i++;
			Logger.debug("Banner List" +bannerList.getBanner().getName() );
		}

		diagramData.setCategories(kategori);
		diagramData.setClick(click);
		diagramData.setImpresi(impresi);
		diagramData.setText("Jumlah Impresi Dan Klik Dari Banner");
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
