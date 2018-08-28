package com.alxg2112.sandbox.crypto.signature.ver;

import java.nio.file.Path;

import com.alxg2112.sandbox.crypto.signature.Algorithm;

/**
 * @author Alexander Gryshchenko
 */
public interface SignatureVerifier {

	static SignatureVerifier newInstance(Algorithm algorithm, Path publicKeyPath) {
		return new SignatureVerifierImpl(algorithm, publicKeyPath);
	}

	boolean verify(String document, byte[] sign);
}
