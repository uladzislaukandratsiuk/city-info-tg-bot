package com.city.info.bot.service;

import com.city.info.bot.model.City;
import com.city.info.bot.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository repository;

    public CityServiceImpl(CityRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<City> getAllCities() {
        return repository.findAll();
    }

    @Override
    public City saveCity(City city) {
        return repository.save(city);
    }

    @Override
    public Optional<City> getCityByName(String cityName) {
        return repository.findByName(cityName);
    }

    @Override
    public void updateCityInfo(String info, String name) {
        repository.updateCityInfoByName(info, name);
    }
}
