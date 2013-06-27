package models.service.notification;

import java.util.List;

import javax.persistence.OptimisticLockException;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;

import models.data.Notification;
import models.data.User;

public class NotificationCenter {
	
	/*
	 * Tipe notifikasi
	 * Notifikasi Iklan nonaktif //untuk pengiklan
	 * Notifikasi saldo habis  //untuk pengiklan 
	 * notifikasi ada permintaan validasi pengisian saldo //untuk manager
	 * notifikasi saldo sudah divalidasi / terisi
	 * notifikasi untuk melihat laporan
	 * notifikasi 
	 * 
	 * Untuk administrator
	 * -> Notifikasi ada iklan baru
	 * -> notifikasi ada banner baru
	 * -> notifikasi ada 
	 */


	public boolean pushNew(User user, String param, NotificationType type){
		try {
			Notification notif=new Notification();
			notif.setParam(param);
			notif.setUser(user);
			notif.setNotification_type(type);
			notif.setRead(false);
			notif.save();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public void setRead(int idNotif){
		try {
			Notification notif=Notification.find.byId(idNotif);
			notif.setRead(true);
			notif.update();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setRead(Notification notif){
		try {
			notif.setRead(true);
			notif.update();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	public Notification getSingleNotif(int idNotif){
		return Notification.find.byId(idNotif);
	}
	public Page<Notification> getListNotif(int show, int page, int user){
			
		try {
			Page<Notification> notifs=Notification.find.where()
			    		  				.eq("user", user)
								        .order().asc("timestamp")
								        .findPagingList(show)
								        .getPage(page);	
			for(Notification notif:notifs.getList()){
				if(!notif.isRead()){
					setRead(notif);
				}
			}
			return notifs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public int countUnread(int idUser){
		try {
			int result=Notification.find.where().eq("user", User.find.byId(idUser)).
												 eq("isRead",false).findRowCount();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	private int countNotifByUser(int idUser){
		try {
			int result=Notification.find.where().eq("user", User.find.byId(idUser))
												 .findRowCount();
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	public boolean delete(int idNotif){
		try {
			Notification notif=Notification.find.byId(idNotif);
			notif.delete();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean deleteAll(int idUser){
		try {
			List<Notification> result=Notification.find.where().eq("user", User.find.byId(idUser))
					 											.findList();
			Ebean.delete(result);
			return true;
		} catch (OptimisticLockException e) {
			e.printStackTrace();
			return false;
		}

	}


}
