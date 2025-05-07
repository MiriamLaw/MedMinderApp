package com.medminder.repositories;

import com.medminder.domains.Alarm;
import com.medminder.domains.MedicationEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findByMedicationEntryOrderByTime(MedicationEntry medicationEntry);
}
