package app.retail.repository;

import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import org.springframework.stereotype.Repository;
import app.retail.model.db.PriceData;

@Repository
public interface PriceDataRepository extends ReactiveCosmosRepository<PriceData, Long> {
    // Use parent class methods for get/update operations
}
