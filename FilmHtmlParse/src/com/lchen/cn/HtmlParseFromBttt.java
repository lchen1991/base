package com.lchen.cn;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class HtmlParseFromBttt {

	/**
	 * @param url
	 * @throws IOException
	 */
	public void getHtmlResourcePage(String url) throws IOException {
		Document document = Jsoup.connect(url).timeout(5000).get();

		// 电影分类
		Elements elementsTitle = document.getElementsByClass("Btitle");
		for (Element elements : elementsTitle) {
			Elements mTagForA = elements.getElementsByTag("a");
			for (Element elementa : mTagForA) {
				String href = elementa.attr("href");
				String title = elementa.attr("title");
				String value = elementa.html();
				System.out.println("value:" + value + ";title:" + title
						+ ";href:" + href);
			}
		}

		System.out.println();
		// 电影列表
		Elements elementsFilmItemList = document.getElementsByClass("item");
		for (Element elementItem : elementsFilmItemList) {
			//介绍信息
			Elements elementsFilmList = elementItem.getElementsByClass("title");
			for (Element elements : elementsFilmList) {
				Elements mTagForP = elements.getElementsByTag("p");
				if(mTagForP.size()>=3)
				{
					String title = mTagForP.get(0).text();
					String regEx="\\d{4}/\\d{2}/\\d{2}";
					Pattern p = Pattern.compile(regEx);
					Matcher m = p.matcher(title);
					if(m.find()){
					    String result=m.group();
					    System.out.println("更新日期："+result);
					    title = title.replace(result,"");
					}
					System.out.println(title);
					String titleName = mTagForP.get(1).text();
					System.out.println(titleName);
					String filmPerformers = mTagForP.get(2).text();
					System.out.println(filmPerformers);
					String filmscore = mTagForP.get(3).text();
					System.out.println(filmscore);
				}
			}
			//图片
			Elements elementsPic= elementItem.getElementsByClass("litpic");
			for (Element element : elementsPic) {
				Elements elementsa = element.getElementsByTag("a");
				if (elementsa.size() > 0) {
					String mDetailUrl = elementsa.get(0).attr("href");
					Elements elementsImg = elementsa.get(0).getElementsByTag("img");
					if(elementsImg.size()>0)
					{
						String mFilmPic = elementsImg.get(0).attr("src");
						System.out.println("图片地址："+mFilmPic);
					}
					System.out.println("url:" + url + mDetailUrl);
				}
			}
			System.out.println();
		}
		
	}

}
