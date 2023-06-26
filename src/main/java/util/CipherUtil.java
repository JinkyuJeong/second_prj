package util;

import java.security.Key;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class CipherUtil {
	private static byte[] randomKey;
	private final static byte[] iv = new byte[] {(byte)0x8E, 0x12, 0x39, (byte)0x9, 0x07, 0x72, 0x6F, (byte)0x5A, (byte)0x8E, 0x12, 0x39, (byte)0x9, 0x07, 0x72, 0x6F, (byte)0x5A};
	static Cipher cipher; 
	static {
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //CBC => 초기화벡터 필요
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public String makehash(String plain, String algo) throws Exception {
		MessageDigest md = MessageDigest.getInstance(algo);
		byte[] plainByte = plain.getBytes();
		byte[] hashByte = md.digest(plainByte);
		return byteToHex(hashByte);
	}	
	private String byteToHex(byte[] hashByte) {
		if(hashByte==null) return null;
		String str = "";
		for(byte b : hashByte) {
			str += String.format("%02X", b); 
		}
		return str;
	}	
}
