package com.swarts.sb.account.controller;

import com.swarts.sb.account.dto.AccountDto;
import com.swarts.sb.account.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        objectMapper = new ObjectMapper();
    }

    @Test
    public void givenAccountsExist_WhenAllAccountsRequested_ThenReturnAllRecords () throws Exception {

        List<AccountDto> accounts = Collections.singletonList(buildAccount());

        when(accountService.getAccounts()).thenReturn(accounts);

        mockMvc.perform(get("/accounts")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(accounts)));
    }

    @Test
    public void givenIdExist_WhenGetAccountByIdRequested_ThenReturnAccount () throws Exception {

        AccountDto account = buildAccount();

        when(accountService.getAccountById(1)).thenReturn(account);

        mockMvc.perform(get("/accounts/1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(account)));
    }

    @Test
    public void givenIdExist_WhenUpdateAccountByIdRequested_ThenUpdatedRecordSuccessfully () throws Exception {

        AccountDto account = buildAccount();

        doNothing().when(accountService).updateAccount(1, account);

        mockMvc.perform(put("/accounts/1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk());
    }

    @Test
    public void givenIdExist_WhenDeleteAccountByIdRequested_ThenDeletedRecordSuccessfully () throws Exception {

        doNothing().when(accountService).deleteAccountById(1);

        mockMvc.perform(delete("/accounts/1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
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