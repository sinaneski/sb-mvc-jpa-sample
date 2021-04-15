package com.swarts.sb.account.controller;

import com.swarts.sb.account.dto.ErrorDto;
import com.swarts.sb.account.exception.AccountNotFoundException;
import com.swarts.sb.account.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
public class AccountExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void givenWhenIdNotExists_WhenGetAccountById_ThenThrowAccountNotFound() throws Exception {

        ErrorDto errorDto = new ErrorDto(404, "Not Found", "Account with id 999 not found");

        AccountNotFoundException notFoundException = new AccountNotFoundException("Account with id 999 not found");

        when(accountService.getAccountById(999)).thenThrow(notFoundException);

        mockMvc.perform(get("/accounts/999")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(errorDto)));
    }
}