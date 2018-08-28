package com.alxg2112.sandbox.hashing;

import java.util.Base64;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * @author Alexander Gryshchenko
 */
public class HashingPoC {

	public static void main(String[] args) {
		String query = "something";
		HashFunction hashFunction = Hashing.hmacSha256("secretToken".getBytes());
		HashCode hashCode = hashFunction.hashBytes(query.getBytes());
		String signature = hashCode.toString();
		String base64Encoded = Base64.getEncoder().encodeToString(signature.getBytes());
		System.out.println(base64Encoded);
	}
}
