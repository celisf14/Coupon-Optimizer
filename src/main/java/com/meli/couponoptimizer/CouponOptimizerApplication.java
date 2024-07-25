package com.meli.couponoptimizer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@EnableCaching
@SpringBootApplication
public class CouponOptimizerApplication {

  public static void main(String[] args) {
    SpringApplication.run(CouponOptimizerApplication.class, args);
  }

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
            .info(new Info()
                    .title("Coupon Optimizer API")
                    .version("1.0")
                    .description("Coupon Optimizer API for Mercado Libre")
                    .contact(new io.swagger.v3.oas.models.info.Contact().email("javier.celis@mercadolibre.com.co"))
                    .license(new License().name("Apache 2.0").url("http://springdoc.org"))

            );
  }
}
