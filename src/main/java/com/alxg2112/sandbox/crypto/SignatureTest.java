package com.alxg2112.sandbox.crypto;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import com.alxg2112.sandbox.crypto.signature.Algorithm;
import com.alxg2112.sandbox.crypto.signature.gen.SignatureGenerator;
import com.alxg2112.sandbox.crypto.signature.ver.SignatureVerifier;

/**
 * @author Alexander Gryshchenko
 */
public class SignatureTest {

	public static void main(String[] args) {
		Path privateKeyPath = Paths.get("private.key");
		SignatureGenerator signatureGenerator = SignatureGenerator.newInstance(Algorithm.ECDSA, privateKeyPath);

		Path publicKeyPath = Paths.get("public.key");
		SignatureVerifier signatureVerifier = SignatureVerifier.newInstance(Algorithm.ECDSA, publicKeyPath);

		long licenseEndTimestamp = Instant.now().plus(30L, ChronoUnit.DAYS).toEpochMilli();
		String licenseKeyPrototype = "hardwareID" + licenseEndTimestamp;

		byte[] signature = signatureGenerator.sign(licenseKeyPrototype);
		String signatureBase64Encoded = Base64.getEncoder().encodeToString(signature);

		System.out.println("Signature: " + signatureBase64Encoded);

		System.out.println("True license verified: " + signatureVerifier.verify(licenseKeyPrototype, Base64.getDecoder().decode(signatureBase64Encoded)));

		String fakeLicenseKeyPrototype = "hardwareID" + Instant.now().toEpochMilli();

		System.out.println("Fake license verified: " + signatureVerifier.verify(fakeLicenseKeyPrototype, Base64.getDecoder().decode(signatureBase64Encoded)));
	}
}
