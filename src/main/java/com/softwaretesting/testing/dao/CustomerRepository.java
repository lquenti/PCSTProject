package com.softwaretesting.testing.dao;

import com.softwaretesting.testing.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findByUserName(String userName);

    @Query(
            value = "select id, user_name, name, phone_number " +
                    "from customer where phone_number = :phone_number",
            nativeQuery = true
    )
    Optional<Customer> selectCustomerByPhoneNumber(
            @Param("phone_number") String phoneNumber);
}
