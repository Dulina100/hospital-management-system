package com.hospital.controller;

import com.hospital.management.dto.BillDTO;
import com.hospital.management.entity.Bill;
import com.hospital.management.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bills")
@CrossOrigin(origins = "http://localhost:4200")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BillDTO> createBill(@RequestBody Bill bill) {
        Bill createdBill = billService.createBill(bill);
        return new ResponseEntity<>(billService.convertToDTO(createdBill), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PATIENT')")
    public ResponseEntity<BillDTO> getBillById(@PathVariable Long id) {
        Bill bill = billService.getBillById(id);
        return new ResponseEntity<>(billService.convertToDTO(bill), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BillDTO>> getAllBills() {
        List<BillDTO> bills = billService.getAllBills().stream()
                .map(billService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PATIENT')")
    public ResponseEntity<List<BillDTO>> getBillsByPatientId(@PathVariable Long patientId) {
        List<BillDTO> bills = billService.getBillsByPatientId(patientId).stream()
                .map(billService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BillDTO>> getBillsByStatus(@PathVariable String status) {
        List<BillDTO> bills = billService.getBillsByStatus(status).stream()
                .map(billService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BillDTO> updateBill(@PathVariable Long id, @RequestBody Bill billDetails) {
        Bill updatedBill = billService.updateBill(id, billDetails);
        return new ResponseEntity<>(billService.convertToDTO(updatedBill), HttpStatus.OK);
    }

    @PostMapping("/{id}/pay")
    @PreAuthorize("hasAnyRole('ADMIN', 'PATIENT')")
    public ResponseEntity<BillDTO> processPayment(
            @PathVariable Long id,
            @RequestParam BigDecimal amount,
            @RequestParam String paymentMethod) {
        Bill paidBill = billService.processPayment(id, amount, paymentMethod);
        return new ResponseEntity<>(billService.convertToDTO(paidBill), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteBill(@PathVariable Long id) {
        billService.deleteBill(id);
        return new ResponseEntity<>("Bill deleted successfully", HttpStatus.OK);
    }
}
