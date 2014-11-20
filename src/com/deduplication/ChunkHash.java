package com.deduplication;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.mysql.jdbc.Blob;

public class ChunkHash {
	
	public static void generateTxt(String filePath,String data,Boolean append) {
		try {
	         FileOutputStream fos = new FileOutputStream(filePath,append);
	         
	         fos.write(data.getBytes());
	         byte[] newLine="\r\n".getBytes(); 
	         fos.write(newLine); 
	         fos.close();
		}
		catch(FileNotFoundException ex)   {
	         System.out.println("FileNotFoundException : " + ex);
		}
		catch(IOException ioe)  {
	         System.out.println("IOException : " + ioe);	
		}
	}
	
	public static void writeChunk(String chunkID, byte[] chunkData) {

//			 FileOutputStream fos=new FileOutputStream("chunkedfile/" + data);
//	         //FileOutputStream fos = new FileOutputStream(filePath);
//	         
//	         fos.write(file);
//	         fos.close();
			MYSQLDBConnectionManager m = new MYSQLDBConnectionManager();
			m.writeChunk(chunkID, chunkData);
	}
		
}