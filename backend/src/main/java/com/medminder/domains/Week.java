package com.medminder.domains;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "weeks")
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private LocalDate startDate;
    
    @OneToMany(mappedBy = "week", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Day> days = new ArrayList<>();
    
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
    
    @Column(name = "week_number", nullable = false)
    private Integer weekNumber;
    
    // Constructors
    public Week() {}
    
    public Week(User user, LocalDate startDate, Integer weekNumber) {
        this.user = user;
        this.startDate = startDate;
        this.weekNumber = weekNumber;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public List<Day> getDays() {
        return days;
    }
    
    public void setDays(List<Day> days) {
        this.days = days;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public Integer getWeekNumber() {
        return weekNumber;
    }
    
    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }
    
    // Helper method to add a day
    public void addDay(Day day) {
        days.add(day);
        day.setWeek(this);
    }
}
