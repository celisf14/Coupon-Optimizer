package com.meli.couponoptimizer.services.optimizer;

import com.meli.couponoptimizer.exceptions.CouponOptimizerException;
import com.meli.couponoptimizer.models.ItemDetailsDTO;
import com.meli.couponoptimizer.models.ItemDetailsResponseDTO;
import com.meli.couponoptimizer.models.ItemRequestDTO;
import com.meli.couponoptimizer.models.ItemResponseDTO;
import com.meli.couponoptimizer.services.favorites.ItemFavoritesServiceImpl;
import com.meli.couponoptimizer.services.knapsackSolver.KnapsackSolverService;
import com.meli.couponoptimizer.services.recoverItemData.RecoverItemDataService;
import com.meli.couponoptimizer.util.ConstantsMsjError;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OptimizerServicesImpl implements OptimizerServices {

  private final RecoverItemDataService recoverItemDataService;
  private final KnapsackSolverService knapsackSolverServiceImpl;
  private final ItemFavoritesServiceImpl itemFavoritesServiceImpl;

  @Override
  public ItemResponseDTO optimizeCoupon(ItemRequestDTO itemRequestDTO) {
    try {

      validateRequest(itemRequestDTO);

      Map<String, ItemDetailsDTO> listItemsDetails = recoverItemDataService.getListItemsDetailsAsync(itemRequestDTO.getItemIds());
      double sumPrice = validateAndAnalyzeItemDetails(listItemsDetails, itemRequestDTO.getAmount());

      itemFavoritesServiceImpl.updateFavorites(listItemsDetails);

      if (sumPrice <= itemRequestDTO.getAmount()) {
        return buildResponse(listItemsDetails.keySet(), sumPrice);
      }

      return knapsackSolverServiceImpl.knapsackSolver(listItemsDetails, itemRequestDTO.getAmount());

    } catch (CouponOptimizerException e) {
      throw new CouponOptimizerException(ConstantsMsjError.ERR_CODE_500,
              ConstantsMsjError.ERR_MSG_GENERIC);
    }

  }

  /**
   * Validate the request
   * @param itemRequestDTO - the request with the items
   */
  private void validateRequest(ItemRequestDTO itemRequestDTO) {
    if (itemRequestDTO.getItemIds() == null || itemRequestDTO.getItemIds().isEmpty()
            || Double.isNaN(itemRequestDTO.getAmount()) || itemRequestDTO.getAmount() == 0) {
      throw new CouponOptimizerException(ConstantsMsjError.ERR_CODE_400, ConstantsMsjError.ERR_MSG_ITEM_LIST_EMPTY);
    }
    itemRequestDTO.setItemIds(removeDuplicates(itemRequestDTO.getItemIds()));
    itemRequestDTO.setAmount(round(itemRequestDTO.getAmount(), 2));
  }

  /**
   * Validate and analyze the item details
   * @param items - the items to analyze
   * @param amount - the amount to spend
   * @return - the total price of the items
   */
  private double validateAndAnalyzeItemDetails(Map<String, ItemDetailsDTO> items, double amount) {
    removeInactiveItems(items);

    if (items.isEmpty()) {
      throw new CouponOptimizerException(ConstantsMsjError.ERR_CODE_400, ConstantsMsjError.ERR_GET_LIST_ITEM);
    }

    validateItemsBySite(items);
    validateMinimumPrice(items, amount);

    return calculateTotalPrice(items);
  }

  /**
   * Remove inactive items
   * @param items - the items to analyze
   */
  private void removeInactiveItems(Map<String, ItemDetailsDTO> items) {
    items.values().removeIf(item -> !"active".equals(item.getStatus()));
  }

  /**
   * Validate that all items are from the same site
   * @param items - the items to analyze
   */
  private void validateItemsBySite(Map<String, ItemDetailsDTO> items) {
    Map<String, List<ItemDetailsDTO>> itemsBySite = items.values().stream()
            .collect(Collectors.groupingBy(com.meli.couponoptimizer.models.ItemDetailsDTO::getSiteId));
    if (itemsBySite.size() > 1) {
      throw new CouponOptimizerException(ConstantsMsjError.ERR_CODE_400, ConstantsMsjError.ERR_SITE);
    }
  }

  /**
   * Validate that the minimum price is less than the amount
   * @param items - the items to analyze
   * @param amount - the amount to spend
   */
  private void validateMinimumPrice(Map<String, ItemDetailsDTO> items, double amount) {
    double minPrice = items.values().stream()
            .mapToDouble(com.meli.couponoptimizer.models.ItemDetailsDTO::getPrice)
            .min()
            .orElse(Double.NaN);
    if (minPrice > amount) {
      throw new CouponOptimizerException(ConstantsMsjError.ERR_CODE_400, ConstantsMsjError.ERR_MIN_PRICE);
    }
  }

  /**
   * Calculate the total price of the items
   * @param items - the items to analyze
   * @return - the total price
   */
  private double calculateTotalPrice(Map<String, ItemDetailsDTO> items) {
    return items.values().stream()
            .mapToDouble(com.meli.couponoptimizer.models.ItemDetailsDTO::getPrice)
            .sum();
  }

  /**
   * Remove duplicates from the list
   * @param list - the list to analyze
   * @return - the list without duplicates
   */
  private List<String> removeDuplicates(List<String> list) {
    return new ArrayList<>(new LinkedHashSet<>(list));
  }

  /**
   * Build the response
   * @param itemIds - the selected items
   * @param amount - the total amount
   * @return - the response
   */
  private ItemResponseDTO buildResponse(Set<String> itemIds, double amount) {
    return ItemResponseDTO.builder()
            .itemIds(new ArrayList<>(itemIds))
            .amount(amount)
            .build();
  }

  public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = new BigDecimal(Double.toString(value));
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }


  public ItemDetailsResponseDTO getItemsDetails(List<String> itemIds) {
    Map<String, ItemDetailsDTO>  listItemsDetails = recoverItemDataService.getListItemsDetails(itemIds);
    double totalPrice = calculateTotalPrice(listItemsDetails);
    return ItemDetailsResponseDTO.builder()
            .itemIds(new ArrayList<>(listItemsDetails.values()))
            .totalAmount(totalPrice)
            .build();
  }
}