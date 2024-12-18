package com.sergosoft.productservice.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class GithubLoginSecurityTests {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Check the login endpoint can be freely accessed
     */
    @Test
    void shouldAllowAccessToLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    /**
     * Check that secured endpoint now available after OAuth2 login via GitHub
     */
    @Test
    void shouldAllowAccessToProtectedApiWithOauth2Login() throws Exception {
        mockMvc.perform(get("/api/v1/dashboard")
                        .with(oauth2Login().attributes(attrs -> attrs.put("login", "mockgithubuser"))))
                .andExpect(status().isOk());
    }

    /**
     * Redirection to github check
     */
    @Test
    void shouldRedirectToGitHubAuthorizationPage() throws Exception {
        mockMvc.perform(get("/oauth2/authorization/github"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("https://github.com/**"));
    }
}
