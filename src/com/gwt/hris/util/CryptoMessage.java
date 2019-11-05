package com.gwt.hris.util;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author Giulio
 */
public class CryptoMessage {

	// private Key key;

	String key = "7777777777777777";

	String iv = "8888888888888888";

	public String decrypt(String encrypted) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException, InvalidAlgorithmParameterException {
		// Get a cipher object.
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		
		// decode the BASE64 coded message
		byte[] raw = Base64.decodeBase64(encrypted.getBytes());

		// decode the message
		byte[] stringBytes = cipher.doFinal(raw);

		// converts the decoded message to a String
		String clear = new String(stringBytes, "UTF8");
		return clear;
	}
}