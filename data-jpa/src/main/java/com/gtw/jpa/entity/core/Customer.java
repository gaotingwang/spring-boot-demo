package com.gtw.jpa.entity.core;

import com.gtw.jpa.entity.AbstractEntity;
import lombok.Data;
import org.springframework.util.Assert;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "customer")
public class Customer extends AbstractEntity {

    private String firstName;
    private String lastName;

    /**
     * Column属性：
     * nullable - 可选，表示该字段是否允许为 null，默认为 true
     * unique - 可选，表示该字段是否是唯一标识，默认为 false
     * length - 可选，表示该字段的大小，仅对 String 类型的字段有效，默认值255.
     * insertable - 可选，表示在ORM框架执行插入操作时，该字段是否应出现INSETRT语句中，默认为 true
     * updateable - 可选，表示在ORM框架执行更新操作时，该字段是否应该出现在UPDATE 语句中，默认为 true. 对于一经创建就不可以更改的字段，该属性非常有用
     * columnDefinition - 可选，表示该字段在数据库中的实际类型，通常ORM框架可以根据属性类型自动判断数据库中字段的类型。
     *                  但是对于Date 类型仍无法确定数据库中字段类型究竟是 DATE,TIME 还是 TIMESTAMP.
     *                  此外 ,String 的默认映射类型为 VARCHAR, 如果要将 String 类型映射到特定数据库的 BLOB或 TEXT 字段类型，该属性非常有用。
     *
     * eg:@Column(name="BIRTH",nullable="false",columnDefinition="DATE")
     */
    @Column(unique = true)// 邮箱必须唯一
    private EmailAddress emailAddress;

    /**
     * 属性并非一个到数据库表的字段的映射 @Transient, ORM框架将忽略该属性，如果一个属性并非数据库表的字段映射，就务必将其标示为@Transient，否则ORM框架默认其注解为 @Basic
     */
    @Transient
    private int age;

    /**
     * 映射关系属性：
     * fetch - 配置加载方式。取值有Fetch.EAGER:及时加载，多对一默认是Fetch.EAGER; Fetch.LAZY:延迟加载，一对多默认是Fetch.LAZY
     * cascade - 设置级联方式，取值有：
     *            CascadeType.PERSIST - 保存
     *            CascadeType.REMOVE - 删除
     *            CascadeType.MERGE - 修改
     *            CascadeType.REFRESH - 刷新
     *            CascadeType.ALL - 全部
     * orphanRemoval - 是否启用子对象移除，即当Customer持久化、更新、删除时，Address也会被持久化、更新、删除
     *              注：这并不是数据库级别的级联，而是JPA所管理的级联
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    /**
     * JoinColumn 可选，如果不写此属性，会专门建一个关系表
     */
    @JoinColumn(name = "customer_id")
    private Set<Address> addresses = new HashSet<>();

    public void add(Address address) {
        Assert.notNull(address);
        this.addresses.add(address);
    }

    public Set<Address> getAddresses() {
        return Collections.unmodifiableSet(addresses);
    }

    public Customer(String firstName, String lastName, EmailAddress emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    public Customer(String firstName, String lastName, EmailAddress emailAddress, Set<Address> addresses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.addresses = addresses;
    }

    public Customer() {
    }
}
