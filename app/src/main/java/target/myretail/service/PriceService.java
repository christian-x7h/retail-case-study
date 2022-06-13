package target.myretail.service;

import reactor.core.publisher.Mono;
import target.myretail.model.CurrencyCode;
import target.myretail.model.db.PriceData;

import java.math.BigDecimal;

public interface PriceService {

    Mono<PriceData> getPriceData(Long id);

    Mono<Void> updatePriceData(Long id, BigDecimal newPrice, CurrencyCode currencyCode);
}
