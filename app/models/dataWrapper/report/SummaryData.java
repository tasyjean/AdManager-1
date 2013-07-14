package models.dataWrapper.report;

import java.util.Date;
import java.util.List;

import models.custom_helper.Angka;
import models.data.AdsTransaction;
import models.data.Banner;
import models.data.BannerAction;
import models.data.BannerPlacement;
import models.data.Campaign;
import models.data.Impression;
import models.data.User;
import models.data.UserRole;
import models.data.enumeration.ActionTypeEnum;
import models.data.enumeration.RoleEnum;

public class SummaryData {

	private String title;
	private int impression;
	private int click;
	private int totalMoney;
	private int transactionCount;
	private Date to;
	private User user;
	private Date from;
	private List<BannerPlacement> bannerPlacementList;
	public SummaryData(Date from, Date to, User user){
		this.to=to;
		this.from=from;
		this.user=user;
		setBannerPlacementList();
		setImpression();
		setClick();
		setTotalMoney();
		setTransactionCount();
	}
	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title=title;
	}
	
	private void setBannerPlacementList(){
		if(user!=null){
			List<Campaign> campaigns=Campaign.find.where().eq("id_user", user).findList();
			List<Banner> banners=Banner.find.where().in("campaign", campaigns).findList();
			bannerPlacementList = BannerPlacement.find.where().in("banner", banners).findList(); 
					
		}else{
			bannerPlacementList=BannerPlacement.find.all();					
		}
	}
	public int getImpression() {
		return impression;
	}
	public String getImpression_angka(){
		return Angka.toAngka(impression);
	}
	public void setImpression() {
	 impression=Impression.find.where().in("bannerPlacement", bannerPlacementList)
			 	.between("timestamp", from, to).findRowCount();
	}

	public int getClick() {
		return click;
	}
	public String getClick_angka(){
		return Angka.toAngka(click);
	}
	
	public void setClick() {
		List<Impression> impressions=Impression.find.where().in("bannerPlacement", bannerPlacementList).findList();
	    click=BannerAction.find.where().eq("action_type", ActionTypeEnum.CLICK.name()).in("impression",impressions)
	    	 .between("timestamp", from, to).findRowCount();
	}

	public int getTotalMoney() {
		return totalMoney;
	}
	public String getTotalMoney_rupiah(){
		return Angka.toRupiah(totalMoney);
	}
	public void setTotalMoney() {
		
		List<AdsTransaction> transactions=AdsTransaction.find.where().in("bannerPlacement", bannerPlacementList)
										.between("timestamp", from,to).findList();
		int total=0;
		for(AdsTransaction transaction:transactions){
			total=total+transaction.getAmount();
		}
		this.totalMoney = total;
	}

	public int getTransactionCount() {
		return transactionCount;
	}
	public String getTransactionCount_angka(){
		return Angka.toAngka(transactionCount);
	}
	public void setTransactionCount() {
		List<AdsTransaction> transactions=AdsTransaction.find.where()
				.between("timestamp", from,to).findList();
		
		this.transactionCount = transactions.size();
	}
	
}
