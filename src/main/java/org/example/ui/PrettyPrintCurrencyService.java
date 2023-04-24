package org.example.ui;

import org.example.Currency;
//import org.example.bank.Bank;

import java.math.BigDecimal;

public class PrettyPrintCurrencyService {
    public String convert(String buy, String sale,String currency){
        String temp = "\n${currency}/ UAH \nКупівля: ${buy} \nПродаж: ${sale}\n";
        return temp.replace("${currency}", currency)
                .replace("${buy}", buy)
                .replace("${sale}", sale);
    }
}
