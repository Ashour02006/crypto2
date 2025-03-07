/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject3;

import java.util.*;

/**
 *
 * @author Lenovo
 */
public class MonoalphabeticCipherCryptanalysis {
    private static final String ENGLISH_FREQ_ORDER = "ETAOINSHRDLCUMWFGYPBVKJXQZ";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter encrypted text: ");
        String encryptedText = scanner.nextLine().toUpperCase();

        System.out.println("\nFrequency Analysis Decryption Suggestions:");
        List<String> decryptedOptions = frequencyAnalysisDecrypt(encryptedText);
        
        for (int i = 0; i < decryptedOptions.size(); i++) {
            System.out.println((i + 1) + ". " + decryptedOptions.get(i));
        }
    }

    private static List<String> frequencyAnalysisDecrypt(String encryptedText) {
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : encryptedText.toCharArray()) {
            if (Character.isLetter(c)) {
                freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
            }
        }

        List<Map.Entry<Character, Integer>> sortedFreq = new ArrayList<>(freqMap.entrySet());
        sortedFreq.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        Map<Character, Character> substitutionMap = new HashMap<>();
        for (int i = 0; i < sortedFreq.size() && i < ENGLISH_FREQ_ORDER.length(); i++) {
            substitutionMap.put(sortedFreq.get(i).getKey(), ENGLISH_FREQ_ORDER.charAt(i));
        }

        List<String> possibleDecryptions = new ArrayList<>();
        for (int i = 0; i < 3; i++) { // Generate top 3 decryption suggestions
            StringBuilder decryptedText = new StringBuilder();
            for (char c : encryptedText.toCharArray()) {
                decryptedText.append(substitutionMap.getOrDefault(c, c));
            }
            possibleDecryptions.add(decryptedText.toString());
        }
        return possibleDecryptions;
    }
}
