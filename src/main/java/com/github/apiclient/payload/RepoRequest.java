package com.github.apiclient.payload;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

public class RepoRequest {
    @NotBlank
    @Getter @Setter
    private String name;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private Boolean isPrivate;

    public RepoRequest() {

    }

    public RepoRequest(@NotBlank String name, String description) {
        this.name = name;
        this.description = description;
    }
}
