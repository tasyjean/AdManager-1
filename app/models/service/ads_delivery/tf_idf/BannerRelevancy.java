package models.service.ads_delivery.tf_idf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import models.data.Banner;
import models.data.BannerPlacement;

public class BannerRelevancy {
	StringExtractor extractor=new StringExtractor();
	TextCleaner cleaner=new TextCleaner();
	GetKeyword keyword=new GetKeyword();
	Counter counter=new Counter();
	CountSimilarity similarity=new CountSimilarity();
	SummarizePage summarizePage =new SummarizePage(extractor,cleaner,keyword,counter);

	
	public void filterRelevantBanner_banner(List<Banner> list, String url, int bannerCount){
		List<String> keyword=null;
		try {
			keyword = summarizePage.summarize(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (keyword!=null) {
			int i = 0;
			List<ValueTable> table = new ArrayList<ValueTable>();
			for (Banner banner : list) {
				List<String> bannerWord = extractBannerWord(banner);
				double value = similarity.getSimilarityPoint(keyword,
						bannerWord);
				table.add(new ValueTable(i, value));
				i++;
			}
			//sorting
			Collections.sort(table, new Comparator<ValueTable>() {
				@Override
				public int compare(ValueTable o1, ValueTable o2) {
					if (o2.value > o1.value) {
						return 1;
					} else {
						return 0;
					}
				}
			});
			//ekstraksi pilih 5 dengan similarity tertinggi
			List<Banner> newBannerList = new ArrayList<Banner>();
			for (int z = 0; z < bannerCount; z++) {
				newBannerList.add(list.get(table.get(z).index));
			}
			//inisialisasi
			list = newBannerList;
		}
	}
	
	public void filterRelevantBanner(List<BannerPlacement> list, String url, int bannerCount){
		
		List<String> keyword=null;
		try {
			keyword = summarizePage.summarize(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (keyword!=null) {
			int i = 0;
			List<ValueTable> table = new ArrayList<ValueTable>();
			for (BannerPlacement bannerPlacement : list) {
				List<String> bannerWord = extractBannerWord(bannerPlacement
						.getBanner());
				double value = similarity.getSimilarityPoint(keyword,
						bannerWord);
				table.add(new ValueTable(i, value));
				i++;
			}
			//sorting
			Collections.sort(table, new Comparator<ValueTable>() {
				@Override
				public int compare(ValueTable o1, ValueTable o2) {
					if (o2.value > o1.value) {
						return 1;
					} else {
						return 0;
					}
				}
			});
			//ekstraksi pilih 5 dengan similarity tertinggi
			List<BannerPlacement> newPlacement = new ArrayList<BannerPlacement>();
			for (int z = 0; z < bannerCount; z++) {
				newPlacement.add(list.get(table.get(z).index));
			}
			//inisialisasi
			list = newPlacement;
		}
	}
	private List<String> extractBannerWord(Banner banner){
		String word=banner.getAlt_text()+banner.getContent_text()+banner.getDescription()
					+banner.getName()+banner.getTitle()+banner.getCampaign().getCampaignName()
					+banner.getCampaign().getDescription();
		word=cleaner.clean(word);
		String[] wordArray=word.split(" ");
		List<String> result=new ArrayList<String>();
		for(String singleword:wordArray){
			result.add(singleword);
		}
		
		return result;
	}
	
	 class ValueTable{
		int index;
		double value;
		public ValueTable(int index, double value) {
			super();
			this.index = index;
			this.value = value;
		}
		
	}
}
