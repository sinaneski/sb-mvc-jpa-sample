package com.swarts.sb.account.model;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Builder
@Entity
@Table(name = "ACCOUNTS")
public class AccountEntity {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private long id;

    @Column(name = "NAME", nullable = false, length = 64)
    private String name;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "BALANCE")
    private BigDecimal balance;
}