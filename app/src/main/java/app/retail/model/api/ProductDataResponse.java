package app.retail.model.api;

import app.retail.model.CurrencyCode;

import java.math.BigDecimal;

public class ProductDataResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private CurrencyCode currencyCode;

    public ProductDataResponse(Long id, String name, BigDecimal price, CurrencyCode currencyCode) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.currencyCode = currencyCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
