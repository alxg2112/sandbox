package com.alxg2112.sandbox.crypto.signature.gen;

import java.nio.file.Path;

import com.alxg2112.sandbox.crypto.signature.Algorithm;

/**
 * @author Alexander Gryshchenko
 */
public interface SignatureGenerator {

	static SignatureGenerator newInstance(Algorithm algorithm, Path privateKeyPath) {
		return new SignatureGeneratorImpl(algorithm, privateKeyPath);
	}

	byte[] sign(String document);
}
