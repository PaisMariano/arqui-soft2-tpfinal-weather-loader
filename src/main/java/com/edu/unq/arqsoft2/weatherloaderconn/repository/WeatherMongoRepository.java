package com.edu.unq.arqsoft2.weatherloaderconn.repository;

import com.edu.unq.arqsoft2.weatherloaderconn.model.WeatherEntity;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

@Repository
public interface WeatherMongoRepository extends MongoRepository<WeatherEntity, UUID> {
    @Aggregation(pipeline = {
            "{ '$match': { 'createdAt': { $gte: ?0 } } }",
            "{ '$group': { _id: null, avgTemp: { $avg: '$temperature' } } }"
    })
    Double findAverageTemperatureSince(Instant from);


    WeatherEntity findTopByOrderByCreatedAtDesc();
}
