package com.j2ee.util;

import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadXml {
	public static void main(String args[]) {
		Element element = null;
		File f = new File("a.xml");
		DocumentBuilder db = null;// documentBuilder为抽象不能直接实例化(将XML文件转换为DOM文件)
		DocumentBuilderFactory dbf = null;
		try {

			dbf = DocumentBuilderFactory.newInstance(); // 返回documentBuilderFactory对象
			db = dbf.newDocumentBuilder();// 返回db对象用documentBuilderFatory获得返回documentBuildr对象
			Document dt = db.parse(f); // 得到一个DOM并返回给document对象
			element = dt.getDocumentElement();// 得到一个elment根元素

			System.out.println("根元素：" + element.getNodeName()); // 获得根节点
			NodeList childNodes = element.getChildNodes(); // 获得根元素下的子节点

			for (int i = 0; i < childNodes.getLength(); i++) // 遍历这些子节点
			{
				Node node1 = childNodes.item(i); // childNodes.item(i);
													// 获得每个对应位置i的结点
				if ("Account".equals(node1.getNodeName())) {
					// 如果节点的名称为"Account"，则输出Account元素属性type
					System.out.println("\r\n找到一篇账号. 所属区域: "
							+ node1.getAttributes().getNamedItem("type")
									.getNodeValue() + ". ");
					NodeList nodeDetail = node1.getChildNodes(); // 获得<Accounts>下的节点
					for (int j = 0; j < nodeDetail.getLength(); j++) { // 遍历<Accounts>下的节点
						Node detail = nodeDetail.item(j); // 获得<Accounts>元素每一个节点
						if ("code".equals(detail.getNodeName())) // 输出code
							System.out.println("卡号: " + detail.getTextContent());
						else if ("pass".equals(detail.getNodeName())) // 输出pass
							System.out.println("密码: " + detail.getTextContent());
						else if ("name".equals(detail.getNodeName())) // 输出name
							System.out.println("姓名: " + detail.getTextContent());
						else if ("money".equals(detail.getNodeName())) // 输出money
							System.out.println("余额: " + detail.getTextContent());
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}