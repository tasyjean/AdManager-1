package models.service.notification;

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
			return super.toString();
		}
	},
	
	//ketika Iklan nonaktif
	@EnumValue("NON_ACTIVE_ADS")
	NON_ACTIVE_ADS{
		public  String toString() {
			return "";
		}
	},
	//ketika saldo habis
	@EnumValue("EMPTY_DEPOSITO")
	EMPTY_DEPOSITO{
		public String toString() {
			return super.toString();
		}
	},
	//please validasi
	@EnumValue("PLEASE_VALIDATE")
	PLEASE_VALIDATE{
		public String toString() {
			return super.toString();
		}
	},
	//Ketika sudah divalidasi
	@EnumValue("VALIDATED")
	VALIDATED{
		public String toString() {
			return super.toString();
		}
	},
	//Melihat laporan
	@EnumValue("SEE_REPORT")
	SEE_REPORT{
		public String toString() {
			return super.toString();
		}
	},
	//Ketika sudah divalidasi
	@EnumValue("NEW_CAMPAIGN")
	NEW_CAMPAIGN{
		public String toString() {
			return super.toString();
		}
	},	
	//Ketika sudah divalidasi
	@EnumValue("NEW_BANNER")
	NEW_BANNER{
		public String toString() {
			return super.toString();
		}
	},
	//Ketika sudah divalidasi
	@EnumValue("NEW_USER")
	NEW_USER{
		public String toString() {
			return super.toString();
		}
	},
}
