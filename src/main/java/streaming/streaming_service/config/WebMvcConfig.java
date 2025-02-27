package streaming.streaming_service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebMvc
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {
    private static final long MAX_AGE_SECS = 3600;
    private final List<String> allowOriginUrlPatterns;
    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

    public WebMvcConfig(@Value("${cors.allow-origin.urls}") final String allowOriginUrlPatterns) {
        this.allowOriginUrlPatterns =
                Arrays.stream(allowOriginUrlPatterns.split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] patterns = allowOriginUrlPatterns.toArray(String[]::new);

        registry
                .addMapping("/**")
                .allowedOriginPatterns(patterns)
                .allowedHeaders("*")
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
                .exposedHeaders("Authorization", "Set-Cookie")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }
}
