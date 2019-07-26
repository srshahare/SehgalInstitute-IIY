package com.iiysoftware.instituteapp.Fragments;

public class model {

    private String name;
    private String reason;
    private String from;
    private String to;

    public model(){}

    public model(String name, String reason, String from, String to) {
        this.name = name;
        this.reason = reason;
        this.from = from;
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
