package com.thoughtworks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

@Component
public class UsageOfUtilSample {
    private static final Logger LOG = LoggerFactory.getLogger(UsageOfUtilSample.class);

    @Autowired
    ServicesUtils servicesUtils;

    private RestTemplate restTemplate = new RestTemplate();

    // -------- //
    // PRODUCTS //
    // -------- //

    public ResponseEntity<Object> getProduct(int productId) {

        URI uri = servicesUtils.getServiceUrl("product", "http://localhost:8081/product");
        String url = uri.toString() + "/product/" + productId;
        LOG.debug("GetProduct from URL: {}", url);

        ResponseEntity<String> resultStr = restTemplate.getForEntity(url, String.class);
        LOG.debug("GetProduct http-status: {}", resultStr.getStatusCode());
        LOG.debug("GetProduct body: {}", resultStr.getBody());

        Product product = response2Product(resultStr);
        LOG.debug("GetProduct.id: {}", product.getProductId());

        return servicesUtils.createOkResponse(product);
    }

    public Product response2Product(ResponseEntity<String> response) {
        try {
            return getProductReader().readValue(response.getBody());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectReader productReader = null;
    private ObjectReader getProductReader() {

        if (productReader != null) return productReader;

        ObjectMapper mapper = new ObjectMapper();
        return productReader = mapper.reader(Product.class);
    }


    public class Product {
        private int productId;
        private String name;
        private int weight;

        public Product() {

        }

        public Product(int productId, String name, int weight) {
            this.productId = productId;
            this.name = name;
            this.weight = weight;
        }

        public int getProductId() {
            return productId;
        }
    }

}
