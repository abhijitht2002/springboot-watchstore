package com.abhijith.dream_books;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DreamBooksApplicationTests {

	@LocalServerPort
	private int port = 8080;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	@WithMockUser(username = "user", password = "password", roles = "USER")
	void testUserLoginAndAccessDashboard() {
		// Simulate a logged-in user trying to access the user dashboard
		String url = "http://localhost:" + port + "/User-dashboard";
		String response = restTemplate.getForObject(url, String.class);

		// Verify that the response contains the user dashboard content
		assertThat(response).contains("User Dashboard");
	}

	@Test
	void testUnauthorizedAccessToUserDashboard() {
		// Simulate an unauthenticated user trying to access the user dashboard
		String url = "http://localhost:" + port + "/User-dashboard";
		String response = restTemplate.getForObject(url, String.class);

		// Verify that the user is redirected to the login page
		assertThat(response).contains("Login");
	}
}
