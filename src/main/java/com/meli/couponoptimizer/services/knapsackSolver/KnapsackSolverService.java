package com.meli.couponoptimizer.services.knapsackSolver;

import com.meli.couponoptimizer.models.ItemDetailsDTO;
import com.meli.couponoptimizer.models.ItemResponseDTO;

import java.util.Map;

public interface KnapsackSolverService {
  /**
   * Solve the knapsack problem - adapt for the coupon optimizer
   * @param listItemsDetails - list of items with their details
   * @param amount - amount to spend
   * @return - the response with the selected items
   */
  ItemResponseDTO knapsackSolver(Map<String, ItemDetailsDTO> listItemsDetails, double amount);
}
