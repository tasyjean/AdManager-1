package models.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name="ads",schema="adsmanager")
public class Ads extends Model {

	@Id
	private int id_ads;
	
	@ManyToOne
	private Campaign campaign;
	
	@ManyToOne
	private AdsSize adsSize;
	
	@ManyToOne
	private AdsType adsType;
	
	private String name;
	@Column(columnDefinition="TEXT")
	private String description;
	private String title;
	private String content_text;
	@Column(length=400)
	private String content_link; 	//konten kaya flash, gambar dll
	@Column(length=400)	
	private String target;
	@Column(length=400)
	
	private String alt_text;
	private int weight;
	private int impression_count;
	private int click_count;
	private int like_count;
	private int hide_count;
	private boolean isActive;
	private boolean isDeleted;
	private boolean isGanteng;
	
	public static Model.Finder<Integer,Ads> find = new Model.Finder(Integer.class, Ads.class);

	public int getId_ads() {
		return id_ads;
	}
	public void setId_ads(int id_ads) {
		this.id_ads = id_ads;
	}
	public Campaign getCampaign() {
		return campaign;
	}
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	public AdsSize getAdsSize() {
		return adsSize;
	}
	public void setAdsSize(AdsSize adsSize) {
		this.adsSize = adsSize;
	}
	public AdsType getAdsType() {
		return adsType;
	}
	public void setAdsType(AdsType adsType) {
		this.adsType = adsType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent_text() {
		return content_text;
	}
	public void setContent_text(String content_text) {
		this.content_text = content_text;
	}
	public String getContent_link() {
		return content_link;
	}
	public void setContent_link(String content_link) {
		this.content_link = content_link;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getAlt_text() {
		return alt_text;
	}
	public void setAlt_text(String alt_text) {
		this.alt_text = alt_text;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getImpression_count() {
		return impression_count;
	}
	public void setImpression_count(int impression_count) {
		this.impression_count = impression_count;
	}
	public int getClick_count() {
		return click_count;
	}
	public void setClick_count(int click_count) {
		this.click_count = click_count;
	}
	public int getLike_count() {
		return like_count;
	}
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	public int getHide_count() {
		return hide_count;
	}
	public void setHide_count(int hide_count) {
		this.hide_count = hide_count;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
}
