package models.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
@Table(name="banner_type")
public class BannerType extends Model {

	@Id
	private int id_banner_type;
	@Required
	private String name;

	@Column(columnDefinition="TEXT")	
	private String description;
	
	@Column(columnDefinition="TEXT")	
	private String code;

	public static Model.Finder<Integer,BannerType> find = new Model.Finder(Integer.class, BannerType.class);

	
	public int getId_banner_type() {
		return id_banner_type;
	}

	public void setId_banner_type(int id_banner_type) {
		this.id_banner_type = id_banner_type;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static Model.Finder<Integer, BannerType> getFind() {
		return find;
	}

	public static void setFind(Model.Finder<Integer, BannerType> find) {
		BannerType.find = find;
	}

	
}
