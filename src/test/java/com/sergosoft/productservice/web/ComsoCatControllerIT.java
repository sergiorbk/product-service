package com.sergosoft.productservice.web;

import com.sergosoft.productservice.featuretoggle.FeatureToggleExtension;
import com.sergosoft.productservice.featuretoggle.annotation.DisabledFeatureToggle;
import com.sergosoft.productservice.featuretoggle.annotation.EnabledFeatureToggle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.sergosoft.productservice.featuretoggle.FeatureToggles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("CosmoCat Controller IT")
@ExtendWith(FeatureToggleExtension.class)
public class ComsoCatControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void shouldGet404FeatureDisabled() throws Exception {
        mockMvc.perform(get("/api/v1/cosmocats/all")).andExpect(status().isServiceUnavailable());
    }

    @Test
    @EnabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void shouldGet200() throws Exception {
        mockMvc.perform(get("/api/v1/cosmocats/all")).andExpect(status().isOk());
    }
}
