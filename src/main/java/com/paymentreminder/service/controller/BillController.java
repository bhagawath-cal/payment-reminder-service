package com.paymentreminder.service.controller;

import com.paymentreminder.service.model.Bill;
import com.paymentreminder.service.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {
    
    @Autowired
    private BillRepository billRepository;
    
    @GetMapping
    public ResponseEntity<List<Bill>> getAllBills() {
        List<Bill> bills = billRepository.findAll();
        return ResponseEntity.ok(bills);
    }
    
    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody Bill bill) {
        Bill savedBill = billRepository.save(bill);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBill);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
        return billRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Bill> updateBill(@PathVariable Long id, @RequestBody Bill billDetails) {
        return billRepository.findById(id)
                .map(bill -> {
                    bill.setBillName(billDetails.getBillName());
                    bill.setAmount(billDetails.getAmount());
                    bill.setDueDate(billDetails.getDueDate());
                    bill.setCategory(billDetails.getCategory());
                    bill.setStatus(billDetails.getStatus());
                    Bill updatedBill = billRepository.save(bill);
                    return ResponseEntity.ok(updatedBill);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
        if (billRepository.existsById(id)) {
            billRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}