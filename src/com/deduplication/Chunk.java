package com.deduplication;

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

}
