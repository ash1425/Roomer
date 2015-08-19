package com.ashay.odc.roomer.web.controllers

import com.ashay.odc.roomer.service.BookingService
import org.apache.commons.lang.time.DateFormatUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
class EntryPointController {

    @Autowired
    private final BookingService bookingService

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showHome(){
        ModelAndView home = new ModelAndView("home")
        home.addObject("bookings", bookingService.getAllBookings(""))
        home.addObject("dates", DateFormatUtils)
        home
    }
}
