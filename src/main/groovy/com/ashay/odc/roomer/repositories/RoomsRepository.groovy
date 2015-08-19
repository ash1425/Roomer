package com.ashay.odc.roomer.repositories

import com.ashay.odc.roomer.domain.Room
import org.springframework.data.mongodb.repository.MongoRepository

public interface RoomsRepository extends MongoRepository<Room, String> {

}