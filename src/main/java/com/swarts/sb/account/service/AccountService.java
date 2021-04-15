package com.swarts.sb.account.service;


import com.swarts.sb.account.dto.AccountDto;
import com.swarts.sb.account.exception.AccountNotFoundException;
import com.swarts.sb.account.model.AccountEntity;
import com.swarts.sb.account.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {

        this.accountRepository = accountRepository;
    }

    public List<AccountDto> getAccounts() {
        List<AccountDto> accountDtoList = accountRepository.findAll()
                .stream()
                .map(this::toAccount)
                .collect(Collectors.toList());

        return accountDtoList;
    }

    private AccountDto toAccount(AccountEntity entity) {
        return AccountDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .status(entity.getStatus())
                .currency(entity.getCurrency())
                .balance(entity.getBalance())
                .build();
    }

    public AccountDto getAccountById(long id) {
        Optional<AccountEntity> entity = accountRepository.findById(id);

        if(!entity.isPresent()) {
            throw new AccountNotFoundException("Get account not found for id:" + id);
        }

        return toAccount(entity.get());
    }

    public void updateAccount(long id, AccountDto accountDto) {
        Optional<AccountEntity> entity = accountRepository.findById(id);

        if(!entity.isPresent()) {
            throw new AccountNotFoundException("Update account not found for id:" + id);
        }

        accountRepository.save(toAccountEntity(accountDto));
    }

    private AccountEntity toAccountEntity(AccountDto accountDto) {
        return AccountEntity.builder()
                .id(accountDto.getId())
                .name(accountDto.getName())
                .status(accountDto.getStatus())
                .currency(accountDto.getCurrency())
                .balance(accountDto.getBalance())
                .build();
    }

    public void deleteAccountById(long id) {
        try {
            accountRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exp) {
            throw new AccountNotFoundException("Delete account not found for id:" + id);
        }
    }
}

