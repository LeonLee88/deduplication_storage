package com.deduplication;

import java.util.UUID;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FileProfile {
	private String id;
	private String uploadDate;
	private Long size;
	private Long length;
	private ArrayList<Chunk> chunks;
	private String name;

	public FileProfile(String name, Long length) {

		Double doubleSize = length.doubleValue() / 1024;
		
		this.setId();
		this.setName(name);
		this.setSize((Math.round(doubleSize)));
		this.setLength(length);
		this.setUploadDate(getCurrentTimeStr());
	}

	public String getId() {
		return id;
	}

	public void setId() {
		String uuid = UUID.randomUUID().toString();
		this.id = uuid;
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

	public void setSize(String sizeStr) {
		this.size = Long.parseLong(sizeStr);
	}

	public void remove() {

	}

	public void save() {

	}

	public String getCurrentTimeStr() {
		Date date = new Date();
		// DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a",
				Locale.ENGLISH);
		return sdf.format(date);
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public void setLength(String lengthStr) {
		this.length = Long.parseLong(lengthStr);
	}

	public byte[] retriveFile() {

		if (!chunks.isEmpty()) {
			byte[] data = new byte[this.length.intValue()];
			int offset = 0;
			int len;
			File file;
			for (Chunk chunk : this.chunks) {
				len = chunk.getSize();
				File chunkfile = new File("chunks/" + chunk.getId());
				try {
					FileInputStream inputStream = new FileInputStream(chunkfile);
					inputStream.read(data, offset, len);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				offset = offset + chunk.getSize();
			}

			return data;
		} else {
			return null;
		}
	}
}
