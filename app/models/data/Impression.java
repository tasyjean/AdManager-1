package models.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.ebean.Model;

@Entity
@Table(name="impression")
public class Impression extends Model {

	@Id
	private Long id_impression;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	@ManyToOne
	private BannerPlacement bannerPlacement;
	private String viewer_ip;
	private String viewer_source;
	public static Model.Finder<Long,Impression> find = new Model.Finder(Long.class, Impression.class);

	public Long getId_impression() {
		return id_impression;
	}

	public void setId_impression(Long id_impression) {
		this.id_impression = id_impression;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getViewer_ip() {
		return viewer_ip;
	}

	public void setViewer_ip(String viewer_ip) {
		this.viewer_ip = viewer_ip;
	}
	public String getViewer_source() {
		return viewer_source;
	}
	public void setViewer_source(String viewer_source) {
		this.viewer_source = viewer_source;
	}

	public BannerPlacement getAdsPlacement() {
		return bannerPlacement;
	}

	public void setAdsPlacement(BannerPlacement bannerPlacement) {
		this.bannerPlacement = bannerPlacement;
	}
}
