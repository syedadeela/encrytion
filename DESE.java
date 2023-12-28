import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DESE {
    public static void main(String[] args) throws Exception {
        // Generate a DES key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        SecretKey secretKey = keyGenerator.generateKey();

        // Create a DES cipher object
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        // Message to be encrypted
        String message = "hi qadimee!";

        // Encryption
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));

        // Display encrypted message
        System.out.println("Encrypted Message: " + Base64.getEncoder().encodeToString(encryptedBytes));

        // Decryption
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        // Display decrypted message
        System.out.println("Decrypted Message: " + new String(decryptedBytes, StandardCharsets.UTF_8));
    }
}
