package com.lchen.cn;

import java.io.FileReader;
import java.util.Calendar;

public class HtmlParseTest {

	public static void main(String[] args) {
		HtmlParseFromBttt htmlParseFromBttt = new HtmlParseFromBttt();
		HtmlParseFromDytt htmlParseFromDytt = new HtmlParseFromDytt();
		try {
			long s = System.currentTimeMillis();
			//htmlParseFromBttt.downloadtTorrent("http://www.bttiantang.com/download1.php","27477","5f87c89856a31f1d40b4f999");
			//htmlParseFromBttt.getHtmlResourceContent("http://www.bttiantang.com/subject/27476.html");
			htmlParseFromBttt.getHtmlResourcePage("http://www.bttiantang.com/");
			
			//htmlParseFromDytt.getHtmlResourceHome("http://www.dytt.com/");
			//htmlParseFromDytt.getHtmlResourceContent("http://www.dytt.com/xiazai/id22730.html");
			//htmlParseFromDytt.getHtmlResourceClassify("http://www.dytt.com/fenlei/15_8.html");
			
			//System.out.println(htmlParseFromDytt.ThunderEncode("ftp://ds:ds@d3.dytt.com:30246/[迅雷下载www.xiamp4.com]女医明妃传02.HDTV.mp4"));
			
			long e = System.currentTimeMillis();
			
			Calendar c = Calendar.getInstance();  
	        c.setTimeInMillis(e - s);  
	        System.out.println("耗时：" + c.get(Calendar.MINUTE) + " MINUTE "  + c.get(Calendar.SECOND) + " SECOND " + c.get(Calendar.MILLISECOND)  
	                + " MILLISECOND ");  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
