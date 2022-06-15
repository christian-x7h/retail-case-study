package app.retail.manager.impl;

import app.retail.app.ApplicationException;
import app.retail.manager.ProductManager;
import app.retail.model.CurrencyCode;
import app.retail.model.Product;
import app.retail.model.db.PriceData;
import app.retail.service.NameService;
import app.retail.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class DefaultProductManager implements ProductManager {

    private NameService nameService;
    private PriceService priceService;

    @Autowired
    public DefaultProductManager(NameService nameService, PriceService priceService) {
        this.nameService = nameService;
        this.priceService = priceService;
    }

    @Override
    public Mono<Product> getProduct(Long id, CurrencyCode currencyCode) {

        // Make parallel calls to the name and price services
        return Mono.zip(nameService.getName(id), priceService.getPriceData(id))
                .map(tuple -> {
                    String name = tuple.getT1();
                    PriceData priceData = tuple.getT2();

                    // Ensure price information is in requested currency
                    if (priceData.getId() != null && currencyCode != priceData.getCurrencyCode()) {
                        // TODO convert currency codes
                        throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR,
                                "Requested price data in " + currencyCode + " but only " + priceData.getCurrencyCode() + " is available");
                    }

                    return new Product(id, name, priceData.getPrice(), priceData.getCurrencyCode());
                });
    }

    @Override
    public Mono<PriceData> upsertPrice(Long id, BigDecimal newPrice, CurrencyCode currencyCode) {
        return priceService.upsertPriceData(id, newPrice, currencyCode);
    }
}
