package models.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name="banner_placement")
public class BannerPlacement extends Model {

	@Id
	private int id_banner_placement;
	@ManyToOne
	private Banner banner;
	@ManyToOne
	private Zone zone;
	
	public static Model.Finder<Integer,BannerPlacement> find = new Model.Finder(BannerPlacement.class, User.class);

	public int getId_banner_placement() {
		return id_banner_placement;
	}

	public void setId_banner_placement(int id_banner_placement) {
		this.id_banner_placement = id_banner_placement;
	}

	public Banner getBanner() {
		return banner;
	}

	public void setAds(Banner banner) {
		this.banner = banner;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	
}
