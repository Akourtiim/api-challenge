package com.n26.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by ahmed on 4.10.17.
 */


public class Statistic {


    private long timestamp;
    private double sum;
    private double max;
    private double min;
    private long count;
    private double avg;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Statistic(long timestamp, double sum, double max, double min, long count) {
        this.timestamp = timestamp;
        this.sum = sum;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public Statistic() {

    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    @Override
    public String toString() {
        return "{" +
                "sum=" + sum +
                ", count=" + count +
                ", max=" + max +
                ", min=" + min +
                ", avg=" + avg +
                '}';
    }
}
