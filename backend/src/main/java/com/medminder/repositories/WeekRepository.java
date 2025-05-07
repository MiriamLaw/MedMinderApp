package com.medminder.repositories;

import com.medminder.domains.User;
import com.medminder.domains.Week;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeekRepository extends JpaRepository<Week, Long> {
    List<Week> findByUserOrderByStartDateDesc(User user);
}
