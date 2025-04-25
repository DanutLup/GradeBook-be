package gradebook.config.CORS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

  @Bean
  public CorsFilter corsFilter() {

    CorsConfigurationSource source =
        request -> {
          CorsConfiguration config = new CorsConfiguration();
          // config.setAllowCredentials(true); // you USUALLY want this
          config.addAllowedOrigin("*");
          config.addAllowedHeader("*");
          config.addAllowedMethod("OPTIONS");
          config.addAllowedMethod("HEAD");
          config.addAllowedMethod("GET");
          config.addAllowedMethod("PUT");
          config.addAllowedMethod("POST");
          config.addAllowedMethod("DELETE");
          config.addAllowedMethod("PATCH");
          return config;
        };

    return new CorsFilter(source);
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**");
  }
}
