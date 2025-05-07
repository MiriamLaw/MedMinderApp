package com.medminder.domains;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "alarms")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "medication_id", nullable = false)
    private MedicationEntry medicationEntry;
    
    @Column(nullable = false)
    private LocalTime time;
    
    @Enumerated(EnumType.STRING)
    private AlarmStatus status = AlarmStatus.SET;
    
    // Constructors
    public Alarm() {}
    
    public Alarm(MedicationEntry medicationEntry, LocalTime time) {
        this.medicationEntry = medicationEntry;
        this.time = time;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public MedicationEntry getMedicationEntry() {
        return medicationEntry;
    }
    
    public void setMedicationEntry(MedicationEntry medicationEntry) {
        this.medicationEntry = medicationEntry;
    }
    
    public LocalTime getTime() {
        return time;
    }
    
    public void setTime(LocalTime time) {
        this.time = time;
    }
    
    public AlarmStatus getStatus() {
        return status;
    }
    
    public void setStatus(AlarmStatus status) {
        this.status = status;
    }
}
