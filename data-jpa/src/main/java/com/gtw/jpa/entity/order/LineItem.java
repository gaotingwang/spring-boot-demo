package com.gtw.jpa.entity.order;

import com.gtw.jpa.entity.AbstractEntity;
import com.gtw.jpa.entity.core.Product;
import lombok.Data;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "line_item")
@Data
public class LineItem extends AbstractEntity {

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private BigDecimal price;

    private int amount;

    public BigDecimal getUnitPrice() {
        return price;
    }

    public BigDecimal getTotal() {
        return price.multiply(BigDecimal.valueOf(amount));
    }

    public LineItem(Product product) {
        this(product, 1);
    }

    public LineItem(Product product, int amount) {

        Assert.notNull(product, "The given Product must not be null!");
        Assert.isTrue(amount > 0, "The amount of Products to be bought must be greater than 0!");

        this.product = product;
        this.amount = amount;
        this.price = product.getPrice();
    }

    public LineItem() {

    }
}
