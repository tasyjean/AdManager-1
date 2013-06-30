package models.service.notification;

import java.util.List;

import javax.persistence.OptimisticLockException;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;

import models.data.Notification;
import models.data.User;
import models.data.UserRole;

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
	 *
	 *-> Aturan param
	 *	-> subyek|obyek
	 *  subyek : pelakunya siapa, atau siapa yang melakukan
	 *  obyek : obyek yang dilakukan, berupa id dan ini nanti yang dijadiin link
	 *	
	 * list -> 
	 * 
	 *	notif.active_ads=Campaign |nama_campaign|  diaktifkan   (nama campaign, id campaign) untuk pemilik campaign
		notif.non_active_ads=Campaign |nama_campaign|  di non aktifkan (nama campaign, id campaign) untuk pemilik campaign
		notif.empty_deposito=Saldo tidak mencukupi, silahkan isi kembali ("", "") atau kosong, untuk pemilik saldo
		notif.please_validate=Ada pembayaran baru dari |nama_user| yang perlu divalidasi (nama_user, id validasi) untuk management
		notif.validated=Pembayaran senilai |jumlah| telah divalidasi (jumlah, id pembayaran) untuk pemilik pembayaran
		notif.see_report= Silahkan lihat laporan iklan  (jumlah, id pembayaran) untuk advertiser
		notif.new_campaign=|nama_user| telah membuat campaign baru  (nama user, id user) untuk administrator dan manager
		notif.new_user=Pengguna baru bernama |nama_user| telah terdaftar  (nama user, id user) untuk administrator
		notif.should_active=Campaign |nama_campaign| sudah memasuki masa mulai, namun belum diaktifkan, silahkan aktifkan atau mundurkan waktu mulai  (nama_campaign, id_campaign)
	*
	 */

	
	public boolean pushNew(NotifItem item){
		try {
			Notification notif=new Notification();
			notif.setParam(item.getParam());
			notif.setUser(item.getUser());
			notif.setNotification_type(item.getType());
			notif.setRead(false);
			notif.save();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean isDuplicate(NotifItem item){
		Notification n=Notification.find.where().eq("user",item.getUser()).
												 eq("param", item.getParam()).
												 eq("notification_type", item.getType().name()).findUnique();
		return (n!=null)? true:false;
	}
	//untuk push notif massal ke tipe pengguna
	public void pushNew(String to, NotifItem item){
		try {
			if(to.equals("ADMINISTRATOR")){
				UserRole admin=UserRole.find.where().eq("name","administrator").findUnique();
				List<User> administrator=User.find.where().eq("role", admin).findList();
				for(User user:administrator){
					item.setUser(user);
					pushNew(item);
				}
			}else if(to.equals("ADVERTISER")){
				UserRole advert=UserRole.find.where().eq("name","advertiser").findUnique();
				List<User> advertiser=User.find.where().eq("role", advert).findList();
				for(User user:advertiser){
					item.setUser(user);
					pushNew(item);
				}			
			}else if(to.equals("MANAGER")){
				UserRole manager=UserRole.find.where().eq("name","manager").findUnique();
				List<User> management=User.find.where().eq("role", manager).findList();
				for(User user:management){
					item.setUser(user);
					pushNew(item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	public Page<Notification> getListNotif(int show, int page, User user){
			
		try {
			Page<Notification> notifs=Notification.find.where()
			    		  				.eq("user", user)
								        .order().asc("timestamp")
								        .findPagingList(show)
								        .getPage(page);	
//			for(Notification notif:notifs.getList()){
//				if(!notif.isRead()){
//					setRead(notif);
//				}
//			}
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
