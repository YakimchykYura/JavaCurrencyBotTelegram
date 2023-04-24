package org.example;

import java.util.List;

public interface CurrencyRateApiService {
    List<RateResponceDto> getRates(Integer roundUp);
}
