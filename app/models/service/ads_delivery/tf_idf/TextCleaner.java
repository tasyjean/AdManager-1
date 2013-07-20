package models.service.ads_delivery.tf_idf;

public class TextCleaner {

	final String[] stopList={"di","ke","apa","dengan","dan","ini","itu",
							 "pun","agar","akan","dicari","juga","oleh",
							 "pada","yang","bahwa","dapat","namun","untuk",
							 "dengan","kepada","","tidak","supaya","bagaimana",
							 "mengapa","dari","dalam","lebih","makin", "masih", 
							 "sudah", "saat", "bisa","hingga","apr","ketika"};
	
	public String clean(String input){
		input=lowerCase(input);
		input=removeStopWords(input);
		input=removePunctation(input);
		input=removeNumber(input);
		input=removeWhiteSpace(input);
		input=removeMultipleTitik(input);
		return input;
	}
	private String removeStopWords(String input){
		
		for(String word:stopList){
			input=input.replace(" "+word+" ", " ");
		}
		return input;
	}
	private String removeWhiteSpace(String input){
		return input.trim().replaceAll(" +", " ");
	}
	private String removePunctation(String input){
		input=input.replaceAll("[&,:‘’”•/!|?()\\-]", "");
		return input;
	}
	private String removeNumber(String input){
		input=input.replaceAll("[0-9]", "");
		return input;
	}	
	private String removeMultipleTitik(String input){
		input=input.replace("........", ".");
		input=input.replace(".......", ".");
		input=input.replace("......", ".");
		input=input.replace(".....", ".");
		input=input.replace("....", ".");
		input=input.replace("...", ".");
		input=input.replace("..", ".");
		return input;
	}	
	private String lowerCase(String input){
		return input.toLowerCase();
	}

}
