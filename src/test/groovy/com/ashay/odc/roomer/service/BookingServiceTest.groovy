package com.ashay.odc.roomer.service

import com.ashay.odc.roomer.RoomerApplication
import com.ashay.odc.roomer.repositories.BookingsRepository
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration(classes = RoomerApplication)
public class BookingServiceTest {

    @Autowired
    BookingService bookingService

    @Autowired
    BookingsRepository bookingRepository

    @Before
    def void "setup with default booking from now to next 30 mins"() {
       bookingRepository.deleteAll()
       bookingService.book("team1", "room1", DateTime.now().toDate(),30)
    }

    @Test(expected = RuntimeException)
    def void "book should not book overlapping slot for same room"() {
       bookingService.book("team2", "room1", DateTime.now().plusMinutes(10).toDate(),30)
    }

    @Test
    def void "book should book overlapping slot for different room"() {
        def savedBooking = bookingService.book("team1", "room2", DateTime.now().plusMinutes(10).toDate(),30)
        assert savedBooking.roomName == "room2"
        assert savedBooking.teamName == "team1"
    }

    @Test
    def void "book should book available slot for same room"() {
        def savedBooking = bookingService.book("team1", "room1", DateTime.now().plusMinutes(40).toDate(),30)
        assert savedBooking.roomName == "room1"
        assert savedBooking.teamName == "team1"
    }

    @Test
    def void "cancel should delete given booking"() {
        def savedBooking = bookingService.book("team1", "room1", DateTime.now().plusMinutes(40).toDate(),30)
        bookingService.cancel(savedBooking)
        assert !bookingRepository.findOne(savedBooking.id)
    }

    @Test
    def void "getAllBookings should return all bookings based on parameters"() {
        bookingService.book("team1", "room3", DateTime.now().toDate(),30)
        assert bookingService.getAllBookings("").size() == 2
        assert bookingService.getAllBookings("someOtherRoom").size() == 0
        assert bookingService.getAllBookings("room1").size() == 1
    }
}