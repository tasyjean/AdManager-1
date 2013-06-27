package models.service.ads_delivery;

import play.mvc.Content;
import models.data.Banner;
import models.data.Zone;

public class AdSelector {

	/*
	 * Step
	 * -> Tentukan tipe zona
	 * -> Cari banner yang terhubung dengan zona, dan kondisi aktif
	 *    Jika ngga ada, tampilkan default
	 * -> JIka ada   	
	 * 	  -> cek apakah ada banner yang campaignnya eklusif dimana
	 * 		 -> Aktif
	 * 		 -> Masih berlaku  -> jika ngga, nonaktifkan, dan kirim notifikasi
	 * 		 -> Pemiliknya masih punya cukup dana, kalo ga cukup, nonaktifkan iklan, dan kirim notifikasi
	 * 			-> transaksi dihitung tiap tampilan pertama dalam 1 hari
	 * 			-> Artinya jika dalam sehari tidak ada impresi, maka tidak ada transaksi
	 * 		 -> jika memenuhi syarat, iklan tampil
	 * 		 -> jika ngga, populasikan banner non eklusif
 	 * 	  -> populasikan banner non eklusif
 	 * 	     -> pastikan semua memenuhi syarat
 	 * 			-> aktif
 	 * 			-> slot impressi/click masih ada (jika ngga, nonaktifkan, kirim notif)
 	 * 			-> pemiliknya masih punya cukup dana  (jika ngga, nonaktifkan, kirim notif)
 	 * 				-> untuk klik, transaksi dihitung tiap klik
 	 * 				-> untuk impressi, transaksi dihitung tiap kelipatan 1000
 	 * 			-> jika memenuhi syarat, maka tampilkan
 	 * 		-> jika yang memenuhi syarat lebih dari 1
 	 * 		   -> gunakan probabilitas campaign mana yang akan dipilih, melalui harga
 	 * 		   -> Jika yang terpilih memiliki 2 kandidat banner, maka banner yang ditampilkan yang bobotnya lebih tinggi
 	 * 		   -> jika bobotnya sama, pake random
 	 * 
	 */
	public Content get(int zone_id){

		
		final int zone=zone_id;
		Content content=new Content() {
			@Override
			public String contentType() {
				return "html/text";
			}
			@Override
			public String body() {
				String text="<h1>Test Konten</h1>+" +
						"<p>Ini adalah tes dari zona "+zone+" <p>";
				return text;
			}
		};
		return content;
	}
}
