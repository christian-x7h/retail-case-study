package integration;

import app.retail.App;
import app.retail.api.v1.ProductEndpoint;
import app.retail.model.CurrencyCode;
import app.retail.model.api.PriceDataRequest;
import app.retail.model.db.PriceData;
import app.retail.repository.PriceDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Integration test for the Spring application
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ComponentIntegrationConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {App.class})
public class ComponentIntegrationTest {

    @Autowired
    private PriceDataRepository mockPriceDataRepository;

    @Captor
    private ArgumentCaptor<PriceData> priceDataArgumentCaptor;

    private long testId = 12345;
    private WebTestClient webTestClient;

    @BeforeEach
    private void setUp(ApplicationContext context) {
        webTestClient = WebTestClient.bindToApplicationContext(context)
                .configureClient()
                .baseUrl("/v1/products")
                .build();

        when(mockPriceDataRepository.findById(testId)).thenReturn(Mono.just(new PriceData(testId, BigDecimal.valueOf(10), CurrencyCode.USD)));
        when(mockPriceDataRepository.save(Mockito.any(PriceData.class))).thenReturn(Mono.just(new PriceData(testId, BigDecimal.valueOf(10), CurrencyCode.USD)));
    }

    @Test
    public void testGetProduct() {
        webTestClient.get()
                .uri(ProductEndpoint.GET_PRODUCT_BY_ID_PATH, "12345")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void testUpsertPrice() {
        webTestClient.put()
                .uri(ProductEndpoint.PUT_UPSERT_PRICE_PATH, "12345")
                .bodyValue(new PriceDataRequest(new BigDecimal("12.34"), CurrencyCode.USD))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.data.id").isEqualTo(testId)
                .jsonPath("$.data.price").isEqualTo("10")
                .jsonPath("$.data.currencyCode").isEqualTo("USD");

        verify(mockPriceDataRepository).save(priceDataArgumentCaptor.capture());
        assertEquals(testId, priceDataArgumentCaptor.getValue().getId());
        assertEquals(BigDecimal.valueOf(12.34), priceDataArgumentCaptor.getValue().getPrice());
        assertEquals(CurrencyCode.USD, priceDataArgumentCaptor.getValue().getCurrencyCode());
    }
}
