package com.deduplication;

import java.util.ArrayList;
import java.util.Date;

public class DFile {
	private String id;
	private String name;
	private Date uploadDate;
	private Integer size;
	private ArrayList<Chunk> chunks;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public ArrayList<Chunk> getChunks() {
		return chunks;
	}
	public void setChunks(ArrayList<Chunk> chunks) {
		this.chunks = chunks;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
}
