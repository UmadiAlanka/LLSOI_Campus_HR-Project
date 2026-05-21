package com.klef.fsad.sdp.repository;

import com.klef.fsad.sdp.model.Leave;
import com.klef.fsad.sdp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Integer> {

    List<Leave> findByEmployee(Employee employee);

    List<Leave> findByStatus(String status);

    List<Leave> findByEmployeeAndStatus(Employee employee, String status);
}
