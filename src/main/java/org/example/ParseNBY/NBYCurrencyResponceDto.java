package org.example.ParseNBY;

import org.example.Currency;

import java.math.BigDecimal;

public class NBYCurrencyResponceDto {
    private Integer r030;
    private  String txt;
    private BigDecimal rate;
    private Currency cc;
    private String exchangedate;

    private Currency currencyTo = Currency.UAH;

    public BigDecimal getRate() {
        return rate;
    }
    public Currency getCc() {
        return cc;
    }
    public Currency getCurrencyTo() {
        return currencyTo;
    }

    @Override
    public String toString() {
        return "NBYCurrencyResponceDto{" +
                "r030=" + r030 +
                ", txt='" + txt + '\'' +
                ", rate=" + rate +
                ", cc=" + cc +
                ", exchangedate=" + exchangedate +
                '}';
    }
}


