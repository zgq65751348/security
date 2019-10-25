package org.peak.myth.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <p>
 *  SpringMVC配置
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/23 0023 下午 4:51
 */
@Configuration
@Slf4j
public class SpringWebConfig  extends WebMvcConfigurerAdapter {

    static final String ORIGINS[] = new String[] { "GET", "POST", "PUT", "DELETE" };

    //解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods(ORIGINS);
    }
}
