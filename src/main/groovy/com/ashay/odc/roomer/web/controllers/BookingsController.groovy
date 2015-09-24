package com.ashay.odc.roomer.web.controllers

import com.ashay.odc.roomer.repositories.RoomsRepository
import com.ashay.odc.roomer.repositories.TeamsRepository
import com.ashay.odc.roomer.service.BookingService
import com.ashay.odc.roomer.web.form.BookingForm
import org.apache.velocity.tools.generic.DateTool
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.view.RedirectView

import javax.validation.Valid

@Controller
class BookingsController {

    @Autowired
    private final BookingService bookingService

    @Autowired
    private final RoomsRepository roomsRepository

    @Autowired
    private final TeamsRepository teamsRepository

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showHome(){
        ModelAndView home = new ModelAndView("home")
        home.addObject("bookings", bookingService.getAllBookings().sort {it.startTime})
        home.addObject("rooms", roomsRepository.findAll().collect { it.roomName })
        home.addObject("teams", teamsRepository.findAll().collect { it.teamName })
        home.addObject("dateTools", DateTool.newInstance())
        home
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public ModelAndView book(@Valid @ModelAttribute BookingForm command, BindingResult result, final RedirectAttributes redirectAttributes){
        if(result.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "Invalid input data!")
        }else {
            try{
                bookingService.book(roomsRepository.findByRoomName(command.roomName), teamsRepository.findByTeamName(command.teamName),
                        DateTime.now().withTimeAtStartOfDay().plusHours(command.startTimeHours).plusMinutes(command.startTimeMins).toDate(),
                        command.duration)
                redirectAttributes.addFlashAttribute("message", "Booking registered!")
            }catch (RuntimeException e) {
                redirectAttributes.addFlashAttribute("message", "Room not available for requested slot!")
            }
        }
        return new ModelAndView(new RedirectView("/", true, true, false))
    }
}
