package com.example.filesfromyou.api;

import com.example.filesfromyou.AverageCpuUsage;
import com.example.filesfromyou.api.dto.CPUUsage;
import com.example.filesfromyou.domain.CpuUsageModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CpuUsageController {
    private final CpuUsageModel cpuUsageModel;

    @PostMapping("/cpu-usage")
    public void postCPUUsage(@RequestBody final CPUUsage cpuUsage){
        log.debug("New submission of CPU usage for host {}", cpuUsage);
        cpuUsageModel.put(cpuUsage);
    }

    @GetMapping("/")
    public void showCPUUsage(){ //todo test
        List<AverageCpuUsage> averages = cpuUsageModel.getAverages();
        averages.forEach( it -> {
            final String formattedValue = new DecimalFormat("0.00").format(it.getAverage());
            System.out.println(it.getHost() + " - " + formattedValue + "%");
        });
    }
}
