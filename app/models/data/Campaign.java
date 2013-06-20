package models.data;

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

import models.custom_helper.MD5;
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
	@Column(columnDefinition="TEXT")	
	private String description;

	@OneToMany(cascade=CascadeType.ALL)
	private List<Banner> banner;
	
	
	public static Model.Finder<Integer,Campaign> find = new Model.Finder(Integer.class, Campaign.class);


	@Override
	public void save(){

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
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
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
	public void setLimit_impression(int limit_impression) {
		this.limit_impression = limit_impression;
	}
	public int getLimit_click() {
		return limit_click;
	}
	public void setLimit_click(int limit_click) {
		this.limit_click = limit_click;
	}
	public int getCurrent_impression() {
		return current_impression;
	}
	public void setCurrent_impression(int current_impression) {
		this.current_impression = current_impression;
	}
	public int getCurrent_click() {
		return current_click;
	}
	public void setCurrent_click(int current_click) {
		this.current_click = current_click;
	}
	public int getBid_price() {
		return bid_price;
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

}
