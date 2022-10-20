package com.gtw.es.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ProductSerializer extends JsonSerializer<Product> {
    @Override
    public void serialize(Product product, JsonGenerator generator, SerializerProvider serializerProvider)
        throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeStringField("name", product.getName());
        generator.writeStringField("description", product.getDescription());
        generator.writeStringField("color", product.getColor());
        generator.writeNumberField("stock_available", product.getStockAvailable());
        generator.writeNumberField("price", product.getPrice());
        generator.writeEndObject();
    }
}
