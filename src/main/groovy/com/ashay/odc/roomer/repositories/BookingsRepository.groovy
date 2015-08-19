package com.ashay.odc.roomer.repositories

import com.ashay.odc.roomer.domain.Booking
import org.springframework.data.mongodb.repository.MongoRepository

public interface BookingsRepository extends MongoRepository<Booking, String> {

    public List<Booking> findByRoomNameAndEndTimeBetween(String roomName, Date startTime, Date endTime)

    public List<Booking> findByRoomName(String roomName)
}