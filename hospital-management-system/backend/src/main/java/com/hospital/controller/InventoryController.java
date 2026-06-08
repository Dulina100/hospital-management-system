package com.hospital.controller;

import com.hospital.management.dto.InventoryDTO;
import com.hospital.management.entity.Inventory;
import com.hospital.management.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "http://localhost:4200")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InventoryDTO> createInventoryItem(@RequestBody Inventory inventory) {
        Inventory createdItem = inventoryService.createInventoryItem(inventory);
        return new ResponseEntity<>(inventoryService.convertToDTO(createdItem), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'PHARMACIST')")
    public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable Long id) {
        Inventory inventory = inventoryService.getInventoryById(id);
        return new ResponseEntity<>(inventoryService.convertToDTO(inventory), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'PHARMACIST')")
    public ResponseEntity<List<InventoryDTO>> getAllInventory() {
        List<InventoryDTO> inventory = inventoryService.getAllInventory().stream()
                .map(inventoryService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'PHARMACIST')")
    public ResponseEntity<List<InventoryDTO>> getInventoryByCategory(@PathVariable String category) {
        List<InventoryDTO> inventory = inventoryService.getInventoryByCategory(category).stream()
                .map(inventoryService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @GetMapping("/low-stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'PHARMACIST')")
    public ResponseEntity<List<InventoryDTO>> getLowStockItems() {
        List<InventoryDTO> inventory = inventoryService.getLowStockItems().stream()
                .map(inventoryService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InventoryDTO> updateInventory(@PathVariable Long id, @RequestBody Inventory inventoryDetails) {
        Inventory updatedInventory = inventoryService.updateInventory(id, inventoryDetails);
        return new ResponseEntity<>(inventoryService.convertToDTO(updatedInventory), HttpStatus.OK);
    }

    @PutMapping("/{id}/quantity")
    @PreAuthorize("hasAnyRole('ADMIN', 'PHARMACIST')")
    public ResponseEntity<InventoryDTO> updateQuantity(@PathVariable Long id, @RequestParam Integer quantity) {
        Inventory updatedInventory = inventoryService.updateQuantity(id, quantity);
        return new ResponseEntity<>(inventoryService.convertToDTO(updatedInventory), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return new ResponseEntity<>("Inventory item deleted successfully", HttpStatus.OK);
    }
}
