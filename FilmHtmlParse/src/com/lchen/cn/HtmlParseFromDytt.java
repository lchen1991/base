package com.lchen.cn;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class HtmlParseFromDytt {

	/**
	 * ��ҳ
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
				// ��Ʒ�Ƽ�������
				for (Element elementBout : elementsBoutList) {
					Elements elementsTops = elementBout.getElementsByTag("li");
					for (Element elementTop : elementsTops) {
						Element element = elementTop.getElementsByTag("a").get(
								0);
						// ���� ���� ���� ʱ��
						String type = elementTop.getElementsByTag("p").get(1)
								.html();// 2 �� ����
						String pingfen = elementTop.getElementsByTag("p")
								.get(3).html();
						String href = element.attr("href");
						String title = element.attr("title");
						getHtmlResourceContent(url + href);
						System.out.println(element.html() + "  href:" + href
								+ " title:" + title + "===type:" + type
								+ ";���֣�" + pingfen);
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
					getHtmlResourceContent(url + href);
					System.out.println("****");
				}
			}
			System.out.println("---------------------");
		}
	}

	/**
	 * ����ҳ
	 * @param url
	 * @throws IOException
	 */
	public void getHtmlResourceContent(String url) throws IOException {
		Document document = Jsoup.connect(url).timeout(5000).get();
		Elements elementsEndinfo = document.getElementsByClass("endinfo");
		if (elementsEndinfo.size() > 0) {
			Element elementsEndinfos = elementsEndinfo.get(0);
			// ����ͼƬ
			Elements elementsImg = elementsEndinfos.getElementsByClass("pic");
			if (elementsImg.size() > 0) {
				Elements elementsImgTag = elementsImg.get(0).getElementsByTag(
						"img");
				if (elementsImg.size() > 0) {
					String elementsImgValue = elementsImgTag.get(0).attr("src");
					System.out.println("ͼƬ��ַ��" + elementsImgValue);
				}
			}
			// ��������
			System.out.println(elementsEndinfos.getElementsByTag("h1").get(0)
					.html());
			// ��������
			Elements elementsDetails = elementsEndinfos.getElementsByTag("li");
			String filmInfoTag = "";
			StringBuffer sb = new StringBuffer();

			for (Element elementDetail : elementsDetails) {
				List<Node> nodes = elementDetail.childNodes();
				for (Node node : nodes) {

					if (node.childNodes().size() > 0) {
						String tag = node.childNodes().get(0).outerHtml();

						if ("��ӳ��".equals(tag) || "״̬��".equals(tag)
								|| "������".equals(tag) || "�������ڣ�".equals(tag)
								|| "�������ڣ�".equals(tag) || "���ͣ�".equals(tag)
								|| "���ݣ�".equals(tag)) {
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
		
		//���
		Elements elementsAlltext = document.getElementsByClass("alltext");
		if (elementsAlltext.size() > 0) {
			System.out.println("��飺"+elementsAlltext.html());
			System.out.println();
		}
	}
	
	public void getHtmlResourceClassify(String url) throws IOException
	{
		Document document = Jsoup.connect(url).timeout(5000).get();
		Elements elementsBox = document.getElementsByClass("box");
		if(elementsBox.size()>0)
		{
			Elements elementsTops = elementsBox.get(0).getElementsByTag("li");
			for (Element elementTop : elementsTops) {
				Element element = elementTop.getElementsByTag("a").get(0);
				
				// ��� ���� ���� ���� ���� ���� ʱ��
				String dizhi = elementTop.getElementsByTag("p").get(1).html();// 2 �� ����
				String pingfen = elementTop.getElementsByTag("p").get(3).html();
				String href = element.attr("href");
				String title = element.attr("title");
				System.out.println(element.html() + "  ��ַ:" + href
						+ " ����:" + title + ";���:" + dizhi
						+ ";���֣�" + pingfen);
				getHtmlResourceContent("http://www.dytt.com/" + href);
			}
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^");
		
		}
	}
}
