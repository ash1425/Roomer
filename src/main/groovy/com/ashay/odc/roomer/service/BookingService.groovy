package com.ashay.odc.roomer.service

import com.ashay.odc.roomer.domain.Booking
import com.ashay.odc.roomer.repositories.BookingsRepository
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class BookingService {

    @Autowired
    private final BookingsRepository bookingRepository

    public Booking book(String teamName, String roomName, Date startTime, int durationInMins) {

        if(! bookingRepository.findByRoomNameAndEndTimeBetween(roomName, startTime, getEndTime(startTime, durationInMins)).isEmpty()) {
           throw new RuntimeException("Booking Already exists!")
        }
        Booking booking = new Booking()
        booking.roomName = roomName
        booking.teamName = teamName
        booking.startTime = startTime
        booking.endTime = getEndTime(startTime, durationInMins)

        bookingRepository.save(booking)
    }

    public void cancel(Booking booking) {
        bookingRepository.delete(booking)
    }

    public List<Booking> getAllBookings(String roomName){
        if(StringUtils.isEmpty(roomName)){
            bookingRepository.findAll()
        }else {
            bookingRepository.findByRoomName(roomName)
        }
    }

    private static def getEndTime(Date startTime, int durationInMins){
        new DateTime(startTime).plusMinutes(durationInMins).toDate()
    }
}
