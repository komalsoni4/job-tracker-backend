


/*
The deployed version code
package com.jobtracker.DataManagement.config;
// This file is empty to avoid CORS configuration conflicts.



 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.web.filter.CorsFilter;
 import org.springframework.web.cors.CorsConfiguration;
 import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

 import java.util.Arrays;

 @Configuration
 public class WebConfig {

     @Bean
     public CorsFilter corsFilter() {
         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
         CorsConfiguration config = new CorsConfiguration();
         config.setAllowCredentials(true);
         config.setAllowedOrigins(Arrays.asList(
                 "https://job-tracker-frontend-nq5dt8qgm6-komalsoni4s-projects.vercel.app",
                 "chrome-extension://nmkkegognokcgcmddjhlljcpdbjgkdfi", // This is your extension ID
                 "null" // This is for local file testing
         ));
         config.addAllowedHeader("*");
         config.addAllowedMethod("*");
         source.registerCorsConfiguration("/**", config);
         return new CorsFilter(source);
     }
 }

 */


package com.jobtracker.DataManagement.config;
// This file is empty to avoid CORS configuration conflicts.

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // Left empty. All CORS is handled by SecurityConfig.
}
