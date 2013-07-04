package models.service.finance;

import models.data.User;

public enum HighScoreEnum {
/*
 * Saldo Tertinggi
 * -> Jumlah Pengeluaran Iklan Tertinggi
 * -> Jumlah transaksi terbanyak
 *  private int currentBalance; //balance saat ini
	private int adsTransaction_count; //jumlah iklan yang ditransaksikan
	private int paymentCount; //jumlah berapa kali pembayaran yang dilaukan
 	private long totalAdsSpending; //total biaya yang udah dikeluarkan untuk iklan
	private long totalDeposito; //jumlah total nilai uang yang pernah di depositokan
	private User user;
 */
	HIGHEST_BALANCE,
	HIGHEST_TOTAL_ADS_SPENDING,
	HIGHEST_ADS_TRANSACTION_COUNT,
	HIGHEST_TOTAL_DEPOSITO,
	HIGHEST_PAYMENT_COUNT,
}
