package com.convertino.hire.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.google-ai-studio")
@Getter
@Setter
public class GoogleAIStudioConfig {
    private String apiKey;
}
