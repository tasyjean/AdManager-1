package models.custom_helper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Angka {
	public static NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
	public static DecimalFormat format= (DecimalFormat)nf;
	
	
	public static String toRupiah(int input)
	{
		format.applyPattern("###,###.###");
		return "Rp "+format.format((long)input)+",00";
	}
	
	public static String toRupiah(double input)
	{
		format.applyPattern("###,###.###");
		return "Rp "+format.format((long)input)+",00";
	}
	public static String toAngka(int input){
		format.applyPattern("###,###.###");
		return format.format((long)input);

	}
	
}
