package com.HashGeneratorUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.xml.bind.DatatypeConverter;

import com.deduplication.ChunkHash;
import com.deduplication.FileChunk;

public class HashGeneratorUtils {
	public static void genrateMD5(File file) throws Exception {
		hashFile(file, "MD5");
	}

	private static void hashFile(File file, String algorithm) throws Exception {
		try (FileInputStream inputStream = new FileInputStream(file)) {
			MessageDigest digest = MessageDigest.getInstance(algorithm);

			byte[] fileBuffer = new byte[(int) file.length()];
			byte[] chunkBuffer = new byte[128*1024];
			int bytesRead = -1;
			boolean append = false;
			
			File f1=new File("chunklist"); boolean b1 = f1.mkdirs();
			File f2=new File("chunkedfile"); boolean b2 = f2.mkdirs();

			digest.update(fileBuffer);
			String fileHashID = convertByteArrayToHexString(digest.digest());
			ArrayList<String> hashlist = new ArrayList<String>();
			String hashStr;
			while ((bytesRead = inputStream.read(chunkBuffer)) != -1) {
				digest.update(chunkBuffer);
				byte[] hashedBytes = digest.digest();
				hashStr = convertByteArrayToHexString(hashedBytes);
				ChunkHash.writeChunk(hashStr,hashedBytes);
				hashlist.add(hashStr);
				//ChunkHash.generateTxt("chunklist/list",convertByteArrayToHexString(hashedBytes),append);
				//append = true;
				//System.out.println(bytesRead);
				//System.out.println(convertByteArrayToHexString(hashedBytes));
			}
			FileChunk.writeFileHash(fileHashID, hashlist);
			//return convertByteArrayToHexString(hashedBytes);
		} catch (NoSuchAlgorithmException | IOException ex) {
			throw new Exception("Could not generate hash from file", ex);
		}
	}

	private static String convertByteArrayToHexString(byte[] hashedBytes) {
		return DatatypeConverter.printHexBinary(hashedBytes);
	}
}
