package models.service.ads_delivery.tf_idf;

import java.util.List;

public class CountSimilarity {

	//Untuk mencari kesamaan dua buah string
	public double getSimilarityPoint(List<String> string1, List<String> string2){
	       int intersection = 0;
	       int union = string1.size() + string2.size();
	       for (int i=0; i<string1.size(); i++) {
	           Object pair1=string1.get(i);
	           for(int j=0; j<string2.size(); j++) {
	               Object pair2=string2.get(j);
	               if (pair1.equals(pair2)) {
	                   intersection++;
	                   string2.remove(j);
	                   break;
	               }
	           }
	       }
	       return (2.0*intersection)/union;
	}
}
