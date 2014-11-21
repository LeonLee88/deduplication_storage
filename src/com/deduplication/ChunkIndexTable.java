package com.deduplication;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class ChunkIndexTable extends Hashtable<String, String>{
	private static ChunkIndexTable uniqueInstance;

	public ChunkIndexTable() {
		//key is hash of a chunk, value is the number of file which contains that chunk
		Hashtable<String, String> ChunkIndexTable = new Hashtable<String, String>();
		//Initialize the content
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document doc = null;
		try {
			DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
			doc = dbBuilder.parse("hash.xml");
		} catch (SAXException | IOException | ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		NodeList nlist = doc.getElementsByTagName("chunk");
		for (int i = 0; i < nlist.getLength(); i++) {
			Node nNode = nlist.item(i);
			ChunkIndexTable.put(nNode.getTextContent().toString(), nNode.getAttributes().toString());
		}	
		
	}

	public static ChunkIndexTable getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new ChunkIndexTable();
		}
		return uniqueInstance;
	}
	
	public boolean IsDuplicatedChunk(String chunkHash) {

		if(ChunkIndexTable.getInstance().containsKey(chunkHash)) {
			return true;
		}
		return false;
	}
	
	public ArrayList<Chunk> load() {
		return null;

	}
	
	public void Save() {
		ArrayList<String> chunkid = new ArrayList<String>(uniqueInstance.keySet());
		ArrayList<String> chunkcounter = new ArrayList<String>(uniqueInstance.values());
		FileChunk.writeFileHash(chunkid, chunkcounter);
	}
	
	
}
