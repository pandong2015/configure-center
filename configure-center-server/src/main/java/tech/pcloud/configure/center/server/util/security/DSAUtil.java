package tech.pcloud.configure.center.server.util.security;

import java.security.*;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @ClassName DSAUtil
 * @Author pandong
 * @Date 2018/9/17 14:18
 **/
public class DSAUtil {
    public static final String ALGORITHM = "DSA";

    private static final int KEY_SIZE = 1024;
    public static final String DEFAULT_SEED = "0f22507a10bbddd07d8a3082122966e3";

    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        return Base64.getEncoder().encodeToString(sign(data, keyBytes));
    }

    public static byte[] sign(byte[] data, byte[] privateKey) throws Exception {
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
        signature.initSign(priKey);
        signature.update(data);

        return signature.sign();
    }

    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        return verify(data, keyBytes, Base64.getDecoder().decode(sign));
    }

    public static boolean verify(byte[] data, byte[] publicKey, byte[] sign)
            throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
        signature.initVerify(pubKey);
        signature.update(data);
        return signature.verify(sign);
    }

    public static DSAKeyPair initKey(String seed) throws Exception {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(seed.getBytes());
        keygen.initialize(KEY_SIZE, secureRandom);

        KeyPair keys = keygen.genKeyPair();

        DSAPublicKey publicKey = (DSAPublicKey) keys.getPublic();
        DSAPrivateKey privateKey = (DSAPrivateKey) keys.getPrivate();

        return new DSAKeyPair(publicKey, privateKey);
    }

    public static class DSAKeyPair extends BaseKeyPair {
        private DSAPublicKey publicKey;
        private DSAPrivateKey privateKey;

        public DSAKeyPair() {
        }

        public DSAKeyPair(DSAPublicKey publicKey, DSAPrivateKey privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        @Override
        public PublicKey getPublicKey() {
            return publicKey;
        }

        @Override
        public PrivateKey getPrivateKey() {
            return privateKey;
        }

        public void setPublicKey(DSAPublicKey publicKey) {
            this.publicKey = publicKey;
        }

        public void setPrivateKey(DSAPrivateKey privateKey) {
            this.privateKey = privateKey;
        }
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("jdk.crypto.KeyAgreement.legacyKDF", "true");
        DSAKeyPair keyPair = DSAUtil.initKey(DSAUtil.DEFAULT_SEED);
        System.out.println(keyPair.getPrivateKeyWithBase64());
        System.out.println(keyPair.getPublicKeyWithBase64());

    }
}
