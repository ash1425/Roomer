package com.ashay.odc.roomer.repositories.custom

import com.ashay.odc.roomer.domain.Booking
import com.ashay.odc.roomer.domain.Room

public interface BookingsRepositoryCustom {

    public List<Booking> findExistingBookings(Room room, Date startTime, Date endTime)

}