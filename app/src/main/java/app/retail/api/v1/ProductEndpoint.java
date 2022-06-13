package app.retail.api.v1;

import app.retail.manager.ProductManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import app.retail.app.ApplicationException;
import app.retail.model.CurrencyCode;
import app.retail.model.api.DataResponse;
import app.retail.model.api.PriceDataRequest;
import app.retail.model.api.PriceDataResponse;
import app.retail.model.api.ProductDataResponse;

@Controller
@ResponseBody
@RequestMapping(path = "/v1/products", produces = "application/json")
public class ProductEndpoint {

    @Autowired
    private ProductManager productManager;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Mono<DataResponse<ProductDataResponse>> getProduct(@PathVariable(name = "id") String id) {

        Long parsedId = parseId(id);

        // Defaulting to USD for now, can be added as a request parameter
        CurrencyCode currencyCode = CurrencyCode.USD;

        return productManager
                .getProduct(parsedId, currencyCode)
                .map(data -> new DataResponse<>(new ProductDataResponse(data.getId(), data.getName(), data.getPrice(), data.getCurrencyCode())));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}/price")
    public Mono<DataResponse<PriceDataResponse>> upsertPrice(@PathVariable(name = "id") String id, @RequestBody PriceDataRequest body) {
        Long parsedId = parseId(id);
        return productManager
                .upsertPrice(parsedId, body.getPrice(), body.getCurrencyCode())
                .map(data -> new DataResponse<>(new PriceDataResponse(data.getId(), data.getPrice(), data.getCurrencyCode())));
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
