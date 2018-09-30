package tech.pcloud.configure.center.server.server.handler;

import com.google.protobuf.ByteString;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import tech.pcloud.configure.center.server.server.ManagerFactory;
import tecp.pcloud.configure.center.core.model.po.Configure;
import tecp.pcloud.configure.center.core.model.po.Profile;
import tecp.pcloud.configure.center.core.model.po.Service;
import tecp.pcloud.configure.center.core.util.CCProtocolUtil;
import tecp.pcloud.configure.center.protocol.CCProtocol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ServerChannelHandler
 * @Author pandong
 * @Date 2018/9/17 16:20
 **/
public class ServerChannelHandler extends SimpleChannelInboundHandler<CCProtocol.Protocol> {
    private ManagerFactory managerFactory;

    public ServerChannelHandler(ManagerFactory managerFactory) {
        this.managerFactory = managerFactory;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CCProtocol.Protocol protocol) throws Exception {
        Map<String, List<String>> headerMap = CCProtocolUtil.headerMap(protocol.getHeader());
        Service service = managerFactory.getServiceMapper().loadByName(CCProtocolUtil.getHeaderValue(headerMap, CCProtocolUtil.HEADER_SERVICE_NAME));
        List<Profile> profiles = headerMap.get(CCProtocolUtil.HEADER_PROFILE_NAME).stream()
                .map(s -> managerFactory.getProfileMapper().loadByName(s))
                .filter(p -> p != null)
                .collect(Collectors.toList());
        Map<String, Configure> configures = new HashMap<String, Configure>();
        profiles.forEach(p -> addConfig(managerFactory.getConfigureManager().selectServiceAndProfile(service.getId(), p.getId()), configures));
        CCProtocol.ProtocolBody.Builder bodyBuilder = CCProtocol.ProtocolBody.newBuilder();
        if (configures != null && !configures.isEmpty()) {
            configures.entrySet().stream()
                    .map(entry -> CCProtocol.PropertyEntry.newBuilder()
                            .setData(CCProtocol.DataEntry.newBuilder()
                                    .setName(ByteString.copyFromUtf8(entry.getKey()))
                                    .setValue(entry.getValue() == null ? ByteString.EMPTY : ByteString.copyFromUtf8(entry.getValue().getValue()))
                                    .build())
                            .setType(CCProtocol.PropertyEntryType.valueOf(entry.getValue().getType()))
                            .build())
                    .forEach(entry -> {
                        bodyBuilder.addProperties(entry);
                    });
        }
        CCProtocol.ProtocolBody body = bodyBuilder.build();
        CCProtocol.Protocol newProtocol = CCProtocol.Protocol.newBuilder()
                .setHeader(protocol.getHeader())
                .setBody(body)
                .build();
        channelHandlerContext.writeAndFlush(newProtocol);
    }

    private void addConfig(List<Configure> configures, Map<String, Configure> configureMap) {
        configures.forEach(c -> configureMap.put(c.getName(), c));
    }
}
