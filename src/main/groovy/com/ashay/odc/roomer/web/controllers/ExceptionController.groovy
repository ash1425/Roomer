package com.ashay.odc.roomer.web.controllers

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.ModelAndView

@ControllerAdvice
class ExceptionController {


    @ExceptionHandler(Exception)
    public ModelAndView handleUnknownException(Exception e) {
        ModelAndView error = new ModelAndView("error")
        error.addObject("message", "Sorry, Something went wrong. Please try again.")
    }
}
