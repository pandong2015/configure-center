package tecp.pcloud.configure.center.core.util.security;

import tech.pcloud.configure.center.server.util.Global;
import tecp.pcloud.configure.center.core.exception.DESException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

public class DESUtil {
    public static final String ALGORITHM = "DES";

    private static Key toKey(String key) throws Exception {
        return toKey(Base64.getDecoder().decode(key));
    }

    private static Key toKey(byte[] key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(dks);
        return secretKey;
    }

    public static byte[] decrypt(String data, String key) {
        return decrypt(data.getBytes(Charset.forName("UTF-8")), key);
    }

    public static byte[] decrypt(byte[] data, String key) {
        try {
            Key k = toKey(key);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, k);

            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new DESException(e.getMessage(), e);
        }
    }

    public static byte[] encrypt(String data, String key) {
        return encrypt(data.getBytes(Charset.forName("UTF-8")), key);
    }

    public static byte[] encrypt(byte[] data, String key) {
        try {
            Key k = toKey(key);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, k);

            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new DESException(e.getMessage(), e);
        }
    }

    public static String initKey() {
        return initKey(null);
    }

    public static String initKey(String seed) {
        try {
            SecureRandom secureRandom = null;

            if (seed != null) {
                secureRandom = new SecureRandom(Base64.getDecoder().decode(seed));
            } else {
                secureRandom = new SecureRandom();
            }

            KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
            kg.init(secureRandom);

            SecretKey secretKey = kg.generateKey();

            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (Exception e) {
            throw new DESException(e.getMessage(), e);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(DESUtil.encrypt("123456", Global.SECURITY_KEY));
    }
}
