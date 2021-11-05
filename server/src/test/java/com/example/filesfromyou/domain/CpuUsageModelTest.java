package com.example.filesfromyou.domain;

import com.example.filesfromyou.model.CpuUsageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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


    @Test
    public void getAverages() {
        CPUUsage usage1 = new CPUUsage("sameHost", 100, "1.0", LocalDateTime.now());
        CPUUsage usage2 = new CPUUsage("sameHost",100 , "1.0", LocalDateTime.now());
        CPUUsage usage3 = new CPUUsage("sameHost", 50, "1.0", LocalDateTime.now());

        CPUUsage usage4 = new CPUUsage("otherHost", 50, "1.0", LocalDateTime.now());

        cpuUsageModel.put(usage1);
        cpuUsageModel.put(usage2);
        cpuUsageModel.put(usage3);
        cpuUsageModel.put(usage4);

        assertEquals(2, cpuUsageModel.getAverages().size());
        assertEquals((100 + 100 + 50) / 3, (int) cpuUsageModel.getAverages().get(0).getAverageCpuUsage());
        assertEquals(50, (int) cpuUsageModel.getAverages().get(1).getAverageCpuUsage());

    }
}