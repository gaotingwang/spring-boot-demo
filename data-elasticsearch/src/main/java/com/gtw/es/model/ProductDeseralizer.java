package com.gtw.es.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ProductDeseralizer extends JsonDeserializer {
    @Override
    public Object deserialize(JsonParser jp, DeserializationContext deserializationContext)
        throws IOException, JsonProcessingException {
        JsonNode productNode = jp.getCodec().readTree(jp);

        Product product = new Product();
        if (productNode.has("name")) {
            product.setName(productNode.get("name").textValue());
        }
        if (productNode.has("description")) {
            product.setDescription(productNode.get("description").textValue());
        }
        if (productNode.has("price")) {
            product.setPrice(productNode.get("price").doubleValue());
        }
        if (productNode.has("stock_available")) {
            product.setStockAvailable(productNode.get("stock_available").intValue());
        }
        return product;
    }
}
