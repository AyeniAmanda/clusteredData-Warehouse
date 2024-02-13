package com.example.clustereddatawarehouse.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "deals")
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "deal_id", unique = true)
    @NotNull
    private String dealUniqueId;

    @Column(name = "ordering_currency")
    @NotNull
    private Currency orderingCurrencyISO;

    @Column(name = "to_currency")
    @NotNull
    private Currency toCurrencyISO;

    @Column(name = "deal_timestamp", columnDefinition = "TIMESTAMP")
    @NotNull
    private Instant dealTimestamp;

    @Column(name = "amount_in_ordering_currency")
    @NotNull
    private BigDecimal amountInOrderingCurrency;
}
