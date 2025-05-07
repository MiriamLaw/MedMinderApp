package com.medminder.services;

import com.medminder.domains.Day;
import com.medminder.domains.User;
import com.medminder.domains.Week;
import com.medminder.repositories.WeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WeekService {
    
    @Autowired
    private WeekRepository weekRepository;
    
    @Autowired
    private UserService userService;
    
    public List<Week> getCurrentUserWeeks() {
        User currentUser = userService.getCurrentUser();
        return weekRepository.findByUserOrderByStartDateDesc(currentUser);
    }
    
    public Optional<Week> getWeekById(Long id) {
        return weekRepository.findById(id);
    }
    
    public Week createWeek(LocalDate startDate) {
        User currentUser = userService.getCurrentUser();
        
        Week week = new Week();
        week.setUser(currentUser);
        week.setStartDate(startDate);
        
        // Create days for the week
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String dayName : daysOfWeek) {
            Day day = new Day();
            day.setDayOfWeek(dayName);
            week.addDay(day);
        }
        
        return weekRepository.save(week);
    }
    
    public void deleteWeek(Long id) {
        weekRepository.deleteById(id);
    }
}
