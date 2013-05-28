package com.antsirs.train12306.job;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import com.antsirs.train12306.service.TrainTicketManagerService;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:applicationContext-*.xml"})
public class TicketTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private TrainTicketManagerService trainTicketManagerService;
		
	/**
	 * 测试证件和姓名相同	
	 * @throws Exception 
	 * @throws Exception
	 */
	@Test
//	@Rollback(false)
	public void testRecordTicket() throws Exception{
    	Crawl12306 job = new Crawl12306();
//    	System.out.println(job.initUrl().toString());
    	String html = job.getHtmlByUrl(job.initUrl().toString());
//    	System.out.println(html);//	    	
    	JSONObject jsonObject = new JSONObject(html);
    	String result = jsonObject.get("datas").toString();
//    	System.out.println(result);	    	
    	job.analyseRecords(result);
    	//0,<span id='id_24000000T50E' class='base_txtdiv' onmouseover=javascript:onStopHover('24000000T50E#BXP#LZZ') onmouseout='onStopOut()'>T5</span>,<img src='/otsquery/images/tips/first.gif'>&nbsp;&nbsp;&nbsp;&nbsp;北京西&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;15:45,&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;柳州&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;16:41,24:56,--,--,--,--,--,8,5,--,<font color='darkgray'>无</font>,150,--,\n1,<span id='id_240000T18909' class='base_txtdiv' onmouseover=javascript:onStopHover('240000T18909#BXP#LZZ') onmouseout='onStopOut()'>T189</span>,<img src='/otsquery/images/tips/first.gif'>&nbsp;&nbsp;&nbsp;&nbsp;北京西&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;18:17,&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;柳州&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;20:00,25:43,--,--,--,--,12,11,<font color='darkgray'>无</font>,--,9,10,--,\n2,<span id='id_240000K1570Q' class='base_txtdiv' onmouseover=javascript:onStopHover('240000K1570Q#BXP#LZZ') onmouseout='onStopOut()'>K157</span>,<img src='/otsquery/images/tips/first.gif'>&nbsp;&nbsp;&nbsp;&nbsp;北京西&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;18:26,&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;柳州&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;01:18,30:52,--,--,--,--,--,11,<font color='darkgray'>无</font>,--,<font color='darkgray'>无</font>,193,--,
    	String time = jsonObject.get("time").toString();
//    	System.out.println(time);	    	
//    	String html = job.getHtmlByUrl("http://baoxian.taobao.com/json/PurchaseList.do?page=1&itemId=17305541936&sellerId=1128953583&callback=mycallback&sold_total_num=0");
//    	job.analyseTransactionRecordsHtml(html);
//    	job.poolRequest();
//    	job.transformToJsonObject();
	}	
}