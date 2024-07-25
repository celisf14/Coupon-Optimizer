package com.meli.couponoptimizer.services.favorites;

import com.meli.couponoptimizer.persistence.entity.ItemFavoritesEntity;
import com.meli.couponoptimizer.models.ItemDetailsDTO;
import com.meli.couponoptimizer.persistence.repositories.ItemFavoritesRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ItemFavoritesServiceImpl implements ItemFavoritesService {

  private final ItemFavoritesRepository repository;


  @Async
  @Override
  public void updateFavorites(Map<String, ItemDetailsDTO> listItemsDetails) {

    for (Map.Entry<String, ItemDetailsDTO> entry : listItemsDetails.entrySet()) {
      ItemDetailsDTO itemDetail = entry.getValue();
      ItemFavoritesEntity entity = getItemById(entry.getKey());
      if (entity != null) {
        incrementFavoritesCounter(entity);
      } else {
        saveNewItem(entry.getKey(), itemDetail);
      }
    }
  }

  @Override
  public ItemFavoritesEntity getItemById(String id) {
    return repository.findById(id).orElse(null);
  }

  @Override
  public void incrementFavoritesCounter(ItemFavoritesEntity entity) {
    entity.setFavoritesCounter(entity.getFavoritesCounter() + 1);
    repository.save(entity);
  }

  @Override
  public void saveNewItem(String itemId, ItemDetailsDTO itemDetail) {
    ItemFavoritesEntity newItem = new ItemFavoritesEntity();
    newItem.setId(itemId);
    newItem.setTitle(itemDetail.getTitle());
    newItem.setPrice(itemDetail.getPrice());
    newItem.setSiteId(itemDetail.getSiteId());
    newItem.setStatus(itemDetail.getStatus());
    newItem.setFavoritesCounter(1);
    repository.save(newItem);
  }

  @Override
  public List<ItemFavoritesEntity> getTopFavorites(int topSize) {
    return repository.findTopByFavoritesCounter(PageRequest.of(0, topSize));
  }

}
