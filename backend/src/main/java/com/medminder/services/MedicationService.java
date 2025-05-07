package com.medminder.services;

import com.medminder.domains.Day;
import com.medminder.domains.MedicationEntry;
import com.medminder.repositories.DayRepository;
import com.medminder.repositories.MedicationEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicationService {
    
    @Autowired
    private MedicationEntryRepository medicationEntryRepository;
    
    @Autowired
    private DayRepository dayRepository;
    
    public List<MedicationEntry> getMedicationsForDay(Long dayId) {
        Day day = dayRepository.findById(dayId)
                .orElseThrow(() -> new RuntimeException("Day not found"));
        return medicationEntryRepository.findByDayOrderByTimeSlot(day);
    }
    
    public Optional<MedicationEntry> getMedicationById(Long id) {
        return medicationEntryRepository.findById(id);
    }
    
    public MedicationEntry createMedication(Long dayId, String name, String dosage, Integer quantity, 
                                           String frequency, String timeSlot) {
        Day day = dayRepository.findById(dayId)
                .orElseThrow(() -> new RuntimeException("Day not found"));
        
        MedicationEntry medication = new MedicationEntry();
        medication.setDay(day);
        medication.setName(name);
        medication.setDosage(dosage);
        medication.setQuantity(quantity);
        medication.setFrequency(frequency);
        medication.setTimeSlot(timeSlot);
        medication.setTaken(false);
        
        return medicationEntryRepository.save(medication);
    }
    
    public MedicationEntry updateMedication(Long id, String name, String dosage, Integer quantity, 
                                           String frequency, String timeSlot) {
        MedicationEntry medication = medicationEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medication not found"));
        
        medication.setName(name);
        medication.setDosage(dosage);
        medication.setQuantity(quantity);
        medication.setFrequency(frequency);
        medication.setTimeSlot(timeSlot);
        
        return medicationEntryRepository.save(medication);
    }
    
    public MedicationEntry updateMedicationTakenStatus(Long id, boolean taken) {
        MedicationEntry medication = medicationEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medication not found"));
        
        medication.setTaken(taken);
        
        return medicationEntryRepository.save(medication);
    }
    
    public void deleteMedication(Long id) {
        medicationEntryRepository.deleteById(id);
    }
}
