package com.meli.couponoptimizer.services.recoverItemData;

import com.meli.couponoptimizer.config.CaffeineCacheConfig;
import com.meli.couponoptimizer.external_apis.ItemDetails;
import com.meli.couponoptimizer.models.ItemDetailsDTO;
import com.meli.couponoptimizer.services.logException.LogService;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class RecoverItemDataServiceImpl implements RecoverItemDataService{

  private final CacheManager cacheManager;
  private final ItemDetails itemDetails;
  private final LogService logService;


  @Cacheable(value = CaffeineCacheConfig.ITEMS_CACHE, unless = "#result == null")
  @Override
  public ItemDetailsDTO getDetalleItem(String id) {
    return cacheManager.getCache(CaffeineCacheConfig.ITEMS_CACHE).get(id, () -> itemDetails.getItem(id).block());
  }

  @Async
  @Override
  public CompletableFuture<ItemDetailsDTO> getDetalleItemAsync(String id) {
    return CompletableFuture.supplyAsync(() -> getDetalleItem(id));
  }

  @Override
  public Map<String, ItemDetailsDTO> getListItemsDetailsAsync(List<String> itemIds) {
    Map<String, CompletableFuture<ItemDetailsDTO>> futures = new HashMap<>();
    for (String itemId : itemIds) {
      CompletableFuture<ItemDetailsDTO> itemFuture = getDetalleItemAsync(itemId);
      futures.put(itemId, itemFuture);
    }

    Map<String, ItemDetailsDTO> items = new HashMap<>();
    for (Map.Entry<String, CompletableFuture<ItemDetailsDTO>> entry : futures.entrySet()) {
      try {
        items.put(entry.getKey(), entry.getValue().get());
      } catch (InterruptedException | ExecutionException e) {
        logService.saveLog(e);
      }
    }
    return items;
  }

  @Override
  public Map<String, ItemDetailsDTO> getListItemsDetails(List<String> itemIds) {
    return itemDetails.getListItems(itemIds).block();
  }

}
