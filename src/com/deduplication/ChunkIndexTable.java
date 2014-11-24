package com.deduplication;

import java.awt.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ChunkIndexTable extends LinkedHashMap<String, String> {
	private static ChunkIndexTable uniqueInstance;
	private String path = "chunkIndex.json";
	public ChunkIndexTable() {
		// key is hash of a chunk, value is the number of file which contains
		// that chunk
		this.load();
	}

	public static ChunkIndexTable getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new ChunkIndexTable();
		}
		return uniqueInstance;
	}

	public boolean IsDuplicatedChunk(String chunkHash) {

		if (ChunkIndexTable.getInstance().containsKey(chunkHash)) {
			return true;
		}
		return false;
	}

	private void load() {
		byte[] encoded;

		try {

			encoded = Files.readAllBytes(Paths.get(path));
			String indexJsonStr = new String(encoded);
			JSONParser parser = new JSONParser();
			ContainerFactory containerFactory = new ContainerFactory() {
				public Map createObjectContainer() {
					return new LinkedHashMap();
				}

				public LinkedList creatArrayContainer() {
					return new LinkedList();
				}

			};

			LinkedHashMap<String, String> m = (LinkedHashMap) parser.parse(indexJsonStr, containerFactory);
			this.putAll(m);
			
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Save() {
		StringWriter out = new StringWriter();
		try {
			FileWriter jsonFileWriter = new FileWriter(path);
			JSONObject indexJsonObject = new JSONObject(this.getInstance());
			//JSONValue.writeJSONString(this.getInstance(), out);
			jsonFileWriter.write(indexJsonObject.toJSONString());
			jsonFileWriter.flush();
			jsonFileWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsonText = out.toString();
		System.out.print(jsonText);
	}
	
	public void DeleteChunks(ArrayList<Chunk> chunks){
		//Check chunks in index table, increase counter or delete it
	}
}
