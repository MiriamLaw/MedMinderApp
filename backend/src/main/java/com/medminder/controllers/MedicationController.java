package com.medminder.controllers;

import com.medminder.domains.MedicationEntry;
import com.medminder.services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

@RestController
@RequestMapping("/medications")
public class MedicationController {
    
    @Autowired
    private MedicationService medicationService;
    
    @GetMapping("/day/{dayId}")
    public ResponseEntity<?> getMedicationsForDay(@PathVariable Long dayId) {
        List<Map<String, Object>> medications = medicationService.getMedicationsForDay(dayId).stream()
            .map(med -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", med.getId());
                map.put("name", med.getName());
                map.put("dosage", med.getDosage());
                map.put("quantity", med.getQuantity());
                map.put("frequency", med.getFrequency());
                map.put("timeSlot", med.getTimeSlot());
                map.put("taken", med.isTaken());
                return map;
            })
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(medications);
    }
    
    @PostMapping
    public ResponseEntity<?> createMedication(@RequestBody Map<String, Object> request) {
        try {
            Long dayId = Long.parseLong(request.get("dayId").toString());
            String name = (String) request.get("name");
            String dosage = (String) request.get("dosage");
            Integer quantity = request.get("quantity") != null ? 
                Integer.parseInt(request.get("quantity").toString()) : null;
            String frequency = (String) request.get("frequency");
            String timeSlot = (String) request.get("timeSlot");
            
            MedicationEntry medication = medicationService.createMedication(
                dayId, name, dosage, quantity, frequency, timeSlot);
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", medication.getId());
            response.put("name", medication.getName());
            response.put("dosage", medication.getDosage());
            response.put("quantity", medication.getQuantity());
            response.put("frequency", medication.getFrequency());
            response.put("timeSlot", medication.getTimeSlot());
            response.put("taken", medication.isTaken());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMedication(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            String name = (String) request.get("name");
            String dosage = (String) request.get("dosage");
            Integer quantity = request.get("quantity") != null ? 
                Integer.parseInt(request.get("quantity").toString()) : null;
            String frequency = (String) request.get("frequency");
            String timeSlot = (String) request.get("timeSlot");
            
            MedicationEntry medication = medicationService.updateMedication(
                id, name, dosage, quantity, frequency, timeSlot);
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", medication.getId());
            response.put("name", medication.getName());
            response.put("dosage", medication.getDosage());
            response.put("quantity", medication.getQuantity());
            response.put("frequency", medication.getFrequency());
            response.put("timeSlot", medication.getTimeSlot());
            response.put("taken", medication.isTaken());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}/taken")
    public ResponseEntity<?> updateMedicationTakenStatus(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            boolean taken = Boolean.parseBoolean(request.get("taken").toString());
            
            MedicationEntry medication = medicationService.updateMedicationTakenStatus(id, taken);
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", medication.getId());
            response.put("taken", medication.isTaken());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMedication(@PathVariable Long id) {
        try {
            medicationService.deleteMedication(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
