package com.medminder.controllers;

import com.medminder.services.MedicationService;
import com.medminder.domains.MedicationEntry;
import com.medminder.domains.Day;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/medications")
public class MedicationsApiController {

    @Autowired
    private MedicationService medicationService;

    @PostMapping("/{day}/{timeSlot}")
    public ResponseEntity<?> saveMedications(
            @PathVariable String day,
            @PathVariable String timeSlot,
            @RequestBody List<Map<String, Object>> medications,
            Authentication authentication
    ) {
        try {
            Day dayEntity = medicationService.getOrCreateDayForUser(authentication.getName(), day);
            Long dayId = dayEntity.getId();
            for (Map<String, Object> med : medications) {
                String name = (String) med.get("name");
                String dosage = (String) med.get("dosage");
                Integer quantity = med.get("quantity") != null ? ((Number) med.get("quantity")).intValue() : 1;
                String frequency = (String) med.get("frequency");
                medicationService.createMedication(dayId, name, dosage, quantity, frequency, timeSlot);
            }
            return ResponseEntity.ok(Map.of("status", "success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
} 