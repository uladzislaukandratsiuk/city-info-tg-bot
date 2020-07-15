package com.city.info.bot.repository;

import com.city.info.bot.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    Optional<City> findByName(String name);

    @Modifying
    @Query("update City u set u.info = ?1 where u.id = ?2")
    void updateCityInfoByName(String info, String name);
}
