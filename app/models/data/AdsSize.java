package models.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import play.db.ebean.Model;

@Entity
@Table(name="adsmanager.ads_size")
public class AdsSize extends Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private int id_ads_size;
	
	private String name;
	
	private int width;
	
	private int height;
	
	@Max(10000)
	private String description;
	
	public static Model.Finder<Long,AdsSize> find = new Model.Finder(Long.class, AdsSize.class);

	public AdsSize(String name, int width, int height, String description) {
		super();
		this.name = name;
		this.width = width;
		this.height = height;
		this.description = description;
	}

	
	public int getId_ads_size() {
		return id_ads_size;
	}

	public void setId_ads_size(int id_ads_size) {
		this.id_ads_size = id_ads_size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	
	

}
