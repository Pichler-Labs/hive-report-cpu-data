package com.example.filesfromyou.domain;

import com.example.filesfromyou.api.dto.CPUUsage;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CpuUsageModel {

    private static final int MAX_SIZE = 1000;
    private static HashMap<String, List<CPUUsage>> allCpuData = new HashMap<>();

    public void put(final CPUUsage cpuUsage){

        final var host = cpuUsage.getHost();
        final List<CPUUsage> hostCpuUsageList = allCpuData.computeIfAbsent(host, k -> new ArrayList());

        hostCpuUsageList.add(cpuUsage);

        //evict
        if (hostCpuUsageList.size() > MAX_SIZE) {
            hostCpuUsageList.remove(0);
        }
        if (allCpuData.keySet().size() > MAX_SIZE) {
            allCpuData.remove(allCpuData.keySet().stream().findFirst().get());
        }
    }

    public HashMap<String, List<CPUUsage>> getAllCpuData() {
        return allCpuData;
    }
}
