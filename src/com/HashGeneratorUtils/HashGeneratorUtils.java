package com.HashGeneratorUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import com.deduplication.ChunkHash;

public class HashGeneratorUtils {
	public static void genrateMD5(File file) throws Exception {
		hashFile(file, "MD5");
	}

	private static void hashFile(File file, String algorithm) throws Exception {
		try (FileInputStream inputStream = new FileInputStream(file)) {
			MessageDigest digest = MessageDigest.getInstance(algorithm);

			byte[] bytesBuffer = new byte[131072];
			int bytesRead = -1;
			boolean append = false;
			
			File f1=new File("chunklist"); boolean b1 = f1.mkdirs();
			File f2=new File("chunkedfile"); boolean b2 = f2.mkdirs();
			
			while ((bytesRead = inputStream.read(bytesBuffer)) != -1) {
				digest.update(bytesBuffer);
				byte[] hashedBytes = digest.digest();
				ChunkHash.writeChunk(bytesBuffer,convertByteArrayToHexString(hashedBytes));
				ChunkHash.generateTxt("chunklist/list",convertByteArrayToHexString(hashedBytes),append);
				append = true;
				System.out.println(bytesRead);
				System.out.println(convertByteArrayToHexString(hashedBytes));
			}

			//return convertByteArrayToHexString(hashedBytes);
		} catch (NoSuchAlgorithmException | IOException ex) {
			throw new Exception("Could not generate hash from file", ex);
		}
	}

	private static String convertByteArrayToHexString(byte[] hashedBytes) {
		// TODO Auto-generated method stub
		return DatatypeConverter.printHexBinary(hashedBytes);
	}
}
