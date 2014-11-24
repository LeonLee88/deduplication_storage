package com.deduplication;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Chunk {
	private String id; // Hash
	private int num; // Chunk order
	private int size; // content size
	private int fileCounter;
	private byte[] data;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public void setNum(String numStr) {
		this.num = Integer.parseInt(numStr);
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setSize(String sizeStr) {
		this.size = Integer.parseInt(sizeStr);
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Integer getFileCounter() {
		return fileCounter;
	}

	public void setFileCounter(Integer fileCounter) {
		this.fileCounter = fileCounter;
	}
	
	public void setFileCounter(String fileCounterStr) {
		this.fileCounter = Integer.parseInt(fileCounterStr);
	}

	public static void saveChunkFile(String chunkHash, byte[] chunkData) {
		try { 
			 FileOutputStream fos=new FileOutputStream("chunks/" + chunkHash);
	         fos.write(chunkData);
	         fos.flush();
	         fos.close(); 
		}
		catch(FileNotFoundException ex)   {
	         System.out.println("FileNotFoundException : " + ex);
		}
		catch(IOException ioe)  {
	         System.out.println("IOException : " + ioe);	
		}
	}
}
