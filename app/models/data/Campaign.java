package models.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import models.custom_helper.Angka;
import models.custom_helper.DateBinder;
import models.custom_helper.MD5;
import models.custom_helper.setting.KeyEnum;
import models.custom_helper.setting.SettingManager;
import models.data.enumeration.CampaignTypeEnum;
import models.data.enumeration.PricingModelEnum;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
@Table(name="campaign")
public class Campaign extends Model{

	@Id
	private int id_campaign;
	
	@Required
	@ManyToOne
	private User id_user;	
	private String campaignName;
	private CampaignTypeEnum campaign_type;
	
	private Date start_date;
	private Date end_date;
	private PricingModelEnum pricing_model;
	private int limit_impression;
	private int limit_click;
	public int current_impression;
	private int current_click;
	private int bid_price;

	private boolean isActivated;
	private boolean isDeleted;
	private Date createdAt;
	@Column(columnDefinition="TEXT")	
	private String description;

	@OneToMany(cascade=CascadeType.ALL)
	private List<Banner> banner;
	
	SimpleDateFormat  format_read=new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat  format_write=new SimpleDateFormat("dd/MM/yyyy");
	
	public static Model.Finder<Integer,Campaign> find = new Model.Finder(Integer.class, Campaign.class);


	@Override
	public void save(){
		createdAt=new Date();
		super.save();
	}
	public void setAds(Collection<Banner> banner){
        final List<Banner> clone = new ArrayList<Banner>(this.banner);
        //delete yang udah ada
        for(Banner x:clone){
        	getBanner().remove(x);
        	x.setCampaign(null);
        }
        for(Banner x:banner){
        	getBanner().add(x);
        	x.setCampaign(this);
        }
	}
	//Mendapatkan daftar kontak user
	public List<Banner> getBanner(){
	    if (banner == null) {
            banner = new ArrayList<Banner>();
        }
        return banner;
    }
	
	public int getId_campaign() {
		return id_campaign;
	}
	public void setId_campaign(int id_campaign) {
		this.id_campaign = id_campaign;
	}
	public User getId_user() {
		return id_user;
	}
	public void setId_user(User id_user) {
		this.id_user = id_user;
	}
	public CampaignTypeEnum getCampaign_type() {
		return campaign_type;
	}
	public void setCampaign_type(CampaignTypeEnum campaign_type) {
		this.campaign_type = campaign_type;
	}
	public Date getStart_date() {
		return start_date;
	}
	public String getStart_date_formatted() {
		return format_read.format(start_date);
	}
	public String getStart_date_write_formatted() {
		return format_write.format(start_date);
	}		
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}	
	public String getEnd_date_formatted() {
		return format_read.format(end_date);
	}	
	public String getEnd_date_write_formatted() {
		return format_write.format(end_date);
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public PricingModelEnum getPricing_model() {
		return pricing_model;
	}
	public void setPricing_model(PricingModelEnum pricing_model) {
		this.pricing_model = pricing_model;
	}
	public int getLimit_impression() {
		return limit_impression;
	}	
	public String getLimit_impression_string() {
		return Angka.toAngka(limit_impression);
	}
	public void setLimit_impression(int limit_impression) {
		this.limit_impression = limit_impression;
	}
	public int getLimit_click() {
		return limit_click;
	}	
	public String getLimit_click_string() {
		return Angka.toAngka(limit_click);
	}
	public void setLimit_click(int limit_click) {
		this.limit_click = limit_click;
	}
	public int getCurrent_impression() {
		return current_impression;
	}
	public String getCurrent_impression_string() {
		return Angka.toAngka(current_impression);
	}
	public void setCurrent_impression(int current_impression) {
		this.current_impression = current_impression;
	}
	public int getCurrent_click() {
		return current_click;
	}
	public String getCurrent_click_string(){
		return Angka.toAngka(current_click);
	}
	public void setCurrent_click(int current_click) {
		this.current_click = current_click;
	}
	public int getBid_price() {
		return bid_price;
	}	
	public String getBid_price_string() {
		return Angka.toRupiah(bid_price);
	}
	public void setBid_price(int bid_price) {
		this.bid_price = bid_price;
	}
	public boolean isActivated() {
		return isActivated;
	}
	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	public void setDescription(String description){
		this.description=description;
	}
	public String getDescription(){
		return this.description;
	}
	public int getImpressionLeft(){
		return limit_impression-current_impression;
	}
	public int getClickLeft(){
		return limit_click-current_click;		
	}	
	public String getImpressionLeft_string(){
		return Angka.toAngka(limit_impression-current_impression);
	}
	public String getClickLeft_string(){
		return Angka.toAngka(limit_click-current_click);		
	}
	public int getDaysLeft(){
		DateBinder binder=new DateBinder();
		Date today=new Date();
		int result=binder.getDayLength(today, end_date)+1;
		if(result<0){
			return 0;
		}else{
			return result;
		}
	}
	public  String getCreatedAt_formatted(){
		SimpleDateFormat format=new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
		return format.format(createdAt);
	}
	public String isActivated_string(){
		return (isActivated)? "Aktif" : "Tidak Aktif";
	}
	public int campaignDuration(){
		DateBinder binder=new DateBinder();
		return binder.getDayLength(start_date, end_date);
		
	}
	//menghitung harga
	private int countPrice(){
		SettingManager manager=new SettingManager();
		if(pricing_model.equals(PricingModelEnum.CPA)){
			return bid_price*limit_click;
		}
		if(pricing_model.equals(PricingModelEnum.CPM)){
			return bid_price*limit_impression/1000;
		}
		if(pricing_model.equals(PricingModelEnum.FLAT)){
			int hari=campaignDuration();
			float discount=manager.getFloat(KeyEnum.DISCOUNT_FACTOR);
			return (int)Math.floor((hari*bid_price)-(Math.floor(hari/7)*discount*bid_price*7));
		}
		return 0;
	}
	public String costEstimation(){
		return Angka.toRupiah(countPrice());
	}
	
}
