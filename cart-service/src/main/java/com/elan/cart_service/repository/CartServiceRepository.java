package com.elan.cart_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.elan.cart_service.entity.CartServiceEntity;

@Repository
public interface CartServiceRepository extends JpaRepository<CartServiceEntity, Long> {

    List<CartServiceEntity> findByUserId(Long userId);

    @Transactional                         // start a transaction for this delete
    @Modifying(clearAutomatically = true)  // tells Spring Data this is a modifying query
    @Query("DELETE FROM CartServiceEntity c WHERE c.userId = :userId")
    void deleteByUserId(@Param("userId") Long userId);

}