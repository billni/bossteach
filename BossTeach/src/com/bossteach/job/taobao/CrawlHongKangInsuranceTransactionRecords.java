package com.bossteach.job.taobao;

import java.io.InputStreamReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.bossteach.model.HongKangInsuranceTransaction;


public class CrawlHongKangInsuranceTransactionRecords {
	private Log logger = LogFactory.getLog(CrawlHongKangInsuranceTransactionRecords.class);
	private static final String PROXY_HOST= "10.18.8.108";
	private static final int PROXY_PORT = 8008;
	private static final String PROXY_USERNAME= "niyong";
	private static final String PROXY_PASSWORD= "nY111111";
	private static final String PROXY_WORKSTATION= "isa06";
	private static final String PROXY_DOMAIN= "ulic";

	
    public DefaultHttpClient getHttpClient(DefaultHttpClient httpClient){
        NTCredentials credentials = new NTCredentials(PROXY_USERNAME ,PROXY_PASSWORD , PROXY_WORKSTATION, PROXY_DOMAIN);	        
        httpClient.getCredentialsProvider().setCredentials(new AuthScope(PROXY_HOST, PROXY_PORT), credentials);	      
        HttpHost proxy = new HttpHost(PROXY_HOST, PROXY_PORT);
        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        return httpClient;
    }	
	
	/** 
	     * 根据URL获得html信息 
	     * @param url 
	     * @return 
	     */  
	    public String getHtmlByUrl(String url){
	        StringWriter sw = new StringWriter();
	        
//	        DefaultHttpClient httpClient = getHttpClient(new DefaultHttpClient());//创建httpClient对象                	   
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	        //如果代理需要密码验证，这里设置用户名密码  	        
	        HttpGet httpGet = new HttpGet(url);	        
	        try {	        	
	            HttpResponse response = httpClient.execute(httpGet);//得到responce对象  
	            int resStatu = response.getStatusLine().getStatusCode();//返回 	 
	            if (resStatu == HttpStatus.SC_OK) {//200正常
	                //获得相应实体  	            	
	                HttpEntity entity = response.getEntity();
	                if (entity!=null) {	             
	                	InputStreamReader insr = new InputStreamReader(entity.getContent(), "gb2312" /*ContentType.getOrDefault(entity).getCharset()*/);
                        IOUtils.copy(insr, sw);
	                	insr.close();
	                }
	            } else {
		            System.out.println("Http Status Code:" + resStatu);
	            }
	        } catch (Exception e) {  
	        	 System.out.println("访问【"+url+"】出现异常!");
	             e.printStackTrace();  
	        } finally {	        	        	
	        	logger.info("HttpClient连接关闭.");
	            httpClient.getConnectionManager().shutdown();	            
	        }  
	        return sw.toString();  
	    }
	    
	    public List analyseHtml(){
	    	HongKangInsuranceTransaction tbt = null;
	    	List<HongKangInsuranceTransaction> list = new ArrayList<HongKangInsuranceTransaction>();
	    	  String html = getHtmlByUrl("http://baoxian.taobao.com/json/PurchaseList.do?page=1&itemId=17305541936&sellerId=1128953583&callback=mycallback&sold_total_num=0&callback=mycallback");
	        if (html!= null && !"".equals(html)) {	        	
	            Document doc = Jsoup.parse(html);  
	            Elements trs = doc.select("tbody tr");
	            for (Element tr: trs) {
	            	tr = tr.html(tr.html().replace("\\r", "").replace("\\t", "").replace("\\n", "").replace("\\/td", "").replace("&lt;", "").replace("&gt;", "").replace("\\/tr", "").replace(" <em>", "").replace("\\/em", "").replace("</em>", "").replace("\\/table", ""));
	            	tbt = new HongKangInsuranceTransaction();
	            	//去掉第一行
	            	if ("".equals(tr.select("td:eq(0)").html())) {
	            		continue;
	            	}
	            	//过滤不是今天的记录
	            	String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	            	if (!currentDate.equals(tr.select("td:eq(4)").html().subSequence(0, 10))) {
	            		break;
	            	}
	            	System.out.println(tr.html());
	            	tbt.setSellerId("1128953583");
	            	tbt.setItemId("17305541936");
	            	tbt.setBuyer(tr.select("td:eq(0)").html());
	            	tbt.setItemName(tr.select("td:eq(1)").html());
	            	tbt.setPremium(Double.parseDouble(tr.select("td:eq(2)").html()));
	            	tbt.setCount(Long.parseLong(tr.select("td:eq(3)").html()));
	            	tbt.setTransactionDate(tr.select("td:eq(4)").html());
	            	tbt.setStatus(tr.select("td:eq(5)").html());
	            	list.add(tbt);
	            }  
	        } 
	        return list;
	    }
	    
	    public static void main(String[] args) throws Exception {
	    	CrawlHongKangInsuranceTransactionRecords job = new CrawlHongKangInsuranceTransactionRecords();
	    	job.analyseHtml();
//	    	job.poolRequest();
//	    	job.transformToJsonObject();
		}
}
