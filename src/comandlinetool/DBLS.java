package comandlinetool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.HashGeneratorUtils.HashGeneratorUtils;
import com.deduplication.Chunk;
import com.deduplication.ChunkIndexTable;
import com.deduplication.ChunkedFile;
import com.deduplication.FileChunkMappings;
import com.deduplication.FileProfile;

public class DBLS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Please select a function:");
		System.out.println("1. List fles");
		System.out.println("2. Upload");
		System.out.println("3. Remove");
		System.out.println("4. Download");
		System.out.println("5. Return");
		String option;
		InputStreamReader is=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(is);
        try{
               option=(String) br.readLine();
//               System.out.println(option);
               
               switch (option) {
				case "1":
					jump1();
				case "2":

				case "3":
					jump3();
				case "4":
					jump4();
				case "5":
			}
               
        }catch(IOException e){
            System.out.println("System Error!");
            e.printStackTrace();
        }finally{
            try{
                is.close();
                br.close();
            }catch(IOException e){
                System.out.println("Stream Close Error");
                e.printStackTrace();
            }
        } 

	}

	
	public static void jump1() {
		
		System.out.println("Please input the file directory for upload: ");
		InputStreamReader is=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(is);
        String filename;
        try{
            filename=(String) br.readLine();
//             System.out.println(filename);
               uploadfile(filename);
        }catch(IOException e){
            System.out.println("System Error!");
            e.printStackTrace();
        }
        finally{
            try{
                is.close();
                br.close();
            }catch(IOException e){
                System.out.println("Stream Close Error");
                e.printStackTrace();
            }
        } 
	} // jump1 end
	
	public static void jump3() {
		
		System.out.println("Please input the file directory for deletion: ");
		InputStreamReader is=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(is);
        String filename;
        try{
            filename=(String) br.readLine();
//             System.out.println(filename);
               deletefile(filename);
        }catch(IOException e){
            System.out.println("System Error!");
            e.printStackTrace();
        }
        finally{
            try{
                is.close();
                br.close();
            }catch(IOException e){
                System.out.println("Stream Close Error");
                e.printStackTrace();
            }
        } 
	} // jump3 end
	
public static void jump4() {
		
//		System.out.println("Please input the filename for download: ");
		InputStreamReader is=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(is);
        String filename;
        String filepath;
        try{
        System.out.println("Please input the filename for download: ");
            filename=(String) br.readLine();
    	System.out.println("Please input the filepath for download: ");    
            filepath=(String) br.readLine();
               downloadfile(filename,filepath);
        }catch(IOException e){
            System.out.println("System Error!");
            e.printStackTrace();
        }
        finally{
            try{
                is.close();
                br.close();
            }catch(IOException e){
                System.out.println("Stream Close Error");
                e.printStackTrace();
            }
        } 
	} // jump4 end
	
//********************************************
public static void uploadfile(String name) {
		File file = new File(name);
		FileProfile fpro = new FileProfile(file);
		try {
			HashGeneratorUtils.genrateMD5(file, fpro);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	} //uploadfile end
	
	
	   public static void deletefile(String fileName){
		   try {			   
			   String fileId = FileChunkMappings.getIdByFilename(fileName);
			   System.out.println(fileId);
			   ArrayList<Chunk> chunklist = FileChunkMappings
						.getChunksByFile(fileId);
				ChunkIndexTable.DeleteChunks(chunklist);
				FileChunkMappings.deleteFile(fileId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		} //deletefile end	
	
	   
   public static void deletefile2(String pathName){  // delete file from absolute path
       File tempFile =new File( pathName.trim());  
	   
	   try {
		   String fileName = tempFile.getName();
		   System.out.println(fileName);
		   String fileId = FileChunkMappings.getIdByFilename(fileName);
		   System.out.println(fileId);
		   ArrayList<Chunk> chunklist = FileChunkMappings
					.getChunksByFile(fileId);
			ChunkIndexTable.DeleteChunks(chunklist);
			FileChunkMappings.deleteFile(fileId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	} //deletefile2 end
   
	private static void downloadfile(String filename,String filepath) {
		
		File fileToSave = new File(filepath);
		if (!fileToSave.exists()) {
			try {
				fileToSave.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			FileOutputStream fos = new FileOutputStream(
					fileToSave);
			fos.write(ChunkedFile.retriveFileData(FileChunkMappings.getIdByFilename(filename)));
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	
}
