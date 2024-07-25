package com.meli.couponoptimizer.services.recoverItemData;

import com.meli.couponoptimizer.models.ItemDetailsDTO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface RecoverItemDataService {

  /**
   * Get the detail of an item
   * @param id The id of the item
   * @return The detail of the item
   */
  ItemDetailsDTO getDetalleItem(String id);

  /**
   * Get the detail of an item asynchronously
   * @param id The id of the item
   * @return The detail of the item
   */
  CompletableFuture<ItemDetailsDTO> getDetalleItemAsync(String id);

  /**
   * Get the details of a list of items asynchronously
   * @param itemIds The list of items ids
   * @return The details of the items
   */
  Map<String, ItemDetailsDTO> getListItemsDetailsAsync(List<String> itemIds);

  /**
   * Get the details of a list of items
   * @param itemIds The list of items ids
   * @return The details of the items
   */
  Map<String, ItemDetailsDTO> getListItemsDetails(List<String> itemIds);
}
