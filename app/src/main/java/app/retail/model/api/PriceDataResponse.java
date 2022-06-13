package app.retail.model.api;

import app.retail.model.CurrencyCode;

import java.math.BigDecimal;

public class PriceDataResponse {

    private Long id;
    private BigDecimal price;
    private CurrencyCode currencyCode;

    public PriceDataResponse(Long id, BigDecimal price, CurrencyCode currencyCode) {
        this.id = id;
        this.price = price;
        this.currencyCode = currencyCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }
}
