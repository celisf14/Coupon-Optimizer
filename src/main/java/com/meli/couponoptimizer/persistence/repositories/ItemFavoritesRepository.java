package com.meli.couponoptimizer.persistence.repositories;

import com.meli.couponoptimizer.persistence.entity.ItemFavoritesEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemFavoritesRepository extends JpaRepository<ItemFavoritesEntity, String> {

  @Query("SELECT i FROM ItemFavoritesEntity i ORDER BY i.favoritesCounter DESC")
  List<ItemFavoritesEntity> findTopByFavoritesCounter(Pageable pageable);

}
