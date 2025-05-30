package com.medminder.services;

import com.medminder.domains.Day;
import com.medminder.domains.MedicationEntry;
import com.medminder.domains.User;
import com.medminder.domains.Week;
import com.medminder.repositories.DayRepository;
import com.medminder.repositories.MedicationEntryRepository;
import com.medminder.repositories.UserRepository;
import com.medminder.repositories.WeekRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MedicationService {
    
    private static final Logger logger = LoggerFactory.getLogger(MedicationService.class);
    
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
        logger.info("Creating medication with dayId: {}, name: {}, dosage: {}, quantity: {}, frequency: {}, timeSlot: {}", 
                    dayId, name, dosage, quantity, frequency, timeSlot);
        try {
            Day day = dayRepository.findById(dayId)
                    .orElseThrow(() -> new RuntimeException("Day not found"));
            
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Medication name cannot be null or empty");
            }
            
            MedicationEntry medication = new MedicationEntry();
            medication.setDay(day);
            medication.setName(name);
            medication.setDosage(dosage != null ? dosage : "");
            medication.setQuantity(quantity != null ? quantity : 1);
            medication.setFrequency(frequency != null ? frequency : "");
            medication.setTimeSlot(timeSlot != null ? timeSlot : "");
            medication.setTaken(false);
            
            return medicationEntryRepository.save(medication);
        } catch (Exception e) {
            logger.error("Error creating medication: {}", e.getMessage(), e);
            throw e;
        }
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
        int weekNumber = startOfWeek.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        Week week = weekRepository.findByUserOrderByStartDateDesc(user).stream()
            .filter(w -> w.getStartDate().equals(startOfWeek))
            .findFirst()
            .orElseGet(() -> {
                Week newWeek = new Week();
                newWeek.setUser(user);
                newWeek.setStartDate(startOfWeek);
                newWeek.setWeekNumber(weekNumber);
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
