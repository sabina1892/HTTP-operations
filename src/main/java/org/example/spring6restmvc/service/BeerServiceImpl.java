package org.example.spring6restmvc.service;

import lombok.extern.slf4j.Slf4j;
import org.example.spring6restmvc.model.Beer;
import org.example.spring6restmvc.model.BeerStyles;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    private Map<UUID, Beer> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();
        Beer beer1 =Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyles.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyles.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyles.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }


    @Override
    public Beer getBeerById(UUID id) {

        log.debug("Get Beer by Id - in service. Id: " + id.toString());

        return beerMap.get(id);
    }

    @Override
    public List<Beer> listBeers() {
        return new ArrayList<>(beerMap.values());
    }


    @Override
    public Beer savedBeer(Beer beer) {
        Beer saveBeer = Beer.builder()
                .updateDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .price(beer.getPrice())
                .upc(beer.getUpc())
                .beerStyle(beer.getBeerStyle())
                .beerName(beer.getBeerName())
                .version(beer.getVersion())
                .id(UUID.randomUUID())
                .quantityOnHand(beer.getQuantityOnHand()).build();
        beerMap.put(saveBeer.getId(),saveBeer);
        return saveBeer;
    }

    @Override
    public void updatedBeerId(UUID beerId, Beer beer) {
        Beer existing = beerMap.get(beer.getId());
        existing.setBeerName(beer.getBeerName());
        existing.setId(beer.getId());
        existing.setVersion(beer.getVersion());
        existing.setBeerStyle(beer.getBeerStyle());
        existing.setUpc(beer.getUpc());
        existing.setPrice(beer.getPrice());
        existing.setQuantityOnHand(beer.getQuantityOnHand());
        existing.setCreatedDate(LocalDateTime.now());
        existing.setUpdateDate(LocalDateTime.now());
    }

    @Override
    public void deleteById(UUID beerId) {
        beerMap.remove(beerId);
    }

    @Override
    public void patchBeerById(UUID beerId, Beer beer) {
        Beer existing = beerMap.get(beerId);
        if(beer.getBeerStyle()!=null) existing.setBeerStyle(beer.getBeerStyle());
        if(StringUtils.hasText(beer.getUpc())) existing.setUpc(beer.getUpc());
        if(beer.getPrice()!=null) existing.setPrice(beer.getPrice());
        if(StringUtils.hasText(beer.getBeerName())) existing.setBeerName(beer.getBeerName());
        if(beer.getVersion()!=null) existing.setVersion(beer.getVersion());

    }
}

