package testxml;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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

public class DomTest {

	// Ð´Èë£ø£í£ìÎÄ¼þ
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
		// build chunk tag
		// Element chunk=doc.createElement("chunk");
		// file.appendChild(chunk);

		// loop for build hash tag
		for (int temp = 0; temp < hashlist.size(); temp++) {
			Element hash = doc.createElement("hash");
			Text hashtext = doc.createTextNode(hashlist.get(temp));
			hash.appendChild(hashtext);
			file.appendChild(hash);
		}
		// out put
		try {
			FileOutputStream fos = new FileOutputStream(fileId);
			OutputStreamWriter outwriter = new OutputStreamWriter(fos);

			callWriteXmlFile(doc, outwriter, "gb2312");
			outwriter.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws Exception {
		String str = "myfile.xml";
		DomTest t = new DomTest();
		ArrayList<String> hashlist = new ArrayList<String>();
		hashlist.add("aaaa");
		hashlist.add("bbbb");
		hashlist.add("cccc");
		hashlist.add("dddd");
		// writeFileHash("myfile", hashlist);
		t.writeFileHash(str, hashlist);
	}
}
