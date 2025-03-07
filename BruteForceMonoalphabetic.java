/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bruteforcemonoalphabetic;
import java.util.*;
/**
 *
 * @author Lenovo
 */
public class BruteForceMonoalphabetic {

      private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int MAX_ATTEMPTS = 1000; // Limit brute force attempts

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter encrypted text: ");
        String encryptedText = scanner.nextLine().toUpperCase();

        System.out.println("Brute Force Attack Results:");
        bruteForceDecrypt(encryptedText);
    }

    private static void bruteForceDecrypt(String encryptedText) {
        Set<String> testedKeys = new HashSet<>();
        Random random = new Random();

        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            String key = shuffleAlphabet(random);
            if (testedKeys.contains(key)) continue; // Avoid duplicate keys

            testedKeys.add(key);
            String decrypted = decryptWithKey(encryptedText, key);
            System.out.println("Key: " + key + " => " + decrypted);
        }
    }

    private static String decryptWithKey(String text, String key) {
        Map<Character, Character> decryptMap = new HashMap<>();
        for (int i = 0; i < ALPHABET.length(); i++) {
            decryptMap.put(key.charAt(i), ALPHABET.charAt(i));
        }
        
        StringBuilder decryptedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            decryptedText.append(decryptMap.containsKey(c) ? decryptMap.get(c) : c);

        }
        return decryptedText.toString();
    }

    private static String shuffleAlphabet(Random random) {
        List<Character> letters = new ArrayList<>();
        for (char c : ALPHABET.toCharArray()) {
            letters.add(c);
        }
        Collections.shuffle(letters, random);
        StringBuilder shuffled = new StringBuilder();
        for (char c : letters) {
            shuffled.append(c);
        }
        return shuffled.toString();
    }
    
}
