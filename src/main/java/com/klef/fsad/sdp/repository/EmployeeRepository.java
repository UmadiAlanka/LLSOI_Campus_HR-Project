package com.klef.fsad.sdp.repository;

import com.klef.fsad.sdp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// CHANGE: String -> Long to match the new auto-increment ID
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // These use Spring Data JPA's magic naming convention
    Optional<Employee> findByUsername(String username);
    Employee findByEmail(String email);
    Employee findByResetToken(String resetToken);

    // REMOVED: findByEmployeeId and existsByEmployeeId
    // Standard JPA provides findById(Long) and existsById(Long) automatically.

    // REMOVED: countAllEmployees()
    // Standard JPA provides count() automatically, which we used in your Service.
}