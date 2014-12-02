package com.HashGeneratorUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.bind.DatatypeConverter;

import com.deduplication.Chunk;
import com.deduplication.ChunkIndexTable;
import com.deduplication.FileProfile;
import com.deduplication.FileChunkMappings;

public class HashGeneratorUtils {
	private static int CHUNK_SIZE = 28;
	
	public static void genrateMD5(File file, FileProfile fpro) throws Exception {
		fpro.setChunkSize(CHUNK_SIZE*1024);
		hashFile(file, fpro, "MD5");
	}

	private static void hashFile(File file, FileProfile fpro, String algorithm) throws Exception {
		try (FileInputStream inputStream = new FileInputStream(file)) {
			MessageDigest digest = MessageDigest.getInstance(algorithm);

			byte[] fileBuffer = new byte[(int) file.length()];
			byte[] chunkBuffer = new byte[CHUNK_SIZE*1024];
			int bytesRead = -1;
			boolean append = false;

			
			String chunkHash;
			ArrayList<Chunk> fileChunks = new ArrayList<Chunk>();
			
			int i = 0;
			int lastChunkSize = -1;
			while ((bytesRead = inputStream.read(chunkBuffer)) != -1) {
				digest.update(chunkBuffer);
				byte[] hashedBytes = digest.digest();
				chunkHash = convertByteArrayToHexString(hashedBytes);
				ChunkIndexTable index = ChunkIndexTable.getInstance();
				
				if(index.containsKey(chunkHash)){
					int count = Integer.parseInt(index.get(chunkHash)) + 1;
					index.put(chunkHash, Integer.toString(count));
				} else {
					index.put(chunkHash, Integer.toString(1));
					Chunk.saveChunkFile(chunkHash,chunkBuffer);
				}
				
				//Clear the array by filling it with 0
				java.util.Arrays.fill(chunkBuffer, 0,chunkBuffer.length-1,(byte)0);
				lastChunkSize = bytesRead;
				Chunk fileChunk = new Chunk();
				fileChunk.setId(chunkHash);
				//fileChunk.setNum(i);
				fileChunks.add(fileChunk);
				i=i+1;
			}
			
			fpro.setLastChunkSize(lastChunkSize);
			FileChunkMappings.writeFileMapping(fpro, fileChunks);

		} catch (NoSuchAlgorithmException | IOException ex) {
			throw new Exception("Could not generate hash from file", ex);
		}
	}

	private static String convertByteArrayToHexString(byte[] hashedBytes) {
		return DatatypeConverter.printHexBinary(hashedBytes);
	}
}