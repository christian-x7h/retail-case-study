package app.retail.service;

import app.retail.model.db.PriceData;
import reactor.core.publisher.Mono;
import app.retail.model.CurrencyCode;

import java.math.BigDecimal;

public interface PriceService {

    Mono<PriceData> getPriceData(Long id);

    Mono<PriceData> upsertPriceData(Long id, BigDecimal newPrice, CurrencyCode currencyCode);
}
