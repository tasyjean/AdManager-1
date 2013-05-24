package models.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import models.data.enumeration.RoleEnum;

import play.db.ebean.Model;
import be.objectify.deadbolt.core.models.Role;

@Entity
@Table(name="user_role", schema="adsmanager")
public class UserRole extends Model implements Role {

	@Id
	private int id_role;
	
	private RoleEnum name;
	
    public static final Model.Finder<Integer, UserRole> find = new Model.Finder<Integer, UserRole>(Integer.class, UserRole.class);

    public UserRole(RoleEnum value){
    	this.name=value;
    }
    
	@Override
	public String getName() {
		switch (name) {
			case  ADMINISTRATOR: return "administrator";
			case  ADVERTISER : return "advertiser";
			case  MANAGER : return "manager";	
			default: return "advertiser";
		}
	}
    public static UserRole findByName(RoleEnum name)
    {
        return find.where()
                   .eq("name",
                       name)
                   .findUnique();
    }
	public int getId_role() {
		return id_role;
	}
	public void setId_role(int id_role) {
		this.id_role = id_role;
	}
	public void setName(RoleEnum name) {
		this.name = name;
	}

    
}
