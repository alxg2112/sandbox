package com.alxg2112.sandbox.crypto.signature.ver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import com.alxg2112.sandbox.crypto.signature.Algorithm;
import com.google.common.base.Charsets;

/**
 * @author Alexander Gryshchenko
 */
class SignatureVerifierImpl implements SignatureVerifier {

	private final Algorithm algorithm;
	private final PublicKey publicKey;

	SignatureVerifierImpl(Algorithm algorithm, Path publicKeyPath) {
		this.algorithm = algorithm;
		try {
			this.publicKey = loadKey(publicKeyPath);
		} catch (Exception e) {
			throw new SignatureVerifierInitializationException(e);
		}
	}

	private PublicKey loadKey(Path publicKeyPath) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] publicKeyBytes = Files.readAllBytes(publicKeyPath);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm.getKeyAlgorithm());
		return keyFactory.generatePublic(keySpec);
	}

	@Override
	public boolean verify(String document, byte[] sign) {
		try {
			return verifySignature(document, sign);
		} catch (Exception e) {
			throw new SignatureVerificationException(e);
		}
	}

	private boolean verifySignature(String document, byte[] sign) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		Signature signature = Signature.getInstance(algorithm.getSignatureAlgorithm());
		signature.initVerify(publicKey);
		signature.update(document.getBytes(Charsets.UTF_8));
		return signature.verify(sign);
	}
}
