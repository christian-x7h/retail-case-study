package target.myretail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import target.myretail.app.ApplicationException;
import target.myretail.model.CurrencyCode;
import target.myretail.model.db.PriceData;
import target.myretail.repository.PriceDataRepository;
import target.myretail.service.PriceService;

import java.math.BigDecimal;

@Service
public class CosmosPriceService implements PriceService {

    @Autowired
    private PriceDataRepository priceDataRepository;

    @Override
    public Mono<PriceData> getPriceData(Long id) {
        return priceDataRepository.findById(id);
    }

    @Override
    public Mono<Void> updatePriceData(Long id, BigDecimal newPrice, CurrencyCode currencyCode) {
        return priceDataRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new ApplicationException(HttpStatus.BAD_REQUEST, "No price data for id " + id)))
                .flatMap(priceData -> {
                    priceData.setPrice(newPrice);
                    priceData.setCurrencyCode(currencyCode);
                    return priceDataRepository.save(new PriceData(id, newPrice, currencyCode));
                })
                .thenEmpty(Mono.empty());
    }
}
