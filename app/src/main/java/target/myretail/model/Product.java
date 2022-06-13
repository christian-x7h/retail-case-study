package target.myretail.model;

import java.math.BigDecimal;

public record Product(Long id, String name, BigDecimal price, CurrencyCode currencyCode) {

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
