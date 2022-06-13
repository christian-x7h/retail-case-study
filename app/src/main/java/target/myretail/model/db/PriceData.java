package target.myretail.model.db;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import org.springframework.data.annotation.Id;
import target.myretail.model.CurrencyCode;

import java.math.BigDecimal;

@Container(containerName = "target_case_study_price_data")
public class PriceData {

    @Id
    @PartitionKey
    private Long id;
    private BigDecimal price;
    private CurrencyCode currencyCode;

    public PriceData() {
    }

    public PriceData(Long id, BigDecimal price, CurrencyCode currencyCode) {
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

    @Override
    public String toString() {
        return "PriceData{" +
                "id=" + id +
                ", price=" + price +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
