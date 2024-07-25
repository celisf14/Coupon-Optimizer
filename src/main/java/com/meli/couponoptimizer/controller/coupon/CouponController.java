package com.meli.couponoptimizer.controller.coupon;

import com.meli.couponoptimizer.models.ItemDetailsResponseDTO;
import com.meli.couponoptimizer.models.ItemRequestDTO;
import com.meli.couponoptimizer.models.ItemResponseDTO;
import com.meli.couponoptimizer.services.favorites.ItemFavoritesService;
import com.meli.couponoptimizer.services.optimizer.OptimizerServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/coupon")
@Tag(name = "Coupon Controller", description = "Controller for coupon optimization and item statistics")
public class CouponController {

  private OptimizerServices optimizerServices;
  private ItemFavoritesService itemFavoritesService;

  @PostMapping
  @Operation(summary = "Optimize coupon", description = "Optimize the coupon based on provided item request")
  public ResponseEntity<ItemResponseDTO> optimizeCoupon(@RequestBody ItemRequestDTO request) {
    return ResponseEntity.ok(optimizerServices.optimizeCoupon(request));
  }

  @PostMapping("/items-details")
  @Operation(summary = "Get item details", description = "Retrieve details of items based on provided item IDs")
  public ResponseEntity<ItemDetailsResponseDTO> getListItemsDetails(@RequestBody List<String> itemIds) {
    return ResponseEntity.ok(optimizerServices.getItemsDetails(itemIds));
  }


  @GetMapping("/stats/{top}")
  @Operation(summary = "Get top favorite items", description = "Retrieve the top N favorite items")
  public ResponseEntity<Object> stats(@PathVariable int top) {
    return ResponseEntity.ok(itemFavoritesService.getTopFavorites(top));
  }

  @GetMapping("/stats")
  @Operation(summary = "Get top 5 favorite items", description = "Retrieve the top 5 favorite items")
  public ResponseEntity<Object> statsTop5() {
    return ResponseEntity.ok(itemFavoritesService.getTopFavorites(5));
  }

}
