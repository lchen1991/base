package com.lchen.cn;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class HtmlParseFromDytt {

	/**
	 * 首页
	 * 
	 * @param url
	 * @throws IOException
	 */
	public void getHtmlResourceHome(String url) throws IOException {
		Document document = Jsoup.connect(url).timeout(5000).get();
		Elements elementsBox = document.getElementsByClass("box");
		for (Element elements : elementsBox) {
			Elements elementsContent = elements.getElementsByClass("title");
			if (elementsContent.size() > 0) {
				System.out.println(elementsContent.get(0).getElementsByTag("b")
						.text());
			}
			Elements elementsBoutList = elements.getElementsByClass("boutlist");
			if (elementsBoutList.size() != 0) {
				// 精品推荐。。。
				for (Element elementBout : elementsBoutList) {
					Elements elementsTops = elementBout.getElementsByTag("li");
					for (Element elementTop : elementsTops) {
						Element element = elementTop.getElementsByTag("a").get(
								0);
						// 类型 地区 评分 时间
						String type = elementTop.getElementsByTag("p").get(1)
								.html();// 2 ： 地区
						String pingfen = elementTop.getElementsByTag("p")
								.get(3).html();
						String href = element.attr("href");
						String title = element.attr("title");
						// getHtmlResourceContent(url + href);
						System.out.println(element.html() + "  href:" + href
								+ " title:" + title + "===type:" + type
								+ ";评分：" + pingfen);
					}
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^");
				}

			} else {
				Elements elementsList = elements.getElementsByTag("a");
				for (Element elementOther : elementsList) {
					String href = elementOther.attr("href");
					String title = elementOther.attr("title");
					System.out.println(elementOther.html() + "  href:" + href
							+ " title:" + title);
					// getHtmlResourceContent(url + href);
					System.out.println("****");
				}
			}
			System.out.println("---------------------");
		}
	}

	/**
	 * 内容页
	 * 
	 * @param url
	 * @throws IOException
	 */
	public void getHtmlResourceContent(String url) throws IOException {
		Document document = Jsoup.connect(url).timeout(5000).get();
		Elements elementsEndinfo = document.getElementsByClass("endinfo");
		if (elementsEndinfo.size() > 0) {
			Element elementsEndinfos = elementsEndinfo.get(0);
			// 解析图片
			Elements elementsImg = elementsEndinfos.getElementsByClass("pic");
			if (elementsImg.size() > 0) {
				Elements elementsImgTag = elementsImg.get(0).getElementsByTag(
						"img");
				if (elementsImg.size() > 0) {
					String elementsImgValue = elementsImgTag.get(0).attr("src");
					System.out.println("图片地址：" + elementsImgValue);
				}
			}
			// 解析标题
			System.out.println(elementsEndinfos.getElementsByTag("h1").get(0)
					.html());
			// 解析内容
			Elements elementsDetails = elementsEndinfos.getElementsByTag("li");
			String filmInfoTag = "";
			StringBuffer sb = new StringBuffer();

			for (Element elementDetail : elementsDetails) {
				List<Node> nodes = elementDetail.childNodes();
				for (Node node : nodes) {

					if (node.childNodes().size() > 0) {
						String tag = node.childNodes().get(0).outerHtml();
						if ("上映：".equals(tag) || "状态：".equals(tag)
								|| "地区：".equals(tag) || "更新日期：".equals(tag)
								|| "更新周期：".equals(tag) || "类型：".equals(tag)
								|| "主演：".equals(tag)) {
							if (sb.length() > 0) {
								sb.deleteCharAt(sb.length() - 1);
							}
							System.out.println(filmInfoTag + sb.toString());
							sb.setLength(0);
							filmInfoTag = tag;
						} else {
							String tagV = node.childNodes().get(0).outerHtml();

							if (!"&nbsp;&nbsp;".equals(tagV)
									&& tagV.length() > 1) {
								sb.append(node.childNodes().get(0).outerHtml());
								sb.append(",");
							}
						}
					} else {
						String tagV = node.outerHtml();
						if (!"&nbsp;&nbsp;".equals(tagV) && tagV.length() > 1) {
							sb.append(tagV);
							sb.append(",");
						}
					}
				}
			}
		}

		// 简介
		Elements elementsAlltext = document.getElementsByClass("alltext");
		if (elementsAlltext.size() > 0) {
			System.out.println("简介：" + elementsAlltext.html());
		}

		// 下载地址
		Elements elementsThunder = document.getElementsByClass("downlist");
		for (Element element : elementsThunder) {
			Elements elementsScript = element.getElementsByTag("script");
			String GvodUrls = elementsScript.html();
			if(GvodUrls!=null&&GvodUrls.contains("GvodUrls"))
			{
				int s = GvodUrls.indexOf("\"")+1;
				int e = GvodUrls.indexOf("\";");
				GvodUrls = GvodUrls.substring(s,e).trim();
				String[] ts = GvodUrls.split("###");
				for (String string:ts) {
					System.out.println(ThunderEncode(string));
				}
			}
		}
		
	}

	public void getHtmlResourceClassify(String url) throws IOException {
		Document document = Jsoup.connect(url).timeout(5000).get();
		Elements elementsBox = document.getElementsByClass("box");
		if (elementsBox.size() > 0) {
			Elements elementsTops = elementsBox.get(0).getElementsByTag("li");
			for (Element elementTop : elementsTops) {
				Element element = elementTop.getElementsByTag("a").get(0);

				// 年代 地区 评分 类型 主演 发布 时间
				String dizhi = elementTop.getElementsByTag("p").get(1).html();// 2 // ：// 地区
				String pingfen = elementTop.getElementsByTag("p").get(3).html();
				String href = element.attr("href");
				String title = element.attr("title");
				System.out.println(element.html() + "  地址:" + href + " 名称:" + title + ";年代:" + dizhi + ";评分：" + pingfen);
				getHtmlResourceContent("http://www.dytt.com/" + href);
			}
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^");
		}
	}

	public String ThunderEncode(String t_url) {
		String thunderPrefix = "AA";
		String thunderPosix = "ZZ";
		String thunderTitle = "thunder://";
		String tem_t_url = t_url;
		String thunderUrl = "";
		try {
			thunderUrl = thunderTitle  + encodeBase64((thunderPrefix + tem_t_url+ thunderPosix).getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return thunderUrl;
	}
	
	/*** 
     * encode by Base64 
     */  
    public static String encodeBase64(byte[]input) throws Exception{  
        Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");  
        Method mainMethod= clazz.getMethod("encode", byte[].class);  
        mainMethod.setAccessible(true);  
         Object retObj=mainMethod.invoke(null, new Object[]{input});  
         return (String)retObj;  
    }  

}