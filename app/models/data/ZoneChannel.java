package models.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import models.custom_helper.MD5;

import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;

import play.db.ebean.Model;

@Entity
@Table(name="zone_channel")
public class ZoneChannel extends Model{

	@Id
	private int id_zone_channel;
	private String channel_name;
	
	@Column(columnDefinition="TEXT")
	private String channel_description;
	private boolean isDeleted;
	public static Model.Finder<Integer,ZoneChannel> find = new Model.Finder(Integer.class, ZoneChannel.class);

	@OneToMany(cascade=CascadeType.ALL)
	public List<Zone> zone;
	
	@Override
	public void save(){
		isDeleted=false;
		super.save();
	}
	
	//Getter Setter
	public void setZone(Collection<Zone> zone){
        final List<Zone> clone = new ArrayList<Zone>(this.zone);
        //delete yang udah ada
        for(Zone x:clone){
        	getZone().remove(x);
        	x.setZone_channel(null);
        }
        for(Zone x:zone){
        	getZone().add(x);
        	x.setZone_channel(this);
        }
	}
	
	public List<Zone> getZone(){
	    if (zone == null) {
            zone = new ArrayList<Zone>();
        }
        return zone;
    }	
	
	public int getId_zone_channel() {
		return id_zone_channel;
	}

	public void setId_zone_channel(int id_zone_channel) {
		this.id_zone_channel = id_zone_channel;
	}

	public String getChannel_name() {
		return channel_name;
	}

	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}

	public String getChannel_description() {
		return channel_description;
	}

	public void setChannel_description(String channel_description) {
		this.channel_description = channel_description;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
