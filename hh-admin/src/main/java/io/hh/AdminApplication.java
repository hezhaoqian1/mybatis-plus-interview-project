/**
 * Copyright (c) 2021 宏汉科技 All rights reserved.
 * <p>
 * https://www.hhsas.com
 * <p>
 * 版权所有，侵权必究！
 */

package io.hh;

import io.hh.common.utils.EnvUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * hh-admin
 *
 * @author 
 */
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@Slf4j
@MapperScan({"io.hh.modules.*.dao","io.hh.modules.*.mapper"})
public class AdminApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(AdminApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        EnvUtil.initEnvApplication(env);
        log.info("\n----------------------------------------------------------\n\t" +
                "Application honghan-framework-java is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "Doc: \t\thttp://" + ip + ":" + port + path + "/doc.html\n" +
                "----------------------------------------------------------");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AdminApplication.class);
    }
}
