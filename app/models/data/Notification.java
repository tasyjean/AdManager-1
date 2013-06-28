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
	//mendapatkan teks yang udah di parse
	/*
	 * Aturan : 
	 * 	 *	notif.active_ads=Campaign |nama_campaign|  diaktifkan   (nama campaign, id campaign) untuk pemilik campaign
		notif.non_active_ads=Campaign |nama_campaign|  di non aktifkan (nama campaign, id campaign) untuk pemilik campaign
		notif.empty_deposito=Saldo tidak mencukupi, silahkan isi kembali ("", "") atau kosong, untuk pemilik saldo
		notif.please_validate=Ada pembayaran baru dari |nama_user| yang perlu divalidasi (nama_user, id validasi) untuk management
		notif.validated=Pembayaran senilai |jumlah| telah divalidasi (jumlah, id pembayaran) untuk pemilik pembayaran
		notif.see_report= Silahkan lihat laporan iklan  (jumlah, id pembayaran) untuk advertiser
		notif.new_campaign=|nama_user| telah membuat campaign baru  (nama user, id user) untuk administrator dan manager
		notif.new_user=Pengguna baru bernama |nama_user| telah terdaftar  (nama user, id user) untuk administrator
		notif.should_active=Campaign |nama_campaign| sudah memasuki masa mulai, namun belum diaktifkan, silahkan aktifkan atau mundurkan waktu mulai  (nama_campaign, id_campaign)

	 */
	public String getParsedText(){
		String text=notification_type.toString();
		String replaceParam=param.split(":")[0];
		switch (notification_type) {
			case ACTIVE_ADS:text=text.replace("|nama_campaign|", replaceParam);break;
			case EMPTY_DEPOSITO:break;
			case NEW_CAMPAIGN:text=text.replace("|nama_user|", replaceParam);break;
			case NEW_USER:text=text.replace("|nama_user|", replaceParam);break;
			case NON_ACTIVE_ADS:text=text.replace("|nama_campaign|", replaceParam);break;
			case PLEASE_VALIDATE:text=text.replace("|nama_user|", replaceParam);break;
			case SEE_REPORT:break;
			case VALIDATED:text=text.replace("|jumlah|", replaceParam);break;
			case SHOULD_ACTIVE:text=text.replace("|nama_campaign|", replaceParam);break;
		default:
			break;
		}
		
		return text;
	}
	

	
}
