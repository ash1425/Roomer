package com.ashay.odc.roomer.service

import com.ashay.odc.roomer.domain.Booking
import com.ashay.odc.roomer.domain.Room
import com.ashay.odc.roomer.domain.Team
import com.ashay.odc.roomer.repositories.BookingsRepository
import com.ashay.odc.roomer.repositories.RoomsRepository
import com.ashay.odc.roomer.repositories.TeamsRepository
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class BookingService {

    @Autowired
    private final BookingsRepository bookingRepository

    public Booking book(Room room, Team team, Date startTime, int durationInMins) {

        if (bookingRepository.findByRoomAndEndTimeBetween(room, getStartTime(startTime), getEndTime(startTime, durationInMins)) ||
            bookingRepository.findByRoomAndStartTimeBetween(room, getStartTime(startTime), getEndTime(startTime, durationInMins))) {
            throw new RuntimeException("Booking Already exists!")
        }
        Booking booking = new Booking()
        booking.room = room
        booking.team = team
        booking.startTime = startTime
        booking.endTime = getEndTime(startTime, durationInMins)

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

    private static def getEndTime(Date startTime, int durationInMins) {
        new DateTime(startTime).plusMinutes(--durationInMins).toDate()
    }

    private static def getStartTime(Date startTime) {
        new DateTime(startTime).minusMinutes(1).toDate()
    }
}
