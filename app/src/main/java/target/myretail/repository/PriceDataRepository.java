package target.myretail.repository;

import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import org.springframework.stereotype.Repository;
import target.myretail.model.db.PriceData;

@Repository
public interface PriceDataRepository extends ReactiveCosmosRepository<PriceData, Long> {
    // Use parent class methods for get/update operations
}
