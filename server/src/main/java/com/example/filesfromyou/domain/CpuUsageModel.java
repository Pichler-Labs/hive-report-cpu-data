package com.example.filesfromyou.domain;

import com.example.filesfromyou.api.dto.CPUUsage;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CpuUsageModel {

    private static HashMap<String, List<CPUUsage>> allCpuData; //persist in a storage

    public void put(final CPUUsage cpuUsage){

        final var host = cpuUsage.getHost();
        final List<CPUUsage> hostCpuUsageList = allCpuData.computeIfAbsent(host, k -> new ArrayList());

        hostCpuUsageList.add(cpuUsage);

        //todo evict old data

    }
}
