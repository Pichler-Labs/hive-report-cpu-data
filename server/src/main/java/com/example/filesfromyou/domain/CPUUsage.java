package com.example.filesfromyou.domain;

import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class CPUUsage  implements Comparable <CPUUsage> {

    @NonNull
    private String host;

    private double averageCpuUsage;

    /**
     * FilesFromYou version.
     */
    @NonNull
    private String version;

    @NonNull
    private LocalDateTime timestamp;

    @Override
    public int compareTo(CPUUsage other) {
        return (int) (other.averageCpuUsage - averageCpuUsage);
    }

}
