package com.example.filesfromyou;

public class AverageCpuUsage implements Comparable <AverageCpuUsage> {

    private String host;
    private double average;

    public AverageCpuUsage(final String host, double average) {
        this.host = host;
        this.average = average;
    }

    @Override
    public int compareTo(AverageCpuUsage other) {
        return (int) (other.average - average);
    }

    public String getHost() {
        return host;
    }
    public double getAverage() {
        return average;
    }
}
