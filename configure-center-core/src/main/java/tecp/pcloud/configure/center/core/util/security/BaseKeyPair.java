package tecp.pcloud.configure.center.core.util.security;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * @ClassName BaseKeyPair
 * @Author pandong
 * @Date 2018/9/17 14:43
 **/
public abstract class BaseKeyPair {
    public abstract PublicKey getPublicKey();
    public abstract PrivateKey getPrivateKey();

    public String getPublicKeyWithBase64() {
        return Base64.getEncoder().encodeToString(getPublicKey().getEncoded());
    }

    public String getPrivateKeyWithBase64() {
        return Base64.getEncoder().encodeToString(getPrivateKey().getEncoded());
    }
}
