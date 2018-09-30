package tech.pcloud.configure.center.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import tech.pcloud.configure.center.client.Client;
import tech.pcloud.configure.center.client.config.ClientProperties;

import java.util.Map;

/**
 * @ClassName ConfigureCenterPropertiesPostProcessor
 * @Author pandong
 * @Date 2018/9/18 11:07
 **/
public class ConfigureCenterPropertiesPostProcessor implements EnvironmentPostProcessor {
    private static final String PROPERTY_SOURCE_NAME = "ConfigCenterProperties";
    private static final String PROPERTY_SERVICE_NAME = "configure.center.serviceName";
    private static final String PROPERTY_PRIVTEKEY = "configure.center.privateKey";
    private static final String PROPERTY_PUBLICKEY = "configure.center.publicKey";
    private static final String PROPERTY_HOST = "configure.center.host";
    private static final String PROPERTY_PORT = "configure.center.port";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment configurableEnvironment, SpringApplication springApplication) {
        ClientProperties properties = getProprttise(configurableEnvironment);
        Client client = new Client(properties);
        client.connection(configurableEnvironment.getActiveProfiles());
        Map<String, Object> propertySource = client.getConfigureMapping();
        configurableEnvironment.getPropertySources().addFirst(new MapPropertySource(PROPERTY_SOURCE_NAME, propertySource));
    }

    private ClientProperties getProprttise(ConfigurableEnvironment configurableEnvironment) {
        String serviceName = configurableEnvironment.getProperty(PROPERTY_SERVICE_NAME);
        String privateKey = configurableEnvironment.getProperty(PROPERTY_PRIVTEKEY);
        String publicKey = configurableEnvironment.getProperty(PROPERTY_PUBLICKEY);
        String host = configurableEnvironment.getProperty(PROPERTY_HOST);
        int port = configurableEnvironment.getProperty(PROPERTY_PORT, Integer.class, 9902);
        ClientProperties properties = new ClientProperties();
        properties.setPublicKey(publicKey);
        properties.setPrivateKey(privateKey);
        properties.setService(serviceName);
        properties.setPort(port);
        properties.setHost(host);

        return properties;
    }
}
