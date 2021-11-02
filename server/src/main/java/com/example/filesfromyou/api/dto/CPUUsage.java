package com.example.filesfromyou.api.dto;

import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value
//@Jacksonized
public class CPUUsage {

    @NonNull
    private String host;

    private int cpuUsage;

    /**
     * FilesFromYou version.
     */
    @NonNull
    private String version;

    @NonNull
    private LocalDateTime timestamp;

}
