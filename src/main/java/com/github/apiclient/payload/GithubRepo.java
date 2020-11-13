package com.github.apiclient.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

public class GithubRepo {
    @Getter @Setter
    private Long id;
    
    @Getter @Setter
    private String name;

    @Getter @Setter
    @JsonProperty("full_name")
    private String fullName;

    @Getter @Setter
    private String description;

    @Getter @Setter
    @JsonProperty("is_private")
    private String isPrivate;

    @Getter @Setter
    @JsonProperty("is_fork")
    private String isFork;

    @Getter @Setter
    private String url;

    @Getter @Setter
    @JsonProperty("html_url")
    private String htmlUrl;

    @Getter @Setter
    @JsonProperty("git_url")
    private String gitUrl;

    @Getter @Setter
    @JsonProperty("forks_count")
    private Long forksCount;

    @Getter @Setter
    @JsonProperty("stargazers_count")
    private Long stargazersCount;

    @Getter @Setter
    @JsonProperty("watchers_count")
    private Long watchersCount;
}
