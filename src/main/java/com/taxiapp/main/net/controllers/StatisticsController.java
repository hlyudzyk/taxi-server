package com.taxiapp.main.net.controllers;

import com.taxiapp.main.services.util.StatisticsService;
import java.time.LocalDate;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/ratio")
    public ResponseEntity<Double> getRatio(){
        return ResponseEntity.ok(statisticsService.calculateOrderStatusRatio());
    }

    @GetMapping("/period")
    public ResponseEntity<Map<LocalDate,Integer>> getPeriodData(@RequestParam String before,
        @RequestParam String after){
        return ResponseEntity.ok(statisticsService.calculateTotalOrdersPerTimePeriod(before,after));
    }
}
