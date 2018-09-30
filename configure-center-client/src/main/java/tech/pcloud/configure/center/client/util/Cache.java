package tech.pcloud.configure.center.client.util;

import tecp.pcloud.configure.center.protocol.CCProtocol;

/**
 * @ClassName Cache
 * @Author pandong
 * @Date 2018/9/18 13:11
 **/
public class Cache {
    private CCProtocol.ProtocolBody protocol;

    public CCProtocol.ProtocolBody getProtocol() {
        return protocol;
    }

    public void setProtocol(CCProtocol.ProtocolBody protocol) {
        this.protocol = protocol;
    }
}
