package com.deduplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class FileChunkMappings {

	static String mapping_path = "file_mappings.xml";
	ArrayList<FileProfile> fileList = new ArrayList<FileProfile>();
	
	public static void writeFileMapping(FileProfile fpro, ArrayList<Chunk> chunks)
			throws Exception {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = dbf.newDocumentBuilder();
		} catch (Exception e) {
		}
		Document doc = builder.parse(mapping_path);
		Element filesRootNode = doc.getDocumentElement();
		// build fileId tag
		Element file = doc.createElement("file");
		file.setAttribute("name", fpro.getName());
		file.setAttribute("id", fpro.getId());
		file.setAttribute("time", fpro.getUploadDate());
		file.setAttribute("size", fpro.getSize().toString());
		file.setAttribute("length", fpro.getLength().toString());

		// loop for build hash tag
		for (int temp = 0; temp < chunks.size(); temp++) {
			Element chunk = doc.createElement("chunk");
			chunk.setAttribute("num", chunks.get(temp).getNum().toString());
			chunk.setAttribute("size", chunks.get(temp).getSize().toString());
			Text chunktext = doc.createTextNode(chunks.get(temp).getId());
			chunk.appendChild(chunktext);
			file.appendChild(chunk);
		}
		
		filesRootNode.appendChild(file);
		// out put
		try {
			
			DOMSource source = new DOMSource(doc);
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
	        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
	        StreamResult result = new StreamResult(mapping_path);
	        transformer.transform(source, result);
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<FileProfile> readFileList(){

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(mapping_path));
			NodeList nodeList = document.getElementsByTagName("file");
			
			for (int i = 0; i < nodeList.getLength(); i++){
				String name = nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue();
				String size = nodeList.item(i).getAttributes().getNamedItem("size").getNodeValue();
				String length = nodeList.item(i).getAttributes().getNamedItem("length").getNodeValue();
				String uploadDate = nodeList.item(i).getAttributes().getNamedItem("time").getNodeValue();
				FileProfile fpro= new FileProfile(name, Long.parseLong(length));
				fpro.setUploadDate(uploadDate);
				fileList.add(fpro);
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileList;
	}
	
	public static ArrayList<Chunk> readChunksByFile(String fileNmae) {
		
		ArrayList<Chunk> chunks = new ArrayList<Chunk>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(mapping_path));
			NodeList nodeList = document.getElementsByTagName("file");
			Node fileNode = getFileNodeByName(fileNmae, nodeList);
			// 读取<file>里的内容， 包括file名称、file的id，上传时间，上传大小
			if (fileNode != null && fileNode.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) fileNode;
				NodeList nodeList2 = e.getElementsByTagName("chunk");
				
				int sizechunk = nodeList2.getLength();
				for (int x = 0; x < sizechunk; x++) {
					Chunk tempChunk = new Chunk();
					tempChunk.setNum(nodeList2.item(x).getAttributes()
							.getNamedItem("number").getNodeValue());
					tempChunk.setSize(nodeList2.item(x).getAttributes()
							.getNamedItem("size").getNodeValue());
					tempChunk.setId(nodeList2.item(x).getTextContent());
					chunks.add(tempChunk);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return chunks;
	}

	public static Node getFileNodeByName(String fileName, NodeList nodeList) {
		Node node = null;
		for (int x = 0; x < nodeList.getLength(); x++) {
			if (nodeList.item(x).getAttributes().getNamedItem("name")
					.getNodeValue().equals(fileName)) {
				return nodeList.item(x);
			}

		}
		return node;
	}
	public static FileProfile getFileInformation(String fileid){
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(mapping_path));
			NodeList nodeList = document.getElementsByTagName("file");
			for(int i=0;i<nodeList.getLength();i++){
				if (nodeList.item(i).getAttributes().getNamedItem("id").getNodeValue().equals(fileid)){
					String name = nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue();
					String length = nodeList.item(i).getAttributes().getNamedItem("length").getNodeValue();
					FileProfile file=new FileProfile(name,Long.parseLong(length));
					file.setId(fileid);
					String size = nodeList.item(i).getAttributes().getNamedItem("size").getNodeValue();
					file.setSize(size);
					String uploadDate = nodeList.item(i).getAttributes().getNamedItem("time").getNodeValue();
					file.setUploadDate(uploadDate);
					return file;
				}
				}
		}catch(ParserConfigurationException | SAXException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
		return null;
		}	
	
	public static void deleteFile(String filename) throws Exception {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		db = dbf.newDocumentBuilder();
		Document document = db.parse(new File(mapping_path));
		NodeList nodeList = document.getElementsByTagName("file");
		Node e = getFileNodeByName(filename, nodeList);
		Element root = document.getDocumentElement();//get root node
		root.removeChild(e);

		try {

			DOMSource source = new DOMSource(document);
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult result = new StreamResult(mapping_path);
			transformer.transform(source, result);

		} catch (Exception c) {
			c.printStackTrace();
		}
	}
	
}
