import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Encrypt {
    // 暗号化するテキストを入力
    private static final String plainText = "";

    private static final String ALGO = "AES/CBC/PKCS5Padding";
    private static final String PASSWORD = "";
    private static final String SALT = "";

    public static void main(String[] args) throws Exception {
        
        // Keyを生成
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] saltBytes = Base64.getMimeDecoder().decode(SALT);
        KeySpec spec = new PBEKeySpec(PASSWORD.toCharArray(), saltBytes, 65536, 256);
        SecretKeySpec key = new SsecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

        // IVを生成
        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // 暗号化
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
        String ivBase64 = Base64.getEncoder().encodeToString(iv);
        String keyBase64 = Base64.getEncoder().encodeToString(key.getEncoded());

        System.out.println("暗号化された文字列: " + encryptedText);
        System.out.println("IV: " + ivBase64);
        System.out.println("キー: " + keyBase64);
    }
}
