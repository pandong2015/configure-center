package tecp.pcloud.configure.center.core.util.security;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @ClassName DHUtil
 * @Author pandong
 * @Date 2018/9/17 14:38
 **/
public class DHUtil {
    public static final String ALGORITHM = "DH";
    public static final String SECRET_ALGORITHM = "DES";

    private static final int KEY_SIZE = 2048;

    /**
     * 初始化甲方密钥
     *
     * @return DHKeyPair
     * @throws Exception
     */
    public static DHKeyPair initKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator
                .getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
        DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
        return new DHKeyPair(publicKey, privateKey);
    }

    /**
     * 初始化乙方密钥
     *
     * @param key 甲方公钥
     * @return DHKeyPair
     * @throws Exception
     */
    public static DHKeyPair initKey(String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        return initKey(keyBytes);
    }

    /**
     * 初始化乙方密钥
     *
     * @param key 甲方公钥
     * @return DHKeyPair
     * @throws Exception
     */
    public static DHKeyPair initKey(byte[] key) throws Exception {
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        DHParameterSpec dhParamSpec = ((DHPublicKey) pubKey).getParams();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator
                .getInstance(keyFactory.getAlgorithm());
        keyPairGenerator.initialize(dhParamSpec);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
        DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
        return new DHKeyPair(publicKey, privateKey);
    }

    /**
     * 加密
     *
     * @param data       待加密数据
     * @param publicKey  甲方公钥
     * @param privateKey 乙方私钥
     * @return byte[]
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, String publicKey,
                                 String privateKey) throws Exception {
        return encrypt(data, Base64.getDecoder().decode(publicKey), Base64.getDecoder().decode(privateKey));
    }

    /**
     * 加密
     *
     * @param data       待加密数据
     * @param publicKey  甲方公钥
     * @param privateKey 乙方私钥
     * @return byte[]
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] publicKey,
                                 byte[] privateKey) throws Exception {
        // 生成本地密钥
        SecretKey secretKey = getSecretKey(publicKey, privateKey);
        // 数据加密
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     * @param data       待解密数据
     * @param publicKey  乙方公钥
     * @param privateKey 乙方私钥
     * @return byte[]
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, String publicKey,
                                 String privateKey) throws Exception {
        return decrypt(data, Base64.getDecoder().decode(publicKey), Base64.getDecoder().decode(privateKey));
    }

    /**
     * 解密
     *
     * @param data       待解密数据
     * @param publicKey  乙方公钥
     * @param privateKey 乙方私钥
     * @return byte[]
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] publicKey,
                                 byte[] privateKey) throws Exception {
        // 生成本地密钥
        SecretKey secretKey = getSecretKey(publicKey, privateKey);
        // 数据解密
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    /**
     * 构建密钥
     *
     * @param publicKey  公钥
     * @param privateKey 私钥
     * @return SecretKey
     * @throws Exception
     */
    private static SecretKey getSecretKey(byte[] publicKey, byte[] privateKey)
            throws Exception {
        // 初始化公钥
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

        // 初始化私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
        Key priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        KeyAgreement keyAgree = KeyAgreement.getInstance(keyFactory
                .getAlgorithm());
        keyAgree.init(priKey);
        keyAgree.doPhase(pubKey, true);

        // 生成本地密钥
        SecretKey secretKey = keyAgree.generateSecret(SECRET_ALGORITHM);
        return secretKey;
    }
}
