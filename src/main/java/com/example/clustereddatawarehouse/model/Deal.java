package com.example.clustereddatawarehouse.model;

import lombok.*;

import javax.persistence.*;
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

    @Column(nullable = false, name = "deal_id", unique = true)
    private String dealUniqueId;

    @Column(nullable = false, name = "ordering_currency")
    private Currency orderingCurrencyISO;

    @Column(nullable = false, name = "to_currency")
    private Currency toCurrencyISO;

    @Column(nullable = false, name = "deal_timestamp")
    private Instant dealTimestamp;

    @Column(nullable = false, name = "amount_in_ordering_currency")
    private BigDecimal amountInOrderingCurrency;
}
