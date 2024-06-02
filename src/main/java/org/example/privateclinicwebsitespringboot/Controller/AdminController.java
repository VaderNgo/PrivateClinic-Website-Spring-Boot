package org.example.privateclinicwebsitespringboot.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.privateclinicwebsitespringboot.DTO.AppointmentDTO;
import org.example.privateclinicwebsitespringboot.DTO.DoctorAccountDTO;
import org.example.privateclinicwebsitespringboot.DTO.DoctorDTO;
import org.example.privateclinicwebsitespringboot.Model.Appointment;
import org.example.privateclinicwebsitespringboot.Model.Doctor;
import org.example.privateclinicwebsitespringboot.Model.MyUser;
import org.example.privateclinicwebsitespringboot.Service.AppointmentService;
import org.example.privateclinicwebsitespringboot.Service.DoctorService;
import org.example.privateclinicwebsitespringboot.Service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private MyUserService myUserService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/dashboard")
    public ModelAndView adminDashboard() {
        ModelAndView mav = new ModelAndView();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "dashboard");
            mav.setViewName("admin-dashboard");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");
            mav.addObject("content", "content.html");
        } catch (Exception e) {
            System.out.println(e);
        }
        return mav;
    }

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

            List<Appointment> pendingAppointments = appointmentService.getAppointmentsByStatus("Pending");
            List<Appointment> acceptedAppointments = appointmentService.getAppointmentsByStatus("Accepted");
            List<Appointment> deniedAppointments = appointmentService.getAppointmentsByStatus("Denied");

            session.setAttribute("pendingAppointments", pendingAppointments);
            session.setAttribute("acceptedAppointments", acceptedAppointments);
            session.setAttribute("deniedAppointments", deniedAppointments);

            List<Doctor> doctors = doctorService.getAllDoctors();
            session.setAttribute("doctors", doctors);
        } catch (Exception e) {
            System.out.println(e);
        }
        mav.setViewName("admin-appointment");
        return mav;
    }

    @PostMapping("/appointments/accept")
    public ModelAndView acceptAppointment(@RequestParam("appointmentId") Long appointmentId, @RequestParam("doctorId") Long doctorId, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "appointments");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");
            Doctor doctor = doctorService.getDoctorById(doctorId);
            appointmentService.acceptAppointment(appointmentId, doctor);
        } catch (Exception e) {
            System.out.println(e);
        }
        mav.setViewName("redirect:/admin/appointments");
        return mav;
    }

    @PostMapping("/appointments/deny")
    public ModelAndView rejectAppointment(@RequestParam("appointmentId") Long appointmentId, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "appointments");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");
            appointmentService.denyAppointment(appointmentId);
        } catch (Exception e) {
            System.out.println(e);
        }
        mav.setViewName("redirect:/admin/appointments");
        return mav;
    }

    @GetMapping("/doctors")
    public ModelAndView doctor(@ModelAttribute("DoctorAccountDTO") DoctorAccountDTO doctorAccountDTO, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "doctors");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");
            mav.addObject("doctorAccountDTO", new DoctorAccountDTO());
            List<Doctor> doctors = doctorService.getAllDoctors();
            session.setAttribute("doctors", doctors);
        } catch (Exception e) {
            System.out.println(e);
        }
        mav.setViewName("admin-doctor");
        return mav;
    }

    @PostMapping("/doctors/add")
    public ModelAndView addDoctor(@ModelAttribute("DoctorAccountDTO") DoctorAccountDTO doctorAccountDTO, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "doctors");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");
            mav.addObject("doctorAccountDTO", doctorAccountDTO);
            Doctor doctor = doctorService.createDoctor(doctorAccountDTO);
            myUserService.signUpDoctor(doctorAccountDTO, passwordEncoder, doctor);
        } catch (Exception e) {
            System.out.println(e);
        }
        mav.setViewName("redirect:/admin/doctors");
        return mav;
    }

}
