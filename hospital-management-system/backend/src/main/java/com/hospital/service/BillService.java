package com.hospital.service;

import com.hospital.management.dto.BillDTO;
import com.hospital.management.entity.Bill;
import com.hospital.management.entity.Patient;
import com.hospital.management.repository.BillRepository;
import com.hospital.management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private PatientRepository patientRepository;

    public Bill createBill(Bill bill) {
        Patient patient = patientRepository.findById(bill.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        bill.setPatient(patient);
        bill.setBillNumber(generateBillNumber());
        bill.setRemainingAmount(bill.getTotalAmount());
        bill.setStatus("PENDING");
        bill.setBillDate(LocalDateTime.now());
        return billRepository.save(bill);
    }

    public Bill getBillById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + id));
    }

    public Optional<Bill> getBillByBillNumber(String billNumber) {
        return billRepository.findByBillNumber(billNumber);
    }

    public List<Bill> getBillsByPatientId(Long patientId) {
        return billRepository.findByPatient_Id(patientId);
    }

    public List<Bill> getBillsByStatus(String status) {
        return billRepository.findByStatus(status);
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Bill updateBill(Long id, Bill billDetails) {
        Bill bill = getBillById(id);
        bill.setTotalAmount(billDetails.getTotalAmount());
        bill.setDescription(billDetails.getDescription());
        bill.setDueDate(billDetails.getDueDate());
        return billRepository.save(bill);
    }

    public Bill processPayment(Long id, BigDecimal amount, String paymentMethod) {
        Bill bill = getBillById(id);
        BigDecimal newPaidAmount = bill.getPaidAmount().add(amount);

        if (newPaidAmount.compareTo(bill.getTotalAmount()) > 0) {
            throw new RuntimeException("Payment amount exceeds bill total");
        }

        bill.setPaidAmount(newPaidAmount);
        bill.setRemainingAmount(bill.getTotalAmount().subtract(newPaidAmount));
        bill.setPaymentMethod(paymentMethod);

        if (bill.getRemainingAmount().compareTo(BigDecimal.ZERO) == 0) {
            bill.setStatus("PAID");
        } else {
            bill.setStatus("PARTIAL_PAID");
        }

        return billRepository.save(bill);
    }

    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }

    private String generateBillNumber() {
        return "BILL-" + System.currentTimeMillis();
    }

    public BillDTO convertToDTO(Bill bill) {
        BillDTO dto = new BillDTO();
        dto.setId(bill.getId());
        dto.setBillNumber(bill.getBillNumber());
        dto.setPatientId(bill.getPatient().getId());
        dto.setBillDate(bill.getBillDate());
        dto.setTotalAmount(bill.getTotalAmount());
        dto.setPaidAmount(bill.getPaidAmount());
        dto.setRemainingAmount(bill.getRemainingAmount());
        dto.setStatus(bill.getStatus());
        dto.setPaymentMethod(bill.getPaymentMethod());
        dto.setDescription(bill.getDescription());
        dto.setDueDate(bill.getDueDate());
        dto.setCreatedAt(bill.getCreatedAt());
        dto.setUpdatedAt(bill.getUpdatedAt());
        return dto;
    }
}
