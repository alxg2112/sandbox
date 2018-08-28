package com.alxg2112.sandbox.crypto.signature;

/**
 * @author Alexander Gryshchenko
 */
public enum Algorithm {
	ECDSA("EC", "SHA384withECDSA");

	private final String keyAlgorithm;
	private final String signatureAlgorithm;

	Algorithm(String keyAlgorithm, String signatureAlgorithm) {
		this.keyAlgorithm = keyAlgorithm;
		this.signatureAlgorithm = signatureAlgorithm;
	}

	public String getKeyAlgorithm() {
		return keyAlgorithm;
	}

	public String getSignatureAlgorithm() {
		return signatureAlgorithm;
	}
}
