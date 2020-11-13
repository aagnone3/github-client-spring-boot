package com.github.apiclient.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix="app")
public class AppProperties {
    private final Github github = new Github();

    public static class Github {
        @Getter @Setter
        private String username;
        @Getter @Setter
        private String token;
    }

    public Github getGithub() {
        return github;
    }
}
