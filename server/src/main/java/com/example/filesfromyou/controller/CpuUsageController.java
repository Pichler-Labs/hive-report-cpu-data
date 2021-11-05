package com.example.filesfromyou.controller;

import com.example.filesfromyou.domain.CPUUsage;
import com.example.filesfromyou.model.CpuUsageModel;
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
        log.info("New submission of CPU usage for host {}", cpuUsage);
        cpuUsageModel.put(cpuUsage);
    }

    @GetMapping("/")
    public String showCPUUsage(){ //todo test
        StringBuilder response = new StringBuilder();
        response.append("<html>");
        response.append("<head>");
        response.append("<meta http-equiv=\"refresh\" content=\"1\">");
        response.append("</head>");
        response.append("<body>");
        List<CPUUsage> averages = cpuUsageModel.getAverages();
        averages.forEach( it -> {
            final String formattedValue = new DecimalFormat("0.00").format(it.getAverageCpuUsage());
            response.append("<p>");
            response.append(it.getHost() + " (version " + it.getVersion() + ")" + " - Average CPU usage " + formattedValue + "%");
            response.append("</p>\n");
        });
        response.append("<body/>");
        response.append("<html/>");
        return response.toString();
    }
}
