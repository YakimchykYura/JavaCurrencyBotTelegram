package org.example.ParseMono;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Currency;
import org.example.CurrencyRateApiService;
import org.example.RateResponceDto;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ROUND_CEILING;

public class MONOCurrencyRateService implements CurrencyRateApiService {
    private static final Gson GSON = new Gson();
    private static final String url = "https://api.monobank.ua/bank/currency";
    private static String text2;
    @Override
    public List<RateResponceDto> getRates(Integer roundUp) {
        String text = null;
        try {
            text = Jsoup.connect(url).ignoreContentType(true).get().body().text();
            text2 = text;
        } catch (IOException e) {
            text = text2;
        }
        List<MONOCurrencyResponceDto> responceDtos = convertResponce(text);
        return responceDtos.stream()
                .map(item -> {
                    RateResponceDto dto = new RateResponceDto();
                    dto.setCurrencyFrom(item.getCurrencySell());
                    dto.setCurrencyTo(item.getCurrencyBuy());
                    dto.setRateBuy(item.getRateBuy().setScale(roundUp,ROUND_CEILING));
                    dto.setRateSell(item.getRateSell().setScale(roundUp,ROUND_CEILING));
                    return dto;
                })
                .collect(Collectors.toList());
    }
    private static List<MONOCurrencyResponceDto> convertResponce(String text) {
        Type type = TypeToken
                .getParameterized(List.class, MONOCurrencyResponceDto.class)
                .getType();
        List<MONOCurrencyResponceDto> responceDtos = GSON.fromJson(text, type);
        Map<Integer,String> cur = Map.of(
                980,"UAH",
                840, "USD",
                978, "EUR"
        );

        responceDtos = responceDtos.stream()
                .peek(item ->{
                    if(cur.containsKey(item.getCurrencyCodeB())) {
                        item.setCurrencyBuy(Currency.valueOf(cur.get(item.getCurrencyCodeB())));
                    }
                    if(cur.containsKey(item.getCurrencyCodeA())) {
                        item.setCurrencySell(Currency.valueOf(cur.get(item.getCurrencyCodeA())));
                    }
                })
                .filter(item -> item.getCurrencySell() != null)
                .collect(Collectors.toList());
        return responceDtos;
    }
}
