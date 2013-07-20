package models.service.ads_delivery.tf_idf;

public class TFIDF implements Comparable<TFIDF> {

    private Number totalOccurences;
    private Number totalTermsInDocument;
    private Number totalDocument;
    private Number numberDocumentWithTerms;
    private String terms;
    public TFIDF(){
    	
    }
    public TFIDF(Number totalOccurences, Number totalTermsInDocument, 
    			  Number totalDocument, Number numberDocumentWithTerms, String terms) {
    		this.totalOccurences = totalOccurences;
            this.totalTermsInDocument = totalTermsInDocument;
            this.totalDocument = totalDocument;
            this.numberDocumentWithTerms = numberDocumentWithTerms;
            this.terms=terms;
    }
    /*
     * Min Value untuk mencegah pembagian dengan 0
     */
    public Float getValue(){
            float tf = totalOccurences.floatValue() / (Float.MIN_VALUE + totalTermsInDocument.floatValue());
            float idf = (float) Math.log10(totalDocument.floatValue() / (Float.MIN_VALUE + numberDocumentWithTerms.floatValue()));
            return (tf * idf);
    }
    
    public int getNumOfOccurrences() {
            return this.totalOccurences.intValue();
    }
    public String toString() {
            return this.getValue().toString();
                    
    }
    @Override
    public int compareTo(TFIDF o) {
            return (int) ((this.getValue() - o.getValue()) * 100);
    }
	public Number getTotalOccurences() {
		return totalOccurences;
	}
	public void setTotalOccurences(Number totalOccurences) {
		this.totalOccurences = totalOccurences;
	}
	public Number getTotalTermsInDocument() {
		return totalTermsInDocument;
	}
	public void setTotalTermsInDocument(Number totalTermsInDocument) {
		this.totalTermsInDocument = totalTermsInDocument;
	}
	public Number getTotalDocument() {
		return totalDocument;
	}
	public void setTotalDocument(Number totalDocument) {
		this.totalDocument = totalDocument;
	}
	public Number getNumberDocumentWithTerms() {
		return numberDocumentWithTerms;
	}
	public void setNumberDocumentWithTerms(Number numberDocumentWithTerms) {
		this.numberDocumentWithTerms = numberDocumentWithTerms;
	}
	public String getTerms() {
		return terms;
	}
	public void setTerms(String terms) {
		this.terms = terms;
	}
    

}
