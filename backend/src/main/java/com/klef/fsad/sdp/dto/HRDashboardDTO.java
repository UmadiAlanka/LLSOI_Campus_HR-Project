package com.klef.fsad.sdp.dto;

public class HRDashboardDTO {
    private long totalEmployees;
    private long presentToday;
    private long pendingLeaveRequests;
    private long anomaliesDetected;

    // Default Constructor
    public HRDashboardDTO() {}

    // Constructor to set values easily
    public HRDashboardDTO(long totalEmployees, long presentToday, long pendingLeaveRequests, long anomaliesDetected) {
        this.totalEmployees = totalEmployees;
        this.presentToday = presentToday;
        this.pendingLeaveRequests = pendingLeaveRequests;
        this.anomaliesDetected = anomaliesDetected;
    }

    // Getters and Setters
    public long getTotalEmployees() { return totalEmployees; }
    public void setTotalEmployees(long totalEmployees) { this.totalEmployees = totalEmployees; }

    public long getPresentToday() { return presentToday; }
    public void setPresentToday(long presentToday) { this.presentToday = presentToday; }

    public long getPendingLeaveRequests() { return pendingLeaveRequests; }
    public void setPendingLeaveRequests(long pendingLeaveRequests) { this.pendingLeaveRequests = pendingLeaveRequests; }

    public long getAnomaliesDetected() { return anomaliesDetected; }
    public void setAnomaliesDetected(long anomaliesDetected) { this.anomaliesDetected = anomaliesDetected; }
}