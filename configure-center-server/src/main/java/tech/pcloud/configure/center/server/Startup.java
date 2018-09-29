package tech.pcloud.configure.center.server;

import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @ClassName Startup
 * @author pandong
 * @Date 2018年9月21日 上午9:38:19
 */
public class Startup {
    public static void main(String[] args) {
        System.setProperty("jdk.crypto.KeyAgreement.legacyKDF", "true");
        new SpringApplicationBuilder()
                .sources(AppConfig.class)
                .bannerMode(Banner.Mode.OFF)
                .build()
                .run(args);
    }
}
