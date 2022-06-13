package app.retail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import app.retail.model.CurrencyCode;
import app.retail.model.db.PriceData;
import app.retail.repository.PriceDataRepository;
import app.retail.service.PriceService;

import java.math.BigDecimal;

@Service
public class CosmosPriceService implements PriceService {

    @Autowired
    private PriceDataRepository priceDataRepository;

    @Override
    public Mono<PriceData> getPriceData(Long id) {
        return priceDataRepository.findById(id).defaultIfEmpty(new PriceData());
    }

    @Override
    public Mono<PriceData> upsertPriceData(Long id, BigDecimal newPrice, CurrencyCode currencyCode) {
        return priceDataRepository.save(new PriceData(id, newPrice, currencyCode));
    }
}
