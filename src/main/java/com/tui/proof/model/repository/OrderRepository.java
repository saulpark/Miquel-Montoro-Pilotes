package com.tui.proof.model.repository;

import com.tui.proof.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends CrudRepository<Order, UUID> {

    @Query("select o from Order o " +
            "inner join Client c on o.clientId = c.clientId " +
            "where (:fName is null or c.firstName like :fName%) " +
            "and (:lName is null or c.lastName like :lName%) " +
            "and (:tel is null or c.telephone like :tel%)")
    List<Order> findByCustomerData(@Param("fName") String firstName, @Param("lName") String lastName, @Param("tel") String telephone);
}
