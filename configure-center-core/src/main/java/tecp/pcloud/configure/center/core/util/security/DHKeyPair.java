package tecp.pcloud.configure.center.core.util.security;

import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;

/**
 * @ClassName DHKeyPair
 * @Author pandong
 * @Date 2018/9/17 14:40
 **/
public class DHKeyPair extends BaseKeyPair {
    private DHPublicKey publicKey;
    private DHPrivateKey privateKey;

    public DHKeyPair() {
    }

    public DHKeyPair(DHPublicKey publicKey, DHPrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Override
    public DHPublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(DHPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public DHPrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(DHPrivateKey privateKey) {
        this.privateKey = privateKey;
    }
}
