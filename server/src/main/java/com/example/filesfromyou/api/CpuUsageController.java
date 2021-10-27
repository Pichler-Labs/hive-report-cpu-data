package com.example.filesfromyou.api;

import com.example.filesfromyou.api.dto.CPUUsage;
import com.example.filesfromyou.domain.CpuUsageModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CpuUsageController {
    private final CpuUsageModel cpuUsageModel;

    @PostMapping("/cpu-usage")
    public void postCPUUsage(final CPUUsage cpuUsage){
        log.debug("New submission of CPU usage for host {}", cpuUsage);
        cpuUsageModel.put(cpuUsage);

        //todo test
    }
}
