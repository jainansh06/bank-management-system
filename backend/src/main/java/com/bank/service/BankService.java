package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.model.User;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BankService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public Account createAccount(User user, String accountType) {
        String accountNumber = generateAccountNumber();
        Account account = new Account(accountNumber, 0.0, accountType, user);
        return accountRepository.save(account);
    }
    
    public Account deposit(String accountNumber, Double amount) {
        Optional<Account> accountOpt = accountRepository.findByAccountNumber(accountNumber);
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            account.setBalance(account.getBalance() + amount);
            
            Transaction transaction = new Transaction("DEPOSIT", amount, "Deposit to account", account);
            transactionRepository.save(transaction);
            
            return accountRepository.save(account);
        }
        return null;
    }
    
    public Account withdraw(String accountNumber, Double amount) {
        Optional<Account> accountOpt = accountRepository.findByAccountNumber(accountNumber);
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            if (account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                
                Transaction transaction = new Transaction("WITHDRAWAL", amount, "Withdrawal from account", account);
                transactionRepository.save(transaction);
                
                return accountRepository.save(account);
            }
        }
        return null;
    }
    
    public List<Transaction> getTransactions(String accountNumber) {
        Optional<Account> accountOpt = accountRepository.findByAccountNumber(accountNumber);
        return accountOpt.map(account -> transactionRepository.findByAccount(account)).orElse(null);
    }
    
    public List<Account> getUserAccounts(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.map(user -> accountRepository.findByUser(user)).orElse(null);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    private String generateAccountNumber() {
        Random random = new Random();
        return "ACC" + (1000000000L + random.nextInt(900000000));
    }
}