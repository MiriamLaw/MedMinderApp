package com.medminder.repositories;

import com.medminder.domains.Day;
import com.medminder.domains.Week;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {
    List<Day> findByWeekOrderByDayOfWeek(Week week);
    Day findByWeekAndDayOfWeek(Week week, String dayOfWeek);
}
