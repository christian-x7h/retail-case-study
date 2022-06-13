package target.myretail.service;

import reactor.core.publisher.Mono;

public interface NameService {

    Mono<String> getName(Long id);
}
