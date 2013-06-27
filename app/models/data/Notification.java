package models.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import models.service.notification.NotificationType;

import play.db.ebean.Model;

@Entity
@Table(name="notification")
public class Notification extends Model{
	
	@Id
	private int id_notification;
	@ManyToOne
	private User user;
	private Date timestamp;
	private NotificationType notification_type;
	private String param;
	private boolean isRead;
	
	public static Model.Finder<Integer,Notification> find = new Model.Finder(Integer.class, Notification.class);

	public int getId_notification() {
		return id_notification;
	}
	
	public void save(){
		timestamp= new Date();
		super.save();
	}

	public void setId_notification(int id_notification) {
		this.id_notification = id_notification;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public NotificationType getNotification_type() {
		return notification_type;
	}

	public void setNotification_type(NotificationType notification_type) {
		this.notification_type = notification_type;
	}

	public String getParam() {
		return param;
	}

	public void setLink(String param) {
		this.param = param;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public void setParam(String param) {
		this.param = param;
	}
	
	

	
}
