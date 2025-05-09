package com.medminder.controllers;

import com.medminder.domains.Alarm;
import com.medminder.domains.AlarmStatus;
import com.medminder.services.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

@RestController
@RequestMapping("/api/alarms")
public class AlarmController {
    
    @Autowired
    private AlarmService alarmService;
    
    @GetMapping("/medication/{medicationId}")
    public ResponseEntity<?> getAlarmsForMedication(@PathVariable Long medicationId) {
        List<Map<String, Object>> alarms = alarmService.getAlarmsForMedication(medicationId).stream()
            .map(alarm -> {
                Map<String, Object> map = new HashMap<>();
       I          map.put("id", alarm.getId());
                map.put("time", alarm.getTime());
                map.put("status", alarm.getStatus());
                return map;
            })
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(alarms);
    }
    
    @PostMapping
    public ResponseEntity<?> createAlarm(@RequestBody Map<String, String> request) {
        try {
            Long medicationId = Long.parseLong(request.get("medicationId"));
            LocalTime time = LocalTime.parse(request.get("time"));
            
            Alarm alarm = alarmService.createAlarm(medicationId, time);
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", alarm.getId());
            response.put("time", alarm.getTime());
            response.put("status", alarm.getStatus());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAlarm(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            LocalTime time = LocalTime.parse(request.get("time"));
            
            Alarm alarm = alarmService.updateAlarm(id, time);
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", alarm.getId());
            response.put("time", alarm.getTime());
            response.put("status", alarm.getStatus());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateAlarmStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            AlarmStatus status = AlarmStatus.valueOf(request.get("status"));
            
            Alarm alarm = alarmService.updateAlarmStatus(id, status);
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", alarm.getId());
            response.put("time", alarm.getTime());
            response.put("status", alarm.getStatus());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlarm(@PathVariable Long id) {
        try {
            alarmService.deleteAlarm(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
