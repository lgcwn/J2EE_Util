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
		DocumentBuilder db = null;// documentBuilderΪ������ֱ��ʵ����(��XML�ļ�ת��ΪDOM�ļ�)
		DocumentBuilderFactory dbf = null;
		try {

			dbf = DocumentBuilderFactory.newInstance(); // ����documentBuilderFactory����
			db = dbf.newDocumentBuilder();// ����db������documentBuilderFatory��÷���documentBuildr����
			Document dt = db.parse(f); // �õ�һ��DOM�����ظ�document����
			element = dt.getDocumentElement();// �õ�һ��elment��Ԫ��

			System.out.println("��Ԫ�أ�" + element.getNodeName()); // ��ø��ڵ�
			NodeList childNodes = element.getChildNodes(); // ��ø�Ԫ���µ��ӽڵ�

			for (int i = 0; i < childNodes.getLength(); i++) // ������Щ�ӽڵ�
			{
				Node node1 = childNodes.item(i); // childNodes.item(i);
													// ���ÿ����Ӧλ��i�Ľ��
				if ("Account".equals(node1.getNodeName())) {
					// ����ڵ������Ϊ"Account"�������AccountԪ������type
					System.out.println("\r\n�ҵ�һƪ�˺�. ��������: "
							+ node1.getAttributes().getNamedItem("type")
									.getNodeValue() + ". ");
					NodeList nodeDetail = node1.getChildNodes(); // ���<Accounts>�µĽڵ�
					for (int j = 0; j < nodeDetail.getLength(); j++) { // ����<Accounts>�µĽڵ�
						Node detail = nodeDetail.item(j); // ���<Accounts>Ԫ��ÿһ���ڵ�
						if ("code".equals(detail.getNodeName())) // ���code
							System.out.println("����: " + detail.getTextContent());
						else if ("pass".equals(detail.getNodeName())) // ���pass
							System.out.println("����: " + detail.getTextContent());
						else if ("name".equals(detail.getNodeName())) // ���name
							System.out.println("����: " + detail.getTextContent());
						else if ("money".equals(detail.getNodeName())) // ���money
							System.out.println("���: " + detail.getTextContent());
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}