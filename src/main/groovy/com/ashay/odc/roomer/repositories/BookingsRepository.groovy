package com.ashay.odc.roomer.repositories

import com.ashay.odc.roomer.domain.Booking
import com.ashay.odc.roomer.repositories.custom.BookingsRepositoryCustom
import org.springframework.data.mongodb.repository.MongoRepository

public interface BookingsRepository extends MongoRepository<Booking, String>, BookingsRepositoryCustom {

    public List<Booking> findByRoom(String roomName)

}