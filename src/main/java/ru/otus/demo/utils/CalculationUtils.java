package ru.otus.demo.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Year;

public class CalculationUtils {

    public static BigDecimal calculateSavings(int numberOfYears, BigDecimal rates) {
        int[] years = getYearsRange(numberOfYears);
        if (years.length > 1) {
            BigDecimal sum = BigDecimal.ZERO;
            for (int i : years) {
                sum = sum.add(calculateSavingsPerYear(i, rates));
            }
            return sum;
        } else {
            return calculateSavingsPerYear(LocalDate.now().getYear(), rates);
        }
    }

    public static int[] getYearsRange(int numberOfYears) {
        int currentYear = Year.now().getValue();
        int[] years;
        if (numberOfYears <= 1) {
            years = new int[]{ currentYear };
        } else {
            years = new int[numberOfYears];
            for (int i = 0; i < numberOfYears; i++) {
                years[i] = currentYear + i;
            }
        }
        return years;
    }

    public static BigDecimal incomeCalculation(BigDecimal sum, BigDecimal interest) {
        return sum.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
            .multiply(interest)
            .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
    }

    public static boolean isLeapYear(int year) {
        return (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);
    }

    public static int getDaysInMonth(int month, int year) {
        return switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;       // Месяцы с 31 днем
            case 4, 6, 9, 11 -> 30;                 // Месяцы с 30 днями
            case 2 -> isLeapYear(year) ? 29 : 28;   // Учитываем високосные года
            default -> -1;                          // Некорректный месяц
        };
    }

    public static BigDecimal calculateSavingsPerYear(int year, BigDecimal interestRate) {
        BigDecimal result = BigDecimal.ZERO;
        for (int month = 1; month <= 12; month++) {
            BigDecimal sum = BigDecimal.ZERO;
            for (int i = 1; i <= getDaysInMonth(month, year); i++) {
                sum = sum.add(BigDecimal.valueOf(i));
            }
            result = result.add(sum);
        }
        return result.add(incomeCalculation(result, interestRate));
    }

}