package com.sergosoft.productservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergosoft.productservice.dto.category.CategoryCreateDto;
import com.sergosoft.productservice.util.SlugGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.sergosoft.productservice.web.CategoryControllerIT.buildCreateCategoryDto;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("no-auth")
public class NoAuthProfileTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCategory() throws Exception {
        CategoryCreateDto createCategoryDto = buildCreateCategoryDto(List.of());
        mockMvc.perform(post("/api/v1/categories")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(createCategoryDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.slug").exists())
                .andExpect(jsonPath("$.slug").value(SlugGenerator.generateSlug(createCategoryDto.getTitle())));
    }
}
