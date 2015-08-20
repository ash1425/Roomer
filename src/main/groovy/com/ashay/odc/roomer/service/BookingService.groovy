package com.ashay.odc.roomer.service

import com.ashay.odc.roomer.domain.Booking
import com.ashay.odc.roomer.domain.Room
import com.ashay.odc.roomer.domain.Team
import com.ashay.odc.roomer.repositories.BookingsRepository
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookingService {

    @Autowired
    private final BookingsRepository bookingRepository

    public Booking book(Room room, Team team, Date startTime, int durationInMins) {

        if (bookingRepository.findByRoomAndEndTimeBetween(room, getLowerBound(startTime), getHigherBound(startTime, durationInMins))
                || bookingRepository.findByRoomAndStartTimeBetween(room, getLowerBound(startTime), getHigherBound(startTime, durationInMins))
                || bookingRepository.findByRoomAndStartTimeAndEndTime(room, startTime, getHigherBound(startTime, durationInMins))) {
            throw new RuntimeException("Booking Already exists!")
        }
        Booking booking = new Booking()
        booking.room = room
        booking.team = team
        booking.startTime = startTime
        booking.endTime = new DateTime(startTime).plusMinutes(durationInMins).toDate()

        bookingRepository.save(booking)
    }

    public void cancel(Booking booking) {
        bookingRepository.delete(booking)
    }

    public List<Booking> getAllBookings(Room room) {
        if (room) {
            bookingRepository.findByRoom(room)
        } else {
            bookingRepository.findAll()
        }
    }

    private static def getHigherBound(Date startTime, int durationInMins) {
        new DateTime(startTime).plusMinutes(durationInMins).toDate()
    }

    private static def getLowerBound(Date startTime) {
        new DateTime(startTime).toDate()
    }
}