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
public class PlayfairCipher {
     private char[][] matrix = new char[5][5];
    private static final String ALPHABET = "ABCDEFGHIKLMNOPQRSTUVWXYZ"; // J is merged with I

    public PlayfairCipher(String key) {
        generateMatrix(key);
    }

    private void generateMatrix(String key) {
        Set<Character> seen = new LinkedHashSet<>();
        key = key.toUpperCase().replaceAll("J", "I").replaceAll("[^A-Z]", "");
        for (char c : key.toCharArray()) seen.add(c);
        for (char c : ALPHABET.toCharArray()) seen.add(c);

        Iterator<Character> iter = seen.iterator();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = iter.next();
            }
        }
    }

    private String prepareText(String text, boolean encrypt) {
        text = text.toUpperCase().replaceAll("J", "I").replaceAll("[^A-Z]", "");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            sb.append(text.charAt(i));
            if (i < text.length() - 1 && text.charAt(i) == text.charAt(i + 1)) {
                sb.append('X');
            }
        }
        if (sb.length() % 2 != 0) sb.append('X');
        return sb.toString();
    }

    private String processText(String text, boolean encrypt) {
        text = prepareText(text, encrypt);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i += 2) {
            result.append(processPair(text.charAt(i), text.charAt(i + 1), encrypt));
        }
        return result.toString();
    }

    private String processPair(char a, char b, boolean encrypt) {
        int shift = encrypt ? 1 : -1;
        int[] posA = findPosition(a), posB = findPosition(b);
        char newA, newB;

        if (posA[0] == posB[0]) { // Same row
            newA = matrix[posA[0]][(posA[1] + shift + 5) % 5];
            newB = matrix[posB[0]][(posB[1] + shift + 5) % 5];
        } else if (posA[1] == posB[1]) { // Same column
            newA = matrix[(posA[0] + shift + 5) % 5][posA[1]];
            newB = matrix[(posB[0] + shift + 5) % 5][posB[1]];
        } else { // Rectangle swap
            newA = matrix[posA[0]][posB[1]];
            newB = matrix[posB[0]][posA[1]];
        }
        return "" + newA + newB;
    }

    private int[] findPosition(char c) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == c) return new int[]{i, j};
            }
        }
        return null;
    }

    public String encrypt(String plaintext) {
        return processText(plaintext, true);
    }

    public String decrypt(String ciphertext) {
        return processText(ciphertext, false);
    }

    public void printMatrix() {
        for (char[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine();
        PlayfairCipher cipher = new PlayfairCipher(keyword);
        cipher.printMatrix();

        System.out.print("Enter text to encrypt: ");
        String plaintext = scanner.nextLine();
        String encrypted = cipher.encrypt(plaintext);
        System.out.println("Encrypted: " + encrypted);

        System.out.print("Enter text to decrypt: ");
        String ciphertext = scanner.nextLine();
        String decrypted = cipher.decrypt(ciphertext);
        System.out.println("Decrypted: " + decrypted);
    }
}
