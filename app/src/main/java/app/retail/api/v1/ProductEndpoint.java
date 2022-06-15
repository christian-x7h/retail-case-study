package app.retail.api.v1;

import app.retail.app.ApplicationException;
import app.retail.manager.ProductManager;
import app.retail.model.CurrencyCode;
import app.retail.model.api.DataResponse;
import app.retail.model.api.PriceDataRequest;
import app.retail.model.api.PriceDataResponse;
import app.retail.model.api.ProductDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@ResponseBody
@RequestMapping(path = "/v1/products", produces = "application/json")
public class ProductEndpoint {

    public static final String GET_PRODUCT_BY_ID_PATH = "/{id}";
    public static final String PUT_UPSERT_PRICE_PATH = "/{id}/price";

    @Autowired
    private ProductManager productManager;

    @RequestMapping(method = RequestMethod.GET, path = GET_PRODUCT_BY_ID_PATH)
    public Mono<DataResponse<ProductDataResponse>> getProduct(@PathVariable(name = "id") String id) {

        Long parsedId = parseId(id);

        // Defaulting to USD for now, can be added as a request parameter
        CurrencyCode currencyCode = CurrencyCode.USD;

        return productManager
                .getProduct(parsedId, currencyCode)
                .map(data -> new DataResponse<>(new ProductDataResponse(data.getId(), data.getName(), data.getPrice(), data.getCurrencyCode())));
    }

    @RequestMapping(method = RequestMethod.PUT, path = PUT_UPSERT_PRICE_PATH)
    public Mono<DataResponse<PriceDataResponse>> upsertPrice(@PathVariable(name = "id") String id, @RequestBody PriceDataRequest body) {
        Long parsedId = parseId(id);
        return productManager
                .upsertPrice(parsedId, body.getPrice(), body.getCurrencyCode())
                .map(data -> new DataResponse<>(new PriceDataResponse(data.getId(), data.getPrice(), data.getCurrencyCode()))); // TODO maybe make response a 201 if created
    }

    private Long parseId(String id) {
        Long parsedId;
        try {
            parsedId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid id", e);
        }
        return parsedId;
    }
}
