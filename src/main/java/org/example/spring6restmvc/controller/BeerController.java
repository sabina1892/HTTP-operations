package org.example.spring6restmvc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spring6restmvc.model.Beer;
import org.example.spring6restmvc.service.BeerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
    private final BeerService beerService;
    @RequestMapping(value="{beerId}", method=RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId){
        log.debug("Get Beer by Id 1234- in controller update");
        return beerService.getBeerById(beerId);
    }
    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> getAllBeers(){
        return beerService.listBeers();
    }
    @PostMapping
    public ResponseEntity savedBeer(@RequestBody Beer beer){
        Beer savedBeer = beerService.savedBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/beer/" + savedBeer.getId());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
    @PutMapping({"beerId"})
    public ResponseEntity updateBeerId(@PathVariable UUID beerId, @RequestBody Beer beer){
        beerService.updatedBeerId(beerId,beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("{beerId}")
    public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId){
        beerService.deleteById(beerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PatchMapping({"beerId"})
    public ResponseEntity patchBeer(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer){
        beerService.patchBeerById(beerId,beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
