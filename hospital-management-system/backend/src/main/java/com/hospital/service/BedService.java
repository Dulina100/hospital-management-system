package com.hospital.service;

import com.hospital.management.entity.Bed;
import com.hospital.management.repository.BedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BedService {

    @Autowired
    private BedRepository bedRepository;

    public Bed createBed(Bed bed) {
        return bedRepository.save(bed);
    }

    public Bed getBedById(Long id) {
        return bedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bed not found with id: " + id));
    }

    public Optional<Bed> getBedByBedNumber(String bedNumber) {
        return bedRepository.findByBedNumber(bedNumber);
    }

    public List<Bed> getAllBeds() {
        return bedRepository.findAll();
    }

    public List<Bed> getBedsByWard(String ward) {
        return bedRepository.findByWard(ward);
    }

    public List<Bed> getAvailableBeds() {
        return bedRepository.findByStatus("AVAILABLE");
    }

    public List<Bed> getOccupiedBeds() {
        return bedRepository.findByStatus("OCCUPIED");
    }

    public List<Bed> getBedsByType(String bedType) {
        return bedRepository.findByBedType(bedType);
    }

    public Bed updateBed(Long id, Bed bedDetails) {
        Bed bed = getBedById(id);
        bed.setBedNumber(bedDetails.getBedNumber());
        bed.setWard(bedDetails.getWard());
        bed.setBedType(bedDetails.getBedType());
        bed.setStatus(bedDetails.getStatus());
        return bedRepository.save(bed);
    }

    public void deleteBed(Long id) {
        bedRepository.deleteById(id);
    }
}
