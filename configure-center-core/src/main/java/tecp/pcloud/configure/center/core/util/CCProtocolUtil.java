package tecp.pcloud.configure.center.core.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.protobuf.ByteString;
import tecp.pcloud.configure.center.protocol.CCProtocol;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CCProtocolUtil
 * @Author pandong
 * @Date 2018/9/30 10:18
 **/
public class CCProtocolUtil {
    public static final String HEADER_SERVICE_NAME = "ServiceName";
    public static final String HEADER_PROFILE_NAME = "ProfileName";

    public static Map<String, List<String>> headerMap(CCProtocol.ProtocolHeader header) {
        Map<String, List<String>> headers = Maps.newHashMap();
        header.getHeadersList().stream()
                .forEach(e -> {
                    String key = e.getName().toStringUtf8();
                    String value = e.getValue().toStringUtf8();
                    List<String> values = headers.get(key);
                    if (values == null) {
                        values = Lists.newArrayList();
                        headers.put(key, values);
                    }
                    values.add(value);
                });
        return headers;
    }

    public static String getHeaderValue(Map<String, List<String>> headers, String name) {
        if (!headers.containsKey(name)) {
            return null;
        }
        List<String> values = headers.get(name);
        if (values.isEmpty()) {
            return null;
        }
        return values.get(0);
    }

    public static CCProtocol.ProtocolHeader setHeader(CCProtocol.ProtocolHeader header, String name, String value) {
        return CCProtocol.ProtocolHeader.newBuilder()
                .addAllHeaders(header.getHeadersList())
                .addHeaders(CCProtocol.DataEntry.newBuilder()
                        .setName(ByteString.copyFromUtf8(name))
                        .setValue(ByteString.copyFromUtf8(value))
                        .build())
                .build();
    }
}
