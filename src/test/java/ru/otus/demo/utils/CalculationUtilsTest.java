package ru.otus.demo.utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static ru.otus.demo.utils.CalculationUtils.calculateSavingsPerYear;
import static ru.otus.demo.utils.CalculationUtils.getDaysInMonth;
import static ru.otus.demo.utils.CalculationUtils.incomeCalculation;
import static ru.otus.demo.utils.CalculationUtils.isLeapYear;

public class CalculationUtilsTest {

    @Test
    void mustReturnALeapYear() {
        assertTrue(isLeapYear(2024));
    }

    @Test
    void mustReturnNonLeapYear() {
        assertFalse(isLeapYear(2025));
    }

    @Test
    void mustProcessFebruary() {
        assertEquals(29, getDaysInMonth(2, 2024));
        assertEquals(28, getDaysInMonth(2, 2025));
    }

    @Test
    void mustProcessAprJunSepNov() {
        assertEquals(30, getDaysInMonth(4, 2024));
        assertEquals(30, getDaysInMonth(6, 2024));
        assertEquals(30, getDaysInMonth(9, 2024));
        assertEquals(30, getDaysInMonth(11, 2024));
    }

    @Test
    void mustProcessJanMarMayJulAugOctDec() {
        assertEquals(31, getDaysInMonth(1, 2024));
        assertEquals(31, getDaysInMonth(3, 2024));
        assertEquals(31, getDaysInMonth(5, 2024));
        assertEquals(31, getDaysInMonth(7, 2024));
        assertEquals(31, getDaysInMonth(8, 2024));
        assertEquals(31, getDaysInMonth(10, 2024));
        assertEquals(31, getDaysInMonth(12, 2024));
    }

    @Test
    void mustProcessIncorrectArgument() {
        assertEquals(-1, getDaysInMonth(0, 2024));
    }

    @Test
    void mustCalculateCorrectIncome() {

        BigDecimal initialAmount = new BigDecimal("1000.00");
        BigDecimal interestRate = new BigDecimal("1.5");

        BigDecimal monthlyInterest = new BigDecimal("1.25");

        assertEquals(monthlyInterest, incomeCalculation(initialAmount, interestRate));

    }

    @Test
    void mustCalculateZeroInterestForALeapYear() {
        assertEquals(new BigDecimal("5767.00"), calculateSavingsPerYear(2024, BigDecimal.ZERO));
    }

    @Test
    void mustCalculateZeroInterestForNonLeapYear() {
        assertEquals(new BigDecimal("5738.00"), calculateSavingsPerYear(2025, BigDecimal.ZERO));
    }

    @Test
    void mustCalculateSetInterestForNonLeapYear() {
        assertEquals(new BigDecimal("5745.17"), calculateSavingsPerYear(2025, new BigDecimal("1.5")));
    }

    @Test
    void mustReturnCorrectArraysRange() {
        int[] years = new int[] { 2024, 2025 };
        assertArrayEquals(years, CalculationUtils.getYearsRange(2));
    }

    @Test
    void mustReturnCorrectArraysRangeIfZeroGiven() {
        assertArrayEquals(new int[] {2024}, CalculationUtils.getYearsRange(0));
    }

    @Test
    void AAA() {
        System.out.println(CalculationUtils.calculateSavings(0, new BigDecimal("1.5")));
    }

}