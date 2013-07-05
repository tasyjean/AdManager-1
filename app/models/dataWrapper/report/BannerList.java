package models.dataWrapper.report;

import java.util.Date;
import java.util.List;

import models.data.Banner;
import models.data.BannerAction;
import models.data.BannerPlacement;
import models.data.Impression;

public class BannerList {

	private Banner banner;
	private int impresion_count;
	private int click_count;
	private Date from;
	private Date to;
	private List<BannerPlacement> placement;
	private List<Impression> impresi;
	private List<BannerAction> click;
	
	public BannerList(Banner banner, Date from, Date to){
		this.banner=banner;
		this.from=from;
		this.to=to;
		placement=BannerPlacement.find.where().eq("banner",banner).findList();
		impresi=Impression.find.where().in("bannerPlacement", placement).between("timestamp", from, to).findList();
		setClick_count(BannerAction.find.where().in("impression", impresi).findRowCount());
		setImpresion_count(Impression.find.where().in("bannerPlacement", placement).between("timestamp", from, to).findRowCount());
	}
	
	public Banner getBanner() {
		return banner;
	}
	public void setBanner(Banner banner) {
		this.banner = banner;
	}
	public int getImpresion_count() {
		return impresion_count;
	}	
	public int getImpresion_count_angka() {
		return impresion_count;
	}
	public void setImpresion_count(int impresion_count) {
		this.impresion_count = impresion_count;
	}
	public int getClick_count() {
		return 	click_count;
	}	
	public int getClick_count_angka() {
		return 	click_count;
	}
	public void setClick_count(int click_count) {
		this.click_count = click_count;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	public String getCTR(){
		try {
			if(impresion_count==0) throw new Exception();
			float click_float=(float)click_count;
			float impresion_float=(float)impresion_count;
			return String.format("%.2f", ((double)(click_float/impresion_float)*100))+"%";
		} catch (Exception e) {
			e.printStackTrace();
			return "0 %";
		}
	}

}
