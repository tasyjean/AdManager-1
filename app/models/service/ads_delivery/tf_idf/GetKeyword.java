package models.service.ads_delivery.tf_idf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/*
 * Kelas Untuk mendapatkan Keyword
 */
public class GetKeyword {

	//daftar kata yang ngga dipakai buat keyword
	final String[] stopList={"di","ke","apa","dengan","dan","ini","itu",
			 "pun","agar","akan","dicari","juga","oleh",
			 "pada","yang","bahwa","dapat","namun","untuk",
			 "dengan","kepada",""," ",".","bagaimana",""};  
	public List<String> get(String input){
		String[] words = input.split(" ");
		
		List<String> wordList=new ArrayList<String>();
		for(String word:words){
			boolean valid=true;
			for(String stop:stopList){
				if(word.equals(stop)){
					valid=false;
				}
			}
			if(valid && !wordList.contains(word)){
				word=word.replace(".","").replace(" ", "");
				if(!wordList.contains(word)){
					wordList.add(word);					
				}
			}
		}
		return wordList;
	}
	

}
