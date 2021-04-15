package com.swarts.sb.account.controller;

import com.swarts.sb.account.dto.AccountDto;
import com.swarts.sb.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService service) {
        this.accountService = service;
    }

    @GetMapping(value = "/accounts")
    public List<AccountDto> getAccounts() {

        log.info("All accounts requested");

        return accountService.getAccounts();
    }

    @GetMapping(value = "/accounts/{id}")
    public AccountDto getAccountById(@PathVariable long id) {

        log.info("Get account for id {} requested", id);

        return accountService.getAccountById(id);
    }


    @PutMapping(value = "/accounts/{id}")
    public void updateAccount(@PathVariable long id, @RequestBody AccountDto account) {

        log.info("Update account for id {} requested", id);

        accountService.updateAccount(id, account);
    }

    @DeleteMapping(value = "/accounts/{id}")
    public void deleteAccountById(@PathVariable long id) {

        log.info("Delete account for id {} requested", id);

        accountService.deleteAccountById(id);
    }
}
