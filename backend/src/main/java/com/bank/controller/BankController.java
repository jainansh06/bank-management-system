package com.bank.controller;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.model.User;
import com.bank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bank")
@CrossOrigin(origins = "http://localhost:5500")
public class BankController {
    
    @Autowired
    private BankService bankService;
    
    @PostMapping("/account/create")
    public ResponseEntity<?> createAccount(@RequestBody Map<String, String> request) {
        Long userId = Long.parseLong(request.get("userId"));
        String accountType = request.get("accountType");
        
        // In a real app, you would get user from database
        User user = new User();
        user.setId(userId);
        
        Account account = bankService.createAccount(user, accountType);
        
        Map<String, Object> response = new HashMap<>();
        if (account != null) {
            response.put("success", true);
            response.put("account", account);
        } else {
            response.put("success", false);
            response.put("message", "Failed to create account");
        }
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/account/deposit")
    public ResponseEntity<?> deposit(@RequestBody Map<String, Object> request) {
        String accountNumber = (String) request.get("accountNumber");
        Double amount = Double.parseDouble(request.get("amount").toString());
        
        Account account = bankService.deposit(accountNumber, amount);
        
        Map<String, Object> response = new HashMap<>();
        if (account != null) {
            response.put("success", true);
            response.put("account", account);
        } else {
            response.put("success", false);
            response.put("message", "Deposit failed");
        }
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/account/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody Map<String, Object> request) {
        String accountNumber = (String) request.get("accountNumber");
        Double amount = Double.parseDouble(request.get("amount").toString());
        
        Account account = bankService.withdraw(accountNumber, amount);
        
        Map<String, Object> response = new HashMap<>();
        if (account != null) {
            response.put("success", true);
            response.put("account", account);
        } else {
            response.put("success", false);
            response.put("message", "Withdrawal failed - insufficient funds or invalid account");
        }
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/account/transactions/{accountNumber}")
    public ResponseEntity<?> getTransactions(@PathVariable String accountNumber) {
        List<Transaction> transactions = bankService.getTransactions(accountNumber);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("transactions", transactions);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/user/accounts/{userId}")
    public ResponseEntity<?> getUserAccounts(@PathVariable Long userId) {
        List<Account> accounts = bankService.getUserAccounts(userId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("accounts", accounts);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/admin/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = bankService.getAllUsers();
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("users", users);
        return ResponseEntity.ok(response);
    }
}   
