package com.meli.couponoptimizer.services.optimizer;

import com.meli.couponoptimizer.exceptions.CouponOptimizerException;
import com.meli.couponoptimizer.models.ItemDetailsDTO;
import com.meli.couponoptimizer.models.ItemDetailsResponseDTO;
import com.meli.couponoptimizer.models.ItemRequestDTO;
import com.meli.couponoptimizer.models.ItemResponseDTO;
import com.meli.couponoptimizer.services.favorites.ItemFavoritesServiceImpl;
import com.meli.couponoptimizer.services.knapsackSolver.KnapsackSolverService;
import com.meli.couponoptimizer.services.knapsackSolver.KnapsackSolverServiceImpl;
import com.meli.couponoptimizer.services.recoverItemData.RecoverItemDataService;
import com.meli.couponoptimizer.util.ConstantsMsjError;
import data.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class OptimizerServicesImplTest {



  @Mock
  private RecoverItemDataService recoverItemDataService;

  @Mock
  private KnapsackSolverService knapsackSolverServiceImpl;

  @Mock
  private ItemFavoritesServiceImpl itemFavoritesServiceImpl;

  @InjectMocks
  private OptimizerServicesImpl optimizerServicesImpl;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }


  @Test
  void optimizeCoupon_InvalidRequest_ThrowsException() {
    // Arrange
    ItemRequestDTO requestDTO = new ItemRequestDTO();
    requestDTO.setItemIds(Arrays.asList());
    requestDTO.setAmount(0.0);

    // Act & Assert
    CouponOptimizerException exception = assertThrows(CouponOptimizerException.class, () -> optimizerServicesImpl.optimizeCoupon(requestDTO));
    assertEquals(ConstantsMsjError.ERR_MSG_GENERIC, exception.getMessage());
  }

}

