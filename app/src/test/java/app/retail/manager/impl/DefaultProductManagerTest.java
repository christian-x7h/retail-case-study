package app.retail.manager.impl;

import app.retail.app.ApplicationException;
import app.retail.model.CurrencyCode;
import app.retail.model.db.PriceData;
import app.retail.service.NameService;
import app.retail.service.PriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

public class DefaultProductManagerTest {

    private DefaultProductManager productManager;
    private NameService nameServiceMock;
    private PriceService priceServiceMock;

    @BeforeEach
    public void setup() {
        nameServiceMock = Mockito.mock(NameService.class);
        priceServiceMock = Mockito.mock(PriceService.class);
        productManager = new DefaultProductManager(nameServiceMock, priceServiceMock);
    }

    @Test
    public void testGetProduct() {

        long testId = 12345;

        when(nameServiceMock.getName(testId)).thenReturn(Mono.just("Test Name"));
        when(priceServiceMock.getPriceData(testId)).thenReturn(Mono.just(new PriceData(testId, BigDecimal.TEN, CurrencyCode.USD)));

        var product = productManager.getProduct(testId, CurrencyCode.USD);

        product.subscribe(p -> {
            assert p.getId() == testId;
            assert p.getName().equals("Test Name");
            assert p.getPrice().equals(BigDecimal.TEN);
            assert p.getCurrencyCode() == CurrencyCode.USD;
        });
    }

    @Test
    public void testGetProduct_mismatchCurrencyThrowsException() {

        long testId = 12345;

        when(nameServiceMock.getName(testId)).thenReturn(Mono.just("Test Name"));
        when(priceServiceMock.getPriceData(testId)).thenReturn(Mono.just(new PriceData(testId, BigDecimal.TEN, CurrencyCode.USD)));

        var product = productManager.getProduct(testId, CurrencyCode.EUR);

        product.subscribe(p -> {
            fail("Should have thrown exception");
        }, e -> {
            assert e instanceof ApplicationException;
            assert ((ApplicationException) e).getStatus() == HttpStatus.INTERNAL_SERVER_ERROR;
            assert e.getMessage().equals("Requested price data in EUR but only USD is available");
        });
    }
}
