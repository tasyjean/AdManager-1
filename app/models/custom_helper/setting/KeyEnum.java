package models.custom_helper.setting;

import com.avaje.ebean.annotation.EnumValue;

public enum KeyEnum {

	/*
	 * Daftar Pengaturan
	 * -> Harga iklan dasar
	 * 	  -> Per klik
	 * 	  -> Per 1000 impressi
	 * 	  -> Per Hari
	 * 	  -> tingkat faktor diskon
	 * -> Default iklan untuk masing-masing ukuran zona
	 * -> 
	 */
	@EnumValue("BASE_PRICE_CLICK")
	BASE_PRICE_CLICK{

		public String toString() {
			return "Biaya Tiap Klik";
	}},
	@EnumValue("BASE_PRICE_IMPRESSION")
	BASE_PRICE_IMPRESSION{

		public String toString() {
			return "Biaya Tiap Impressi";
	}},
	@EnumValue("DISCOUNT_FACTOR")
	DISCOUNT_FACTOR{

		public String toString() {
			return "Faktor Diskon Biaya Iklan";
	}},
	@EnumValue("BASE_PRICE_DAYS")
	BASE_PRICE_DAYS{

		public String toString() {
			return "Biaya Tiap Hari";
	}},
	@EnumValue("PAYMENT_INSTRUCTION")
	PAYMENT_INSTRUCTION{
		public String toString(){
			return "Instruksi Pembayaran";
		}
	},
	@EnumValue("ABOUT_PAGE")
	ABOUT_PAGE{
		public String toString(){
			return "Halaman Tentang";
		}
	},
	@EnumValue("CONTACT_PAGE")
	CONTACT_PAGE{
		public String toString(){
			return "Halaman Kontak";
		}
	},
	@EnumValue("HELP_PAGE")
	HELP_PAGE{
		public String toString(){
			return "Halaman Bantuan";
		}
	}
	

	

}
