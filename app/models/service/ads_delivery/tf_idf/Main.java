package models.service.ads_delivery.tf_idf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeSet;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StringExtractor extractor=new StringExtractor();
		TextCleaner cleaner=new TextCleaner();
		GetKeyword keyword=new GetKeyword();
		Counter counter=new Counter();
		CountSimilarity similarity=new CountSimilarity();
		SummarizePage summarizePage =new SummarizePage(extractor,cleaner,keyword,counter);
		String[] pages={//"http://arstechnica.com/science/2013/07/making-heavy-elements-by-colliding-neutron-stars/",
						//"http://www.teknimo.com/2013/01/menggali-lebih-jauh-data-facebook-dengan-layanan-wolfram-alpha/",
						"http://www.teknimo.com/2013/04/kini-seperempat-perangkat-android-menggunakan-versi-jelly-bean/"};
		List<String> result = null;
		for(String page:pages){
			System.out.println("===============================================================");
			System.out.println("Page : "+page);
			try {
				result=summarizePage.summarize(page);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Keyword summary : ");
			for(String res:result){
				System.out.print(res+", ");
			}
			System.out.println();
		}
		String[] string1={"", "", "data", "corona", "", "sistem", "hadoop", "artikel", "seperti"
				, "membuat", "berita", "gadget", "merupakan", "cepat", "internet", "software", "media", "dibuat", "jumlah", "apache"};
		List<String> stringList1=new ArrayList<String>();
		List<String> stringList2=new ArrayList<String>();
		List<String> stringList3=new ArrayList<String>();
		List<String> stringList4=new ArrayList<String>();
		List<String> stringList5=new ArrayList<String>();
		
		for(String string:string1){
			stringList1.add(string);
		}
		stringList2.add("gadget");
		stringList2.add("telepon selular");
		stringList2.add("ponsel");
		stringList2.add("smartphone");
		
		stringList3.add("data mining");
		stringList3.add("big data");
		stringList3.add("apache");
		stringList3.add("hadoop");	
		
		stringList4.add("email");
		stringList4.add("google");
		stringList4.add("search engine");
		stringList4.add("kaskus");			
		
		System.out.println(similarity.getSimilarityPoint(result, stringList1));
		System.out.println(similarity.getSimilarityPoint(result, stringList2));
		System.out.println(similarity.getSimilarityPoint(result, stringList3));
		System.out.println(similarity.getSimilarityPoint(result, stringList4));
		System.out.println(similarity.getSimilarityPoint(result, stringList5));
	}
	
}
