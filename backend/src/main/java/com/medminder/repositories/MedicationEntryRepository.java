package com.medminder.repositories;

import com.medminder.domains.Day;
import com.medminder.domains.MedicationEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationEntryRepository extends JpaRepository<MedicationEntry, Long> {
    List<MedicationEntry> findByDayOrderByTimeSlot(Day day);
}
