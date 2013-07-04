package models.service.finance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.avaje.ebean.Page;

import models.data.AdsTransaction;
import models.data.Banner;
import models.data.BannerPlacement;
import models.data.Campaign;
import models.data.Deposito;
import models.data.Notification;
import models.data.User;
import models.data.UserRole;
import models.data.enumeration.RoleEnum;
import models.dataWrapper.finance.UserFinancialData;

public class TransactionFetcher {

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

	public UserFinancialData getUserFinancialData(User user){
		return new UserFinancialData(user);
	}
	
	public Page<AdsTransaction> getAdsTransaction(User user, int pageSize,int page){
		try {
			List<Campaign> campaign=Campaign.find.where().eq("id_user", user).findList();
			List<Banner> banner=Banner.find.where().in("campaign", campaign).findList();
			List<BannerPlacement> placement=BannerPlacement.find.where().in("banner", banner).findList();
			
			Page<AdsTransaction> transactions=AdsTransaction.find.where()
											  .in("bannerPlacement",placement)
											  .order().desc("timestamp")
											  .findPagingList(pageSize)
											  .getPage(page);
			return transactions;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public Page<Deposito> getDeposito(User user, int pageSize,int page){
		try {
			Page<Deposito> deposito=Deposito.find.where()
									.eq("user",user)
									.order().desc("timestamp")
									.findPagingList(pageSize)
									.getPage(page);
			return deposito;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<UserFinancialData> getHighScore(HighScoreEnum enums){
		List<User> users=getUser();
		List<UserFinancialData> financialData=new ArrayList<UserFinancialData>();
		for(User user:users){
			financialData.add(new UserFinancialData(user));
		}
		
		switch (enums) {
		case HIGHEST_ADS_TRANSACTION_COUNT:financialData=sortByAdsTransactionCount(financialData);break;
		case HIGHEST_BALANCE:financialData=sortByGetCurrentBalance(financialData);break;
		case HIGHEST_PAYMENT_COUNT:financialData=sortByPaymentCount(financialData);break;
		case HIGHEST_TOTAL_ADS_SPENDING:financialData=sortByTotalAdsSpending(financialData);break;
		case HIGHEST_TOTAL_DEPOSITO:financialData=sortByTotalDeposito(financialData);break;
		default:
			break;
		}
		return financialData;
	}
	private List<UserFinancialData> sortByAdsTransactionCount(List<UserFinancialData> list){
		Collections.sort(list, new Comparator<UserFinancialData>() {
			public int compare(UserFinancialData o1, UserFinancialData o2) {
				return o2.getAdsTransaction_count()-o1.getAdsTransaction_count();
			}
		});
		return list;
	}
	private List<UserFinancialData> sortByGetCurrentBalance(List<UserFinancialData> list){
		Collections.sort(list, new Comparator<UserFinancialData>() {
			public int compare(UserFinancialData o1, UserFinancialData o2) {
				return o2.getCurrentBalance()-o1.getCurrentBalance();
			}
		});
		return list;
	}
	private List<UserFinancialData> sortByPaymentCount(List<UserFinancialData> list){
		Collections.sort(list, new Comparator<UserFinancialData>() {
			public int compare(UserFinancialData o1, UserFinancialData o2) {
				return o2.getPaymentCount()-o1.getPaymentCount();
			}
		});
		return list;
	}
	private List<UserFinancialData> sortByTotalAdsSpending(List<UserFinancialData> list){
		Collections.sort(list, new Comparator<UserFinancialData>() {
			public int compare(UserFinancialData o1, UserFinancialData o2) {
				return (int) (o2.getTotalAdsSpending()-o1.getTotalAdsSpending());
			}
		});
		return list;
	}
	private List<UserFinancialData> sortByTotalDeposito(List<UserFinancialData> list){
		Collections.sort(list, new Comparator<UserFinancialData>() {
			public int compare(UserFinancialData o1, UserFinancialData o2) {
				return (int)(o2.getTotalDeposito()-o1.getTotalDeposito());
			}
		});
		return list;
	}	
	private List<User> getUser(){
		UserRole advertiser_role=UserRole.find.where().eq("name", RoleEnum.ADVERTISER).findUnique();
		return User.find.where().eq("role", advertiser_role).findList();
	}
}
