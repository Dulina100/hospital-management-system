package com.hospital.service;

import com.hospital.management.dto.InventoryDTO;
import com.hospital.management.entity.Inventory;
import com.hospital.management.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public Inventory createInventoryItem(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with id: " + id));
    }

    public Optional<Inventory> getInventoryByItemCode(String itemCode) {
        return inventoryRepository.findByItemCode(itemCode);
    }

    public List<Inventory> getInventoryByCategory(String category) {
        return inventoryRepository.findByCategory(category);
    }

    public List<Inventory> getLowStockItems() {
        return inventoryRepository.findByQuantityLessThanEqual(10);
    }

    public List<Inventory> getInventoryBySupplier(String supplier) {
        return inventoryRepository.findBySupplier(supplier);
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory updateInventory(Long id, Inventory inventoryDetails) {
        Inventory inventory = getInventoryById(id);
        inventory.setItemName(inventoryDetails.getItemName());
        inventory.setCategory(inventoryDetails.getCategory());
        inventory.setQuantity(inventoryDetails.getQuantity());
        inventory.setUnit(inventoryDetails.getUnit());
        inventory.setReorderLevel(inventoryDetails.getReorderLevel());
        inventory.setUnitPrice(inventoryDetails.getUnitPrice());
        inventory.setSupplier(inventoryDetails.getSupplier());
        inventory.setLocation(inventoryDetails.getLocation());
        inventory.setExpiryDate(inventoryDetails.getExpiryDate());
        return inventoryRepository.save(inventory);
    }

    public Inventory updateQuantity(Long id, Integer quantity) {
        Inventory inventory = getInventoryById(id);
        inventory.setQuantity(quantity);
        return inventoryRepository.save(inventory);
    }

    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }

    public InventoryDTO convertToDTO(Inventory inventory) {
        InventoryDTO dto = new InventoryDTO();
        dto.setId(inventory.getId());
        dto.setItemName(inventory.getItemName());
        dto.setItemCode(inventory.getItemCode());
        dto.setCategory(inventory.getCategory());
        dto.setQuantity(inventory.getQuantity());
        dto.setUnit(inventory.getUnit());
        dto.setReorderLevel(inventory.getReorderLevel());
        dto.setUnitPrice(inventory.getUnitPrice());
        dto.setSupplier(inventory.getSupplier());
        dto.setLocation(inventory.getLocation());
        dto.setExpiryDate(inventory.getExpiryDate());
        dto.setCreatedAt(inventory.getCreatedAt());
        dto.setUpdatedAt(inventory.getUpdatedAt());
        return dto;
    }
}
