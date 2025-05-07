package com.medminder.domains;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "days")
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "week_id", nullable = false)
    private Week week;
    
    @Column(nullable = false)
    private String dayOfWeek;
    
    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicationEntry> medicationEntries = new ArrayList<>();
    
    // Constructors
    public Day() {}
    
    public Day(Week week, String dayOfWeek) {
        this.week = week;
        this.dayOfWeek = dayOfWeek;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Week getWeek() {
        return week;
    }
    
    public void setWeek(Week week) {
        this.week = week;
    }
    
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    
    public List<MedicationEntry> getMedicationEntries() {
        return medicationEntries;
    }
    
    public void setMedicationEntries(List<MedicationEntry> medicationEntries) {
        this.medicationEntries = medicationEntries;
    }
    
    // Helper method to add a medication entry
    public void addMedicationEntry(MedicationEntry medicationEntry) {
        medicationEntries.add(medicationEntry);
        medicationEntry.setDay(this);
    }
}
