package com.filesfromyou.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.filesfromyou.client.dto.CPUUsage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
public class Main {

    private String hostname = "host-" + (int) (Math.random()  * 1000);
    private String version = (int) (Math.random()  * 2) + "." + (int) (Math.random()  * 10);

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Scheduled(fixedRate = 1000)
    public void sendCPUUsage() throws URISyntaxException, IOException, InterruptedException {
        final double averageCpuUsage = (int) (Math.random() * 100) + (Double.valueOf(version) * 2); //simulating a problem with: higher the version, higher the cpu usage
        final CPUUsage cpuUsage = new CPUUsage(hostname, averageCpuUsage, version, LocalDateTime.now());

        String body = asJson(cpuUsage);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/cpu-usage"))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("POST sent: " + body);

    }

    private String asJson(final CPUUsage body) throws JsonProcessingException {
        return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(body);
    }




}
