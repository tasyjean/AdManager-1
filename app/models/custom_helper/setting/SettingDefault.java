package models.custom_helper.setting;

import play.Logger;
import play.i18n.Messages;
import models.data.SystemPreferences;

public class SettingDefault {

	final String DEFAULT_CLICK="2000";
	final String DEFAULT_IMPRESSION="2000";
	final String DISCOUNT_FACTOR="0.1";
	final String DEFAULT_DAYS="8000";

	/*
		 * 	BASE_PRICE_CLICK,
		BASE_PRICE_IMPRESSION,
		BASE_PRICE_DAYS,
		DISCOUNT_FACTOR,
		
		/* iklan masukin ke banner type aja 
		DEFAULT_ADS_RECTANGLE,
		DEFAULT_ADS_WIDE_SKYCRAPPER,
		DEFAULT_ADS_MEDIUM_RECTANGLE,
		DEFAULT_ADS_LEADERBOARD,
	 */
	
	public void setDefault(KeyEnum keyEnum){
		switch (keyEnum) {
			case BASE_PRICE_CLICK	  : setBase_price_click();
								        break;
			case BASE_PRICE_DAYS	  : setBase_price_days();
								        break;
			case BASE_PRICE_IMPRESSION: setBase_price_impression();
			  						    break;
			case DISCOUNT_FACTOR 	  :setDiscount_factor();
										break;
			case PAYMENT_INSTRUCTION : setPayment_instruction();
									   break;
			default:
				break;
		}
	}
	public void setAll(){
		setBase_price_click();
		setBase_price_days();
		setDiscount_factor();
		setBase_price_impression();
		setPayment_instruction();
	}
	private void setPayment_instruction(){
		SystemPreferences sysPref=SystemPreferences.find.
				where().
				eq("key", KeyEnum.PAYMENT_INSTRUCTION.name()).
				findUnique();		
		boolean isNull=false;
		if(sysPref==null){
			sysPref=new SystemPreferences();
			isNull=true;
		}
		String input="<h5>Transfer via bank<h5><ol><li>Transfer dulu dengan jumlah sesuai dengan " +
					 "estimasi harga campaign, ke rekening xxx</li><li>konfirmasikan pembayaran melalui " +
					 "menu konfirmasi, isi data dengan tepat untuk mempermudah verifikasi</li><li>Tunggu hingga" +
					 " konfirmasi diverifikasi</li><li>Ketika saldo sudah masuk ke sistem, maka iklan siap tampil</li></ol>";
		Logger.debug(input);
		
		sysPref.setKey(KeyEnum.PAYMENT_INSTRUCTION.name());
		sysPref.setName(KeyEnum.BASE_PRICE_CLICK.toString());
		sysPref.setValue(input +" <br><br> Selamat beriklan!");
		sysPref.setDescription(KeyEnum.PAYMENT_INSTRUCTION.toString());
		
		if(isNull){
			sysPref.save();
		}else{
			sysPref.update();
		}
	}
	private void setBase_price_click(){
		SystemPreferences sysPref=SystemPreferences.find.
								where().
								eq("key", KeyEnum.BASE_PRICE_CLICK.name()).
								findUnique();
		boolean isNull=false;
		if(sysPref==null){
			sysPref=new SystemPreferences();
			isNull=true;
		}
		sysPref.setKey(KeyEnum.BASE_PRICE_CLICK.name());
		sysPref.setName(KeyEnum.BASE_PRICE_CLICK.toString());
		sysPref.setValue(DEFAULT_CLICK);
		sysPref.setDescription("Harga Dasar Setiap Klik Iklan");
		
		if(isNull){
			sysPref.save();
		}else{
			sysPref.update();
		}
	}
	
	private void setBase_price_impression(){
		SystemPreferences sysPref=SystemPreferences.find.
									where().
									eq("key", KeyEnum.BASE_PRICE_IMPRESSION.name()).
									findUnique();
		boolean isNull=false;
		
		if(sysPref==null){
			sysPref=new SystemPreferences();
			isNull=true;
		}
		
		sysPref.setKey(KeyEnum.BASE_PRICE_IMPRESSION.name());
		sysPref.setName(KeyEnum.BASE_PRICE_IMPRESSION.toString());
		sysPref.setValue(DEFAULT_IMPRESSION);
		sysPref.setDescription("Harga Dasar Setiap 1000 Impressi iklan");
		
		if(isNull){
			sysPref.save();
		}else{
			sysPref.update();
		}		
	}
	private void setBase_price_days(){
		SystemPreferences sysPref=SystemPreferences.find.
				where().
				eq("key", KeyEnum.BASE_PRICE_DAYS.name()).
				findUnique();
		boolean isNull=false;
		if(sysPref==null){
			sysPref=new SystemPreferences();
			isNull=true;
		}
		
		sysPref.setKey(KeyEnum.BASE_PRICE_DAYS.name());
		sysPref.setName(KeyEnum.BASE_PRICE_DAYS.toString());
		sysPref.setValue(DEFAULT_DAYS);
		sysPref.setDescription("Harga Dasar Setiap Iklan Tampil Dalam 1 Hari");
		
		if(isNull){
			sysPref.save();
		}else{
			sysPref.update();
		}		
	}
	private void setDiscount_factor(){
		SystemPreferences sysPref=SystemPreferences.find.
				where().
				eq("key", KeyEnum.DISCOUNT_FACTOR.name()).
				findUnique();
		boolean isNull=false;
		if(sysPref==null){
			sysPref=new SystemPreferences();
			isNull=true;
		}
		
		sysPref.setKey(KeyEnum.DISCOUNT_FACTOR.name());
		sysPref.setName(KeyEnum.DISCOUNT_FACTOR.toString());
		sysPref.setValue(DISCOUNT_FACTOR);
		sysPref.setDescription("Merupakan faktor diskon harga jika iklan tampil lebih dari 1 bulan");
		
		if(isNull){
			sysPref.save();
		}else{
			sysPref.update();
		}	
	}
}
