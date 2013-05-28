package models.data;


/**
 * 
 * 
 * @author Adnan HP (xenovon) (komputok@gmail.com)
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

import be.objectify.deadbolt.core.models.Permission;

@Entity
@Table(name="user_permission")
public class UserPermission extends Model implements Permission {

	@Id
	private int id_permission;
	@Column(name="permission_value")
	private  String value;
	
    public static final Model.Finder<Integer, UserPermission> find = new Model.Finder<Integer, UserPermission>(Integer.class, UserPermission.class);

	@Override
	public String getValue() {

		return value;
	}
	//cari berdasarkan value
	public UserPermission findByValue(String value){
		return find.where().eq("value", value).findUnique();
	}
	
	public int getId_permission() {
		return id_permission;
	}
	public void setId_permission(int id_permission) {
		this.id_permission = id_permission;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
	


}
