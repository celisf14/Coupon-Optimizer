package com.meli.couponoptimizer.services.favorites;

import com.meli.couponoptimizer.models.ItemDetailsDTO;
import com.meli.couponoptimizer.persistence.entity.ItemFavoritesEntity;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

public interface ItemFavoritesService {

  /**
   * Updates the favorites counter for a list of items.
   *
   * @param listItemsDetails A map containing the item id as key and the item details as value.
   */
  void updateFavorites(Map<String, ItemDetailsDTO> listItemsDetails);

  /**
   * Retrieves an item by its id.
   *
   * @param id The item id.
   * @return The item entity.
   */
  ItemFavoritesEntity getItemById(String id);

  /**
   * Increments the favorites counter for an item.
   *
   * @param entity The item entity.
   */
  void incrementFavoritesCounter(ItemFavoritesEntity entity);

  /**
   * Saves a new item.
   *
   * @param itemId The item id.
   * @param itemDetail The item details.
   */
  void saveNewItem(String itemId, ItemDetailsDTO itemDetail);

  /**
   * Retrieves the top favorites.
   * @param topSize
   * @return
   */
  List<ItemFavoritesEntity> getTopFavorites(int topSize);

}
