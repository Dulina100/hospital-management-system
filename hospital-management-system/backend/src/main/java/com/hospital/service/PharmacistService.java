package com.hospital.service;

import com.hospital.management.entity.Pharmacist;
import com.hospital.management.repository.PharmacistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PharmacistService {

    @Autowired
    private PharmacistRepository pharmacistRepository;

    public Pharmacist createPharmacist(Pharmacist pharmacist) {
        return pharmacistRepository.save(pharmacist);
    }

    public Pharmacist getPharmacistById(Long id) {
        return pharmacistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pharmacist not found with id: " + id));
    }

    public Optional<Pharmacist> getPharmacistByUserId(Long userId) {
        return pharmacistRepository.findByUser_Id(userId);
    }

    public List<Pharmacist> getAllPharmacists() {
        return pharmacistRepository.findAll();
    }

    public List<Pharmacist> getAvailablePharmacists() {
        return pharmacistRepository.findByIsAvailableTrue();
    }

    public Pharmacist updatePharmacist(Long id, Pharmacist pharmacistDetails) {
        Pharmacist pharmacist = getPharmacistById(id);
        pharmacist.setQualification(pharmacistDetails.getQualification());
        pharmacist.setExperienceYears(pharmacistDetails.getExperienceYears());
        pharmacist.setPharmacyLocation(pharmacistDetails.getPharmacyLocation());
        pharmacist.setIsAvailable(pharmacistDetails.getIsAvailable());
        return pharmacistRepository.save(pharmacist);
    }

    public void deletePharmacist(Long id) {
        pharmacistRepository.deleteById(id);
    }
}
