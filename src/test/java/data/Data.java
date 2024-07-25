package data;

import com.meli.couponoptimizer.models.ItemDetailsDTO;
import com.meli.couponoptimizer.models.ItemRequestDTO;
import com.meli.couponoptimizer.models.ItemResponseDTO;

import java.util.List;
import java.util.Map;

public class Data {

  public static ItemRequestDTO getItemRequestDTO() {
    return ItemRequestDTO.builder()
            .itemIds(buildItemsRes())
            .amount(30000.0)
            .build();
  }

  public static ItemRequestDTO getItemRequestDTO2() {
    return ItemRequestDTO.builder()
            .itemIds(buildItemsRes())
            .amount(50000.0)
            .build();
  }


  public static List<String> buildItemsReq() {
    return List.of("MLC2069516100",
            "MLC2498006388",
            "MLC1476580369");
  }

  public static ItemResponseDTO getItemResponseDTO() {
    return ItemResponseDTO.builder()
            .itemIds(buildItemsRes())
            .amount(25990.0)
            .build();
  }

  public static List<String> buildItemsRes() {
    return List.of("MLC2069516100");
  }

  public static Map<String, ItemDetailsDTO> getListItemsDetails() {
    return Map.of("MLC2069516100", getItemDetailsDTO1(),
            "MLC2498006388", getItemDetailsDTO2(),
            "MLC1476580369", getItemDetailsDTO3());
  }

  public static List<ItemDetailsDTO> getListItemDetailsDTO() {
    return List.of(getItemDetailsDTO1(), getItemDetailsDTO2(), getItemDetailsDTO3());
  }

  public static ItemDetailsDTO getItemDetailsDTO1() {
    return ItemDetailsDTO.builder()
                    .id("MLC2069516100")
                    .title("Crema Hidratante Facial")
                    .price(25990.0)
                    .siteId("MLC")
                    .status("active")
                    .build();
  }

  public static ItemDetailsDTO getItemDetailsDTO2() {
    return ItemDetailsDTO.builder()
                    .id("MLC2498006388")
                    .title("Hervidor Electrico Hervidor")
                    .price(9980.0)
                    .siteId("MLC")
                    .status("active")
                    .build();
  }

  public static ItemDetailsDTO getItemDetailsDTO3() {
    return ItemDetailsDTO.builder()
                    .id("MLC1476580369")
                    .title("Coca-cola Original Botella 3 L")
                    .price(3300.0)
                    .siteId("MLC")
                    .status("paused")
                    .build();
  }


}
