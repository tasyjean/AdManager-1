package models.service.ads_delivery.tf_idf;

public class Counter {
	
	String[] documents;
	String text;
	String[] words;
	int totalDocument;
	int totalTermsInDocument;
	public void setText(String text){
		this.text = text;
		this.words = text.split(" ");
		this.documents = text.split("\\.");
		this.totalDocument=documents.length;
		this.totalTermsInDocument=words.length;
	}
	public void countIDfVariable(TFIDF input){
		String terms=input.getTerms();
		int numberDocumentWithTerms=0;
		for(String document:documents){
			if(document.contains(terms)){
				numberDocumentWithTerms++;
			}
		}
		input.setTotalDocument(totalDocument);
		input.setNumberDocumentWithTerms(numberDocumentWithTerms);
	}
	public void countTfVariable(TFIDF input){
	    int totalOccurences=0;  //tf
	    String terms=input.getTerms();	    
	    for(String word:words){
	    	if(word.equals(terms)){
	    		totalOccurences++;
	    	}
	    }
	    input.setTotalOccurences(totalOccurences);
	    input.setTotalTermsInDocument(totalTermsInDocument);
	}

}
