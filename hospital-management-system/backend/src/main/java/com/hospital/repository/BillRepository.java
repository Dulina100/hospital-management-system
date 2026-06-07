package com.hospital.repository;

import com.hospital.management.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByBillNumber(String billNumber);
    List<Bill> findByPatient_Id(Long patientId);
    List<Bill> findByStatus(String status);
    List<Bill> findByPatient_IdAndStatus(Long patientId, String status);
}
