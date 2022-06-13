package target.myretail.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import target.myretail.service.NameService;

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
                .bodyToMono(JSONObject.class)
                .map(jsonObject -> jsonObject.getJSONObject("data")
                        .getJSONObject("product")
                        .getJSONObject("item")
                        .getJSONObject("product_description")
                        .getString("title")
                );
    }
}
