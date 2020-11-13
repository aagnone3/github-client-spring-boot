package com.github.apiclient;

import javax.validation.Valid;

import com.github.apiclient.config.AppProperties;
import com.github.apiclient.payload.GithubRepo;
import com.github.apiclient.payload.RepoRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class GithubController {
   
    @Autowired
    private GithubClient githubClient;

    @Autowired
    private AppProperties appProperties;

    private static final Logger logger = LoggerFactory.getLogger(GithubController.class);

    @GetMapping("/repos")
    public Flux<GithubRepo> listRepositories() {
        return githubClient.listGithubRepositories();
    }

    @PostMapping("/repos")
    public Mono<GithubRepo> createRepository(@RequestBody RepoRequest repoRequest) {
        return githubClient.createGithubRepository(repoRequest);
    }

    @GetMapping("/repos/{repo}")
    public Mono<GithubRepo> getRepository(@PathVariable String repo) {
        return githubClient.getGithubRepository(appProperties.getGithub().getUsername(), repo);
    }

    @PatchMapping("/repos/{repo}")
    public Mono<GithubRepo> updateRepository(@PathVariable String repo, @Valid @RequestBody RepoRequest updateRepoRequest) {
        return githubClient.editGithubRepository(appProperties.getGithub().getUsername(), repo, updateRepoRequest);
    }

    @DeleteMapping("/repos/{repo}")
    public Mono<Void> deleteRepository(@PathVariable String repo) {
        return githubClient.deleteGithubRepository(appProperties.getGithub().getUsername(), repo);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<String> handleWebClientResponseException(WebClientResponseException ex) {
        logger.error("Error from WebClient - Status {}, Body {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
        return ResponseEntity
            .status(ex.getRawStatusCode())
            .body(ex.getResponseBodyAsString());
    }
}
