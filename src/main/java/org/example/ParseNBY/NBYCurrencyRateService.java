package org.example.ParseNBY;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.CurrencyRateApiService;
import org.example.RateResponceDto;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ROUND_CEILING;

public class NBYCurrencyRateService implements CurrencyRateApiService {
    private static final Gson GSON = new Gson();
    private static final String url = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    @Override
    public List<RateResponceDto> getRates(Integer roundUp) {

        String text = null;
        try {
            text = Jsoup.connect(url).ignoreContentType(true).get().body().text();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<NBYCurrencyResponceDto> responceDtos = convertResponce(text);

        return responceDtos.stream()
                .map(item -> {
                    RateResponceDto dto = new RateResponceDto();
                    dto.setCurrencyFrom(item.getCc());
                    dto.setCurrencyTo(item.getCurrencyTo());
                    dto.setRateBuy(item.getRate().setScale(roundUp,ROUND_CEILING));
                    dto.setRateSell(BigDecimal.valueOf(0).setScale(roundUp,ROUND_CEILING));
                    return dto;
                })
                .collect(Collectors.toList());
    }
    private List<NBYCurrencyResponceDto> convertResponce(String text) {
        Type type = TypeToken
                .getParameterized(List.class, NBYCurrencyResponceDto.class)
                .getType();
        List<NBYCurrencyResponceDto> responceDtos = GSON.fromJson(text, type);

        return responceDtos.stream()
                .filter(item -> item.getCc() != null)
                .collect(Collectors.toList());
    }

}