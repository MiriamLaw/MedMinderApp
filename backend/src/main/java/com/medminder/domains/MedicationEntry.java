package com.medminder.domains;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medication_entries")
public class MedicationEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "day_id", nullable = false)
    private Day day;
    
    @Column(nullable = false)
    private String name;
    
    private String dosage;
    
    private Integer quantity;
    
    private String frequency;
    
    private String timeSlot; // MORN, NOON, NIGHT, BACKUP
    
    private boolean taken = false;
    
    @OneToMany(mappedBy = "medicationEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alarm> alarms = new ArrayList<>();
    
    // Constructors
    public MedicationEntry() {}
    
    public MedicationEntry(Day day, String name, String timeSlot) {
        this.day = day;
        this.name = name;
        this.timeSlot = timeSlot;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Day getDay() {
        return day;
    }
    
    public void setDay(Day day) {
        this.day = day;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDosage() {
        return dosage;
    }
    
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public String getFrequency() {
        return frequency;
    }
    
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    
    public String getTimeSlot() {
        return timeSlot;
    }
    
    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }
    
    public boolean isTaken() {
        return taken;
    }
    
    public void setTaken(boolean taken) {
        this.taken = taken;
    }
    
    public List<Alarm> getAlarms() {
        return alarms;
    }
    
    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }
    
    // Helper method to add an alarm
    public void addAlarm(Alarm alarm) {
        alarms.add(alarm);
        alarm.setMedicationEntry(this);
    }
}
