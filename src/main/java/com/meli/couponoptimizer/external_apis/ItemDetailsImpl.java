package com.meli.couponoptimizer.external_apis;

import com.meli.couponoptimizer.models.ItemDetailsDTO;
import com.meli.couponoptimizer.services.logException.LogService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class ItemDetailsImpl implements ItemDetails {

  private final WebClient webClient;
  private final LogService logService;

  public ItemDetailsImpl(@Qualifier("apiMeliWebClient") WebClient webClient, LogService logService) {
    this.webClient = webClient;
    this.logService = logService;
  }

  @Override
  public Mono<ItemDetailsDTO> getItem(String itemId) {
    return webClient.get()
            .uri("/items/{id}", itemId)
            .retrieve()
            .bodyToMono(ItemDetailsDTO.class);
  }

  @Override
  public Mono<Map<String, ItemDetailsDTO>> getListItems(List<String> itemIds) {
    return Flux.fromIterable(itemIds)
            .flatMap(itemId -> getItem(itemId)
                    .map(itemDetail -> Map.entry(itemId, itemDetail))
                    .onErrorResume(e -> {
                      logService.saveLog(new Exception(e));
                      return Mono.empty();
                    })
            )
            .collectMap(Map.Entry::getKey, Map.Entry::getValue);
  }

}
