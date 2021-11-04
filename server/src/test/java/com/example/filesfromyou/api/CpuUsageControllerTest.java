package com.example.filesfromyou.api;

import com.example.filesfromyou.api.dto.CPUUsage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CpuUsageControllerTest {

    @Autowired
    private CpuUsageController controller;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void postCPUUsage() throws Exception {
        final var body = new CPUUsage("host", 1, "1.0", LocalDateTime.now());
        final var requestBuilder = MockMvcRequestBuilders.post("/cpu-usage")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(body));
        this.mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    private String asJson(final CPUUsage body) throws JsonProcessingException {
        return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(body);
    }

    @Test
    void showCPUUsage() throws Exception {
        for (int i = 0; i < 100; i++) {

            final var body = new CPUUsage("host" + i % 10, i, "1.0", LocalDateTime.now());
            final var requestBuilder = MockMvcRequestBuilders.post("/cpu-usage")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJson(body));
            this.mockMvc.perform(requestBuilder).andExpect(status().isOk());
        }

        final var requestBuilder = MockMvcRequestBuilders.get("/");

        this.mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }
}