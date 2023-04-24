package org.example.ParsePrivat;

import org.example.Currency;

import java.math.BigDecimal;

public class PrivatCurrencyResponceDto {
    private Currency ccy;
    private Currency base_ccy;
    private BigDecimal buy;
    private BigDecimal sale;

    public Currency getCcy() {
        return ccy;
    }

    public Currency getBase_ccy() {
        return base_ccy;
    }
    public BigDecimal getBuy() {
        return buy;
    }
    public BigDecimal getSale() {
        return sale;
    }

    @Override
    public String toString() {
        return "PrivatCurrencyResponceDto{" +
                "ccy=" + ccy +
                ", base_ccy=" + base_ccy +
                ", buy=" + buy +
                ", sale=" + sale +
                '}';
    }
}
