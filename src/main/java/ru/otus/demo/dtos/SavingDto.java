package ru.otus.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavingDto {
    private int[] period;
    private BigDecimal interestRate;
    private BigDecimal savingsAmount;
    private BigDecimal interestAmount;
    private BigDecimal totalAmount;
}