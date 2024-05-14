package com.taxiapp.main.net.responses;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinancialStatementResponse {
    private int totalOrders;
    private double totalRevenue;
    private double averageRevenuePerOrder;
    private double averageDistancePerOrder;
    private double averageOrdersAmountPerDay;
}
