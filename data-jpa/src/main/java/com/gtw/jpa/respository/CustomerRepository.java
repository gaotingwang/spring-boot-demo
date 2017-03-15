package com.gtw.jpa.respository;

import com.gtw.jpa.entity.core.Customer;
import com.gtw.jpa.entity.core.EmailAddress;
import org.springframework.data.repository.Repository;

/**
 * Repository标识接口，为了让Spring Data Repository的基础设施找到用户自定义的Repository接口
 * Repository <--- CrudRepository <--- PagingAndSortingRepository <--- JpaRepository
 */
public interface CustomerRepository extends Repository<Customer, Long> {

    Customer save(Customer account);

    /**
     * 根据名称会自动衍生查询
     */
    Customer findByEmailAddress(EmailAddress emailAddress);
}
