package com.example.filesfromyou.model;

import com.example.filesfromyou.domain.CPUUsage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Component
public class CpuUsageModel {

    private static final int MAX_SIZE = 1000;
    private static HashMap<String, List<CPUUsage>> allCpuData = new HashMap<>();

    public void put(final CPUUsage cpuUsage){
        if(cpuUsage.getAverageCpuUsage() < 0)  {
            throw new IllegalArgumentException("Averages must be equal or more than 0.");
        }
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

    public List<CPUUsage> getAverages() {
        final List<CPUUsage> averages = new ArrayList<>();

        getAllCpuData().entrySet().forEach(it -> {
            final String host = it.getKey();
            final int entriesCount = it.getValue().size();
            String version = null;

            double average = 0;
            for (CPUUsage cpuUsage : it.getValue()) {
                average += cpuUsage.getAverageCpuUsage() / entriesCount;
                if (version == null) {
                    version = cpuUsage.getVersion();
                }
            }
            averages.add(new CPUUsage(host, average, version, LocalDateTime.now()));
        });

        Collections.sort(averages);
        return averages;
    }
}
