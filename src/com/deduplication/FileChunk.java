package com.deduplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class FileChunk {
	public static byte[] readFileInChunk(File file){
		byte[] fileByteData = new byte[(int) file.length()];
        try {
              FileInputStream fileInputStream = new FileInputStream(file);
              fileInputStream.read(fileByteData);
         } catch (FileNotFoundException e) {
                     System.out.println("File Not Found.");
                     e.printStackTrace();
         }
         catch (IOException e1) {
                  System.out.println("Error Reading The File.");
                   e1.printStackTrace();
         }
		return fileByteData;
	}
	
	public static void writeChunkInFile(String filePath, byte[] data) {
        try {
             FileOutputStream fos = new FileOutputStream(filePath);
             
             fos.write(data);
             fos.close();
       }
      catch(FileNotFoundException ex)   {
             System.out.println("FileNotFoundException : " + ex);
      }
     catch(IOException ioe)  {
             System.out.println("IOException : " + ioe);
      }
	}
	
	public static void deleteFile(String fpath) throws IOException{
		File file = new File(fpath);
		file.delete();
	}
	
	
	public static void callWriteXmlFile(Document doc, Writer w, String encoding) {
		try {
			Source source = new DOMSource(doc);
			Result result = new StreamResult(w);
			Transformer xformer = TransformerFactory.newInstance()
					.newTransformer();
			// xformer.setOutputProperty(OutputKeys.ENCODING, encoding);
			xformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	public static void writeFileHash(String fileId, ArrayList<String> hashlist)
			throws Exception {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = dbf.newDocumentBuilder();
		} catch (Exception e) {
		}
		Document doc = builder.newDocument();

		// build fileId tag
		Element file = doc.createElement("file");
		file.setAttribute("id", fileId);
		doc.appendChild(file);

		// loop for build hash tag
		for (int temp = 0; temp < hashlist.size(); temp++) {
			Element hash = doc.createElement("hash");
			Text hashtext = doc.createTextNode(hashlist.get(temp));
			hash.appendChild(hashtext);
			file.appendChild(hash);
		}
		// out put
		try {
			FileOutputStream fos = new FileOutputStream("hash.xml");
			OutputStreamWriter outwriter = new OutputStreamWriter(fos);

			callWriteXmlFile(doc, outwriter, "gb2312");
			outwriter.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> getFileChunkIDs(String fileID) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document doc = null;
		try {
			DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
			doc = dbBuilder.parse("hash.xml");
		} catch (SAXException | IOException | ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Element e = doc.getElementById(fileID);
		NodeList nlist = e.getElementsByTagName("hash");
		ArrayList<String> hashlist = new ArrayList<>();
		for (int i = 0; i < nlist.getLength(); i++) {

			Node nNode = nlist.item(i);
			hashlist.add(nNode.getTextContent());
		}

		return hashlist;
	}
	
}
