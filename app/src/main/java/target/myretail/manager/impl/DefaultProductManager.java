package target.myretail.manager.impl;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import target.myretail.manager.ProductManager;
import target.myretail.model.CurrencyCode;
import target.myretail.model.Product;
import target.myretail.model.db.PriceData;
import target.myretail.service.NameService;
import target.myretail.service.PriceService;

import java.math.BigDecimal;

@Service
public class DefaultProductManager implements ProductManager {

    @Autowired
    private NameService nameService;
    @Autowired
    private PriceService priceService;

    @Override
    public Mono<Product> getProduct(Long id, CurrencyCode currencyCode) {

        // Make parallel calls to the name and price services
        return Mono.zip(nameService.getName(id), priceService.getPriceData(id))
                .map(tuple -> {
                    String name = tuple.getT1();
                    PriceData priceData = tuple.getT2();

                    // Ensure price information is in requested currency
                    if (currencyCode != priceData.getCurrencyCode()) {
                        // TODO convert currency codes
                        throw new NotImplementedException(
                                "Requested price data in " + currencyCode +
                                        " but only " + priceData.getCurrencyCode() + " is available");
                    }

                    return new Product(id, name, priceData.getPrice(), priceData.getCurrencyCode());
                });
    }

    @Override
    public Mono<Void> updatePrice(Long id, BigDecimal newPrice, CurrencyCode currencyCode) {

        // TODO update price in DB


        // validate price/currency
        // check id in price table (verify it exists)
        // update price
        return Mono.empty();
    }
}
