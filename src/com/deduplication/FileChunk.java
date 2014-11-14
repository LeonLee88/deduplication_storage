package com.deduplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileChunk {
	public static byte[] readFileInChunk(File file){
		byte[] fileByteData = new byte[(int) file.length()];
        try {
              FileInputStream fileInputStream = new FileInputStream(file);
              fileInputStream.read(fileByteData);
         } catch (FileNotFoundException e) {
                     System.out.println("File Not Found.");
                     e.printStackTrace();
         }
         catch (IOException e1) {
                  System.out.println("Error Reading The File.");
                   e1.printStackTrace();
         }
		return fileByteData;
	}
	
	public static void writeChunkInFile(String filePath, byte[] data) {
        try {
             FileOutputStream fos = new FileOutputStream(filePath);
             
             fos.write(data);
             fos.close();
       }
      catch(FileNotFoundException ex)   {
             System.out.println("FileNotFoundException : " + ex);
      }
     catch(IOException ioe)  {
             System.out.println("IOException : " + ioe);
      }
	}
	
	public static void deleteFile(String fpath) throws IOException{
		File file = new File(fpath);
		file.delete();
	}
}
