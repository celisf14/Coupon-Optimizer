package com.meli.couponoptimizer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnalyzeItemDTO {
  private int totalItems;
  private double minPrice;
  private double maxPrice;
  private double sumPrice;
  private int totalSites;
  private String maxSite;
  private double sitePercentage;
}
