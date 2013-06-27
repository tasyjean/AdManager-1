package models.service.notification;

import java.util.List;

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
	
	public void pushNew(User user, String param, NotificationType type){
		
	}
	public void setRead(int idNotif){
		
	}
	public Notification getSingleNotif(int idNotif){
		return null;
	}
	public List<Notification> getListNotif(int show, int page, int user){
		return null;
	}
	public int countUnread(int idUser){
		return 0;
	}
	private int countNotifByUser(int idUser){
		return 0;
	}
	public void delete(int idNotif){
		
	}


}
