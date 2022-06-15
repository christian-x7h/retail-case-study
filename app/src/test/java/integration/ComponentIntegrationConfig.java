package integration;

import app.retail.repository.PriceDataRepository;
import app.retail.service.NameService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class ComponentIntegrationConfig {

    @Bean
    public PriceDataRepository priceDataRepository() {
        // Because I'm using a reactive repository, which the JPA repositories test code
        // does not support, I'm just gonna mock the repository for this simple test/app.
        return Mockito.mock(PriceDataRepository.class);
    }

    @Bean
    public NameService externalNameService() {
        // This class's functionality should be unit tested, but for this component test, we can just stub it.
        return (id) -> Mono.just("Test Product " + id);
    }
}
