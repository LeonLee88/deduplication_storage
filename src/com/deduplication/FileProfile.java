package com.deduplication;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileProfile{
	private String id;
	private String uploadDate;
	private Long size;
	private ArrayList<Chunk> chunks;
	private String name;

	public FileProfile(String name, Long size){
		this.setName(name);
		this.setSize(size);
		this.setUploadDate(getCurrentTimeStr());
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public ArrayList<Chunk> getChunks() {
		return chunks;
	}

	public void setChunks(ArrayList<Chunk> chunks) {
		this.chunks = chunks;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long l) {
		this.size = l;
	}

	public void setSize(String size) {
		this.size = Long.parseLong(size);
	}

	public void remove() {

	}

	public void save() {

	}
	
	public String getCurrentTimeStr() {
		Date date = new Date();
		// DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		DateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
		return sdf.format(date);
	}
}
