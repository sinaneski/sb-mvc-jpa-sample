package com.swarts.sb.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private long id;
    private String name;
    private String status;
    private String currency;
    private BigDecimal balance;
}