package com.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordDTO {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDateTime recordDate;
    private String diagnosis;
    private String treatmentPlan;
    private String prescription;
    private String notes;
    private String vitalSigns;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
