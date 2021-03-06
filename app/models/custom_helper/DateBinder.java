package models.custom_helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;

import play.Play;
import play.libs.Akka;

public class DateBinder {
	
//	String dateformat=Play.application().configuration().getString("format.date");
//	SimpleDateFormat format=new SimpleDateFormat(dateformat);
	private SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy"); //sementara

	public boolean isAfterToday(Date input){
		Calendar today=Calendar.getInstance();
		today.add(Calendar.DATE, -1); //minus sehari
		Date yasterday=today.getTime();
		return input.after(yasterday)? true : false; 
	}
	public  boolean isBeforeToday(Date input){
		Calendar today=Calendar.getInstance();
		today.add(Calendar.DATE,-1);
		Date yasterday=today.getTime();
		return input.before(yasterday)? true : false; 		
	}
	public int getDayLength(Date from,Date to){
		DateTime fromx=new DateTime(from);
		DateTime tox=new DateTime(to);
		
		return Days.daysBetween(fromx, tox).getDays();
	}
	public boolean todayIsBetween(Date from, Date to){
		Date today=new Date();
		if(today.after(from) && today.before(to)){
			return true;
		}
		return false;
	}
	public SimpleDateFormat getFormat(){
		return format;
	}
	
	//keperluan testing
	/*public static void main(String[] args){
		String  tanggal1="13/07/2013";
		String  tanggal2="23/06/2013";
		
		DateBinder binder=new DateBinder();
		Date tanggal1date = null;
		Date tanggal2date = null;
		try {
			tanggal1date=binder.format.parse(tanggal1);
			tanggal2date=binder.format.parse(tanggal2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println(binder.isAfterToday(tanggal1date));
		System.out.println(binder.isAfterToday(tanggal2date));
		System.out.println(binder.getDayLength(tanggal1date, tanggal2date));

	} */
}
