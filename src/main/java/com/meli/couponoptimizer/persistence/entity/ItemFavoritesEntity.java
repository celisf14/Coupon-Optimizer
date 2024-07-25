package com.meli.couponoptimizer.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item_favorites")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemFavoritesEntity {

  @Id
  @Column(name = "id", nullable = false, length = 100)
  private String id;

  @Column(name = "title", length = 1000)
  private String title;

  @Column(name = "price")
  private Double price;

  @Column(name = "site_id", length = 100)
  private String siteId;

  @Column(name = "status", length = 100)
  private String status;

  @Column(name = "favorites_counter")
  private int favoritesCounter;

}
