package app.retail.service.impl;

import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import app.retail.app.ApplicationException;
import app.retail.service.NameService;

@Service
public class ExternalNameService implements NameService {

    private static final String BASE_URL = "https://redsky-uat.perf.target.com/redsky_aggregations/v1/redsky/case_study_v1";
    private static final String API_KEY = "3yUxt7WltYG7MFKPp7uyELi1K40ad2ys";

    private final WebClient client;

    @Autowired
    public ExternalNameService(WebClient.Builder builder) {
        this.client = builder
                .baseUrl(BASE_URL)
                .build();
    }

    @Override
    public Mono<String> getName(Long id) {
        return this.client.get()
                .uri(builder -> builder
                        .queryParam("key", API_KEY)
                        .queryParam("tcin", id)
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        status -> status.value() == 404,
                        clientResponse -> Mono.error(new ApplicationException(HttpStatus.BAD_REQUEST, "Id " + id + " not found"))
                )
                .onStatus(
                        status -> status.value() == 400 || status.is5xxServerError(),
                        clientResponse -> Mono.error(new IllegalStateException("External service returned an error"))
                )
                .bodyToMono(String.class)
                .map(response -> {
                    // Not the most elegant way, but didn't have time to correctly configure Jackson with the Vertx
                    // modules and I couldn't get a raw stream. So I'm putting full response into a string,
                    // then reparsing it into vert.x JSONObject. Ideally, the objectmapper directly serializes from
                    // stream into our JsonObject entity.
                    return new JsonObject(response)
                            .getJsonObject("data")
                            .getJsonObject("product")
                            .getJsonObject("item")
                            .getJsonObject("product_description")
                            .getString("title");
                });
    }
}
