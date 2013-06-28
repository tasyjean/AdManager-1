package models.service.notification;

import models.data.User;

public class NotifItem {

	private String[] param;
	private NotificationType type;
	private User user;
	
	public String getParam() {
		String result = "";
		for(String x:param){
			result=result+x+",";
		}
		return result;
	}
	public void setParam(String[] param) {
		this.param = param;
	}
	public NotificationType getType() {
		return type;
	}
	public void setType(NotificationType type) {
		this.type = type;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
