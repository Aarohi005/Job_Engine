
package com.aarohi.jobengine.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class JobRequest {

    @NotBlank
    private String payload;

    @Min(1)
    @Max(10)
    private int priority = 1;

    public JobRequest() {
    }

    public JobRequest(String payload, int priority) {
        this.payload = payload;
        this.priority = priority;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
