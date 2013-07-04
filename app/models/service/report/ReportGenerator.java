package models.service.report;

import java.util.Date;

import models.data.Campaign;
import models.data.User;
import models.dataWrapper.report.ReportData;

public class ReportGenerator {

	public ReportData getReport(User user, Campaign campaign, Date from, Date to){
		if(campaign==null){
			return getReportAllCampaign(user, from, to);
		}else{
			return getReportByCampaign(user, campaign, from, to);
		}
	}
	
	private ReportData getReportAllCampaign(User user, Date from, Date to){
		return null;
	}
	private ReportData getReportByCampaign(User user, Campaign campaign, Date from, Date to){
		return null;
	}

}
