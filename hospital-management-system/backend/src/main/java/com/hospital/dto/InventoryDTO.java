package com.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private Long id;
    private String itemName;
    private String itemCode;
    private String category;
    private Integer quantity;
    private String unit;
    private Integer reorderLevel;
    private Double unitPrice;
    private String supplier;
    private String location;
    private LocalDateTime expiryDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
