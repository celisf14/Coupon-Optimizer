package com.meli.couponoptimizer.controller.coupon;

import com.meli.couponoptimizer.models.ItemDetailsResponseDTO;
import com.meli.couponoptimizer.models.ItemRequestDTO;
import com.meli.couponoptimizer.models.ItemResponseDTO;
import com.meli.couponoptimizer.services.favorites.ItemFavoritesServiceImpl;
import com.meli.couponoptimizer.services.knapsackSolver.KnapsackSolverService;
import com.meli.couponoptimizer.services.recoverItemData.RecoverItemDataService;
import data.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DisplayName("Integration Test ::: Coupon-Optimizer")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CouponControllerTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Mock
  private RecoverItemDataService recoverItemDataService;

  @Mock
  private ItemFavoritesServiceImpl itemFavoritesServiceImpl;

  @Test
  @DisplayName("Test ::: optimizeCoupon")
  void optimizeCoupon() throws Exception {
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<ItemRequestDTO> request = new HttpEntity<>(Data.getItemRequestDTO(), headers);
    ResponseEntity<ItemResponseDTO> response =
            this.testRestTemplate.exchange("/coupon", HttpMethod.POST, request, ItemResponseDTO.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
  }

  @Test
  @DisplayName("Test ::: getListItemsDetails")
  void getListItemsDetails() throws Exception {
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<List<String>> request = new HttpEntity<>(Data.buildItemsReq(), headers);
    ResponseEntity<ItemDetailsResponseDTO> response =
            this.testRestTemplate.exchange("/coupon/items-details",
                    HttpMethod.POST, request, ItemDetailsResponseDTO.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
  }

  @Test
  @DisplayName("Test ::: stats")
  void stats() throws Exception {
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<Integer> request = new HttpEntity<>(5, headers);
    ResponseEntity<Object> response =
            this.testRestTemplate.exchange("/coupon/stats/5", HttpMethod.GET, request, Object.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
  }

  @Test
  @DisplayName("Test ::: statsTop5")
  void statsTop5() throws Exception {
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<Integer> request = new HttpEntity<>(5, headers);
    ResponseEntity<Object> response =
            this.testRestTemplate.exchange("/coupon/stats", HttpMethod.GET, request, Object.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
  }


  @Test
  @DisplayName("Test ::: optimizeCoupon")
  void optimizeCoupon2() throws Exception {
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<ItemRequestDTO> request = new HttpEntity<>(Data.getItemRequestDTO2(), headers);
    ResponseEntity<ItemResponseDTO> response =
            this.testRestTemplate.exchange("/coupon", HttpMethod.POST, request, ItemResponseDTO.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
  }

  @Test
  @DisplayName("Test ::: optimizeCoupon")
  void optimizeCoupon3() throws Exception {
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<ItemRequestDTO> request = new HttpEntity<>(Data.getItemRequestDTO2(), headers);
    ResponseEntity<ItemResponseDTO> response =
            this.testRestTemplate.exchange("/coupon", HttpMethod.POST, request, ItemResponseDTO.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
  }

}