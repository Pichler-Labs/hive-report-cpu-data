package com.example.filesfromyou.domain;

import com.example.filesfromyou.AverageCpuUsage;
import com.example.filesfromyou.api.dto.CPUUsage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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

    public List<AverageCpuUsage> getAverages() {
        final List<AverageCpuUsage> averages = new ArrayList<>();

        getAllCpuData().entrySet().forEach(it -> {
            String host = it.getKey();
            List<CPUUsage> value = it.getValue();
            int entriesCount = it.getValue().size();
            double average = 0;
            for (CPUUsage cpuUsage : it.getValue()) {
                average += (double) cpuUsage.getCpuUsage() / entriesCount;
            }
            averages.add(new AverageCpuUsage(host, average));
        });

        Collections.sort(averages);
        return averages;
    }
}
