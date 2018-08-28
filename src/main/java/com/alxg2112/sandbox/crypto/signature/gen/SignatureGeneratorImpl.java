package com.alxg2112.sandbox.crypto.signature.gen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import com.alxg2112.sandbox.crypto.signature.Algorithm;
import com.alxg2112.sandbox.crypto.signature.ver.SignatureVerifierInitializationException;
import com.google.common.base.Charsets;

/**
 * @author Alexander Gryshchenko
 */
class SignatureGeneratorImpl implements SignatureGenerator {

	private final Algorithm algorithm;
	private final PrivateKey privateKey;

	SignatureGeneratorImpl(Algorithm algorithm, Path privateKeyPath) {
		try {
			this.algorithm = algorithm;
			this.privateKey = loadKey(privateKeyPath);
		} catch (Exception e) {
			throw new SignatureVerifierInitializationException(e);
		}
	}

	private PrivateKey loadKey(Path privateKeyPath) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] privateKeyBytes = Files.readAllBytes(privateKeyPath);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm.getKeyAlgorithm());
		return keyFactory.generatePrivate(keySpec);
	}

	@Override
	public byte[] sign(String document) {
		try {
			return createSignature(document);
		} catch (Exception e) {
			throw new SignatureGenerationException(e);
		}
	}

	private byte[] createSignature(String document) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		Signature signature = Signature.getInstance(algorithm.getSignatureAlgorithm());
		signature.initSign(privateKey);
		signature.update(document.getBytes(Charsets.UTF_8));
		return signature.sign();
	}
}
