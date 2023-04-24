package org.example;

import org.example.ParseMono.MONOCurrencyRateService;
import org.example.ParseNBY.NBYCurrencyRateService;
import org.example.ParsePrivat.PrivatCurrencyRateService;
import org.example.bank.Bank;
import org.example.ui.PrettyPrintCurrencyService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BotLogic {
    static   CurrencyRateApiService apiNBY = new NBYCurrencyRateService();
    static   CurrencyRateApiService apiMONO = new MONOCurrencyRateService();
    static   CurrencyRateApiService apiPRIVAT = new PrivatCurrencyRateService();
    public static Bank bankChoice=Bank.ПриватБанк;
    public static int parseChose=2;

    public static String getChosenCurrency() {
        return String.valueOf(chosenCurrency);
    }
    public static List<Currency> chosenCurrency= new ArrayList<>();

    static {chosenCurrency.add(Currency.USD);}
   public static HashMap<String,RateResponceDto> currencyInfo = new HashMap<>();

    public static String bankMenuButton(String tmp){
        String message="";

            switch (tmp){

                case "НБУ": bankChoice=Bank.НБУ;
                    message="НБУ";
                    break;
                case "МоноБанк":bankChoice=Bank.Монобанк;
                    message="МоноБанк";
                    break;
                case "ПриватБанк":
                    bankChoice=Bank.ПриватБанк;
                    message="ПриватБанк";
                    break;

            }
        return message;
    }
    public static void Curracy(String temp){
        switch (temp ) {
            case "USD":
                if (!chosenCurrency.contains(Currency.USD)) {
                    chosenCurrency.add(Currency.USD);

                } else {
                    chosenCurrency.remove(Currency.USD);

                }
                break;
            case "EUR":
                if (!chosenCurrency.contains(Currency.EUR)) {
                chosenCurrency.add(Currency.EUR);

            } else {
                chosenCurrency.remove(Currency.EUR);

            }
                break;
            default:
                System.out.println("На жаль валюти " + chosenCurrency +" немає!Спробуйте ще раз.");
        }

    }
    public static void CurracyI(List temp){

        switch (bankChoice) {
            case НБУ:
               if(temp.contains(Currency.USD)){
                    currencyInfo.put("USD",apiNBY.getRates(parseChose).get(0));
                }else {
                    currencyInfo.put("USD",null);
               }
               if(temp.contains(Currency.EUR)){
                currencyInfo.put("EUR",apiNBY.getRates(parseChose).get(1));
               }
               else { currencyInfo.put("EUR",null);}
               break;

            case Монобанк:
                if(temp.contains(Currency.USD)){
                    currencyInfo.put("USD",apiMONO.getRates(parseChose).get(0));
                }
                else {
                    currencyInfo.put("USD",null);
                }
                if(temp.contains(Currency.EUR)){
                currencyInfo.put("EUR",apiMONO.getRates(parseChose).get(1));
                }
                else {
                    currencyInfo.put("EUR",null);
                }
                break;
            case ПриватБанк:
                if(temp.contains(Currency.USD)){
                    currencyInfo.put("USD",apiPRIVAT.getRates(parseChose).get(1));
                }
                else {
                    currencyInfo.put("USD",null);
                }
                if(temp.contains(Currency.EUR)){
                currencyInfo.put("EUR",apiPRIVAT.getRates(parseChose).get(0));
                }
                else {
                    currencyInfo.put("EUR",null);
                }
                break;
            default:
                System.out.println("На жаль валюти " + chosenCurrency +" немає!Спробуйте ще раз.");
        }

    }
    public static String parseMenuButton(String chose ){

         String parseMessage="";

            switch (chose){
                case "2": parseChose=2;
                    parseMessage= String.valueOf(parseChose);
                    break;
                case "3":parseChose=3;
                    parseMessage= String.valueOf(parseChose);
                    break;
                case "4":parseChose=4;
                    parseMessage= String.valueOf(parseChose);
                    break;
            }
            return parseMessage;
    }

    public static String getFinalMessage() {
        PrettyPrintCurrencyService prettyPrintCurrencyService=new PrettyPrintCurrencyService();
        CurracyI(chosenCurrency);
        RateResponceDto USD=currencyInfo.get("USD");
        RateResponceDto EUR=currencyInfo.get("EUR");
        String finalMassage="Банк "+bankChoice;
        String finalMassage2="";
        String finalMassage3="";
        if(USD!=null){
            finalMassage2=prettyPrintCurrencyService.convert(String.valueOf(USD.getRateBuy()),String.valueOf(USD.getRateSell()),String.valueOf(USD.getCurrencyFrom()));
        }
        if (EUR!=null){
            finalMassage3=prettyPrintCurrencyService.convert(String.valueOf(EUR.getRateBuy()),String.valueOf(EUR.getRateSell()),String.valueOf(EUR.getCurrencyFrom()));
        }
        return finalMassage+finalMassage2+finalMassage3;
    }
}

