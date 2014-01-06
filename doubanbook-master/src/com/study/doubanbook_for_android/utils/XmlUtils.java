package com.study.doubanbook_for_android.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.study.doubanbook_for_android.model.CommentReslult;
import com.study.doubanbook_for_android.model.Entry;

//TODO add three method to explain xml files or string
public class XmlUtils {

	public static CommentReslult readXML(InputStream inStream) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(inStream);
			Element root = dom.getDocumentElement();
			CommentReslult commentReslult = new CommentReslult();
			// NodeList feedList = root.getElementsByTagName("feed");
			// 设置 属性
			// commentReslult.setTitle(((Element) feedList.item(0))
			// .getAttribute("title"));
			// commentReslult.setTotal(Integer.parseInt(((Element) feedList
			// .item(0)).getAttribute("opensearch:totalResults")));
			// commentReslult.setStart(Integer.parseInt(((Element) feedList
			// .item(0)).getAttribute("opensearch:startIndex")));
			// commentReslult.setCount(Integer.parseInt(((Element) feedList
			// .item(0)).getAttribute("opensearch:itemsPerPage")));

			NodeList entrylist = root.getElementsByTagName("entry");// 查找所有person节点
			System.out.println(entrylist.getLength());

			for (int i = 0; i < entrylist.getLength(); i++) {
				Entry person = new Entry();
				// 得到第一个person节点
				Element entryNode = (Element) entrylist.item(i);
				if ("published".equals(entryNode.getNodeName())) {
					DebugUtils.e("TTT", entryNode.getLocalName() + ","
							+ entryNode.getAttribute("summary"));
				}
				if ("updated".equals(entryNode.getNodeName())) {
					DebugUtils.e("TTT", entryNode.getLocalName() + ","
							+ entryNode.getAttribute("summary"));
				}
				if ("summary".equals(entryNode.getNodeName())) {
					DebugUtils.e("TTT", entryNode.getLocalName() + ","
							+ entryNode.getAttribute("summary"));
				}
				if ("published".equals(entryNode.getNodeName())) {
					DebugUtils.e("TTT", entryNode.getLocalName() + ","
							+ entryNode.getAttribute("summary"));
				}

				commentReslult.add(person);
			}
			inStream.close();
			if (commentReslult != null)
				return commentReslult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//
	// public CommentReslult getResult(InputStream input) {
	// XStream xStream = new XStream(new DomDriver());
	// xStream.alias("feed", CommentReslult.class);
	// xStream.alias("entry", Entry.class);
	// xStream.alias("author", Author.class);
	// CommentReslult result = (CommentReslult) xStream.fromXML(input);
	// return result;
	// }

	/** Dom方式，解析 XML */
	public static String domResolveXML(InputStream is) {
		StringWriter xmlWriter = new StringWriter();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(is);

			doc.getDocumentElement().normalize();
			NodeList nlRoot = doc.getElementsByTagName("feed");
			Element eleRoot = (Element) nlRoot.item(0);
			String title = eleRoot.getAttribute("title");
			String published = eleRoot.getAttribute("published");

			NodeList nlPerson = eleRoot.getElementsByTagName("entry");
			int personsLen = nlPerson.getLength();
			// Person[] persons = new Person[personsLen];
			for (int i = 0; i < personsLen; i++) {
				Element elePerson = (Element) nlPerson.item(i); // person节点
				// Person person = new Person(); // 创建Person对象

				NodeList nlId = elePerson.getElementsByTagName("id");
				Element eleId = (Element) nlId.item(0);
				String id = eleId.getChildNodes().item(0).getNodeValue();
				// person.setId(Integer.parseInt(id));

				NodeList nlName = elePerson.getElementsByTagName("name");
				Element eleName = (Element) nlName.item(0);
				String name = eleName.getChildNodes().item(0).getNodeValue();
				// person.setName(name);

				NodeList nlBlog = elePerson.getElementsByTagName("blog");
				Element eleBlog = (Element) nlBlog.item(0);
				String blog = eleBlog.getChildNodes().item(0).getNodeValue();
				// person.setBlog(blog);

				// xmlWriter.append(person.toString()).append("\n");
				// persons[i] = person;
			}

		} catch (ParserConfigurationException e) { // factory.newDocumentBuilder
			e.printStackTrace();
		} catch (SAXException e) { // builder.parse
			e.printStackTrace();
		} catch (IOException e) { // builder.parse
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return xmlWriter.toString();
	}
}
