package net.bookCRUD.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.stereotype.Component;

@Component
public class RequestLoggingConfig {

    @Bean
    public CommonsRequestLoggingFilter loggingFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeClientInfo(true);    // IP, session ID
        filter.setIncludeQueryString(true);   // URL query params
        filter.setIncludePayload(true);       // Request body
        filter.setMaxPayloadLength(10000);    // Limit body length
        filter.setIncludeHeaders(false);      // Optional: log headers
        return filter;
    }
}
