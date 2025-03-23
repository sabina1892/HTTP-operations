package org.example.spring6restmvc.service;

import org.example.spring6restmvc.model.Beer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    Beer getBeerById (UUID id);
    List<Beer> listBeers ();

    Beer savedBeer(Beer beer);

    void updatedBeerId(UUID beerId, Beer beer);

    void deleteById(UUID beerId);

    void patchBeerById(UUID beerId, Beer beer);
}
