package models.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.Logger;
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
	
	private boolean isActive;
	public static Model.Finder<Integer,BannerPlacement> find = new Model.Finder(Integer.class, BannerPlacement.class);

	/* 
	 * Aturan nyimpen
	 * pastiin banner ama zona unik
	 * jika ada, biarin, cuekin
	 */
	public void save(){
		this.isActive=true;
		if(BannerPlacement.find.where().eq("banner",banner).eq("zone",zone).findUnique()==null){
			super.save();			
		}else {
			Logger.debug("Nothing todo");
		}
	}
	/*
	 * Aturan update
	 *ntar aja
	 */
	public void update(){
		super.update();
	}
	public int getId_banner_placement() {
		return id_banner_placement;
	}
	public void setId_banner_placement(int id_banner_placement) {
		this.id_banner_placement = id_banner_placement;
	}

	public Banner getBanner() {
		return banner;
	}

	public Zone getZone() {
		return zone;
	}
	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setBanner(Banner banner) {
		this.banner = banner;
	}

	
}
