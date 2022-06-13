package target.myretail.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import target.myretail.manager.ProductManager;
import target.myretail.app.ApplicationException;
import target.myretail.model.CurrencyCode;
import target.myretail.model.Product;
import target.myretail.model.api.DataResponse;

@Controller
@ResponseBody
@RequestMapping(path = "/v1/products", produces = "application/json")
public class ProductEndpoint {

    @Autowired
    private ProductManager productManager;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Mono<DataResponse<Product>> getProduct(@PathVariable(name = "id") String id) {

        Long parsedId;

        try {
            parsedId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid id", e);
        }

        // Defaulting to USD for now, can be added as a request parameter
        CurrencyCode currencyCode = CurrencyCode.USD;

        return productManager.getProduct(parsedId, currencyCode).map(DataResponse::new);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}/price")
    public String updatePrice(@PathVariable(name = "id") String id) {
        return "Product " + id;
    }
}
