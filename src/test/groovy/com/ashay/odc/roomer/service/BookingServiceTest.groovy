package com.ashay.odc.roomer.service

import com.ashay.odc.roomer.RoomerApplication
import com.ashay.odc.roomer.domain.Room
import com.ashay.odc.roomer.domain.Team
import com.ashay.odc.roomer.repositories.BookingsRepository
import com.ashay.odc.roomer.repositories.RoomsRepository
import com.ashay.odc.roomer.repositories.TeamsRepository
import org.joda.time.DateTime
import org.junit.After
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

    @Autowired
    RoomsRepository roomsRepository

    @Autowired
    TeamsRepository teamsRepository

    @Before
    def void "setup with default booking from now to next 30 mins"() {
        roomsRepository.deleteAll()
        roomsRepository.save(new Room(roomName: "room1"))
        roomsRepository.save(new Room(roomName: "room2"))
        roomsRepository.save(new Room(roomName: "room3"))

        teamsRepository.deleteAll()
        teamsRepository.save(new Team(teamName: "team1"))
        teamsRepository.save(new Team(teamName: "team2"))

        bookingRepository.deleteAll()
        bookingService.book(roomsRepository.findByRoomName("room1"), teamsRepository.findByTeamName("team1"), DateTime.now().withTimeAtStartOfDay().toDate(),30)
    }

    @After
    def void "clear data after test"() {
        bookingRepository.deleteAll()
    }

    @Test(expected = RuntimeException)
    def void "book should not book overlapping slot for same room - after"() {
       bookingService.book(roomsRepository.findByRoomName("room1"), teamsRepository.findByTeamName("team2"), DateTime.now().withTimeAtStartOfDay().plusMinutes(10).toDate(),30)
    }

    @Test
    def void "book should book connected slot for same room - just after"() {
       def booking = bookingService.book(roomsRepository.findByRoomName("room1"), teamsRepository.findByTeamName("team2"), DateTime.now().withTimeAtStartOfDay().plusMinutes(30).toDate(),30)
        assert booking.room.roomName == "room1"
        assert booking.team.teamName == "team2"
    }

    @Test
    def void "book should book connected slot for same room - just before"() {
        def booking = bookingService.book(roomsRepository.findByRoomName("room1"), teamsRepository.findByTeamName("team2"), DateTime.now().withTimeAtStartOfDay().minusMinutes(30).toDate(),30)
        assert booking.room.roomName == "room1"
        assert booking.team.teamName == "team2"
    }

    @Test(expected = RuntimeException)
    def void "book should not book overlapping slot for same room - before"() {
        bookingService.book(roomsRepository.findByRoomName("room1"), teamsRepository.findByTeamName("team2"), DateTime.now().withTimeAtStartOfDay().minusMinutes(10).toDate(),30)
    }

    @Test(expected = RuntimeException)
    def void "book should not book overlapping slot for same room - same"() {
        bookingService.book(roomsRepository.findByRoomName("room1"), teamsRepository.findByTeamName("team2"), DateTime.now().withTimeAtStartOfDay().toDate(),30)
    }

    @Test(expected = RuntimeException)
    def void "book should not book overlapping slot for same room - inclusive slot"() {
        bookingService.book(roomsRepository.findByRoomName("room1"), teamsRepository.findByTeamName("team2"), DateTime.now().withTimeAtStartOfDay().plusMinutes(1).toDate(),15)
    }

    @Test
    def void "book should book overlapping slot for different room"() {
        def savedBooking = bookingService.book(roomsRepository.findByRoomName("room2"), teamsRepository.findByTeamName("team1"), DateTime.now().withTimeAtStartOfDay().plusMinutes(10).toDate(),30)
        assert savedBooking.room.roomName == "room2"
        assert savedBooking.team.teamName == "team1"
    }

    @Test
    def void "book should book available slot for same room"() {
        def savedBooking = bookingService.book(roomsRepository.findByRoomName("room1"), teamsRepository.findByTeamName("team1"), DateTime.now().withTimeAtStartOfDay().plusMinutes(40).toDate(),30)
        assert savedBooking.room.roomName == "room1"
        assert savedBooking.team.teamName == "team1"
    }

    @Test
    def void "cancel should delete given booking"() {
        def savedBooking = bookingService.book(roomsRepository.findByRoomName("room1"), teamsRepository.findByTeamName("team1"), DateTime.now().withTimeAtStartOfDay().plusMinutes(40).toDate(),30)
        bookingService.cancel(savedBooking)
        assert !bookingRepository.findOne(savedBooking.id)
    }

    @Test
    def void "getAllBookings should return all bookings based on parameters"() {
        bookingService.book(roomsRepository.findByRoomName("room3"), teamsRepository.findByTeamName("team1"), DateTime.now().withTimeAtStartOfDay().toDate(),30)
        assert bookingService.getAllBookings().size() == 2
        assert bookingService.getAllBookings().findAll {it.room.roomName == "roomX"}.size() == 0
        assert bookingService.getAllBookings().findAll {it.room.roomName == "room3"}.size() == 1
    }
}