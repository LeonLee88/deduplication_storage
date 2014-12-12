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

			InputStreamReader is=new InputStreamReader(System.in);
			BufferedReader br=new BufferedReader(is);
			try{
				while(true){
				System.out.println("#################################################");
				System.out.println("              DBLS Command Line UI               ");
				System.out.println("-------------------------------------------------");
				System.out.println("                 *MAIN MENU*                     ");
				System.out.println("                                                 ");		
				System.out.println("                1. LIST FILES                    ");
				System.out.println("                2. UPLOAD                        ");
				System.out.println("                3. REMOVE                        ");
				System.out.println("                4. DOWNLOAD                      ");
				System.out.println("                5. QUIT                          ");
				System.out.println("-------------------------------------------------");
				System.out.print(" Command # >> ");	
				
            		switch ((String) br.readLine()) {
						case "1": { jump1();break; }	//list file
						case "2": { jump2();break; }	//upload
						case "3": { jump3();break; }	//remove
						case "4": { jump4();break; }	//download
						case "5": { jump5();break; }	//quit
            		} //switch end
				} //while end
			}catch(IOException e){
				System.out.println("System Error!");
				e.printStackTrace();
			}
//			finally{
//	            try{
//	                is.close();
//	                br.close();
//	            }catch(IOException e){
//	                System.out.println("Stream Close Error");
//	                e.printStackTrace();
//	            }
//	        }
			
	}	// main end
	
	public static void jump1() {
	// List File
		System.out.println("-------------------------------------------------");
		System.out.println("               *FILE LIST*                       ");
		return;
	}
	
	public static void jump2() {
	// Upload File	
		System.out.println("-------------------------------------------------");
		System.out.println(" # UPLOAD: Please ENTER the directory of file");
		System.out.print(" #   Directory >> ");
		InputStreamReader is=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(is);
        String filename;
        try{
            filename=(String) br.readLine();
            	uploadfile(filename);
        		System.out.println(" # Upload File Success!");
        		System.out.println();
            	return;
        }catch(IOException e){
            System.out.println("System Error!");
            e.printStackTrace();
        }
//        finally{
//            try{
//                is.close();
//                br.close();
//            }catch(IOException e){
//                System.out.println("Stream Close Error");
//                e.printStackTrace();
//            }
//        } 
	} // jump2 end
	
	public static void jump3() {
	// Remove File
		System.out.println("-------------------------------------------------");
		System.out.println(" # REMOVE: Please ENTER the Filename");
		System.out.print(" #   Filename >> ");
		InputStreamReader is=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(is);
        String filename;
        try{
            filename=(String) br.readLine();
            	removefile(filename);
        		System.out.println(" # Remove File Success!");
        		System.out.println();
            	return;
        }catch(IOException e){
            System.out.println("System Error!");
            e.printStackTrace();
        }
//        finally{
//            try{
//                is.close();
//                br.close();
//            }catch(IOException e){
//                System.out.println("Stream Close Error");
//                e.printStackTrace();
//            }
//        } 
	} // jump3 end
	
	public static void jump4() {
	// Download File
		System.out.println("-------------------------------------------------");
		InputStreamReader is=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(is);
        String filename;
        String filepath;
        try{
        	System.out.println(" # DOWNLOAD: Please ENTER the Source File Name");
        	System.out.print(" #   Source Filename >> ");
            filename=(String) br.readLine();
            System.out.println(" # DOWNLOAD: Please ENTER the Destination File Directory");
            System.out.print(" #   Dest. Directory >> ");    
            filepath=(String) br.readLine();
               downloadfile(filename,filepath);
       		System.out.println(" # Download File Success!");
       		System.out.println();
               return;
        }catch(IOException e){
            System.out.println("System Error!");
            e.printStackTrace();
        }
//        finally{
//            try{
//                is.close();
//                br.close();
//            }catch(IOException e){
//                System.out.println("Stream Close Error");
//                e.printStackTrace();
//            }
//        } 
	} // jump4 end
	
	public static void jump5() {
		System.exit(0);	
	}
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
		return;
	} //uploadfile end
	
	
	   public static void removefile(String fileName){
		   try {			   
			   String fileId = FileChunkMappings.getIdByFilename(fileName);
			   ArrayList<Chunk> chunklist = FileChunkMappings
						.getChunksByFile(fileId);
				ChunkIndexTable.DeleteChunks(chunklist);
				FileChunkMappings.deleteFile(fileId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			return;
		} //removefile end	
	
	   
//   public static void deletefile2(String pathName){  
//	   // not in use, delete file from absolute path
//       File tempFile =new File( pathName.trim());  
//	   
//	   try {
//		   String fileName = tempFile.getName();
//		   String fileId = FileChunkMappings.getIdByFilename(fileName);
//		   ArrayList<Chunk> chunklist = FileChunkMappings
//					.getChunksByFile(fileId);
//			ChunkIndexTable.DeleteChunks(chunklist);
//			FileChunkMappings.deleteFile(fileId);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
//	} //deletefile2 end
   
	private static void downloadfile(String filename,String filepath) {
	// download file
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
		return;
	} //downloadfile end

	
}
