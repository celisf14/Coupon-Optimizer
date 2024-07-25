package com.meli.couponoptimizer.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetailsResponseDTO {
  @JsonProperty("item_ids")
  private List<ItemDetailsDTO> itemIds;

  @JsonProperty("total_amount")
  private double totalAmount;
}
