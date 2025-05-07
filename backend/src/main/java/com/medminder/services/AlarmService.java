package com.medminder.services;

import com.medminder.domains.Alarm;
import com.medminder.domains.AlarmStatus;
import com.medminder.domains.MedicationEntry;
import com.medminder.repositories.AlarmRepository;
import com.medminder.repositories.MedicationEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlarmService {
    
    @Autowired
    private AlarmRepository alarmRepository;
    
    @Autowired
    private MedicationEntryRepository medicationEntryRepository;
    
    public List<Alarm> getAlarmsForMedication(Long medicationId) {
        MedicationEntry medication = medicationEntryRepository.findById(medicationId)
                .orElseThrow(() -> new RuntimeException("Medication not found"));
        return alarmRepository.findByMedicationEntryOrderByTime(medication);
    }
    
    public Optional<Alarm> getAlarmById(Long id) {
        return alarmRepository.findById(id);
    }
    
    public Alarm createAlarm(Long medicationId, LocalTime time) {
        MedicationEntry medication = medicationEntryRepository.findById(medicationId)
                .orElseThrow(() -> new RuntimeException("Medication not found"));
        
        Alarm alarm = new Alarm();
        alarm.setMedicationEntry(medication);
        alarm.setTime(time);
        alarm.setStatus(AlarmStatus.SET);
        
        return alarmRepository.save(alarm);
    }
    
    public Alarm updateAlarm(Long id, LocalTime time) {
        Alarm alarm = alarmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alarm not found"));
        
        alarm.setTime(time);
        
        return alarmRepository.save(alarm);
    }
    
    public Alarm updateAlarmStatus(Long id, AlarmStatus status) {
        Alarm alarm = alarmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alarm not found"));
        
        alarm.setStatus(status);
        
        return alarmRepository.save(alarm);
    }
    
    public void deleteAlarm(Long id) {
        alarmRepository.deleteById(id);
    }
}
