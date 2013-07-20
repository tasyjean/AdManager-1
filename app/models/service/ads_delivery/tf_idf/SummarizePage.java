package models.service.ads_delivery.tf_idf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class SummarizePage {


	StringExtractor extractor;
	TextCleaner cleaner;
	GetKeyword keyword;
	Counter counter;
	
	
	public SummarizePage(StringExtractor extractor, TextCleaner cleaner,
			GetKeyword keyword, Counter counter) {
		super();
		this.extractor = extractor;
		this.cleaner = cleaner;
		this.keyword = keyword;
		this.counter = counter;
	}
	public List<String> summarize(String page) throws Exception{
		long  x=System.currentTimeMillis();
		//ekstrak teks dari web page
		String text=extractor.extract(page);
		System.out.println("Retrieve Text :"+(System.currentTimeMillis()-x)+" milisecond");
		
		long y=System.currentTimeMillis();
		//clean text
		text=cleaner.clean(text);
		System.out.println("Clean Text :"+(System.currentTimeMillis()-y)+" milisecond");

		//buat daftar nama terms
		long z=System.currentTimeMillis();
		List<String> term=keyword.get(text);
		System.out.println("Generate Terms :"+(System.currentTimeMillis()-z)+" milisecond ("+term.size()+" term)");
		//inisialisasi TFIDF
		long q=System.currentTimeMillis();		
		List<TFIDF> idfList=new ArrayList<TFIDF>();
		int i = 0;
		//Isi kan tiap tiap Term dan hitung TFIDFnya
		counter.setText(text);
		for(String word:term){
			TFIDF tfidf=new TFIDF();
			tfidf.setTerms(word);
			counter.countIDfVariable(tfidf);
			counter.countTfVariable(tfidf);
			idfList.add(tfidf);
		}
		System.out.println("Count IDF :"+(System.currentTimeMillis()-q)+" milisecond");
		
		long v=System.currentTimeMillis();

		//urutkan berdasarkan size TFIDF
		Collections.sort(idfList, new Comparator<TFIDF>() {
			@Override
			public int compare(TFIDF o1, TFIDF o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		//delete NaN Value
		for(Iterator<TFIDF> itr = idfList.iterator();itr.hasNext();)
		{
			TFIDF element=itr.next();
			if(element.getValue().isNaN()){
				itr.remove();
			}
		}
		//ekstrak 30 teratas
		List<String> result=new ArrayList<String>();
		for(TFIDF tfidf:getTopTerm(idfList)){
			result.add(tfidf.getTerms());
		}
		//return
		System.out.println("Sorting dan seleksi :"+(System.currentTimeMillis()-v)+" milisecond");

		System.out.println("\n Waktu proses total :"+(System.currentTimeMillis()-x)+" milisecond");

		return result;
	}
	private  List<TFIDF> getTopTerm(List<TFIDF> list){
		if(list.size()>30){
			return list.subList(0, 30);
		}else{
			return list;
		}
		
	}

}
