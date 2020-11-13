package com.github.apiclient;

import com.github.apiclient.payload.GithubRepo;
import com.github.apiclient.payload.RepoRequest;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class ApiclientApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void contextLoads() {
	}

	@Test
	@Order(1)
	public void testCreateGithubRepository() {
		RepoRequest repoRequest = new RepoRequest("test-name", "test-description");
		webTestClient.post()
			.uri("/api/repos")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.bodyValue(repoRequest)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody()
				.jsonPath("$.name").isNotEmpty()
				.jsonPath("$.name").isEqualTo("test-name");
	}
	
	@Test
	@Order(2)
	public void testGetAllRepositories() {
		webTestClient.get()
			.uri("/api/repos")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBodyList(GithubRepo.class);
	}

	@Test
	@Order(3)
	public void testGetGithubRepository() {
		webTestClient.get()
			.uri("/api/repos/{repo}", "test-name")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody()
				.jsonPath("$.name").isEqualTo("test-name")
				.jsonPath("$.description").isEqualTo("test-description");
	}

	@Test
	@Order(4)
	public void testUpdateGithubRepository() {
		RepoRequest repoRequest = new RepoRequest("test-new-name", "test-new-description");
		webTestClient.patch()
			.uri("/api/repos/{repo}", "test-name")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.bodyValue(repoRequest)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody()
				.jsonPath("$.name").isEqualTo("test-new-name")
				.jsonPath("$.description").isEqualTo("test-new-description");
	}

	@Test
	@Order(5)
	public void testDeleteGithubRepository() {
		webTestClient.delete()
			.uri("/api/repos/{repo}", "test-new-name")
			.exchange()
			.expectStatus().isOk();
	}

}
