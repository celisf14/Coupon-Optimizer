package com.meli.couponoptimizer.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@AllArgsConstructor
public class ApiMeliConfig {

  private ApiMeliProperties apiMeliProperties;

  @Bean(name = "apiMeliWebClient")
  public WebClient apiMeliWebClient() {
    return WebClient.builder()
            .baseUrl(apiMeliProperties.getApiMeli())
            .build();
  }
}
