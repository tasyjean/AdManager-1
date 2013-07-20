package models.service.ads_delivery.tf_idf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;

public class StringExtractor {

	public String extract(String url_string) throws Exception{
		String content = "null";
            URL url = new URL(url_string);
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection(); 
            httpcon.addRequestProperty("User-Agent", "Mozilla/4.76");
            httpcon.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
            String line;
            StringBuffer stringBuffer=new StringBuffer();
            long x=System.currentTimeMillis();
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
            System.out.println("Buffer String : "+(System.currentTimeMillis()-x)+" milisecond");
            content=stringBuffer.toString();
            Source source=new Source(content);
            TextExtractor extractor=new TextExtractor(source);
            content=extractor.toString();
            reader.close();
        return content;
	}
}
