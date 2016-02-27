package com.lchen.cn;

import java.io.IOException;
import java.util.Calendar;

public class HtmlParseTest {

	public static void main(String[] args) {
		HtmlParseFromBttt htmlParseFromBttt = new HtmlParseFromBttt();
		try {
			long s = System.currentTimeMillis();
			htmlParseFromBttt.getHtmlResourcePage("http://www.bttiantang.com");
			//htmlParseFromDytt.getHtmlResourceHome("http://www.dytt.com/");
			//htmlParseFromDytt.getHtmlResourceClassify("http://www.dytt.com/fenlei/15_8.html");
			long e = System.currentTimeMillis();
			
			Calendar c = Calendar.getInstance();  
	        c.setTimeInMillis(e - s);  
	        System.out.println("耗时：" + c.get(Calendar.MINUTE) + " MINUTE "  
	                + c.get(Calendar.SECOND) + " SECOND " + c.get(Calendar.MILLISECOND)  
	                + " MILLISECOND ");  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
