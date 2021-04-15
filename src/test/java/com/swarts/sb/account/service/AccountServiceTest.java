package com.swarts.sb.account.service;

import com.swarts.sb.account.dto.AccountDto;
import com.swarts.sb.account.model.AccountEntity;
import com.swarts.sb.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    private AccountService accountService;

    private AccountRepository mockRepository;

    @BeforeEach
    public void setUp(){

        mockRepository = mock(AccountRepository.class);
        accountService = new AccountService(mockRepository);
    }

    @Test
    public void givenAccountsExist_WhenAllAccountsRequested_ThenReturnAllRecords ()  {

        List<AccountEntity> accountEntities = Collections.singletonList(buildAccountEntity());
        List<AccountDto> accountDto = Collections.singletonList(buildAccount());

        when(mockRepository.findAll()).thenReturn(accountEntities);

        List<AccountDto> accountResponse = accountService.getAccounts();

        assertEquals(accountDto, accountResponse);
    }

    @Test
    public void givenIdExist_WhenGetAccountByIdRequested_ThenReturnAccount () throws Exception {

        long accountId = 1L;
        AccountEntity accountEntity = buildAccountEntity();
        AccountDto accountDto = buildAccount();

        when(mockRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));

        AccountDto accountResponse = accountService.getAccountById(accountId);

        assertEquals(accountDto, accountResponse);
    }

    @Test
    public void givenIdExist_WhenUpdateAccountByIdRequested_ThenUpdatedRecordSuccessfully () throws Exception {

        long accountId = 1L;
        AccountEntity accountEntity = buildAccountEntity();
        AccountDto accountDto = buildAccount();

        when(mockRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));
        when(mockRepository.save(accountEntity)).thenReturn(accountEntity);

        accountService.updateAccount(accountId, accountDto);

        verify(mockRepository).save(any(AccountEntity.class));
    }

    @Test
    public void givenIdExist_WhenDeleteAccountByIdRequested_ThenDeletedRecordSuccessfully () throws Exception {

        long accountId = 1L;
        AccountEntity accountEntity = buildAccountEntity();

        when(mockRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));
        doNothing().when(mockRepository).delete(accountEntity);

        accountService.deleteAccountById(accountId);

        verify(mockRepository).deleteById(accountId);
    }



    private AccountEntity buildAccountEntity() {
        return AccountEntity.builder()
                .id(1L)
                .name("my-account")
                .status("ACTIVE")
                .currency("GBP")
                .balance(BigDecimal.valueOf(100.01))
                .build();
    }

    private AccountDto buildAccount() {
        return AccountDto.builder()
                .id(1L)
                .name("my-account")
                .status("ACTIVE")
                .currency("GBP")
                .balance(BigDecimal.valueOf(100.01))
                .build();
    }
}