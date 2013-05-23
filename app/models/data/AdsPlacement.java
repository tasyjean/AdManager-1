package models.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name="ads_placement",schema="adsmanager")
public class AdsPlacement extends Model {

	@Id
	private int id_ads_placement;
	@ManyToOne
	private Ads ads;
	@ManyToOne
	private Zone zone;
	
	public static Model.Finder<Integer,AdsPlacement> find = new Model.Finder(AdsPlacement.class, User.class);

	public int getId_ads_placement() {
		return id_ads_placement;
	}

	public void setId_ads_placement(int id_ads_placement) {
		this.id_ads_placement = id_ads_placement;
	}

	public Ads getAds() {
		return ads;
	}

	public void setAds(Ads ads) {
		this.ads = ads;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	
}
