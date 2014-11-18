package com.deduplication;

import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.DocumentBuilderFactory;  


//import org.w3c.dom.Document;
//import org.w3c.dom.NodeList;
//import org.w3c.dom.Node;
//import org.w3c.dom.Element;





import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;

public class FileRetrive {

	public static void main(String argv[]) {

		try {

//			File fXmlFile = new File("hash.xml");
//			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
//					.newInstance();
//			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//			Document doc = dBuilder.parse(fXmlFile);
			 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();  
		        //从DOM工厂中获得DOM解析器  
		        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();  
		        //声明为File为了识别中文名  
		        Document doc = null;  
		        doc = dbBuilder.parse("hash.xml");  
		          
		        //得到文档名称为Student的元素的节点列表  
		        NodeList list = doc.getElementsByTagName("chunk");  
		          
			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("chunk"); // ???//
			ArrayList<String> hashlist = new ArrayList<String>();
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					System.out.println("chunk id : "
							+ eElement.getAttribute("id"));
					int j = 0;
					NodeList a;
					a = eElement.getElementsByTagName("hash");
					a.getLength();
					String hashStr;
					for (j = 0; j < a.getLength(); j++) {

						System.out.println("hash : "
								+ eElement.getElementsByTagName("hash").item(j)
										.getTextContent());
						hashStr =eElement.getElementsByTagName("hash").item(j)
								.getTextContent();
						hashlist.add(hashStr);
						
					}
					System.out.println(hashlist);
					// System.out.println("file+hash : "+eElement.getAttribute("id")
					// +":"+eElement.getElementsByTagName("hash1").item(0).getTextContent()+eElement.getElementsByTagName("hash2").item(0).getTextContent()+eElement.getElementsByTagName("hash3").item(0).getTextContent()+eElement.getElementsByTagName("hash4").item(0).getTextContent());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
