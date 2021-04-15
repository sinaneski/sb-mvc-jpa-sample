package com.swarts.sb.account.repository;

import com.swarts.sb.account.model.AccountEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void givenWhenAccountsExists_WhenGetAll_ThenReturnAccounts() {

        AccountEntity accountEntity = expectedAccount();

        entityManager.persist(accountEntity);

       List<AccountEntity> result = accountRepository.findAll();

        assertEquals(Collections.singletonList(accountEntity), result);
    }

    private AccountEntity expectedAccount() {
        return AccountEntity.builder()
                .id(1L)
                .name("my-account")
                .status("ACTIVE")
                .currency("GBP")
                .balance(BigDecimal.valueOf(100.01))
                .build();
    }

    @Test
    public void givenWhenIdExists_WhenGetAccountById_ThenReturnAccount() {

        AccountEntity accountEntity = expectedAccount();

        entityManager.persist(accountEntity);

        Optional<AccountEntity> result = accountRepository.findById(1L);

        assertEquals(Optional.of(accountEntity), result);
    }

    @Test
    public void givenWhenIdNotExists_WhenGetAccountById_ThenReturnNull() {

        Optional<AccountEntity> result = accountRepository.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    public void givenWhenIdExists_WhenDeleteAccountById_ThenSuccesfullyRemoveAccount() {

        AccountEntity accountEntity = expectedAccount();

        entityManager.persist(accountEntity);

        accountRepository.deleteById(1L);

        assertFalse(accountRepository.findById(1L).isPresent());
    }

    @Test
    public void givenWhenIdNotExists_WhenRemoveAccountById_ThenReturnEmptyOption() {

        assertThrows(EmptyResultDataAccessException.class, () -> accountRepository.deleteById(999L));
    }
}