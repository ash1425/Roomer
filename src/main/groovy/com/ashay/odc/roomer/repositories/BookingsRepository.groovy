package com.ashay.odc.roomer.repositories

import com.ashay.odc.roomer.domain.Booking
import com.ashay.odc.roomer.domain.Room
import org.springframework.data.mongodb.repository.MongoRepository

public interface BookingsRepository extends MongoRepository<Booking, String> {

    public List<Booking> findByRoomAndEndTimeBetween(Room room, Date startTime, Date endTime)

    public List<Booking> findByRoomAndStartTimeBetween(Room room, Date startTime, Date endTime)

    public List<Booking> findByRoom(String roomName)
}