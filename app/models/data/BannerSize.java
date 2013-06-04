package models.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Max;

import play.db.ebean.Model;

@Entity
@Table(name="banner_size")
public class BannerSize extends Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private int id_banner_size;
	
	private String name;
	
	private int width;
	
	private int height;
	
	@Column(columnDefinition="TEXT")
	private String description;
	
	public static Model.Finder<Integer,BannerSize> find = new Model.Finder(Integer.class, BannerSize.class);

	public BannerSize(String name, int width, int height, String description) {
		super();
		this.name = name;
		this.width = width;
		this.height = height;
		this.description = description;
	}

	
	public int getId_banner_size() {
		return id_banner_size;
	}

	public void setId_banner_size(int id_banner_size) {
		this.id_banner_size = id_banner_size;
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
