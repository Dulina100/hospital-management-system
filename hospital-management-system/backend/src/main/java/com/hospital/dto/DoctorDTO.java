package com.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
    private Long id;
    private UserDTO user;
    private String specialization;
    private String licenseNumber;
    private Integer experienceYears;
    private String qualification;
    private String availability;
    private Double consultationFee;
    private String department;
    private Boolean isAvailable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
