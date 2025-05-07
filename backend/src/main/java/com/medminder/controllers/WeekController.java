package com.medminder.controllers;

import com.medminder.domains.Week;
import com.medminder.services.WeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

@RestController
@RequestMapping("/api/weeks")
public class WeekController {
    
    @Autowired
    private WeekService weekService;
    
    @GetMapping
    public ResponseEntity<?> getCurrentUserWeeks() {
        List<Map<String, Object>> weeks = weekService.getCurrentUserWeeks().stream()
            .map(week -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", week.getId());
                map.put("startDate", week.getStartDate());
                return map;
            })
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(weeks);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getWeekById(@PathVariable Long id) {
        return weekService.getWeekById(id)
            .map(week -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", week.getId());
                map.put("startDate", week.getStartDate());
                return ResponseEntity.ok(map);
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<?> createWeek(@RequestBody Map<String, String> request) {
        try {
            LocalDate startDate = LocalDate.parse(request.get("startDate"));
            Week week = weekService.createWeek(startDate);
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", week.getId());
            response.put("startDate", week.getStartDate());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWeek(@PathVariable Long id) {
        try {
            weekService.deleteWeek(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
