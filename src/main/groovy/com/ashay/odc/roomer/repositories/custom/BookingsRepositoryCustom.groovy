package com.ashay.odc.roomer.repositories.custom

import com.ashay.odc.roomer.domain.Booking

public interface BookingsRepositoryCustom {

    public List<Booking> findExistingBookings(String room, Date startTime, Date endTime)

}