package com.edu.unq.arqsoft2.weatherloaderconn.repository;

import com.edu.unq.arqsoft2.weatherloaderconn.model.WeatherEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WeatherMongoRepository extends MongoRepository<WeatherEntity, UUID> {
}
