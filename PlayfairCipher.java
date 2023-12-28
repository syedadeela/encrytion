import java.util.Scanner;

public class PlayfairCipher {
    private char[][] keyMatrix;

    public PlayfairCipher(String key) {
        generateKeyMatrix(key);
    }

    private void generateKeyMatrix(String key) {
        // Create a matrix to store the key
        keyMatrix = new char[5][5];
        String keyWithoutDuplicates = removeDuplicateChars(key + "ABCDEFGHIKLMNOPQRSTUVWXYZ");

        // Fill the matrix with the key
        int index = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                keyMatrix[i][j] = keyWithoutDuplicates.charAt(index);
                index++;
            }
        }
    }

    private String removeDuplicateChars(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (result.indexOf(String.valueOf(currentChar)) == -1) {
                result.append(currentChar);
            }
        }
        return result.toString();
    }

    private String prepareInput(String input) {
        // Convert to uppercase and replace 'J' with 'I'
        input = input.toUpperCase().replace("J", "I");

        // Add padding for digraphs with the same letter
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i += 2) {
            result.append(input.charAt(i));
            if (i + 1 < input.length()) {
                if (input.charAt(i) == input.charAt(i + 1)) {
                    result.append('X');
                }
                result.append(input.charAt(i + 1));
            }
        }
        return result.toString();
    }

    private String encryptDigraph(char first, char second) {
        StringBuilder result = new StringBuilder();
        int[] position1 = findPosition(first);
        int[] position2 = findPosition(second);

        if (position1[0] == position2[0]) { // Same row
            result.append(keyMatrix[position1[0]][(position1[1] + 1) % 5]);
            result.append(keyMatrix[position2[0]][(position2[1] + 1) % 5]);
        } else if (position1[1] == position2[1]) { // Same column
            result.append(keyMatrix[(position1[0] + 1) % 5][position1[1]]);
            result.append(keyMatrix[(position2[0] + 1) % 5][position2[1]]);
        } else { // Different row and column
            result.append(keyMatrix[position1[0]][position2[1]]);
            result.append(keyMatrix[position2[0]][position1[1]]);
        }

        return result.toString();
    }

    private int[] findPosition(char target) {
        int[] result = new int[2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (keyMatrix[i][j] == target) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return null;
    }

    public String encrypt(String plaintext) {
        StringBuilder ciphertext = new StringBuilder();
        plaintext = prepareInput(plaintext);

        for (int i = 0; i < plaintext.length(); i += 2) {
            char first = plaintext.charAt(i);
            char second = (i + 1 < plaintext.length()) ? plaintext.charAt(i + 1) : 'X';

            ciphertext.append(encryptDigraph(first, second));
        }

        return ciphertext.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the key from the user
        System.out.print("Enter the key: ");
        String key = scanner.nextLine();

        // Create the PlayfairCipher object
        PlayfairCipher playfairCipher = new PlayfairCipher(key);

        // Get the plaintext from the user
        System.out.print("Enter the plaintext: ");
        String plaintext = scanner.nextLine();

        // Encrypt the plaintext
        String ciphertext = playfairCipher.encrypt(plaintext);
        System.out.println("Encrypted Text: " + ciphertext);

        scanner.close();
    }
}
