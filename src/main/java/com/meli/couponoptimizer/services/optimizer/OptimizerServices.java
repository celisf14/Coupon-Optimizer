package com.meli.couponoptimizer.services.optimizer;

import com.meli.couponoptimizer.models.ItemDetailsResponseDTO;
import com.meli.couponoptimizer.models.ItemRequestDTO;
import com.meli.couponoptimizer.models.ItemResponseDTO;

import java.util.List;

public interface OptimizerServices {

  /**
   * Optimize the coupon
   * @param itemRequestDTO - the request with the items
   * @return - the response with the selected items
   */
  ItemResponseDTO optimizeCoupon(ItemRequestDTO itemRequestDTO);

  /**
   * Get the details of a list of items
   * @param itemIds - the list of items ids
   * @return - the details of the items
   */
  public ItemDetailsResponseDTO getItemsDetails(List<String> itemIds);
}
