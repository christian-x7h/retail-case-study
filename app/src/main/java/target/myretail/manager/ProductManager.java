package target.myretail.manager;

import reactor.core.publisher.Mono;
import target.myretail.model.CurrencyCode;
import target.myretail.model.Product;

import java.math.BigDecimal;

public interface ProductManager {

    /**
     * Gets the product with the given id, price information defaults to USD.
     *
     * @param id the id of the product
     * @return the product with the given id
     */
    default Mono<Product> getProduct(Long id) {
        return getProduct(id, CurrencyCode.USD);
    }

    /**
     * Gets the product with the given id and currency code.
     *
     * @param id the id of the product
     * @param currencyCode the currency code to return price information in
     * @return the product with the given id
     */
    Mono<Product> getProduct(Long id, CurrencyCode currencyCode);

    /**
     * Updates the price of the product with the given id.
     *
     * @param id the id of the product
     * @param newPrice the new price of the product
     * @param currencyCode the currency code of the new price
     */
    Mono<Void> updatePrice(Long id, BigDecimal newPrice, CurrencyCode currencyCode);
}
