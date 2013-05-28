package models.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
@Table(name="ads_type")
public class AdsType extends Model {

	@Id
	private int id_ad_type;
	@Required
	private String name;

	@Column(columnDefinition="TEXT")	
	private String description;
	
	@Column(columnDefinition="TEXT")	
	private String code;

	public static Model.Finder<Integer,AdsType> find = new Model.Finder(Integer.class, AdsType.class);

	
	public int getId_ad_type() {
		return id_ad_type;
	}

	public void setId_ad_type(int id_ad_type) {
		this.id_ad_type = id_ad_type;
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

	public static Model.Finder<Integer, AdsType> getFind() {
		return find;
	}

	public static void setFind(Model.Finder<Integer, AdsType> find) {
		AdsType.find = find;
	}

	
}
