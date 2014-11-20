package com.deduplication;

public class Chunk {
	private String id; //Hash
	private String num; //Chunk order
	private int size; //content size
	private int fileCounter;
	private byte[] data;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public int getFileCounter() {
		return fileCounter;
	}
	public void setFileCounter(int fileCounter) {
		this.fileCounter = fileCounter;
	}
	
	
}
