package org.example.privateclinicwebsitespringboot.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.privateclinicwebsitespringboot.DTO.DisplayAppointmentDTO;
import org.example.privateclinicwebsitespringboot.Model.Appointment;
import org.example.privateclinicwebsitespringboot.Model.Doctor;
import org.example.privateclinicwebsitespringboot.Model.MyUser;
import org.example.privateclinicwebsitespringboot.Service.AppointmentService;
import org.example.privateclinicwebsitespringboot.Service.DoctorService;
import org.example.privateclinicwebsitespringboot.Service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private MyUserService myUserService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private AppointmentService appointmentService;
    @GetMapping("/appointments")
    public ModelAndView appointment(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "appointments");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");

            List<DisplayAppointmentDTO> appointments = appointmentService.getDoctorTodayAppointments(myUser.getDoctor().getId());
            session.setAttribute("appointments", appointments);
        } catch (Exception e) {
            System.out.println(e);
        }
        mav.setViewName("doctor-appointment");
        return mav;
    }
}
