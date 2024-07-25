package com.meli.couponoptimizer.services.knapsackSolver;

import com.google.ortools.Loader;
import com.meli.couponoptimizer.models.ItemResponseDTO;
import data.Data;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("KnapsackSolver")
class KnapsackSolverServiceImplTest {

  @InjectMocks
  private KnapsackSolverServiceImpl knapsackSolverServiceImpl;

  @BeforeAll
  public static void setup() {
    Loader.loadNativeLibraries();
  }

  @Test
  @DisplayName("Test knapsackSolver")
  void testKnapsackSolver() {
    // Arrange
    // Act
    ItemResponseDTO result = knapsackSolverServiceImpl.knapsackSolver(Data.getListItemsDetails(), 30000);
    // Assert
    assertEquals(29290, result.getAmount());
  }


}