package models.custom_helper;

import java.net.MalformedURLException;
import java.net.URL;

/*convert teks otomatis ke link */
public class ToLink {

	public static String convert(String text){
		try {
			URL url=new URL(text);
			return surroundHREF(url.toString());
		} catch (MalformedURLException e) {
			return text;
		}
	}	
	private static String surroundHREF(String text){
		return "<a href='"+text+"'>"+text+"</a>";
	}

}
