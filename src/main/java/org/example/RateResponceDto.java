package org.example;

import java.math.BigDecimal;

public class RateResponceDto {
    private Currency currencyFrom;
    private Currency currencyTo;
    private BigDecimal rateBuy;
    private BigDecimal rateSell;

    public Currency getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(Currency currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public void setCurrencyTo(Currency currencyTo) {
        this.currencyTo = currencyTo;
    }
    public BigDecimal getRateBuy() {
        return rateBuy;
    }

    public void setRateBuy(BigDecimal rateBuy) {
        this.rateBuy = rateBuy;
    }

    public BigDecimal getRateSell() {
        return rateSell;
    }

    public void setRateSell(BigDecimal rateSell) {
        this.rateSell = rateSell;
    }

    @Override
    public String toString() {
        return  "From:" + currencyFrom +
                ", To:" + currencyTo +
                ", rateBuy:" + rateBuy +
                ", rateSell:" + rateSell;
    }
}
