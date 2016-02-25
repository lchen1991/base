package com.lchen.cn;

import java.io.IOException;
import java.util.Calendar;

public class HtmlParseTest {

	public static void main(String[] args) {
		HtmlParseFromDytt htmlParseFromDytt = new HtmlParseFromDytt();
		try {
			long s = System.currentTimeMillis();
			htmlParseFromDytt.getHtmlResourceClassify("http://www.dytt.com/fenlei/15_8.html");
			long e = System.currentTimeMillis();
			
			Calendar c = Calendar.getInstance();  
	        c.setTimeInMillis(e - s);  
	        System.out.println("∫ƒ ±: " + c.get(Calendar.MINUTE) + "∑÷ "  
	                + c.get(Calendar.SECOND) + "√Î " + c.get(Calendar.MILLISECOND)  
	                + " ∫¡√Î");  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
