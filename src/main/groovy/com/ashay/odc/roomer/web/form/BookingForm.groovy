package com.ashay.odc.roomer.web.form

import org.hibernate.validator.constraints.NotEmpty

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class BookingForm {

    @NotEmpty
    String roomName

    @NotEmpty
    String teamName

    @Min(0L)
    @Max(23L)
    int startTimeHours

    @Min(0L)
    @Max(59L)
    int startTimeMins

    @NotNull
    int duration

}
