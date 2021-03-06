package com.ashay.odc.roomer.service

import com.ashay.odc.roomer.domain.Booking
import com.ashay.odc.roomer.domain.Room
import com.ashay.odc.roomer.repositories.BookingsRepository

import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct

@Service
class BookingService {

    @Autowired
    private final BookingsRepository bookingRepository

    public Booking book(String room, String team, Date startTime, int durationInMins) {

        if (bookingRepository.findExistingBookings(room, startTime, getEndDate(startTime, durationInMins))) {
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
        bookingRepository.findByRoom(room.roomName)
    }

    public List<Booking> getAllBookings() {
        bookingRepository.findAll()
    }

    private static def getEndDate(Date startTime, int durationInMins) {
        new DateTime(startTime).plusMinutes(durationInMins).toDate()
    }
}