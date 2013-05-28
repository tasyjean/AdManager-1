package models.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
