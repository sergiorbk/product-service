package com.sergosoft.productservice.repository;

import com.sergosoft.productservice.repository.entity.OrderEntity;
import com.sergosoft.productservice.repository.projection.OrderSummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    Optional<OrderSummaryProjection> findDistinctBySellerReference(UUID sellerReference);

    @Query("SELECT o FROM OrderEntity o WHERE o.sellerReference = :sellerReference ORDER BY o.createdAt DESC")
    List<OrderEntity> findOrdersBySellersSortedByDate(@Param("sellerReference") UUID sellerReference);

}
