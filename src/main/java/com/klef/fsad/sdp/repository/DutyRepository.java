package com.klef.fsad.sdp.repository;

import com.klef.fsad.sdp.model.Duty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DutyRepository extends JpaRepository<Duty,Integer> {
}
