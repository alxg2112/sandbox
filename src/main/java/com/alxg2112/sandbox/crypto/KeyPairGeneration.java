package com.alxg2112.sandbox.crypto;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.security.*;

/**
 * @author Alexander Gryshchenko
 */
public class KeyPairGeneration {

	public static void main(String[] args) throws NoSuchAlgorithmException, URISyntaxException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
		SecureRandom random = SecureRandom.getInstanceStrong();

		keyGen.initialize(112, random);

		KeyPair keyPair = keyGen.generateKeyPair();
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();

		try (OutputStream privateKeyFos = new FileOutputStream("private.key");
			 OutputStream publicKeyFos = new FileOutputStream("public.key")) {
			privateKeyFos.write(privateKey.getEncoded());
			publicKeyFos.write(publicKey.getEncoded());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
