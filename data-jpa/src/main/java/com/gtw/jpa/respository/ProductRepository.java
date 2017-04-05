package com.gtw.jpa.respository;

import com.gtw.jpa.entity.core.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 继承QueryDslPredicateExecutor 使用Querydsl
 */
public interface ProductRepository extends CrudRepository<Product, Long>, QueryDslPredicateExecutor<Product> {

    /**
     * 这里使用了Containing关键字，最终会根据指定参数，在其前后加%，会通过LIKE操作符进行限制
     *
     * 分页查询，要读取哪一块的数据具体定义是在Pageable/PageRequest中
     * 分页数据Page不仅包含数据本身，还包含了元信息(总页数的等信息)
     * 为了计算元数据，会触发二次查询
     */
    Page<Product> findByDescriptionContaining(String description, Pageable pageable);

    /**
     * List方式同样可以传入Pageable，但会避免额外的信息查询，不会提供元数据
     */
//    List<Product> findByDescriptionContaining(String description, Sort sort);

    @Query("select p from Product p where p.attributes[?1] = ?2")
    List<Product> findByAttributeAndValue(String attribute, String value);
}
