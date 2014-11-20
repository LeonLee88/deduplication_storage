package com.deduplication;

import java.util.ArrayList;
import java.util.Hashtable;

public class ChunkIndexTable extends Hashtable<String, String>{
	private static ChunkIndexTable uniqueInstance;

	public ChunkIndexTable() {
		//key is hash of a chunk, value is the number of file which contains that chunk
		Hashtable<String, String> ChunkIndexTable = new Hashtable<String, String>();
		//Initialize the content
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
	}
}
