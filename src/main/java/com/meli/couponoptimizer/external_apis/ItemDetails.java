package com.meli.couponoptimizer.external_apis;

import com.meli.couponoptimizer.models.ItemDetailsDTO;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;


public interface ItemDetails {

  /**
   * Get item details by item id
   * @param itemId
   * @return
   */
  Mono<ItemDetailsDTO> getItem(String itemId);

  /**
   * Get list of items details by item ids
   * @param itemIds
   * @return
   */
  Mono<Map<String, ItemDetailsDTO>> getListItems(List<String> itemIds);

}
