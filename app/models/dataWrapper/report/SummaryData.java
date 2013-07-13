package models.dataWrapper.report;

import java.util.Date;
import java.util.List;

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

	private int impression;
	private int click;
	private int totalMoney;
	private int transactionCount;
	private Date to;
	private Date from;
	private List<BannerPlacement> bannerPlacementList;
	public SummaryData(Date from, Date to){
		this.to=to;
		this.from=from;
		
		setBannerPlacementList();
		setImpression();
		setClick();
		setTotalMoney();
		setTransactionCount();
	}

	private void setBannerPlacementList(){
		bannerPlacementList=BannerPlacement.find.all();		
	}
	public int getImpression() {
		return impression;
	}
	
	public void setImpression() {
	 impression=Impression.find.where().in("bannerPlacement", bannerPlacementList)
			 	.between("timestamp", from, to).findRowCount();
	}

	public int getClick() {
		return click;
	}

	public void setClick() {
	    click=BannerAction.find.where().eq("action_type", ActionTypeEnum.CLICK.name()).
	    	 between("timestamp", from, to).findRowCount();
	}

	public int getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney() {
		List<AdsTransaction> transactions=AdsTransaction.find.where()
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

	public void setTransactionCount() {
		List<AdsTransaction> transactions=AdsTransaction.find.where()
				.between("timestamp", from,to).findList();
		
		this.transactionCount = transactions.size();
	}
	
}
