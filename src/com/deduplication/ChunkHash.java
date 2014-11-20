package com.deduplication;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
	
	public static void writeChunk(String chunkHash, byte[] chunkData) {
		try { 
			 FileOutputStream fos=new FileOutputStream("chunkedfile/" + chunkHash);
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