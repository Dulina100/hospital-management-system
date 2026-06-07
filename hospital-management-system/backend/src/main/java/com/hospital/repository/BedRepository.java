package com.hospital.repository;

import com.hospital.management.entity.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BedRepository extends JpaRepository<Bed, Long> {
    Optional<Bed> findByBedNumber(String bedNumber);
    List<Bed> findByWard(String ward);
    List<Bed> findByStatus(String status);
    List<Bed> findByBedType(String bedType);
}
