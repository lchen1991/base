package com.lchen.cn;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
	
	public void getHtmlResourceContent(String url)
	{
		Document document = null;
		try {
			document = Jsoup.connect(url).timeout(5000).get();
			Elements elementsDetailList = document.getElementsByClass("moviedteail_list");
			for (Element elementList:elementsDetailList) {
				Elements elementslis = elementList.getElementsByTag("li");
				for (Element elementLi:elementslis) {
					System.out.println(elementLi.text());
				}
			}
			
			Elements elementsTinfo = document.getElementsByClass("tinfo");
			System.out.println("下载地址：");
			for (Element elementLi:elementsTinfo) {
				Elements elementsA = elementLi.getElementsByTag("a");
				String href = elementsA.attr("href");
				System.out.println(href);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void downloadtTorrent(String url,String id,String uhash)
	{
		//action=download&id=27477&uhash=4934020f2d77e465d9c266ad&imageField.x=10&imageField.y=10
		try {
			URL httpUrl = new URL(url) ;
			HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
			connection.setConnectTimeout(5000);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.connect();
			
			
			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(("action=download&id="+id+"&uhash="+uhash+"&imageField.x=68&imageField.y=28").getBytes());
			
			if(connection.getResponseCode() == 200)
			{
				Map<String, List<String>> headers = connection.getHeaderFields();
				//获取文件名
				String fileName = UUID.randomUUID()+".torrent";
				List<String> dispositionList = headers.get("Content-Disposition");
				String s = "filename=\"";
				String e = "torrent";
				for (String string:dispositionList) {
					int nameStart = string.indexOf(s);
					int end = string.lastIndexOf(e);
					fileName = string.substring(nameStart+s.length(), end+e.length());
					fileName = new String(fileName.getBytes("ISO-8859-1"), "UTF-8");
				}
				BufferedInputStream inputStreamReader = new BufferedInputStream(connection.getInputStream());
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("D:/github/"+fileName));
				byte[] data = new byte[1024];
				int len = -1;
				while ((len = inputStreamReader.read(data))!=-1) {
					bufferedOutputStream.write(data, 0, len);
					bufferedOutputStream.flush();
				}
				inputStreamReader.close();
				bufferedOutputStream.close();
			}
			else
			{
				System.out.println("下载失败！");
			}
			
			outputStream.close();
		} catch (Exception e) {
			System.out.println("下载失败！"+e.getMessage());
		}
		
	}
}
