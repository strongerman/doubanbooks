package com.study.doubanbook_for_android.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.study.doubanbook_for_android.model.Author;
import com.study.doubanbook_for_android.model.CommentReslult;
import com.study.doubanbook_for_android.model.Entry;
import com.study.doubanbook_for_android.model.Link;

public class XMLReader {

	public static CommentReslult parseXml(InputStream stream)
			throws ParserConfigurationException, SAXException, IOException {
		// 创建解析器工厂实例，并生成解析器
		CommentReslult commentReslult = new CommentReslult();
		List<Entry> entryList = new ArrayList<Entry>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		// 创建需要解析的文档对象
		// File f = new File("books.xml");
		// 解析文档，并返回一个Document对象，此时xml文档已加载到内存中
		// 好吧，让解析来得更猛烈些吧，其余的事就是获取数据了
		Document doc = builder.parse(stream);
		// 获取文档根元素
		// 你问我为什么这么做?因为文档对象本身就是树形结构，这里就是树根
		// 当然，你也可以直接找到元素集合，省略此步骤
		Element root = doc.getDocumentElement();

	
		System.out.println("total title:" + getValue(root, "title"));
		commentReslult.setTitle(getValue(root, "title"));
		System.out.println("start index:"
				+ getValue(root, "opensearch:startIndex"));
		commentReslult.setStart(Integer.parseInt(getValue(root,
				"opensearch:startIndex")));
		System.out.println("total result:"
				+ getValue(root, "opensearch:totalResults"));
		commentReslult.setTotal(Integer.parseInt(getValue(root,
				"opensearch:totalResults")));
		System.out.println();

		// NodeList linkList = root.getChildNodes();
		// for (int i = 0; i < linkList.getLength(); i++) {
		// Node book = linkList.item(i);
		// if (book.getNodeType() == Node.ELEMENT_NODE) {
		// String email = book.getAttributes().getNamedItem("href")
		// .getNodeValue();
		// System.out.println(email);
		// }
		// }
		// for (int j = 0; j < linkList.getLength(); j++) {
		// Element curEle = (Element) linkList.item(j);
		// Link link = new Link();
		// link.setHref(getValue(curEle, "href"));
		// link.setRel(getValue(curEle, "rel"));
		// System.out.println("link " + j + ":" + link.toString());
		// }
		// 上面找到了根节点，这里开始获取根节点下的元素集合
		NodeList list = root.getElementsByTagName("entry");
		for (int i = 0; i < list.getLength(); i++) {
			Entry entry = new Entry();
			// 通过item()方法找到集合中的节点，并向下转型为Element对象
			Element n = (Element) list.item(i);
			System.out.println("i: " + i);
			System.out.println("entry id url:" + getValue(root, "id"));
			entry.setId(getValue(root, "id"));
			// 打印元素内容，代码很纠结，差不多是个固定格式
			System.out.println("title: " + getValue(n, "title"));
			entry.setTitle(getValue(n, "title"));
			System.out.println("summary: " + getValue(n, "summary"));
			entry.setSummay(getValue(n, "summary"));
			System.out.println("published: " + getValue(n, "published"));
			entry.setPublished(getValue(n, "published"));
			System.out.println("updated: " + getValue(n, "updated"));
			entry.setUpdated(getValue(n, "updated"));
			Author author = new Author();
			System.out.println("name: " + getValue(n, "name"));
			author.setName(getValue(n, "name"));
			System.out.println("uri:" + getValue(n, "uri"));
			author.setUri(getValue(n, "uri"));
			entry.setAuthor(author);
			System.out.println();
			entryList.add(entry);
		}
		commentReslult.setEntry(entryList);
		return commentReslult;
	}

	private static String getValue(Element n, String s) {
		try {
			return n.getElementsByTagName(s).item(0).getFirstChild()
					.getNodeValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
