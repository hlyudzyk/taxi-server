package com.taxiapp.main.net.responses;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DriverFinancialStatementResponse {
    private int totalOrders;
    private double totalRevenue;
    private double avgRevenuePerOrder;
    private double avgWorkHoursPerDay;
    private int totalWorkDaysPerLastMonth;
}
