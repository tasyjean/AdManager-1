package models.service.notification;

import play.i18n.Messages;

import com.avaje.ebean.annotation.EnumValue;

public enum NotificationType {
	/*
	 * Tipe notifikasi
	 * Notifikasi Iklan nonaktif //untuk pengiklan
	 * Iklan Aktif  //terutama untuk 
	 * Notifikasi saldo habis  //untuk pengiklan 
	 * notifikasi ada permintaan validasi pengisian saldo //untuk manager
	 * notifikasi saldo sudah divalidasi / terisi
	 * notifikasi untuk melihat laporan
	 * notifikasi 
	 * 
	 * Untuk administrator
	 * -> Notifikasi ada iklan baru
	 * -> notifikasi ada banner baru
	 * -> notifikasi ada user baru
	 * 
	 */
	
	//ketika iklan aktif
	@EnumValue("ACTIVE_ADS")
	ACTIVE_ADS{
		public String toString() {
			return Messages.get("notif.active_ads");
		}
	},
	
	//ketika Iklan nonaktif
	@EnumValue("NON_ACTIVE_ADS")
	NON_ACTIVE_ADS{
		public  String toString() {
			return Messages.get("notif.non_active_ads");
		}
	},
	//ketika saldo habis
	@EnumValue("EMPTY_DEPOSITO")
	EMPTY_DEPOSITO{
		public String toString() {
			return Messages.get("notif.empty_deposito");
		}
	},
	//please validasi
	@EnumValue("PLEASE_VALIDATE")
	PLEASE_VALIDATE{
		public String toString() {
			return Messages.get("notif.please_validate");
		}
	},
	//Ketika sudah divalidasi
	@EnumValue("VALIDATED")
	VALIDATED{
		public String toString() {
			return Messages.get("notif.validated");
		}
	},
	//Melihat laporan
	@EnumValue("SEE_REPORT")
	SEE_REPORT{
		public String toString() {
			return Messages.get("notif.see_report");
		}
	},
	//Ketika sudah divalidasi
	@EnumValue("NEW_CAMPAIGN")
	NEW_CAMPAIGN{
		public String toString() {
			return Messages.get("notif.new_campaign");
		}
	},	
	//Ketika sudah divalidasi
	@EnumValue("NEW_USER")
	NEW_USER{
		public String toString() {
			return Messages.get("notif.new_user");
		}
	},
	SHOULD_ACTIVE{
		public String toString(){
			return Messages.get("notif.should_active");
		}
	}
}
