package com.example.filesfromyou.domain;

import com.example.filesfromyou.api.dto.CPUUsage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CpuUsageModelTest {
    @Autowired
    private CpuUsageModel cpuUsageModel;

    @BeforeEach
    public void beforeEach() {
        cpuUsageModel.getAllCpuData().clear();
    }

    @Test
    public void putTest() {
        cpuUsageModel.put(new CPUUsage("host", 1, "1.0", LocalDateTime.now()));
        assertThat(cpuUsageModel.getAllCpuData().size()).isEqualTo(1);
    }

    @Test
    public void putTestMaxLimitOfHosts() {
        final int maximumAllowed = 1000;

        int times = 1050;
        for (int i = 0; i < times; i++) {
            CPUUsage host = new CPUUsage("host" + i, 1, "1.0", LocalDateTime.now());
            cpuUsageModel.put(host);
        }
        assertThat(cpuUsageModel.getAllCpuData().size()).isEqualTo(maximumAllowed);
    }

    @Test
    public void putTestMaxLimitOfHostsSubmissions() {
        final int maximumAllowed = 1000;

        int times = 1050;
        for (int i = 0; i < times; i++) {
            CPUUsage host = new CPUUsage("sameHost", 1, "1.0", LocalDateTime.now());
            cpuUsageModel.put(host);
        }
        assertThat(cpuUsageModel.getAllCpuData().size()).isEqualTo(1);
        assertThat(cpuUsageModel.getAllCpuData().get("sameHost").size()).isEqualTo(maximumAllowed);
    }
}