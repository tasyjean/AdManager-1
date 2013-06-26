package models.data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import models.data.enumeration.ZoneTypeEnum;

import play.db.ebean.Model;

@Entity
@Table(name="banner")
public class Banner extends Model {

	@Id
	private int id_banner;
	
	@ManyToOne
	private Campaign campaign;
	
	@ManyToOne
	private BannerSize bannerSize;
	
	private ZoneTypeEnum bannerType;
	
	private String name;
	@Column(columnDefinition="TEXT")
	private String description;
	private String title;
	private String content_text;
	@ManyToOne
	private FileUpload content_link; 	//konten kaya flash, gambar dll
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
	@OneToMany
	private List<BannerPlacement> placement;
	
	public static Model.Finder<Integer,Banner> find = new Model.Finder(Integer.class, Banner.class);

	public int getId_banner() {
		return id_banner;
	}
	public void setId_banner(int id_banner) {
		this.id_banner = id_banner;
	}
	public Campaign getCampaign() {
		return campaign;
	}
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	public BannerSize getBannerSize() {
		return bannerSize;
	}
	public void setBannerSize(BannerSize bannerSize) {
		this.bannerSize = bannerSize;
	}
	public ZoneTypeEnum getBannerType() {
		return bannerType;
	}
	public void setBannerType(ZoneTypeEnum bannerType) {
		this.bannerType = bannerType;
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
	public FileUpload getContent_link() {
		return content_link;
	}
	public void setContent_link(FileUpload content_link) {
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
	public List<BannerPlacement> getPlacement() {
		return placement;
	}
	public void setPlacement(List<BannerPlacement> placement) {
		this.placement = placement;
	}
	
	
}
