package com.gtw.jpa.entity.order;

import com.gtw.jpa.entity.AbstractEntity;
import com.gtw.jpa.entity.core.Address;
import com.gtw.jpa.entity.core.Customer;
import lombok.Data;
import org.springframework.util.Assert;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Data
public class Order extends AbstractEntity {

    /**
     * optional 属性实际上指定关联类与被关联类的join 查询关系
     * 为false 执行 inner join
     * 为true 执行 left join
     */
    @ManyToOne(optional = false)
    private Customer customer;

    @ManyToOne
    private Address billingAddress;// 账单地址

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Address shippingAddress; // 投递地址

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private Set<LineItem> lineItems = new HashSet<>();

    public void add(LineItem lineItem) {
        Assert.notNull(lineItem);
        this.lineItems.add(lineItem);
    }

    public Set<LineItem> getLineItems() {
        return Collections.unmodifiableSet(lineItems);
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (LineItem item : lineItems) {
            total = total.add(item.getTotal());
        }

        return total;
    }

    public Address getBillingAddress() {
        return billingAddress != null ? billingAddress : shippingAddress;
    }

    public Order(Customer customer, Address shippingAddress) {
        this(customer, shippingAddress, null);
    }

    public Order(Customer customer, Address shippingAddress, Address billingAddress) {
        Assert.notNull(customer);
        Assert.notNull(shippingAddress);

        this.customer = customer;
        this.shippingAddress = shippingAddress.getCopy();// 地址改变，不会影响订单的地址
        this.billingAddress = billingAddress == null ? null : shippingAddress.getCopy();
    }

    protected Order() {

    }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer +
                ", billingAddress=" + billingAddress +
                ", shippingAddress=" + shippingAddress +
                ", lineItems=" + lineItems +
                '}';
    }
}
