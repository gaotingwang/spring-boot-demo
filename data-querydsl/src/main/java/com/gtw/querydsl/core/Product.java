package com.gtw.querydsl.core;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 关于@QueryEntity,只是默认注解，Querydsl注解处理器会使用它来生成相关的查询对象，在与特定的存储继承使用时，
 * APT（Annotation Processing Tool，诞生于Java 6版本，主要用于在编译期根据不同的注解类生成或者修改代码）处理器
 * 能够识别出特定的存储实体（对于JPA使用的就是@Entity）并使用它们来衍生查询类。
 */

/**
 * Querydsl自带了各种APT处理器，用于探查不同的注解并生成对应的元模型类
 *
 * QuerydslAnnotationProcessor
 * 		非常核心的注解处理器，会处理querysql的特定注解，如@QueryEntity和@QueryEmbeddable
 * JPAAnnotationProcessor
 * 		用于探查javax.persistence，如@Entity,@Embeddable
 * HibernateAnnotationProcessor
 *		类似于JPA处理器，但是增加了对Hibernate注解的支持
 * JDOAnnotationProcessor
 * 		探查JDO注解，如@PersistenceCapable和@EmbeddedOnly
 * MongoAnnotationProcessor
 * 		spring-data专用处理器，会探查@Document注解
 *
 */
@QueryEntity
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Product extends AbstractEntity {

	private String name, description;
	private BigDecimal price;
	private Map<String, String> attributes = new HashMap<String, String>();

	public Product(String name) {
		this(name, null);
	}

	public Product(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public void setAttribute(String key, String value) {
		this.attributes.put(key, value);
	}

	public Map<String, String> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}

}
/**
 * 在工程模块的target/generated-sources/java对应目录下会生成相应的查询类QProduct
 */
/*
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProduct extends EntityPathBase<Product> {

	private static final long serialVersionUID = -809804056L;

	public static final QProduct product = new QProduct("product");

	public final QAbstractEntity _super = new QAbstractEntity(this);

	public final MapPath<String, String, StringPath> attributes = this.<String, String, StringPath>createMap("attributes", String.class, String.class, StringPath.class);

	public final StringPath description = createString("description");

	//inherited
	public final NumberPath<Long> id = _super.id;

	public final StringPath name = createString("name");

	public final NumberPath<java.math.BigDecimal> price = createNumber("price", java.math.BigDecimal.class);

	public QProduct(String variable) {
		super(Product.class, forVariable(variable));
	}

	public QProduct(Path<? extends Product> path) {
		super(path.getType(), path.getMetadata());
	}

	public QProduct(PathMetadata metadata) {
		super(Product.class, metadata);
	}

}
*/
