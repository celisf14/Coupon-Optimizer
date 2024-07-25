package com.meli.couponoptimizer.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetailsDTO {
  @JsonProperty("id")
  private String id;

  @JsonProperty("title")
  private String title;

  @JsonProperty("price")
  private double price;

  @JsonProperty("site_id")
  private String siteId;

  @JsonProperty("status")
  private String status;
}
