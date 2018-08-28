package com.alxg2112.sandbox.palindrome;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 14.09.2017.
 */
public class Solution {

    /**
     * Searches for solution and calculates time elapsed.
     */
    public static void solve() {
        System.out.printf("Starting solution...%n");
        long startTime = System.currentTimeMillis();
        // First, we check palindromes in (10,000,000,000; 1,000,000,000) range
        if (!findSolution(true)) {
            // If not found, check in (1,000,000,000; 100,000,000) range
            if (!findSolution(false)) {
                System.out.printf("Cannot find a solution!%n");
            }
        }
        System.out.printf("Time elapsed: %s milliseconds.", System.currentTimeMillis() - startTime);
    }

    /**
     * Searches for solution and returns true if found.
     */
    public static boolean findSolution(boolean includeLastDigit) {
        for (int prefix = 99999; prefix > 10000; prefix--) {
            // Don't check palindromes that are even
            if ((prefix / 10000) % 2 == 0) {
                prefix -= 10000;
            }
            Long palindrome = makePalindrome(prefix, includeLastDigit);
            List<Long> primeFactors = primeFactors(palindrome);
            if (primeFactors.size() == 2 && primeFactors.stream().allMatch(factor -> factor > 10000)) {
                System.out.printf("Solution is found: %s = %s * %s.%n",
                        palindrome,
                        primeFactors.get(0),
                        primeFactors.get(1));
                return true;
            }
        }
        return false;
    }


    /**
     * Returns a palindrome number by mirroring given number.
     *
     * @param prefix           a number to make palindrome from
     * @param includeLastDigit determines whether last digit will
     *                         be included into suffix (123321 VS 12321)
     * @return
     */
    public static Long makePalindrome(int prefix, boolean includeLastDigit) {
        String suffix = new StringBuffer(String.valueOf(includeLastDigit ? prefix : prefix / 10)).reverse().toString();
        return Long.parseLong(prefix + suffix);
    }

    /**
     * Decomposes a number into prime factors.
     *
     * @param number a number to factorize
     * @return a list of prime factors
     */
    public static List<Long> primeFactors(Long number) {
        long n = number;
        List<Long> factors = new ArrayList<>();
        for (long i = 2; i <= n / i; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        if (n > 1) {
            factors.add(n);
        }
        return factors;
    }

    public static void main(String[] args) {
        solve();
    }
}