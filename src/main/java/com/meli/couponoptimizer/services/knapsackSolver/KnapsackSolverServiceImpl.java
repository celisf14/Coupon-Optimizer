package com.meli.couponoptimizer.services.knapsackSolver;

import com.google.ortools.Loader;
import com.google.ortools.algorithms.KnapsackSolver;
import com.meli.couponoptimizer.models.ItemDetailsDTO;
import com.meli.couponoptimizer.models.ItemResponseDTO;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class KnapsackSolverServiceImpl implements KnapsackSolverService {

  static {
    Loader.loadNativeLibraries();
  }

  @Override
  public ItemResponseDTO knapsackSolver(Map<String, ItemDetailsDTO> listItemsDetails, double amount) {
    String[] keys = listItemsDetails.keySet().toArray(new String[0]);
    long[] weights = new long[listItemsDetails.size()];
    long[] values = new long[listItemsDetails.size()];

    initializeWeightsAndValues(listItemsDetails, weights, values);

    long[] capacities = {(long) amount};
    KnapsackSolver solver = createSolver(values, weights, capacities);

    long computedValue = solver.solve();
    List<String> selectedItems = getSelectedItems(solver, weights, keys);

    return buildResponse(selectedItems, computedValue);
  }


  /**
   * Initialize the weights and values arrays with the prices of the items
   * @param listItemsDetails - list of items with their details
   * @param weights - array of weights
   * @param values - array of values
   */
  private void initializeWeightsAndValues(Map<String, ItemDetailsDTO> listItemsDetails, long[] weights, long[] values) {
    int index = 0;
    for (ItemDetailsDTO item : listItemsDetails.values()) {
      long price = (long) item.getPrice();
      weights[index] = price;
      values[index] = price;
      index++;
    }
  }

  /**
   * Create the KnapsackSolver object
   * @param values - array of values
   * @param weights - array of weights
   * @param capacities - array of capacities
   * @return
   */
  private KnapsackSolver createSolver(long[] values, long[] weights, long[] capacities) {
    KnapsackSolver solver = new KnapsackSolver(KnapsackSolver.SolverType.KNAPSACK_DYNAMIC_PROGRAMMING_SOLVER, "KnapsackSolver");
    solver.init(values, new long[][]{weights}, capacities);
    return solver;
  }

  /**
   * Get the selected items from the solver
   * @param solver - the KnapsackSolver object
   * @param weights - array of weights
   * @param keys - array of keys
   * @return - the list of selected items
   */
  private List<String> getSelectedItems(KnapsackSolver solver, long[] weights, String[] keys) {
    List<String> selectedItems = new ArrayList<>();
    for (int i = 0; i < weights.length; i++) {
      if (solver.bestSolutionContains(i)) {
        selectedItems.add(keys[i]);
      }
    }
    return selectedItems;
  }

  /**
   * Build the response object
   * @param selectedItems - list of selected items
   * @param computedValue - the computed value
   * @return - the response object
   */
  private ItemResponseDTO buildResponse(List<String> selectedItems, long computedValue) {
    return ItemResponseDTO.builder()
            .itemIds(selectedItems)
            .amount((double) computedValue)
            .build();
  }
}