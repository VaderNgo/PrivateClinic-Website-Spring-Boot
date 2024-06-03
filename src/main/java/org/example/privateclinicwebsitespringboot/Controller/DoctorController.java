package org.example.privateclinicwebsitespringboot.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.privateclinicwebsitespringboot.DTO.DisplayAppointmentDTO;
import org.example.privateclinicwebsitespringboot.Model.Appointment;
import org.example.privateclinicwebsitespringboot.Model.Bill;
import org.example.privateclinicwebsitespringboot.Model.Doctor;
import org.example.privateclinicwebsitespringboot.Model.MyUser;
import org.example.privateclinicwebsitespringboot.Service.AppointmentService;
import org.example.privateclinicwebsitespringboot.Service.BillService;
import org.example.privateclinicwebsitespringboot.Service.DoctorService;
import org.example.privateclinicwebsitespringboot.Service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired
    private BillService billService;


    @GetMapping("/dashboard")
    public ModelAndView userDashboard(){
        ModelAndView mav = new ModelAndView();
        try{
            //get current logged user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user",myUser);
            mav.addObject("active","dashboard");
            mav.setViewName("user-dashboard");
            mav.addObject("header","header.html");
            mav.addObject("sidebar","sidebar.html");
            mav.addObject("content","content.html");
        }
        catch(Exception e) {
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

            List<DisplayAppointmentDTO> appointments = appointmentService.getDoctorTodayAppointments(myUser.getDoctor().getId());
            session.setAttribute("appointments", appointments);
        } catch (Exception e) {
            System.out.println(e);
        }
        mav.setViewName("doctor-appointment");
        return mav;
    }

    @GetMapping("/bills")
    public ModelAndView bill(@RequestParam("billId") Long billId, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "bills");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");

            Bill bill = billService.getBillById(billId);
            session.setAttribute("bill", bill);

        } catch (Exception e) {
            System.out.println(e);
        }
        mav.setViewName("doctor-bill");
        return mav;
    }

    @PostMapping("/bills/add")
    public ModelAndView addBill(@RequestParam("appointmentId") Long appointmentId, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "bills");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");

            Appointment appointment = appointmentService.setFinishAppointment(appointmentId);
            Bill bill = billService.createBillForAppointment(appointment);
            Long billId = bill.getId();
            mav.setViewName("redirect:/doctor/bills/?billId=" + billId);
        } catch (Exception e) {
            System.out.println(e);
        }
        return mav;
    }
}
