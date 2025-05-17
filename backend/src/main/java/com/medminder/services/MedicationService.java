package com.medminder.services;

import com.medminder.domains.Day;
import com.medminder.domains.MedicationEntry;
import com.medminder.domains.User;
import com.medminder.domains.Week;
import com.medminder.repositories.DayRepository;
import com.medminder.repositories.MedicationEntryRepository;
import com.medminder.repositories.UserRepository;
import com.medminder.repositories.WeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MedicationService {
    
    @Autowired
    private MedicationEntryRepository medicationEntryRepository;
    
    @Autowired
    private DayRepository dayRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private WeekRepository weekRepository;
    
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
    
    public Day getOrCreateDayForUser(String email, String dayOfWeek) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        // Find or create the current week (using today as the start date for simplicity)
        LocalDate startOfWeek = LocalDate.now().with(java.time.DayOfWeek.SUNDAY);
        Week week = weekRepository.findByUserOrderByStartDateDesc(user).stream()
            .filter(w -> w.getStartDate().equals(startOfWeek))
            .findFirst()
            .orElseGet(() -> {
                Week newWeek = new Week();
                newWeek.setUser(user);
                newWeek.setStartDate(startOfWeek);
                return weekRepository.save(newWeek);
            });
        // Find or create the Day
        Day day = dayRepository.findByWeekAndDayOfWeek(week, dayOfWeek);
        if (day == null) {
            day = new Day();
            day.setWeek(week);
            day.setDayOfWeek(dayOfWeek);
            day = dayRepository.save(day);
        }
        return day;
    }
}
