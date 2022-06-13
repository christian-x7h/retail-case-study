package app.retail.model.api;

import app.retail.model.CurrencyCode;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PriceDataRequest {

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal price;
    // TODO add enum validation
    private CurrencyCode currencyCode;

    public PriceDataRequest(BigDecimal price, CurrencyCode currencyCode) {
        this.price = price;
        this.currencyCode = currencyCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }
}
