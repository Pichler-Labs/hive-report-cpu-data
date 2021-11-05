package com.filesfromyou.client.dto;

import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class CPUUsage {

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

}
