package com.hospital.service;

import com.hospital.management.entity.Nurse;
import com.hospital.management.repository.NurseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NurseService {

    @Autowired
    private NurseRepository nurseRepository;

    public Nurse createNurse(Nurse nurse) {
        return nurseRepository.save(nurse);
    }

    public Nurse getNurseById(Long id) {
        return nurseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nurse not found with id: " + id));
    }

    public Optional<Nurse> getNurseByUserId(Long userId) {
        return nurseRepository.findByUser_Id(userId);
    }

    public List<Nurse> getAllNurses() {
        return nurseRepository.findAll();
    }

    public List<Nurse> getNursesByShift(String shift) {
        return nurseRepository.findByShift(shift);
    }

    public List<Nurse> getNursesByDepartment(String department) {
        return nurseRepository.findByDepartment(department);
    }

    public List<Nurse> getAvailableNurses() {
        return nurseRepository.findByIsAvailableTrue();
    }

    public Nurse updateNurse(Long id, Nurse nurseDetails) {
        Nurse nurse = getNurseById(id);
        nurse.setQualification(nurseDetails.getQualification());
        nurse.setExperienceYears(nurseDetails.getExperienceYears());
        nurse.setShift(nurseDetails.getShift());
        nurse.setDepartment(nurseDetails.getDepartment());
        nurse.setIsAvailable(nurseDetails.getIsAvailable());
        return nurseRepository.save(nurse);
    }

    public void deleteNurse(Long id) {
        nurseRepository.deleteById(id);
    }
}
