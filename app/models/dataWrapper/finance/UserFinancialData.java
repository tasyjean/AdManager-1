package models.dataWrapper.finance;


import java.util.List;

import models.custom_helper.Angka;
import models.data.AdsTransaction;
import models.data.Banner;
import models.data.BannerPlacement;
import models.data.Campaign;
import models.data.Deposito;
import models.data.User;

import com.avaje.ebean.Page;

public class UserFinancialData {

	//menyimpan data dalam 1 user
	
	/*
	 * User  X: 
	 * Saldo sekarang
	 * Jumlah Transaksi Iklan
	 * Jumlah Pemasukan
	 * Total Transaksi
	 * Total Pengeluaran Iklan
	 * Tabel : Transaksi Terakhir
	 * 
	 * Hall Of Fame
	 * -> Saldo Tertinggi
	 * -> Jumlah Pengeluaran Iklan Tertinggi
	 * -> Jumlah 
	 *  /No/Tanggal/Jenis/Jumlah/Saldo Akhir/Keterangan/
	 */
	
	private int currentBalance; //balance saat ini
	private int adsTransaction_count; //jumlah iklan yang ditransaksikan
	private int paymentCount; //jumlah berapa kali pembayaran yang dilaukan
 	private long totalAdsSpending; //total biaya yang udah dikeluarkan untuk iklan
	private long totalDeposito; //jumlah total nilai uang yang pernah di depositokan
	private User user;
	
	public UserFinancialData(User user) {
		if(user!=null){
			this.user=user;
			setCurrentBalance();
			setAdsTransaction_count();
			setCurrentBalance();
			setPaymentCount();
			setTotalAdsSpending();
			setTotalDeposito();			
		}
	}
	public User getUser(){
		return user;
	}
	public int getCurrentBalance() {
		return currentBalance;
	}	
	public String getCurrentBalance_rupiah() {
		return Angka.toRupiah(currentBalance);
	}
	public void setCurrentBalance() {		
		this.currentBalance = user.getCurrent_balance();
	}
	public int getAdsTransaction_count() {
		return adsTransaction_count;
	}	
	public String getAdsTransaction_count_angka() {
		return Angka.toAngka(adsTransaction_count);
	}
	public void setAdsTransaction_count() {
		List<Campaign> campaign=Campaign.find.where().eq("id_user", user).findList();
		List<Banner> banner=Banner.find.where().in("campaign", campaign).findList();
		List<BannerPlacement> placement=BannerPlacement.find.where().in("banner", banner).findList();
		
		this.adsTransaction_count =AdsTransaction.find.where().in("bannerPlacement", placement).findList().size();
	}
	public int getPaymentCount() {
		return paymentCount;
	}
	public String getPaymentCount_angka() {
		return Angka.toAngka(paymentCount);
	}	
	public void setPaymentCount() {
		
		this.paymentCount = Deposito.find.where().eq("user", user).findList().size();
	}
	public long getTotalAdsSpending() {
	
		return totalAdsSpending;
	}
	public String getTotalAdsSpending_rupiah() {
	
		return Angka.toRupiah(totalAdsSpending);
	}	
	public void setTotalAdsSpending() {
		List<Campaign> campaign=Campaign.find.where().eq("id_user", user).findList();
		List<Banner> banner=Banner.find.where().in("campaign", campaign).findList();
		List<BannerPlacement> placement=BannerPlacement.find.where().in("banner", banner).findList();
		List<AdsTransaction> transactions=AdsTransaction.find.where().in("bannerPlacement", placement).findList();
		long result=0;
		for(AdsTransaction transaction:transactions){
			result=result+transaction.getAmount();
		}
		this.totalAdsSpending = result;
	}
	public long getTotalDeposito() {
		return totalDeposito;
	}
	public String getTotalDeposito_rupiah() {
		return Angka.toRupiah(totalDeposito);
	}	
	public void setTotalDeposito() {
		List<Deposito> paymentCounts = Deposito.find.where().eq("user", user).findList();
		long result=0;
		for(Deposito deposit:paymentCounts){
			result=result+deposit.getAmount();
		}
		this.totalDeposito = result;
	}

	
	
	
	
	

}
