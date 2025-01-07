package learn.springframwork.spring6restmvc.services;

import learn.springframwork.spring6restmvc.model.Beer;

import java.util.UUID;

public interface BeerService {
    Beer getBeerById(UUID id);
}
